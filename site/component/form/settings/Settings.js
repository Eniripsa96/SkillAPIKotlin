import * as React from "react";
import {loadLocally, saveLocally} from "../../../data/storage";

class Settings {
    data = loadLocally('settings') || { };

    getVersion() {
        return this.data.version || '1.13';
    }

    getSidebar() {
        return this.data.sidebar || 'skills';
    }

    applyChanges(changes) {
        this.data = { ...this.data, ...changes };
        saveLocally('settings', this.data);
    }
}

const settings = new Settings();
const { Provider, Consumer } = React.createContext(null);

export { Provider as SettingsProvider, Consumer as SettingsConsumer }

export default settings;