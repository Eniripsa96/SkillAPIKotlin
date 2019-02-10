import React from 'react';
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import {Drawer, IconButton, withStyles} from "@material-ui/core";
import * as PropTypes from "prop-types";
import Icon from "@material-ui/core/es/Icon/Icon";
import Tooltip from "@material-ui/core/Tooltip";
import Sidebar from "../sidebar/Sidebar";
import Action from "../../input/Action";

const style = theme => ({
    root: {
        flexGrow: 1,
        display: 'flex'
    },
    grow: {
        flexGrow: 1
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1
    }
});

class Header extends React.PureComponent {
    static propTypes = {
        backup: PropTypes.func.isRequired,
        showSettings: PropTypes.func.isRequired,
        classes: PropTypes.object.isRequired
    };

    state = {
        showSidebar: false
    };

    render() {
        const {backup, showSettings, classes} = this.props;
        const {showSidebar} = this.state;

        return <div className={classes.root}>
            <AppBar color="primary" position="static" className={classes.appBar}>
                <Toolbar color="primary">
                    <Action icon="menu" tooltip="Open menu" onClick={this.showSidebar}/>
                    <Typography color="primary" variant="h4" className={classes.grow}>
                        SkillAPI
                    </Typography>
                    <Action icon="save_alt" tooltip="Backup All Data" onClick={backup}/>
                    <Action icon="settings" tooltip="View Editor Settings" onClick={showSettings}/>
                </Toolbar>
            </AppBar>
            <Drawer open={showSidebar} onClose={this.hideSidebar}>
                <Sidebar close={this.hideSidebar}/>
            </Drawer>
        </div>
    }

    showSidebar = () => {
        this.setState({showSidebar: true});
    };

    hideSidebar = () => {
        this.setState({showSidebar: false});
    };
}

export default withStyles(style)(Header);