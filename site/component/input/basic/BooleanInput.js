import * as React from "react";
import Dropdown from "./Dropdown";
import * as PropTypes from "prop-types";

const TRUE = "Yes";
const FALSE = "No";
const VALUES = [TRUE, FALSE];
const TRUE_VALUES = ['true', 't', 'yes', 'y', '1'];

class BooleanInput extends React.PureComponent {
    static propTypes = {
        label: PropTypes.string.isRequired,
        onChange: PropTypes.func.isRequired,
        fullWidth: PropTypes.bool,
        value: PropTypes.string,
        context: PropTypes.string
    };

    static defaultProps = {
        value: 'No',
        fullWidth: false
    };

    render() {
        const {value} = this.props;
        return <Dropdown
            {...this.props}
            value={TRUE_VALUES.includes(value.toLowerCase()) ? TRUE : FALSE}
            values={VALUES}
            extraValues={null}/>
    }
}

export default BooleanInput