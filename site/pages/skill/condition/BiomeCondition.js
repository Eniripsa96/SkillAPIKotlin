import React from "react";
import {CONDITION_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const BiomeCondition = {
    name: 'Biome',
    type: Type.CONDITION,
    description: 'Applies child effects if the current target is within a matching biome',
    children: true,
    metadata: [
        ...CONDITION_OPTIONS,
        {
            key: 'biome',
            name: 'Biome',
            description: 'Biomes the target should or should not be in',
            type: InputType.MULTI_DROPDOWN,
            initial: ['Ocean'],
            options: settings => settings.getEnumData().BIOMES
        }
    ]
};

export default BiomeCondition