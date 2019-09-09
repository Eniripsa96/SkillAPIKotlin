import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const ValueCondition = {
    name: 'Value',
    type: Type.CONDITION,
    description: 'Checks whether or not a given value is within the required range',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'key',
            name: 'Key',
            description: 'The unique identifier for the value',
            type: InputType.STRING,
            initial: 'default'
        },
        {
            key: 'min',
            name: 'Minimum',
            description: 'The minimum amount the value must be',
            type: InputType.FORMULA,
            initial: '1'
        },
        {
            key: 'max',
            name: 'Maximum',
            description: 'The maximum amount the value must be',
            type: InputType.FORMULA,
            initial: '999'
        }
    ]
};

export default ValueCondition