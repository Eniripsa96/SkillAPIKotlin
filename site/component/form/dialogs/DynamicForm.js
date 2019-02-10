import * as React from "react";
import * as PropTypes from "prop-types";
import {Dialog, DialogActions, DialogContent, DialogTitle, Divider, Typography} from "@material-ui/core";
import DynamicInput from "../../input/dynamic/DynamicInput";
import FormButton from "../../input/FormButton";

class DynamicForm extends React.PureComponent {
    static propTypes = {
        optionSettings: PropTypes.array.isRequired,
        data: PropTypes.object.isRequired,
        open: PropTypes.bool.isRequired,
        title: PropTypes.string.isRequired,
        close: PropTypes.func.isRequired,
        onDelete: PropTypes.func,
        context: PropTypes.any,
    };

    render() {
        const {optionSettings, open, title, close, onDelete} = this.props;
        return <Dialog open={open} onClose={close}>
            <DialogTitle>
                {title}
                <Divider/>
            </DialogTitle>
            <DialogContent>
                {optionSettings.length
                    ? optionSettings.map(this.renderOption)
                    : <Typography>There are no configurable settings for this component</Typography>}
            </DialogContent>
            <Divider/>
            <DialogActions>
                <FormButton icon="done" text="Done" onClick={close}/>
                {onDelete && <FormButton icon="clear" text="Delete" onClick={onDelete} color="primary"/>}
            </DialogActions>
        </Dialog>
    }

    renderOption = (settings) => {
        const {data} = this.props;
        return <DynamicInput
            key={settings.key}
            settings={settings}
            data={data}
            onChange={this.onChange}/>
    };

    onChange = (value, key) => {
        const {data, onChange} = this.props;
        onChange({...data, [key]: value});
    };
}

export default DynamicForm