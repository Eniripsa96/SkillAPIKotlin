import Home from "./pages/home/Home";
import SkillList from "./pages/skill/SkillList";
import SkillForm from "./pages/skill/SkillForm";

const routes = {
    HOME: {path: '/', component: Home},
    SKILL: {path: '/skills', component: SkillList},
    NEW_SKILL: {path: '/skills/new', component: SkillForm},
    EDIT_SKILL: {path: '/skills/edit/:name', component: SkillForm}
};

function resolve(route, args) {
    let path = route.path;
    Object.keys(args).forEach(key => path = path.replace(`:${key}`, args[key]));
    return path;
}

export {resolve}
export default routes