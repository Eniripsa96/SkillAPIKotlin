import * as React from "react";
import * as PropTypes from "prop-types";
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";
import Chip from "@material-ui/core/Chip";

const styles = theme => ({
    dropdown: {
        marginTop: '16px',
        minWidth: '100px'
    },
    control: {
        marginTop: '16px',
        marginBottom: '8px'
    },
    chip: {
        '&:hover': {
            'background-color': '#dd2211'
        }
    }
});

class Dropdown extends React.PureComponent {
    static propTypes = {
        value: PropTypes.any.isRequired,
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
        const {value, values, extraValues, context, label, fullWidth, autoFocus, classes, multi} = this.props;
        return <FormControl fullWidth={fullWidth} className={classes.control}>
            <InputLabel htmlFor={context}>{label}</InputLabel>
            <Select
                multiple={multi}
                className={classes.dropdown}
                fullWidth={fullWidth}
                autoFocus={autoFocus}
                value={value}
                renderValue={multi ? this.renderSelected : null}
                onChange={this.onChange}
                inputProps={{id: {context}}}>
                {extraValues && this.renderOptions(extraValues)}
                {this.renderOptions(values)}
            </Select>
        </FormControl>
    }

    renderOptions(values) {
        return values.map(value => (
            <MenuItem value={value} key={value}>{value}</MenuItem>
        ));
    }

    renderSelected = (selected) => {
        const {classes} = this.props;
        return <div>
            {selected.map(value => (
                <Chip key={value} label={value} className={classes.chip} onClick={(e) => {
                    e.stopPropagation();
                    this.remove(value)
                }}/>
            ))}
        </div>
    };

    remove(removed) {
        const {value, onChange, context} = this.props;
        const updated = value.filter(v => v !== removed);
        onChange(updated, context);
    }

    onChange = (e) => {
        const {onChange, context, multi} = this.props;
        if (multi) {
            e.target.value.sort();
        }
        onChange(e.target.value, context);
    };
}

export default withStyles(styles)(Dropdown);