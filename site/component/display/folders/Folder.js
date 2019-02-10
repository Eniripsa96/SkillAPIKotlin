import * as React from "react";
import ListButton from "../../input/list/ListButton";
import * as PropTypes from "prop-types";
import Action from "../../input/Action";

class Folder extends React.PureComponent {
    static propTypes = {
        folder: PropTypes.object.isRequired,
        selected: PropTypes.object.isRequired,
        onClick: PropTypes.func.isRequired,
        deleteFolder: PropTypes.func.isRequired,
    };

    render() {
        const {folder, selected} = this.props;
        const active = selected && selected.name === folder.name;
        return <ListButton
            text={folder.name}
            active={active}
            onClick={this.onClick}>
            {folder.name !== 'Default' && <Action
                icon="clear"
                onClick={this.delete}
                color={active ? 'default' : 'secondary'}
                tooltip="Delete"/>}
        </ListButton>
    }

    onClick = () => {
        this.props.onClick(this.props.folder);
    };

    delete = () => {
        this.props.deleteFolder(this.props.folder);
    };
}

export default Folder;