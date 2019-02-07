import * as React from "react";
import * as PropTypes from "prop-types";
import {Button} from "@material-ui/core";
import Icon from "@material-ui/core/Icon";
import withStyles from "@material-ui/core/es/styles/withStyles";

const styles = theme => ({
    button: {
        margin: theme.spacing.unit,
    },
    icon: {
        marginLeft: theme.spacing.unit,
    }
});

class FormButton extends React.PureComponent {
    static propTypes = {
        text: PropTypes.string.isRequired,
        icon: PropTypes.string.isRequired,
        onClick: PropTypes.func.isRequired,
        color: PropTypes.string,
        context: PropTypes.any
    };

    render() {
        const {text, icon, classes, color} = this.props;
        return <Button
            variant="contained"
            color={color || 'secondary'}
            onClick={this.onClick}
            className={classes.button}>

            {text}
            <Icon className={classes.icon}>{icon}</Icon>
        </Button>
    }

    onClick = () => {
        this.props.onClick(this.props.context);
    }
}

export default withStyles(styles)(FormButton);