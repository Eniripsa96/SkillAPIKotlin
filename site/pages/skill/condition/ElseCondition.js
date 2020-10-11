import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const ElseCondition = {
    name: 'Else',
    type: Type.CONDITION,
    description: 'Applies child effects to targets not passing the previous condition check',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'legacy',
            name: 'Legacy',
            description: 'Switches to old behavior of the condition where child effects apply if the last condition, mechanic, or target executed failed to apply to anything.',
            type: InputType.BOOLEAN,
            initial: false
        }
    ]
};

export default CastLevelCondition