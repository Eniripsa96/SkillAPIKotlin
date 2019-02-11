import React from "react";
import {Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const CastTrigger = {
    name: 'Cast',
    type: Type.TRIGGER,
    description: 'Triggers effects upon the skill being cast',
    children: true,
    metadata: [
        {
            key: 'mana',
            name: 'Apply Mana Cost',
            description: 'Whether or not to require the general mana use for the trigger to fire',
            type: InputType.BOOLEAN,
            initial: true
        },
        {
            key: 'cooldown',
            name: 'Apply Cooldown',
            description: 'Whether or not to put the skill on cooldown when the trigger fires',
            type: InputType.BOOLEAN,
            initial: true
        }
    ]
};

export default CastTrigger