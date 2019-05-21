import React from "react";
import {MECHANIC_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const NUMBER_PATTERN = /^[+-]?[0-9]([.,][0-9]+)?$/;

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
        },
        {
            key: 'valueType',
            name: 'Bonus Type',
            description: 'What type of bonus to give. Final values are computed using (base * multiplier + bonus)',
            type: InputType.DROPDOWN,
            initial: 'Bonus',
            options: () => ['Base', 'Bonus', 'Multiplier']
        },
        {
            key: 'duration',
            name: 'Duration',
            description: 'How long to give the bonus for. 0 or less will cause it to not expire.',
            type: InputType.FORMULA,
            initial: '5'
        },
        {
            key: 'stackKey',
            name: 'Stack Key',
            description: 'Key for grouping effects together for stacking purposes. If empty, will be specific to this component.'
        },
        {
            key: 'timerType',
            name: 'Duration Type',
            description: 'Determines how bonuses expire. Overwrite keeps the latest, highest/lowest use only the max/min bonuses, and separate uses stacks that expire individually',
            type: InputType.DROPDOWN,
            initial: 'Overwrite',
            options: () => ['Overwrite', 'Highest', 'Lowest', 'Separate', 'Separate Highest', 'Separate Lowest', 'Separate Oldest'],
            condition: ({ duration }) => NUMBER_PATTERN.test(duration) && parseFloat(duration) <= 0
        },
        {
            key: 'maxStacks',
            name: 'Max Stacks',
            description: 'How many times the effect can stack',
            type: InputType.FORMULA,
            initial: '5',
            condition: ({ timerType }) => timerType.toLocaleString() !== 'overwrite'
        }
    ]
};

export default AttributeMechanic