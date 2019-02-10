import * as React from "react";
import * as PropTypes from "prop-types";
import {Dialog, DialogActions, DialogContent, DialogTitle, Divider} from "@material-ui/core";
import FormButton from "../../input/FormButton";
import TextInput from "../../input/basic/TextInput";

class StringDialog extends React.PureComponent {
    static propTypes = {
        open: PropTypes.bool.isRequired,
        title: PropTypes.string.isRequired,
        name: PropTypes.string.isRequired,
        confirm: PropTypes.func.isRequired,
        cancel: PropTypes.func.isRequired,
        validate: PropTypes.func,
    };

    state = {
        value: '',
        error: null,
        valid: false
    };

    render() {
        const {open, title, name, cancel} = this.props;
        const {value, valid, error} = this.state;

        return <Dialog open={open} onClose={cancel || this.submit} disableRestoreFocus>
            <DialogTitle>
                {title}
                <Divider/>
            </DialogTitle>
            <DialogContent>
                <TextInput
                    error={!!error}
                    helperText={error}
                    fullWidth
                    autoFocus
                    label={name}
                    value={value}
                    onChange={this.onChange}/>
            </DialogContent>
            <Divider/>
            <DialogActions>
                <FormButton icon="done" text="Confirm" onClick={this.confirm} disabled={!valid}/>
                <FormButton icon="clear" text="Cancel" onClick={cancel} color="primary"/>
            </DialogActions>
        </Dialog>
    }

    onChange = (value) => {
        const {validate} = this.props;

        const trimmed = value.trim();
        let error = null, valid = true;
        if (!trimmed.length) {
            valid = false;
        } else if (validate) {
            error = validate(trimmed);
            valid = !error;
        }
        this.setState({value: trimmed, valid, error});
    };

    confirm = () => {
        this.props.confirm(this.state.value);
    };
}

export default StringDialog