import * as React from "react";
import FormButton from "./FormButton";
import {withRouter} from "react-router-dom";
import * as PropTypes from "prop-types";

class FormLink extends React.Component {
    static propTypes = {
        text: PropTypes.string.isRequired,
        icon: PropTypes.string.isRequired,
        context: PropTypes.any,
        onClick: PropTypes.func,
        color: PropTypes.string
    };

    render() {
        const {icon, text, color} = this.props;
        return <FormButton
            icon={icon}
            text={text}
            color={color}
            onClick={this.onClick}/>
    }

    onClick = () => {
        const {history, link, onClick, context} = this.props;
        if (onClick) {
            onClick(context);
        }
        history.push(link);
    }
}

export default withRouter(FormLink)