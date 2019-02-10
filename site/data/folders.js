import {loadLocally, saveLocally} from "./storage";
import {getLoader} from "./loaders";

const DEFAULT_FOLDER = {name: 'Default', items: []};

/**
 * Handles persisting and interacting with defined folders for a given type
 */
class Folders {
    constructor(type) {
        this.loader = getLoader(type);
        this.key = `${type}_folders`;
        this.folders = loadLocally(this.key) || [DEFAULT_FOLDER];
        this.byName = this.folders.reduce((result, folder) => {
            result[folder.name] = folder;
            return result;
        }, { });
    }

    create(name) {
        const folder = {name, items: []};
        this.folders.push(folder);
        this.byName[name] = folder;
        this.sortFolders();
        saveLocally(this.key, this.folders);
        return folder;
    }

    delete(name) {
        this.folders = this.folders.filter(folder => folder.name !== name);
        this.byName[name].items.forEach(id => this.loader.delete({id}));
        delete this.byName[name];
        saveLocally(this.key, this.folders);
    }

    exists(name) {
        return !!this.byName[name];
    }

    addItem(folder, item) {
        const {id} = item;
        folder.items.push(id);
        saveLocally(this.key, this.folders);
    }

    removeItem(folder, item) {
        const {id} = item;
        folder.items = folder.items.filter(item => item !== id);
        saveLocally(this.key, this.folders);
    }

    sortFolders() {
        const [first, ...rest] = this.folders;
        this.folders = [first, ...rest.sort((a, b) => a.name.toLowerCase() - b.name.toLowerCase())];
    }
}

export {DEFAULT_FOLDER}

export default Folders;