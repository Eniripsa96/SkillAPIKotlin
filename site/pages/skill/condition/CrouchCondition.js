import React from "react";
import {Type} from "../commonOptions";

const CrouchCondition = {
    name: 'Crouch',
    type: Type.CONDITION,
    description: 'Applies child effects if the target is crouching',
    children: true,
    metadata: []
};

export default CrouchCondition