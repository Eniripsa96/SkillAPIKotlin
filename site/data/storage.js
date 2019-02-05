export function loadLocally(key) {
    const value = localStorage.getItem(key);
    return value && JSON.parse(value);
}

export function saveLocally(key, value) {
    localStorage.setItem(key, JSON.stringify(value));
}