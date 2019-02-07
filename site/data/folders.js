import {loadLocally, saveLocally} from "./storage";

const DEFAULT_FOLDER = 'Default';

/**
 * Handles persisting and interacting with defined folders for a given type
 */
class Folders {
    constructor(type) {
        this.key = `${type}_folders`;
        this.names = loadLocally(this.key) || [DEFAULT_FOLDER];
    }

    create(name) {
        this.names.push(name);
        saveLocally(this.key, this.names);
    }

    delete(name) {
        this.names = this.names.filter(n => n !== name);
        saveLocally(this.key, this.names);
    }

    exists(name) {
        return this.names.includes(name);
    }
}

export {DEFAULT_FOLDER}

export default Folders;