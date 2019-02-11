import React from "react";
import {TRIGGER_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const ACTION_OPTIONS = ['Any', 'Break', 'Place'];

const BlockTrigger = {
    name: 'Block',
    type: Type.TRIGGER,
    description: 'Triggers effects upon a block being broken',
    children: true,
    metadata: [
        ...TRIGGER_OPTIONS,
        {
            key: 'action',
            name: 'Action',
            description: 'The interactions with blocks to trigger on',
            type: InputType.DROPDOWN,
            initial: 'Break',
            options: () => ACTION_OPTIONS
        },
        {
            key: 'material',
            name: 'Block Types',
            description: 'The types of block that must be interacted with for the trigger to fire',
            type: InputType.MULTI_DROPDOWN,
            initial: ['Any'],
            options: (settings) => ['Any']
        }
    ]
};

export default BlockTrigger