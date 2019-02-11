import * as React from "react";
import {loadLocally, saveLocally} from "../../../data/storage";
import {reloadComponentOptions} from "../../../pages/skill/components";

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