import React from "react";
import {MECHANIC_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const AttributeMechanic = {
    name: 'Attribute',
    type: Type.MECHANIC,
    description: 'Adds to or removes from an attribute of the target',
    children: false,
    metadata: [
        ...MECHANIC_OPTIONS,
        {
            key: 'attribute',
            name: 'Attribute',
            description: 'The attribute to manipulate',
            type: InputType.STRING,
            initial: 'Strength'
        },
        {
            key: 'amount',
            name: 'Amount',
            description: 'How many points to change the attribute by',
            type: InputType.FORMULA,
            initial: '2'
        }
    ]
};

export default AttributeMechanic