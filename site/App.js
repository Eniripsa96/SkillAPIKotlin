import * as React from "react";
import {BrowserRouter as Router, Switch} from "react-router-dom";
import SettingsForm from "./component/form/settings/SettingsForm";
import createMuiTheme from "@material-ui/core/es/styles/createMuiTheme";
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import Header from "./component/display/header/Header";
import settings from "./component/form/settings/Settings";
import Error404 from "./pages/error/Error404";
import Route from "react-router-dom/es/Route";
import routes from './routes';
import {SettingsProvider} from "./component/form/settings/Settings";

const theme = createMuiTheme({
    palette: {
        type: 'dark',
        primary: {
            main: "#ffab3c"
        },
        secondary: {
            main: "#29b6f6"
        }
    },
    overrides: {
        MuiAppBar: {
            colorPrimary: {
                'background-color': 'rgba(0, 0, 0, 0.54)'
            }
        }
    },
    typography: {
        useNextVariants: true
    }
});

class App extends React.Component {
    state = {
        showSettings: false
    };

    render() {
        const {showSettings} = this.state;
        return <Router>
            <MuiThemeProvider theme={theme}>
                <SettingsProvider value={settings}>
                    <Header showSettings={this.showSettings}/>
                    <SettingsForm
                        isOpen={showSettings}
                        close={this.closeSettings}
                        settings={settings}/>
                    <Switch>
                        {Object.keys(routes).map(key => {
                            const { path, component } = routes[key];
                            return <Route path={path} component={component} key={path} exact/>
                        })}
                        <Route component={Error404}/>
                    </Switch>
                </SettingsProvider>
            </MuiThemeProvider>
        </Router>
    }

    showSettings = () => {
        this.setState({showSettings: true});
    };

    closeSettings = () => {
        this.setState({showSettings: false});
    }
}

export default App;