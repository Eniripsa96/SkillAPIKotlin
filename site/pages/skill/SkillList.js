import * as React from "react";
import Grid from "@material-ui/core/Grid";
import {fade} from "@material-ui/core/styles/colorManipulator";
import Icon from "@material-ui/core/Icon";
import {CardContent, Divider, InputBase, withStyles} from "@material-ui/core";
import Card from "@material-ui/core/Card";
import {StorageKey} from "../../data/storage";
import FolderList from "../../component/display/folders/FolderList";
import routes, {resolve} from "../../routes";
import {DEFAULT_SKILL} from "./SkillEditor";
import {skillLoader} from "../../data/loaders";
import FormButton from "../../component/input/FormButton";
import Folders from "../../data/folders";
import List from "@material-ui/core/List";
import Action from "../../component/input/Action";
import ListLink from "../../component/input/list/ListLink";

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
    constructor(props) {
        super(props);
        this.folders = new Folders(StorageKey.SKILL);
        this.state = {selected: this.folders.folders[0]};
    }

    render() {
        const {classes} = this.props;
        const {selected} = this.state;
        return <Grid container spacing={24}>
            <Grid item xs={3}>
                <FolderList
                    folders={this.folders}
                    selected={selected}
                    selectFolder={this.selectFolder}/>
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
                        <List>
                            {selected.items.map(this.renderItem)}
                        </List>
                        <Divider/>
                        <FormButton icon="add" text="New Skill" onClick={this.createSkill}/>
                    </CardContent>
                </Card>
            </Grid>
        </Grid>
    }

    renderItem = (id, index) => {
        return <ListLink
            key={index}
            text={skillLoader.load(id).name}
            link={resolve(routes.SKILL_EDITOR, {id})}>
            <Action
                icon={'clear'}
                tooltip={'Delete'}
                onClick={this.deleteItem}
                context={id}/>
        </ListLink>;
    };

    deleteItem = (id) => {
        this.folders.removeItem(this.state.selected, {id});
        skillLoader.delete({id});
        this.forceUpdate();
    };

    selectFolder = (name) => {
        this.setState({selected: name});
    };

    createSkill = () => {
        const {history} = this.props;
        const {selected} = this.state;

        let id = 1;
        while (skillLoader.load(id)) id++;
        const skill = {...DEFAULT_SKILL, id};

        skillLoader.save(skill);
        this.folders.addItem(selected, skill);

        history.push(resolve(routes.SKILL_EDITOR, {id}));
    };
}

export default withStyles(styles)(SkillList);