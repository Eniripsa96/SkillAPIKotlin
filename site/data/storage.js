const StorageKey = {
    SKILL: "skill",
    CLASS: "class"
};

function loadLocally(key) {
    const value = localStorage.getItem(key);
    return value && JSON.parse(value);
}

function saveLocally(key, value) {
    localStorage.setItem(key, JSON.stringify(value));
}

export {loadLocally, saveLocally, StorageKey};