import React from "react";
import {MECHANIC_OPTIONS, Type} from "../commonOptions";
import InputType from "../../../component/input/dynamic/InputType";

const ItemCondition = {
    name: 'Item',
    type: Type.CONDITION,
    description: 'Applies child effects if the current target has items matching the specified options',
    children: true,
    metadata: [
        ...MECHANIC_OPTIONS,
        {
            key: 'material',
            name: 'Material',
            description: 'The type of item to require',
            type: InputType.MULTI_DROPDOWN,
            initial: ['Any'],
            options: settings => settings.getEnumData().ITEMS
        },
        {
            key: 'location',
            name: 'Location',
            description: 'Where the item needs to be, whether equipped, in inventory, or both',
            type: InputType.DROPDOWN,
            initial: 'Any',
            options: () => ['Any', 'Equipped', 'Inventory']
        },
        {
            key: 'textScope',
            name: 'Where to match text',
            description: 'The places to look for matching text in',
            type: InputType.DROPDOWN,
            initial: 'Disabled',
            options: () => ['Both', 'Disabled', 'Lore', 'Name']
        },
        {
            key: 'text',
            name: 'Text to match',
            description: 'What the item should include in its title or lore. Prefix this with "regex:" to make it a regex match.',
            type: InputType.FORMULA,
            initial: '',
            condition: data => data.textScope !== 'Disabled'
        },
        {
            key: 'quantity',
            name: 'Quantity',
            description: 'How many of the items are required',
            type: InputType.FORMULA,
            initial: '1',
            condition: data => ['Any', 'Inventory'].includes(data.location)
        }
    ]
};

export default ItemCondition