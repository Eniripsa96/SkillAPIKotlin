import * as React from "react";
import * as PropTypes from "prop-types";
import {Icon, ListItem, ListItemIcon, ListItemSecondaryAction, ListItemText} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

class ListButton extends React.PureComponent {
    static propTypes = {
        icon: PropTypes.string,
        text: PropTypes.string.isRequired,
        onClick: PropTypes.func.isRequired,
        context: PropTypes.any,
        selected: PropTypes.bool
    };

    render() {
        const {icon, text, selected, children, active, classes} = this.props;
        return <ListItem button onClick={this.onClick} selected={selected} className={active ? classes.selected : 'nope'}>
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
}

export default withStyles({
    selected: {
        'background-color': '#ffab3c !important'
    }
})(ListButton);