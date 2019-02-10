import React from "react";
import {Type} from "../commonOptions";

const CastTrigger = {
    name: 'Cast',
    type: Type.TRIGGER,
    description: 'Triggers effects upon the skill being cast',
    children: true,
    metadata: []
};

export default CastTrigger