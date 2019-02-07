import * as React from "react";
import * as PropTypes from "prop-types";
import {Dialog, DialogActions, DialogContent, DialogTitle} from "@material-ui/core";
import FormButton from "../../input/FormButton";
import TextInput from "../../input/basic/TextInput";

class StringDialog extends React.Component {
    static propTypes = {
        open: PropTypes.bool.isRequired,
        title: PropTypes.string.isRequired,
        name: PropTypes.string.isRequired,
        confirm: PropTypes.func.isRequired,
        cancel: PropTypes.func.isRequired
    };

    state = {
        value: ''
    };

    render() {
        const {open, title, name, cancel} = this.props;
        const {value} = this.state;

        return <Dialog open={open} onClose={cancel || this.submit} disableRestoreFocus>
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <TextInput
                    fullWidth
                    autoFocus
                    label={name}
                    value={value}
                    onChange={this.onChange}/>
            </DialogContent>
            <DialogActions>
                <FormButton icon="done" text="Confirm" onClick={this.confirm}/>
                <FormButton icon="clear" text="Cancel" onClick={cancel} color="primary"/>
            </DialogActions>
        </Dialog>
    }

    onChange = (value) => {
        this.setState({value});
    };

    confirm = () => {
        this.props.confirm(this.state.value);
    };
}

export default StringDialog