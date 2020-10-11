import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const EntityTypeCondition = {
    name: 'Entity Type',
    type: Type.CONDITION,
    description: 'Applies child effects if the current target matches any of the listed entity types',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'types',
            name: 'Types',
            description: 'Types of entities that apply',
            type: InputType.MULTI_DROPDOWN,
            initial: ['Zombie'],
            options: settings => settings.getEnumData().ENTITIES
        }
    ]
};

export default EntityTypeCondition