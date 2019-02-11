import React from "react";
import {MECHANIC_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const DamageMechanic = {
    name: 'Damage',
    type: Type.MECHANIC,
    description: 'Adds to or removes from an attribute of the target',
    children: false,
    metadata: [
        ...MECHANIC_OPTIONS,
        {
            key: 'amount',
            name: 'Amount',
            description: 'How much damage to deal',
            type: InputType.FORMULA,
            initial: '3 + level'
        },
        {
            key: 'true',
            name: 'True Damage',
            description: 'Whether or not to inflict "true" damage, ignoring any buffs',
            type: InputType.BOOLEAN,
            initial: false
        },
        {
            key: 'classifier',
            name: 'Classifier',
            description: 'The type of skill damage to deal, used for resistances to specific types of damage.',
            type: InputType.STRING,
            initial: 'default'
        }
    ]
};

export default DamageMechanic