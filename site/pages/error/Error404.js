import * as React from "react";
import {Dialog, DialogActions, DialogContent, DialogTitle, Typography} from "@material-ui/core";
import FormButton from "../../component/input/FormButton";

class Error404 extends React.PureComponent {
    render() {
        return <Dialog open>
            <DialogTitle>404 - Page not found</DialogTitle>
            <DialogContent>
                <Typography>
                    You seem to have ended up at an unknown URL.
                    You can use the button below to return to the main page
                    or use your browser to return to the previous page.
                </Typography>
            </DialogContent>
            <DialogActions>
                <FormButton icon="link" text="Home" onClick={this.navigateHome}/>
            </DialogActions>
        </Dialog>
    }

    navigateHome = () => {
        this.props.history.push('/');
    };
}

export default Error404;