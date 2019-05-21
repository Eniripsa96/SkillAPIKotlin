import InputType from "../../component/input/dynamic/InputType";

const Type = {
    CONDITION: 'condition',
    MECHANIC: 'mechanic',
    TARGET: 'target',
    TRIGGER: 'trigger'
};

const ALLY_OPTIONS = ['Any', 'Ally', 'Enemy'];

const TRIGGER_OPTIONS = [
    {
        key: 'mana',
        name: 'Apply Mana Cost',
        description: 'Whether or not to require the general mana use for the trigger to fire',
        type: InputType.BOOLEAN,
        initial: false
    },
    {
        key: 'cooldown',
        name: 'Apply Cooldown',
        description: 'Whether or not to put the skill on cooldown when the trigger fires',
        type: InputType.BOOLEAN,
        initial: false
    }
];

const TARGET_OPTIONS = [
    {
        key: 'group',
        name: 'Group',
        description: 'Whether to only target allies, enemies, or both',
        type: InputType.DROPDOWN,
        initial: 'Enemy',
        options: () => ALLY_OPTIONS
    },
    {
        key: 'caster',
        name: 'Include Caster',
        description: 'Whether or not to include the caster in the resulting list of targets',
        type: InputType.BOOLEAN,
        initial: true
    },
    {
        key: 'wall',
        name: 'Ignore Walls',
        description: 'Whether or not the target can be through a wall or similar obstacle',
        type: InputType.BOOLEAN,
        initial: true
    }
];

const MULTI_TARGET_OPTIONS = [
    {
        key: 'maxTargets',
        name: 'Max Targets',
        description: 'The max number of targets that can be affected at once',
        type: InputType.FORMULA,
        initial: '99'
    },
    {
        key: 'distinct',
        name: 'Distinct',
        description: 'Whether or not to ignore overlapping targets',
        type: InputType.BOOLEAN,
        initial: true
    },
    {
        key: 'random',
        name: 'Random',
        description: 'Whether or not to randomize which targets are selected if the max target limit is hit',
        type: InputType.BOOLEAN,
        initial: false
    },
    ...TARGET_OPTIONS
];

const MECHANIC_OPTIONS = [
    {
        key: 'target',
        name: 'Apply To',
        description: 'Who to apply the mechanic to',
        type: InputType.DROPDOWN,
        initial: 'Target',
        options: () => ['Both', 'Caster', 'Target']
    },
    {
        key: 'casterOnce',
        name: 'Caster Once',
        description: 'If false, each target will cause the effects to apply to the caster an additional time',
        type: InputType.BOOLEAN,
        initial: true,
        condition: ({ target }) => target.toLowerCase() !== 'target'
    }
];

export {Type, TRIGGER_OPTIONS, MECHANIC_OPTIONS, TARGET_OPTIONS, MULTI_TARGET_OPTIONS}