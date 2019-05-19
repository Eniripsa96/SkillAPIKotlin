import * as React from "react";
import PropTypes from 'prop-types';
import {Collapse, Divider, List} from "@material-ui/core";
import Action from "../../component/input/Action";
import {Type} from "./commonOptions";
import DetailedOptionsDialog from "../../component/form/dialogs/DetailedOptionsDialog";
import {getComponentDetails, getComponentOptions} from "./components";
import withStyles from "@material-ui/core/es/styles/withStyles";
import ListButton from "../../component/input/list/ListButton";
import DynamicForm from "../../component/form/dialogs/DynamicForm";
import {loadLocally, saveLocally} from "../../data/storage";

const ICONS = {
    [Type.MECHANIC]: 'code',
    [Type.CONDITION]: 'filter_list',
    [Type.TARGET]: 'select_all',
    [Type.TRIGGER]: 'offline_bolt'
};

const TYPE_OPTIONS = [
    {name: 'Condition', description: 'A requirement for effects to happen on or from the current target(s)'},
    {
        name: 'Mechanic',
        description: 'Applies the actual skill effects to the currently selected target(s) or its location'
    },
    {name: 'Target', description: 'Adjusts what you are going to be affecting with the skill'}
];

class SkillComponent extends React.PureComponent {
    static propTypes = {
        data: PropTypes.object.isRequired,
        update: PropTypes.func.isRequired,
        move: PropTypes.func.isRequired,
        index: PropTypes.number.isRequired,
        lift: PropTypes.func,
        lower: PropTypes.func,
        selected: PropTypes.number
    };

    state = {
        expanded: true,
        typeDialog: false,
        editSettings: false,
        componentOptions: null,
        lastSelected: 0
    };

    componentDidMount() {
        document.addEventListener('keyup', this.onKeyUp);
    }

    componentWillUnmount() {
        document.removeEventListener('keyup', this.onKeyUp);
    }

    render() {
        const {data, selected} = this.props;
        const {expanded, typeDialog, componentOptions, editSettings} = this.state;
        const {name, type, children} = data;

        let error = null;
        if (!name) error = 'Invalid - missing name';
        if (!type) error = 'Invalid - missing type';
        if (!getComponentDetails(type, name)) error = 'Invalid - unknown component';

        const active = data && selected === data.id;

        if (error) {
            return <div>
                <ListButton icon="warning" text={error} onClick={this.select} active={active}/>
                <Divider/>
            </div>
        }

        const canHaveChildren = this.canHaveChildren(data);

        return <div>
            <ListButton
                icon={ICONS[type]}
                text={name}
                onClick={this.select}
                active={active}
                dragTarget={type !== Type.TRIGGER && this}
                onDrop={canHaveChildren && this.onDrop}>

                {canHaveChildren && <Action icon="add" tooltip="Add a new effect" onClick={this.showTypeDialog}
                                            color={active ? 'default' : 'secondary'}/>}
                <Action icon="edit" tooltip={`Edit ${name} settings`} onClick={this.showSettingsDialog}
                        color={active ? 'default' : 'secondary'}/>
                <Action
                    disabled={!children.length}
                    icon={expanded ? 'expand_less' : 'expand_more'}
                    tooltip={expanded ? 'Hide children' : 'Show children'}
                    onClick={this.toggleExpanded}/>
            </ListButton>
            <Divider/>
            {canHaveChildren && this.renderChildren()}
            {typeDialog && <DetailedOptionsDialog
                open
                size="xs"
                title="Which type of component?"
                submit={this.confirmType}
                cancel={this.closeDialog}
                options={TYPE_OPTIONS}/>}
            {componentOptions && <DetailedOptionsDialog
                open
                title="Select a component"
                submit={this.addChild}
                cancel={this.closeDialog}
                options={componentOptions}/>}
            {editSettings && <DynamicForm
                optionSettings={this.getSettings(data)}
                data={data.metadata}
                open
                title={`${data.name} Settings`}
                close={this.closeSettingsDialog}
                onChange={this.updateMetadata}
                onDelete={this.onDelete}/>}
        </div>
    }

    renderChildren() {
        const {data, classes} = this.props;
        const {expanded} = this.state;
        const {children} = data;
        return <div>
            <Collapse in={expanded && !!children.length} className={classes.nested}>
                <List>
                    {children.map((child, index) => <SkillComponent
                        {...this.props}
                        data={child}
                        key={child.id}
                        index={index}
                        update={this.update}
                        move={this.move}
                        lift={this.lift}
                        lower={this.lower}/>)}
                </List>
            </Collapse>
            <Divider/>
        </div>
    }

    closeDialog = () => {
        this.setState({typeDialog: false, componentOptions: null});
    };

    select = () => {
        const {data, select} = this.props;
        const {lastSelected} = this.state;
        const time = new Date().getTime();

        if (lastSelected > time - 500) {
            this.showSettingsDialog();
        } else {
            this.setState({ lastSelected: time });
            select(data.id);
        }
    };

    showTypeDialog = () => {
        this.setState({typeDialog: true});
    };

    confirmType = (decision) => {
        if (!decision) {
            this.setState({typeDialog: false});
        } else {
            const type = decision.name.toLowerCase();
            this.setState({
                typeDialog: false,
                componentOptions: getComponentOptions(type)
            });
        }
    };

    addChild = (component) => {
        const {data, index, generateId, update} = this.props;
        const children = [...data.children, component.instance(generateId())];
        this.setState({
            componentOptions: null
        });
        update({...data, children}, index);
    };

    updateMetadata = (metadata) => {
        const {data, index, update} = this.props;
        update({...data, metadata}, index);
    };

    showSettingsDialog = () => {
        this.setState({editSettings: true});
    };

    closeSettingsDialog = () => {
        this.setState({editSettings: false});
    };

    toggleExpanded = () => {
        this.setState(({expanded}) => ({expanded: !expanded}));
    };

    update = (newChildState, childIndex) => {
        const {update, index, data} = this.props;
        const children = [...data.children];
        if (newChildState) {
            children.splice(childIndex, 1, newChildState);
        } else {
            children.splice(childIndex, 1);
        }

        const newState = {...data, children: children};
        update(newState, index);
    };

    move = (indexFrom, indexTo) => {
        const {data, index, update} = this.props;
        if (indexTo < 0 || indexTo >= data.children.length) return;

        const children = [...data.children];
        const moving = children.splice(indexFrom, 1)[0];
        children.splice(indexTo, 0, moving);
        update({...data, children}, index);
    };

    lift = (childIndex, childState, lifted) => {
        const {data, index, update, lift} = this.props;
        const children = [...data.children];
        if (childState && lifted) {
            children.splice(childIndex, 1, childState, lifted);
            update({...data, children}, index);
        } else if (lift) {
            const lifted = children.splice(childIndex, 1)[0];
            lift(index, {...data, children}, lifted);
        }
    };

    lower = (childIndex) => {
        const {data, index, update} = this.props;
        const targetIndex = this.findParentForLower(childIndex);
        if (targetIndex === -1) return;

        const children = [...data.children];
        const parent = children[targetIndex];
        const lowered = children[childIndex];
        const parentChildren = [...parent.children, lowered];
        const newParent = {...parent, children: parentChildren};
        children.splice(Math.min(childIndex, targetIndex), 2, newParent);

        update({...data, children}, index);
    };

    onDelete = () => {
        if (confirm('Are you sure you want to delete this component and all its children?')) {
            const {update, index} = this.props;
            update(null, index);
        }
    };

    findParentForLower(childIndex) {
        const {data} = this.props;
        const {children} = data;

        const limit = Math.max(childIndex, children.length - childIndex - 1);
        for (let i = 1; i <= limit; i++) {
            if (this.canHaveChildren(children[childIndex - i])) {
                return childIndex - i;
            }
            if (this.canHaveChildren(children[childIndex + i])) {
                return childIndex + i;
            }
        }
        return -1;
    }

    getSettings(data) {
        return data && getComponentDetails(data.type, data.name).metadata;
    }

    canHaveChildren(data) {
        return data && getComponentDetails(data.type, data.name).children;
    }

    onKeyUp = (e) => {
        const {selected, data, index, lift, lower, move, update, generateId} = this.props;
        if (selected !== data.id) return;

        switch (e.key) {
            case 'Delete':
                update(null, index);
                break;
            case 'ArrowUp':
                move(index, index - 1);
                break;
            case 'ArrowDown':
                move(index, index + 1);
                break;
            case 'ArrowLeft':
                lift && lift(index);
                break;
            case 'ArrowRight':
                lower && lower(index);
                break;
            case 'c':
                if (e.ctrlKey) {
                    saveLocally('copy', data);
                }
                break;
            case 'v':
                const copied = loadLocally('copy');
                if (!copied || copied.type === Type.TRIGGER) return;
                const instance = {...copied, id: generateId()};
                if (this.canHaveChildren(data)) {
                    const children = [...data.children, instance];
                    update({...data, children}, index);
                } else if (lift) {
                    lift(index, data, instance);
                }
                break;
        }
    };

    /**
     * @param {SkillComponent} dropped
     */
    onDrop = (dropped) => {
        if (this.isParent(dropped)) {
            return
        }

        // Remove from where the component was
        dropped.props.update(null, dropped.props.index);

        setTimeout(() => {
            const {data, index, generateId, update} = this.props;

            const childData = { ...dropped.props.data, id: generateId() };

            // Add to the current component's children
            const children = [...data.children, childData];

            update({...data, children}, index);
            this.props.select(childData.id);
        }, 100);
    };

    isChild(component) {
        return this.isRelated(component.props.data.id, this.props.data);
    }

    isParent(component) {
        return this.isRelated(this.props.data.id, component.props.data);
    }

    isRelated(id, rootData) {
        let toCheck = [rootData];
        let index = 0;
        while (index < toCheck.length) {
            const current = toCheck[index];
            if (current.id === id) {
                return true;
            }
            current.children.forEach((child) => toCheck.push(child));
            index++
        }
        return false
    }
}

export {Type}
export default withStyles((theme) => ({
    nested: {
        'padding-left': theme.spacing.unit * 4
    }
}))(SkillComponent)