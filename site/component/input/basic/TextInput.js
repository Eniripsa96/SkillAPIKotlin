import * as React from "react";
import TextField from "@material-ui/core/es/TextField/TextField";
import * as PropTypes from "prop-types";

class TextInput extends React.PureComponent {
    static propTypes = {
        label: PropTypes.string.isRequired,
        onChange: PropTypes.func.isRequired,
        value: PropTypes.string,
        blacklistPattern: PropTypes.any,
        fullWidth: PropTypes.bool,
        autoFocus: PropTypes.bool,
        context: PropTypes.any
    };

    static defaultProps = {
        value: '',
        autoFocus: false,
        fullWidth: false
    };

    render() {
        const {fullWidth, label, value, autoFocus, blacklistPattern, context, ...props} = this.props;
        return <TextField
            {...props}
            fullWidth={fullWidth}
            autoFocus={autoFocus}
            label={label}
            value={value}
            onChange={this.onChange}
            margin="normal"/>
    }

    onChange = (e) => {
        const {onChange, context, blacklistPattern} = this.props;
        let value = e.target.value;
        if (blacklistPattern) {
            value = value.replace(blacklistPattern, '');
        }
        onChange(value, context);
    };
}

export default TextInput