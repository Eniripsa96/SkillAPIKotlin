import * as React from "react";
import TextInput from "./TextInput";
import * as PropTypes from "prop-types";

const INT_PATTERN = /[^0-9-]+/g;
const DECIMAL_PATTERN = /[^0-9.-]+/g;
const NEGATIVE_PATTERN = /-/g;
const PERIOD_PATTERN = /\\./g;

class NumberInput extends React.PureComponent {
    static propTypes = {
        label: PropTypes.string.isRequired,
        onChange: PropTypes.func.isRequired,
        value: PropTypes.number,
        integer: PropTypes.bool,
        fullWidth: PropTypes.bool,
        autoFocus: PropTypes.bool,
        context: PropTypes.any
    };

    static defaultProps = {
        value: 0
    };

    render() {
        const {integer, value, ...props} = this.props;
        return <TextInput
            {...props}
            value={value.toString()}
            blacklistPattern={integer ? INT_PATTERN : DECIMAL_PATTERN}
            onChange={this.onChange}/>
    }

    onChange = (value, context) => {

        // Remove unnecessary 0's
        for (let i = 0; i < value.length - 1; i++) {
            const c = value.charAt(i);
            if (value.charAt(i) === '0') {
                value = value.replace('0', '');
                i--;
            } else if (c !== '-') {
                break;
            }
        }

        // Remove extra negative signs
        if (value.lastIndexOf('-') > 0) {
            const negative = value.charAt(0) !== '-';
            value = value.replace(NEGATIVE_PATTERN, "");
            if (negative) {
                value = '-' + value;
            }
        }

        // Remove extra negative signs
        if (!this.props.integer) {
            const first = value.indexOf('-');
            const last = value.lastIndexOf('-');
            if (first !== last) {
                value = value.replace(PERIOD_PATTERN, "");
                value = value.substr(0, first) + '.' + value.substr(first);
            }
        }

        // Prevent it from being empty
        if (value.length === 0 || (value.length === 1 && value.charAt(0) === '-')) {
            value += '0';
        }

        this.props.onChange(
            this.props.integer ? Number.parseInt(value) : Number.parseFloat(value),
            context);
    }
}

export default NumberInput