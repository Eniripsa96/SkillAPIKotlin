import * as React from "react";
import * as PropTypes from "prop-types";
import {MenuItem, Select} from "@material-ui/core";

class Dropdown extends React.PureComponent {
    static propTypes = {
        value: PropTypes.string.isRequired,
        values: PropTypes.array.isRequired,
        context: PropTypes.string.isRequired,
        onChange: PropTypes.func.isRequired,

        extraValues: PropTypes.array
    };

    render() {
        const {value, values, extraValues, context} = this.props;
        return <Select
            fullWidth
            value={value}
            onChange={this.onChange}
            inputProps={{id: {context}}}>
            {extraValues && this.renderValues(extraValues)}
            {this.renderValues(values)}
        </Select>
    }

    renderValues(values) {
        return values.map(value => (
            <MenuItem value={value} key={value}>{value}</MenuItem>
        ));
    }

    onChange = (e) => {
        this.props.onChange(e.target.value, this.props.context);
    };
}

export default Dropdown;