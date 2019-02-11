import React from "react";
import {MECHANIC_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const ValueMechanic = {
    name: 'Value',
    type: Type.MECHANIC,
    description: 'Records a value using a specified key for use in other components',
    children: false,
    metadata: [
        ...MECHANIC_OPTIONS,
        {
            key: 'key',
            name: 'Key',
            description: 'The unique identifier for the value',
            type: InputType.STRING,
            initial: 'default'
        },
        {
            key: 'value',
            name: 'Value',
            description: 'The value to record',
            type: InputType.FORMULA,
            initial: 'caster#default + 1'
        }
    ]
};

export default ValueMechanic