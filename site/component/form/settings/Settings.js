import * as React from "react";
import {loadLocally, saveLocally} from "../../../data/storage";

class Settings {
    data = loadLocally('settings') || { };

    getVersion() {
        return this.data.version || '1.13';
    }

    applyChanges(changes) {
        this.data = { ...this.data, ...changes };
        saveLocally('settings', this.data);
    }
}

const settings = new Settings();
const { provider, consumer } = React.createContext(settings);

export { provider as SettingsProvider, consumer as SettingsConsumer, settings }

export default Settings;