import * as React from "react";
import * as PropTypes from "prop-types";
import {Icon, ListItem, ListItemIcon, ListItemText} from "@material-ui/core";

class ListButton extends React.PureComponent {
    static propTypes = {
        icon: PropTypes.string,
        text: PropTypes.string.isRequired,
        onClick: PropTypes.func.isRequired,
        context: PropTypes.any,
        selected: PropTypes.bool
    };

    render() {
        const {icon, text, selected} = this.props;
        return <ListItem button onClick={this.onClick} selected={selected}>
            {icon &&
                <ListItemIcon>
                    <Icon>{icon}</Icon>
                </ListItemIcon>}
            <ListItemText primary={text}/>
        </ListItem>
    }

    onClick = () => {
        this.props.onClick(this.props.context);
    };
}

export default ListButton;