import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const CombatCondition = {
    name: 'Combat',
    type: Type.CONDITION,
    description: 'Applies child effects if the target has been in combat within the specified duration',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'seconds',
            name: 'Seconds',
            description: 'Time to be in combat',
            type: InputType.FORMULA,
            initial: '10'
        }
    ]
};

export default CombatCondition