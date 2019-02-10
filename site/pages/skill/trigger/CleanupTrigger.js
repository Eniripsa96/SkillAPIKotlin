import React from "react";
import {Type} from "../commonOptions";

const CleanupTrigger = {
    name: 'Cleanup',
    type: Type.TRIGGER,
    description: 'Triggers effects upon the caster dying, logging out, despawning, or forgetting the skill',
    children: true,
    metadata: []
};

export default CleanupTrigger