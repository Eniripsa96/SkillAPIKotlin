import * as React from "react";
import {CardContent, Dialog, DialogActions, DialogContent, DialogTitle, Divider} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import FormButton from "../../input/FormButton";
import * as PropTypes from "prop-types";
import withStyles from "@material-ui/core/es/styles/withStyles";

const styles = () => ({
    dialog: {
        overflow: 'hidden'
    },
    button: {
        width: 165
    }
});

class DetailedOptionsDialog extends React.PureComponent {
    static propTypes = {
        title: PropTypes.string.isRequired,
        open: PropTypes.bool.isRequired,
        options: PropTypes.array,
        submit: PropTypes.func,
        cancel: PropTypes.func
    };

    state = {
        selected: null,
        prevOptions: null
    };

    static getDerivedStateFromProps(props, state) {
        if (props.options && props.options !== state.prevOptions) {
            return {
                prevOptions: props.options,
                selected: props.options[0]
            };
        }
        return null;
    }

    render() {
        const {title, open, options, classes, size} = this.props;

        if (!open || !options) return null;

        return <Dialog fullWidth maxWidth={size || 'md'} open onClose={this.cancel} className={classes.dialog}>
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <Divider/>
                <div style={{'textAlign': 'center'}}>
                    <div style={{display: 'inline-block'}}>
                        {options.map(this.renderOption)}
                    </div>
                </div>
                <Divider/>
                <CardContent>
                    <Typography>{this.determineText()}</Typography>
                </CardContent>
            </DialogContent>
            <DialogActions>
                <FormButton icon="done" text="Confirm" onClick={this.close}/>
                <FormButton icon="clear" text="Cancel" onClick={this.cancel} color="primary"/>
            </DialogActions>
        </Dialog>
    }

    renderOption = (option) => {
        const {classes} = this.props;
        const {selected} = this.state;
        const isSelected = selected && selected === option;

        return <FormButton
            key={option.name}
            className={classes.button}
            text={option.name}
            onClick={this.select}
            color={isSelected ? 'primary' : 'default'}
            context={option}/>
    };

    determineText() {
        const {selected} = this.state;
        return selected
            ? selected.description
            : 'Select one of the options on the left';
    }

    select = (option) => {
        this.setState({selected: option});
    };

    close = () => {
        const {submit} = this.props;
        if (submit) {
            submit(this.state.selected);
        }
    };

    cancel = () => {
        const {submit, cancel} = this.props;
        const callback = cancel || submit;
        if (callback) {
            callback(this.state.selected);
        }
    };
}

export default withStyles(styles)(DetailedOptionsDialog)