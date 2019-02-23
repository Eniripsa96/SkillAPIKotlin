import * as React from "react";
import {loadLocally, saveLocally} from "../../../data/storage";
import {reloadComponentOptions} from "../../../pages/skill/components";
import DataIndex from "../../../data/generated";

class Settings {
    /**
     * @type {{
     *     premium: string,
     *     version: string
     * }}
     */
    data = loadLocally('settings') || {};

    constructor() {
        this.applyChanges({});
    }

    isPremium() {
        return !this.data.premium || this.data.premium === 'True'
    }

    getVersion() {
        return this.data.version || '1.13';
    }

    getServer() {
        return this.data.server || 'Spigot';
    }

    /**
     * @returns {{
     *      ITEMS:string[],
     *      BLOCKS:string[],
     *      SOUNDS:string[],
     *      ENTITIES:string[],
     *      BIOMES:string[],
     *      POTIONS:[],
     *      PARTICLES:[],
     *      DAMAGE_TYPES:[]
     *  }}
     */
    getEnumData() {
        return DataIndex[this.getServer()][this.getVersion()];
    }

    applyChanges(changes) {
        this.data = {...this.data, ...changes};
        saveLocally('settings', this.data);
        reloadComponentOptions(this);
    }
}

const settings = new Settings();
const {Provider, Consumer} = React.createContext(null);

export {Provider as SettingsProvider, Consumer as SettingsConsumer}

export default settings;