import * as React from "react";
import {CardContent, Divider, Typography} from "@material-ui/core";
import Card from "@material-ui/core/Card";
import Folder from "./Folder";
import * as PropTypes from "prop-types";
import List from "@material-ui/core/List";
import ListButton from "../../input/list/ListButton";
import StringDialog from "../../form/dialogs/StringDialog";

class FolderList extends React.Component {
    static propTypes = {
        folders: PropTypes.object.isRequired,
        selected: PropTypes.object.isRequired,
        selectFolder: PropTypes.func.isRequired
    };

    state = {
        dialog: false
    };

    render() {
        const {folders} = this.props;
        const {dialog} = this.state;
        return <Card>
            <CardContent>
                <Typography variant="h5">Folders</Typography>
                <Divider/>
                <List>
                    {folders.folders.map(this.renderFolder)}
                    <Divider/>
                    <ListButton text={"+ New Folder"} onClick={this.showDialog}/>
                </List>

                <StringDialog
                    open={dialog}
                    title="New Folder"
                    name="Folder Name"
                    validate={this.checkName}
                    confirm={this.createFolder}
                    cancel={this.closeDialog}/>
            </CardContent>
        </Card>
    }

    renderFolder = (folder, index) => {
        const {selectFolder, selected, folders} = this.props;
        return <Folder
            key={index}
            folder={folder}
            onClick={selectFolder}
            deleteFolder={this.deleteFolder}
            selected={selected || folders.folders[0]}/>;
    };

    createFolder = (name) => {
        const folder = this.props.folders.create(name);
        this.closeDialog();
        this.props.selectFolder(folder)
    };

    deleteFolder = (folder) => {
        const {folders, selectFolder} = this.props;
        folders.delete(folder.name);
        selectFolder(folders.folders[0]);
    };

    showDialog = () => {
        this.setState({dialog: true});
    };

    closeDialog = () => {
        this.setState({dialog: false});
    };

    checkName = (name) => {
        if (this.props.folders.exists(name)) {
            return 'That name is already taken';
        }
    }
}

export default FolderList