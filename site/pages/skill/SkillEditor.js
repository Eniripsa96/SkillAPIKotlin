import * as React from "react";
import {Card, CardContent, Divider, Drawer, Typography, withStyles} from "@material-ui/core";
import TextInput from "../../component/input/basic/TextInput";
import NumberInput from "../../component/input/basic/NumberInput";
import BooleanInput from "../../component/input/basic/BooleanInput";
import FormButton from "../../component/input/FormButton";
import DetailedOptionsDialog from "../../component/form/dialogs/DetailedOptionsDialog";
import SkillComponent, {Type} from "./SkillComponent";
import List from "@material-ui/core/List";
import {getComponentOptions} from "./components";
import routes from "../../routes";
import {skillLoader} from "../../data/loaders";

const DEFAULT_SKILL = {
    name: 'New Skill',
    maxLevel: 5,
    autoUnlock: 'Yes',
    requirements: [],
    triggers: []
};

const Form = {
    REQUIREMENT: 'requirement'
};

const drawerWidth = 250;

const styles = {
    header: {
        marginTop: '24px'
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0
    },
    drawerCard: {
        width: drawerWidth,
        'margin-top': 65
    },
    effects: {
        flexGrow: 1,
        'margin-left': drawerWidth
    }
};

class SkillEditor extends React.Component {
    state = {
        dialogData: null,
        dialogCallback: null,
        original: null,
        form: null,
        selected: null,
        skill: {}
    };

    componentDidMount() {
        document.addEventListener('mouseup', this.onMouseUp);
        window.onbeforeunload = this.onUnload;
        this.reload();
    }

    componentWillUnmount() {
        document.removeEventListener('mouseup', this.onMouseUp);
        window.onbeforeunload = null;
        this.save();
    }

    reload() {
        const id = this.props.match.params.id;
        const skill = skillLoader.load(id);
        if (!skill) {
            this.props.history.push(routes.SKILL_LIST.path);
        }

        this.setState({
            skill: {...DEFAULT_SKILL, ...skill}
        });

    }

    render() {
        const {classes} = this.props;
        const {skill, dialogData, dialogCallback} = this.state;
        const {name, maxLevel, autoUnlock} = skill;
        return <div>
            <Drawer variant="permanent" anchor="left" className={classes.drawer}>
                <Card className={classes.drawerCard}>
                    <CardContent>
                        <Typography variant="h5">Skill Details</Typography>
                        <Divider/>

                        <TextInput
                            fullWidth
                            label="Name"
                            value={name}
                            onChange={this.onChange}
                            context="name"
                            autoFocus/>

                        <NumberInput
                            fullWidth
                            label="Max Level"
                            value={maxLevel}
                            onChange={this.onChange}
                            context={"maxLevel"}
                            integer/>

                        <BooleanInput
                            fullWidth
                            label="Auto Unlock"
                            value={autoUnlock}
                            onChange={this.onChange}
                            context="autoUnlock"/>

                        <Typography variant="h5" className={classes.header}>Requirements</Typography>
                        <Divider/>
                        {/* TODO - add requirement list display */}
                        <FormButton icon="add" text="Add Requirement" onClick={this.showRequirementForm}/>

                    </CardContent>
                </Card>
            </Drawer>
            <main className={classes.effects}>
                <Typography variant="h4">Effects</Typography>
                <Divider/>
                <List>
                    {skill.triggers && skill.triggers.map(this.renderComponent)}
                </List>
                <FormButton icon="add" text="Add Trigger" onClick={this.openTriggerDialog}/>
            </main>
            <DetailedOptionsDialog
                title="Select a Trigger"
                size="sm"
                open={!!(dialogData && dialogCallback)}
                options={dialogData}
                submit={dialogCallback}
                cancel={this.closeDialog}/>
        </div>
    }

    renderComponent = (component, index) => {
        const {selectedComponent} = this.state;
        return <SkillComponent
            key={component.id}
            data={component}
            index={index}
            select={this.selectComponent}
            selected={selectedComponent}
            move={this.moveComponent}
            update={this.updateComponent}
            generateId={this.generateId}/>
    };

    onChange = (value, key) => {
        this.setState(({skill}) => ({skill: {...skill, [key]: value}}));
    };

    closeDialog = () => {
        this.setState({dialogData: null, dialogCallback: null});
    };

    showRequirementForm = () => {
        this.setState({form: Form.REQUIREMENT});
    };

    openTriggerDialog = () => {
        this.setState({
            dialogData: getComponentOptions(Type.TRIGGER),
            dialogCallback: this.confirmTrigger
        });
    };

    confirmTrigger = (trigger) => {
        this.setState({dialogData: null, dialogCallback: null});
        if (!trigger) return;

        const newTrigger = trigger.instance(this.generateId());
        this.setState(({skill}) => ({skill: {...skill, triggers: [...skill.triggers, newTrigger]}}));
    };

    generateId = () => {
        const {skill} = this.state;
        const ids = [];
        skill.triggers.forEach(trigger => this.appendId(trigger, ids));
        let i = 1;
        while (ids.includes(i)) {
            i++
        }
        return i;
    };

    appendId(component, ids) {
        ids.push(component.id);
        component.children.forEach(child => this.appendId(child, ids));
    }

    selectComponent = (id) => {
        this.setState({selectedComponent: id});
    };

    updateComponent = (data, index) => {
        const {skill} = this.state;
        const triggers = [...skill.triggers];
        if (data) {
            triggers.splice(index, 1, data);
        } else {
            triggers.splice(index, 1);
        }
        this.setState({skill: {...skill, triggers}})
    };

    moveComponent = (fromIndex, toIndex) => {
        const {skill} = this.state;
        if (toIndex < 0 || toIndex >= skill.triggers.length) return;

        const triggers = [...skill.triggers];
        const moved = triggers.splice(fromIndex, 1)[0];
        triggers.splice(toIndex, 0, moved);
        this.setState({skill: {...skill, triggers}})
    };

    onMouseUp = () => {
        if (this.state.selectedComponent !== null) {
            this.setState({selectedComponent: null});
        }
    };

    onUnload = () => {
        this.save();
    };

    save() {
        skillLoader.save(this.state.skill);
    }
}

export {DEFAULT_SKILL}
export default withStyles(styles)(SkillEditor)