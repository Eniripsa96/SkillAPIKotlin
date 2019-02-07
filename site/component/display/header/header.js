import React from 'react';
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import {Drawer, IconButton, withStyles} from "@material-ui/core";
import * as PropTypes from "prop-types";
import Icon from "@material-ui/core/es/Icon/Icon";
import Tooltip from "@material-ui/core/Tooltip";
import Sidebar from "../sidebar/Sidebar";

const styles = {
    root: {
        flexGrow: 1
    },
    grow: {
        flexGrow: 1
    }
};

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

        return <div>
            <AppBar color="primary" position="static">
                <Toolbar color="primary">
                    <IconButton onClick={this.showSidebar} color="default">
                        <Icon>menu</Icon>
                    </IconButton>
                    <Typography color="primary" variant="h4" className={classes.grow}>
                        SkillAPI
                    </Typography>
                    <Tooltip title="Backup All Data">
                        <IconButton
                            onClick={backup}
                            color="secondary">
                            <Icon>save_alt</Icon>
                        </IconButton>
                    </Tooltip>
                    <Tooltip title="View Editor Settings">
                        <IconButton
                            onClick={showSettings}
                            color="secondary">
                            <Icon>settings</Icon>
                        </IconButton>
                    </Tooltip>
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

export default withStyles(styles)(Header);