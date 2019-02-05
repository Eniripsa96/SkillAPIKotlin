import './Header.scss'
import React from 'react';
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import {IconButton, withStyles} from "@material-ui/core";
import * as PropTypes from "prop-types";
import Icon from "@material-ui/core/es/Icon/Icon";
import {Link} from "react-router-dom";
import Tooltip from "@material-ui/core/Tooltip";

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

    render() {
        const {backup, showSettings, classes} = this.props;

        return <AppBar color="primary" position="static">
            <Toolbar color="primary">
                <Typography color="primary" variant="h3" className={classes.grow}>
                    <a href="https://github.com/Eniripsa96/SkillAPI/wiki" target="_blank">SkillAPI</a>
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
    }
}

export default withStyles(styles)(Header);