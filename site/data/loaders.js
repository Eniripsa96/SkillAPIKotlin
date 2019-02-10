import {deleteLocally, loadLocally, saveLocally, StorageKey} from "./storage";

class Loader {
    constructor(type) {
        this.prefix = `${type}|`;
    }

    load = (id) => {
        return loadLocally(`${this.prefix}${id}`);
    };

    save = (data) => {
        saveLocally(`${this.prefix}${data.id}`, data);
    };

    delete = (data) => {
        deleteLocally(`${this.prefix}${data.id}`);
    };

    exists = (id) => {
        return !!this.load(id);
    };
}

const skillLoader = new Loader(StorageKey.SKILL);
const professionLoader = new Loader(StorageKey.PROFESSION);

function getLoader(type) {
    return {
        [StorageKey.SKILL]: skillLoader,
        [StorageKey.PROFESSION]: professionLoader
    }[type];
}

export {skillLoader, professionLoader, getLoader}