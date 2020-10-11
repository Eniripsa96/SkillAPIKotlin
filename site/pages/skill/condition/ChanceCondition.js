import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const ChanceCondition = {
    name: 'Ceiling',
    type: Type.CONDITION,
    description: 'Applies child effects if the probability check passes',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'chance',
            name: 'Chance',
            description: 'The percentage chance of an effect taking place',
            type: InputType.FORMULA,
            initial: '25'
        }
    ]
};

export default ChanceCondition