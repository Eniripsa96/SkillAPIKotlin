import TRIGGERS from "./trigger";
import CONDITIONS from "./condition";
import MECHANICS from "./mechanic";
import TARGETS from "./target";

const COMPONENTS = [];
const COMPONENT_MAP = {};
let COMPONENT_OPTIONS = [];

function instance(id) {
    const {name, type} = this;
    const metadata = {};
    this.metadata.forEach(option => {
        metadata[option.key] = option.initial;
    });
    return {name, type, id, children: [], metadata};
}

function add(component) {
    component.instance = instance;
    COMPONENTS.push(component);

    const {type, name} = component;
    const byName = COMPONENT_MAP[type] = COMPONENT_MAP[type] || {};
    byName[name] = component;
}

function reloadComponentOptions(settings) {
    if (!COMPONENTS.length) {
        CONDITIONS.forEach(add);
        MECHANICS.forEach(add);
        TARGETS.forEach(add);
        TRIGGERS.forEach(add);
    }
    const all = settings.isPremium();
    COMPONENT_OPTIONS = COMPONENTS
        .filter(({premium}) => all || !premium)
        .reduce((result, component) => {
            const list = result[component.type] = result[component.type] || [];
            list.push(component);
            return result;
        }, {});
}

function getComponentDetails(type, name) {
    return COMPONENT_MAP[type][name];
}

function getComponentOptions(type) {
    return COMPONENT_OPTIONS[type];
}

export {
    COMPONENTS,
    getComponentDetails,
    getComponentOptions,
    reloadComponentOptions
};