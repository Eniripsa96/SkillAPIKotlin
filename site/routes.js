import Home from "./pages/home/Home";
import SkillList from "./pages/skill/SkillList";
import SkillForm from "./pages/skill/SkillEditor";

const routes = {
    HOME: {path: '/', component: Home},
    SKILL_LIST: {path: '/skills', component: SkillList},
    SKILL_EDITOR: {path: '/skills/:id', component: SkillForm}
};

function resolve(route, args) {
    let path = route.path;
    Object.keys(args).forEach(key => path = path.replace(`:${key}`, args[key]));
    return path;
}

export {resolve}
export default routes