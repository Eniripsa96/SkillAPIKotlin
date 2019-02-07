import * as React from "react";
import {CardContent, Divider, Typography} from "@material-ui/core";
import Card from "@material-ui/core/Card";
import Folders from "../../../data/folders";
import Folder from "./Folder";
import * as PropTypes from "prop-types";
import List from "@material-ui/core/List";
import ListButton from "../../input/list/ListButton";
import StringDialog from "../../form/dialogs/StringDialog";

class FolderList extends React.Component {
    static propTypes = {
        type: PropTypes.string.isRequired,
        selected: PropTypes.string.isRequired,
        selectFolder: PropTypes.func.isRequired
    };

    state = {
        dialog: false
    };

    constructor(props) {
        super(props);
        this.folders = new Folders(props.type);
    }

    render() {
        const {dialog} = this.state;
        return <Card>
            <CardContent>
                <Typography variant="h5">Folders</Typography>
                <Divider/>
                <List>
                    {this.folders.names.map(this.renderFolder)}
                    <Divider/>
                    <ListButton text={"+ New Folder"} onClick={this.showDialog}/>
                </List>

                <StringDialog
                    open={dialog}
                    title="New Folder"
                    name="Folder Name"
                    confirm={this.createFolder}
                    cancel={this.closeDialog}/>
            </CardContent>
        </Card>
    }

    renderFolder = (name) => {
        const {selectFolder, selected} = this.props;
        return <Folder key={name} name={name} onClick={selectFolder} selected={selected}/>;
    };

    createFolder = (name) => {
        this.folders.create(name);
        this.closeDialog();
    };

    showDialog = () => {
        this.setState({dialog: true});
    };

    closeDialog = () => {
        this.setState({dialog: false});
    };
}

export default FolderList