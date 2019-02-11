import React from "react";
import {MULTI_TARGET_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const SHAPE_OPTIONS = ['Sphere', 'Cube'];

const AreaTarget = {
    name: 'Area',
    type: Type.TARGET,
    description: 'Changes the current target to those surrounding each current target',
    children: true,
    metadata: [
        {
            key: 'radius',
            name: 'Radius',
            description: 'The radius, in blocks, of the area to target',
            type: InputType.STRING,
            initial: 'Strength'
        },
        {
            key: 'shape',
            name: 'Shape',
            description: 'What sort of shape the area should be',
            type: InputType.DROPDOWN,
            initial: 'Circle',
            options: () => SHAPE_OPTIONS
        },
        ...MULTI_TARGET_OPTIONS
    ]
};

export default AreaTarget