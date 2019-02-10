const StorageKey = {
    SKILL: "skill",
    PROFESSION: "profession"
};

function loadLocally(key) {
    const value = localStorage.getItem(key);
    return value && JSON.parse(value);
}

function saveLocally(key, value) {
    localStorage.setItem(key, JSON.stringify(value));
}

function deleteLocally(key) {
    localStorage.removeItem(key);
}

export {loadLocally, saveLocally, deleteLocally, StorageKey};