import * as React from "react";
import ListButton from "../../input/list/ListButton";
import * as PropTypes from "prop-types";

class Folder extends React.PureComponent {
    static propTypes = {
        name: PropTypes.string.isRequired,
        selected: PropTypes.string.isRequired,
        onClick: PropTypes.func.isRequired
    };

    render() {
        const {name, selected} = this.props;
        return <ListButton text={name} selected={selected === name} onClick={this.onClick}/>
    }

    onClick = () => {
        this.props.onClick(this.props.name);
    }
}

export default Folder;