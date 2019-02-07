import * as React from "react";
import Grid from "@material-ui/core/Grid";
import {fade} from "@material-ui/core/styles/colorManipulator";
import Icon from "@material-ui/core/Icon";
import {CardContent, InputBase, withStyles} from "@material-ui/core";
import Card from "@material-ui/core/Card";
import {DEFAULT_FOLDER} from "../../data/folders";
import {StorageKey} from "../../data/storage";
import FolderList from "../../component/display/folders/FolderList";
import FormLink from "../../component/input/FormLink";
import routes from "../../routes";

const styles = theme => ({
    search: {
        position: 'relative',
        borderRadius: theme.shape.borderRadius,
        backgroundColor: fade(theme.palette.common.white, 0.25),
        '&:hover': {
            backgroundColor: fade(theme.palette.common.white, 0.35)
        },
        width: '100%'
    },
    searchIcon: {
        width: theme.spacing.unit * 9,
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center'
    },
    inputRoot: {
        color: 'inherit',
        width: '100%'
    },
    inputInput: {
        paddingTop: theme.spacing.unit,
        paddingRight: theme.spacing.unit,
        paddingBottom: theme.spacing.unit,
        paddingLeft: theme.spacing.unit * 10,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('md')]: {
            width: 200
        }
    }
});

class SkillList extends React.Component {
    state = {
        selected: DEFAULT_FOLDER
    };

    render() {
        const {classes} = this.props;
        const {selected} = this.state;
        return <Grid container spacing={24}>
            <Grid item xs={3}>
                <FolderList type={StorageKey.SKILL} selected={selected} selectFolder={this.selectFolder}/>
            </Grid>
            <Grid item xs={9}>
                <Card>
                    <CardContent>
                        <div className={classes.search}>
                            <div className={classes.searchIcon}>
                                <Icon>search</Icon>
                            </div>
                            <InputBase
                                placeholder="Search..."
                                classes={{
                                    root: classes.inputRoot,
                                    input: classes.inputInput
                                }}/>
                        </div>
                        <FormLink icon="add" text="New Skill" link={routes.NEW_SKILL.path}/>
                    </CardContent>
                </Card>
            </Grid>
        </Grid>
    }

    selectFolder = (name) => {
        this.setState({selected: name});
    };
}

export default withStyles(styles)(SkillList);