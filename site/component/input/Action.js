import * as React from "react";
import {IconButton} from "@material-ui/core";
import Tooltip from "@material-ui/core/Tooltip";
import Icon from "@material-ui/core/Icon";
import * as PropTypes from "prop-types";

class Action extends React.PureComponent {
    static propTypes = {
        icon: PropTypes.string.isRequired,
        onClick: PropTypes.func.isRequired,
        color: PropTypes.string,
        disabled: PropTypes.bool,
        tooltip: PropTypes.string,
        context: PropTypes.any,
    };

    render() {
        const {tooltip, disabled} = this.props;
        return disabled || !tooltip
            ? this.button()
            : <Tooltip title={tooltip}>
                {this.button()}
            </Tooltip>
    }

    button() {
        const {icon, color, disabled, ...props} = this.props;
        return <IconButton
            {...props}
            disabled={disabled}
            onClick={this.onClick}
            color={color || 'secondary'}>
            <Icon>{icon}</Icon>
        </IconButton>
    }

    onClick = () => {
        const {onClick, context} = this.props;
        onClick(context);
    };
}

export default Action