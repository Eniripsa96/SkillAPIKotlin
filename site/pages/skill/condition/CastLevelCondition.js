import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const CastLevelCondition = {
    name: 'Cast Level',
    type: Type.CONDITION,
    description: 'Applies child effects if this skill is cast at a matching level',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'min',
            name: 'Minimum',
            description: 'The minimum level for the skill',
            type: InputType.FORMULA,
            initial: '0'
        },
        {
            key: 'max',
            name: 'Maximum',
            description: 'The maximum level for the skill',
            type: InputType.FORMULA,
            initial: '999'
        }
    ]
};

export default CastLevelCondition