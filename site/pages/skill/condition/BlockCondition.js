import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const BlockCondition = {
    name: 'Block',
    type: Type.CONDITION,
    description: 'Applies child effects if the current target is on/in a matching block',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'material',
            name: 'Material',
            description: 'Biomes the target should or should not be in',
            type: InputType.MULTI_DROPDOWN,
            initial: ['Dirt'],
            options: settings => settings.getEnumData().BLOCKS
        }
    ]
};

export default BlockCondition