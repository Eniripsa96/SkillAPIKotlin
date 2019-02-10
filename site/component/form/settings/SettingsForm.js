import * as React from "react";
import {Dialog, DialogActions, DialogContent, DialogTitle, InputLabel} from "@material-ui/core";
import * as PropTypes from 'prop-types';
import Dropdown from "../../input/basic/Dropdown";
import FormButton from "../../input/FormButton";
import BooleanInput from "../../input/basic/BooleanInput";

const VERSIONS = ['1.13', '1.12', '1.11', '1.10', '1.9', '1.8'];

class SettingsForm extends React.Component {
    static propTypes = {
        isOpen: PropTypes.bool.isRequired,
        close: PropTypes.func.isRequired,
        settings: PropTypes.object.isRequired
    };

    state = {};

    render() {
        const {version, premium} = this.state;
        const {isOpen, settings} = this.props;
        return <Dialog open={isOpen} onClose={this.confirm} disableRestoreFocus>
            <DialogTitle>Editor Settings</DialogTitle>
            <DialogContent>
                <Dropdown
                    autoFocus
                    label="Server Version"
                    value={version || settings.getVersion()}
                    values={VERSIONS}
                    context="version"
                    onChange={this.onChange}/>
                <BooleanInput
                    fullWidth
                    label="Premium"
                    value={premium || (settings.isPremium() ? 'True' : 'False')}
                    context="premium"
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