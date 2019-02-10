import React from "react";
import {Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const ACTION_OPTIONS = ['Any', 'Start Crouching', 'Stop Crouching'];

const CrouchTrigger = {
    name: 'Crouch',
    type: Type.TRIGGER,
    description: 'Triggers effects upon the caster toggling crouch',
    children: true,
    metadata: [
        {
            key: 'action',
            name: 'Action',
            description: 'Controls what crouch transitions cause the trigger to fire',
            type: InputType.DROPDOWN,
            initial: 'Start Crouching',
            options: () => ACTION_OPTIONS
        }
    ]
};

export default CrouchTrigger