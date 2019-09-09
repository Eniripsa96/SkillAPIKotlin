import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const AttributeCondition = {
    name: 'Attribute',
    type: Type.CONDITION,
    description: 'Applies child effects if the current target has an attribute value in the required range',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'attribute',
            name: 'Attribute',
            description: 'The attribute to check the value for',
            type: InputType.STRING,
            initial: 'Strength'
        },
        {
            key: 'min',
            name: 'Minimum',
            description: 'The minimum value required for the attribute',
            type: InputType.FORMULA,
            initial: '0'
        },
        {
            key: 'max',
            name: 'Maximum',
            description: 'The maximum value required for the attribute',
            type: InputType.FORMULA,
            initial: '999'
        }
    ]
};

export default AttributeCondition