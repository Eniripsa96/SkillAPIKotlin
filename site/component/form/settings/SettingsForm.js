import * as React from "react";
import Settings from "./Settings";
import {Dialog, DialogActions, DialogContent, DialogTitle, InputLabel} from "@material-ui/core";
import * as PropTypes from 'prop-types';
import Dropdown from "../../input/Dropdown";
import FormButton from "../../input/FormButton";

const VERSIONS = ['1.13', '1.12', '1.11', '1.10', '1.9', '1.8'];

class SettingsForm extends React.Component {
    static propTypes = {
        isOpen: PropTypes.bool.isRequired,
        close: PropTypes.func.isRequired,
        settings: PropTypes.instanceOf(Settings).isRequired
    };

    state = {};

    render() {
        const {version} = this.state;
        const {isOpen, settings} = this.props;
        return <Dialog open={isOpen} onClose={this.confirm} disableRestoreFocus>
            <DialogTitle>Editor Settings</DialogTitle>
            <DialogContent>
                <InputLabel htmlFor="version">Server Version</InputLabel>
                <Dropdown
                    value={version || settings.getVersion()}
                    values={VERSIONS}
                    context="version"
                    onChange={this.onChange}/>
            </DialogContent>
            <DialogActions>
                <FormButton icon="done" text="Done" onClick={this.confirm}/>
            </DialogActions>
        </Dialog>
    }

    onChange = (value, key) => {
        this.setState({[key]: value});
    };

    confirm = () => {
        this.props.settings.applyChanges(this.state);
        this.props.close();
    }
}

export default SettingsForm;