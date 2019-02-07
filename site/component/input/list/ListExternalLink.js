import * as React from "react";
import * as PropTypes from "prop-types";
import {Icon, ListItem, ListItemIcon, ListItemText} from "@material-ui/core";

class ListExternalLink extends React.PureComponent {
    static propTypes = {
        icon: PropTypes.string.isRequired,
        text: PropTypes.string.isRequired,
        link: PropTypes.string.isRequired
    };

    render() {
        const {icon, text, link} = this.props;
        return <a href={link}>
            <ListItem button>
                <ListItemIcon>
                    <Icon>{icon}</Icon>
                </ListItemIcon>
                <ListItemText primary={text}/>
            </ListItem>
        </a>
    }
}

export default ListExternalLink;