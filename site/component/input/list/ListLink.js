import * as React from "react";
import * as PropTypes from "prop-types";
import {Icon, ListItem, ListItemIcon, ListItemSecondaryAction, ListItemText} from "@material-ui/core";
import {withRouter} from "react-router-dom";

class ListLink extends React.PureComponent {
    static propTypes = {
        icon: PropTypes.string,
        text: PropTypes.string.isRequired,
        link: PropTypes.string.isRequired,
        onClick: PropTypes.func
    };

    render() {
        const {icon, text, children} = this.props;
        return <ListItem button onClick={this.onClick}>
            {icon && <ListItemIcon>
                <Icon>{icon}</Icon>
            </ListItemIcon>}
            <ListItemText primary={text}/>
            {children && <ListItemSecondaryAction>
                {children}
            </ListItemSecondaryAction>}
        </ListItem>
    }

    onClick = () => {
        const {history, link, onClick} = this.props;
        history.push(link);
        if (onClick) {
            onClick();
        }
    };
}

export default withRouter(ListLink);