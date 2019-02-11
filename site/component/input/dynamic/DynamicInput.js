import * as React from "react";
import * as PropTypes from "prop-types";
import InputType from "./InputType";
import BooleanInput from "../basic/BooleanInput";
import TextInput from "../basic/TextInput";
import Dropdown from "../basic/Dropdown";
import NumberInput from "../basic/NumberInput";
import {SettingsConsumer} from "../../form/settings/Settings";

class DynamicInput extends React.PureComponent {
    static propTypes = {
        settings: PropTypes.object.isRequired,
        data: PropTypes.object.isRequired,
        onChange: PropTypes.func.isRequired,
        context: PropTypes.any,
    };

    render() {
        const {settings, data} = this.props;

        if (settings.condition && !settings.condition(data)) {
            return null;
        }

        switch (settings.type) {
            case InputType.BOOLEAN:
                return this.renderBooleanInput();
            case InputType.FORMULA:
                return this.renderStringInput();
            case InputType.DROPDOWN:
                return this.renderDropdownInput();
            case InputType.STRING:
                return this.renderStringInput();
            case InputType.MULTI_DROPDOWN:
                return this.renderMultiDropdownInput();
            case InputType.DOUBLE:
                return this.renderNumberInput(false);
            case InputType.INTEGER:
                return this.renderNumberInput(true);
        }
    }

    renderBooleanInput() {
        const {settings, data} = this.props;
        const {key, name, description, initial} = settings;

        return <BooleanInput
            label={name}
            fullWidth
            onChange={this.onChange}
            value={data[key] || initial}
            tooltip={description}/>
    }

    renderStringInput() {
        const {settings, data} = this.props;
        const {key, name, description, initial} = settings;

        return <TextInput
            fullWidth
            label={name}
            value={data[key] || initial}
            tooltip={description}
            onChange={this.onChange}/>
    }

    renderDropdownInput() {
        const {settings, data} = this.props;
        const {key, name, description, initial, options} = settings;

        return <SettingsConsumer>
            {settings => <Dropdown
                fullWidth
                label={name}
                values={options(settings)}
                value={data[key] || initial}
                tooltip={description}
                onChange={this.onChange}/>}
        </SettingsConsumer>
    }

    renderNumberInput(integer) {
        const {settings, data} = this.props;
        const {key, description, initial} = settings;

        return <NumberInput
            fullWidth
            integer={integer}
            label={name}
            value={data[key] || initial}
            tooltip={description}
            onChange={this.onChange}/>
    }

    renderMultiDropdownInput() {
        const {settings, data} = this.props;
        const {key, name, description, initial, options} = settings;

        return <SettingsConsumer>
            {settings => <Dropdown
                fullWidth
                multi
                label={name}
                values={options(settings)}
                value={data[key] || initial}
                tooltip={description}
                onChange={this.onChange}/>}
        </SettingsConsumer>
    }

    onChange = (value) => {
        const {onChange, settings, context} = this.props;
        onChange(value, settings.key, context);
    };
}

export default DynamicInput