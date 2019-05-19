import * as React from "react";
import * as PropTypes from "prop-types";
import List from "@material-ui/core/List";
import ListLink from "../../input/list/ListLink";
import ListExternalLink from "../../input/list/ListExternalLink";
import {Divider} from "@material-ui/core";
import routes from "../../../routes";

class Sidebar extends React.PureComponent {
    static propTypes = {
        close: PropTypes.func.isRequired
    };

    render() {
        const {close} = this.props;
        return <div>
            <List>
                <ListLink icon="home" link={routes.HOME.path} text="Home" onClick={close}/>
                <ListExternalLink icon="info" link="https://github.com/Eniripsa96/SkillAPI/wiki" text="Wiki"/>
                <ListExternalLink icon="contact_support" text="Discord" link="https://discord.gg/KX2ygZJ"/>
                <ListExternalLink icon="code" link="https://github.com/Eniripsa96/SkillAPI" text="Source"/>
            </List>
            <Divider/>
            <List>
                <ListLink text="Skills" link={routes.SKILL_LIST.path} onClick={close}/>
                <ListLink text="Classes" link="/classes" onClick={close}/>
            </List>
        </div>
    }
}

export default Sidebar;