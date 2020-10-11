import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const DirectionCondition = {
    name: 'Direction',
    type: Type.CONDITION,
    description: 'Applies child effects if the target is in front of the caster or vice versa if applied to the caster',
    children: true,
    metadata: []
};

export default DirectionCondition