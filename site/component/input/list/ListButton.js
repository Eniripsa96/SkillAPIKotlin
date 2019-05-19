import * as React from "react";
import * as PropTypes from "prop-types";
import {Icon, ListItem, ListItemIcon, ListItemSecondaryAction, ListItemText} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";
import {saveLocally} from "../../../data/storage";

class ListButton extends React.PureComponent {
    static propTypes = {
        icon: PropTypes.string,
        text: PropTypes.string.isRequired,
        onClick: PropTypes.func.isRequired,
        context: PropTypes.any,
        selected: PropTypes.bool,
        dragTarget: PropTypes.object,
        onDrop: PropTypes.func
    };

    render() {
        const {icon, text, selected, children, active, classes, dragTarget, onDrop} = this.props;
        return <ListItem
            button
            onClick={this.onClick}
            selected={selected}
            className={active ? classes.selected : 'nope'}
            draggable={!!dragTarget}
            onDragStart={this.onDragStart}
            onDrop={this.onDrop}
            onDragOver={onDrop ? this.allowDrop : null}>

            {icon &&
                <ListItemIcon>
                    <Icon>{icon}</Icon>
                </ListItemIcon>}
            <ListItemText primary={text}/>
            {children && <ListItemSecondaryAction>
                {children}
            </ListItemSecondaryAction>}
        </ListItem>
    }

    onClick = () => {
        this.props.onClick(this.props.context);
    };

    onDragStart = () => {
        dragTarget = this.props.dragTarget
    };

    allowDrop = (e) => {
        e.preventDefault();
    };

    onDrop = () => {
        if (dragTarget) {
            this.props.onDrop(dragTarget);
        }
    }
}

let dragTarget = null;

export default withStyles({
    selected: {
        'background-color': '#ffab3c !important'
    }
})(ListButton);