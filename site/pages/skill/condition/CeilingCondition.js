import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const CeilingCondition = {
    name: 'Ceiling',
    type: Type.CONDITION,
    description: 'Applies child effects if the first solid block above the target is within the given range',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'distance',
            name: 'Distance',
            description: 'The threshold for the ceiling height',
            type: InputType.FORMULA,
            initial: '5'
        },
        {
            key: 'at-least',
            name: 'At Least',
            description: 'Whether or not the distance is the minimum distance (true) or the maximum distance (false)',
            type: InputType.BOOLEAN,
            initial: true
        }
    ]
};

export default CeilingCondition