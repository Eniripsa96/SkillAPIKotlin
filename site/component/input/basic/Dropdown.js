import * as React from "react";
import * as PropTypes from "prop-types";
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

const styles = theme => ({
    dropdown: {
        marginTop: '16px',
        minWidth: '100px'
    },
    control: {
        marginTop: '16px',
        marginBottom: '8px'
    }
});

class Dropdown extends React.PureComponent {
    static propTypes = {
        value: PropTypes.string.isRequired,
        values: PropTypes.array.isRequired,
        onChange: PropTypes.func.isRequired,
        label: PropTypes.string.isRequired,
        autoFocus: PropTypes.bool,
        fullWidth: PropTypes.bool,
        context: PropTypes.string,
        extraValues: PropTypes.array
    };

    static defaultProps = {
        fullWidth: true
    };

    render() {
        const {value, values, extraValues, context, label, fullWidth, autoFocus, classes} = this.props;
        return <FormControl fullWidth={fullWidth} className={classes.control}>
            <InputLabel htmlFor={context}>{label}</InputLabel>
            <Select
                className={classes.dropdown}
                fullWidth={fullWidth}
                autoFocus={autoFocus}
                value={value}
                onChange={this.onChange}
                inputProps={{id: {context}}}>
                {extraValues && this.renderValues(extraValues)}
                {this.renderValues(values)}
            </Select>
        </FormControl>
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

export default withStyles(styles)(Dropdown);