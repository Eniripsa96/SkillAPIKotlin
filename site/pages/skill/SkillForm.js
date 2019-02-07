import * as React from "react";
import {Card, CardContent, Divider, Typography, withStyles} from "@material-ui/core";
import TextInput from "../../component/input/basic/TextInput";
import {loadLocally, StorageKey} from "../../data/storage";
import NumberInput from "../../component/input/basic/NumberInput";
import BooleanInput from "../../component/input/basic/BooleanInput";
import Grid from "@material-ui/core/Grid";
import FormButton from "../../component/input/FormButton";

const DEFAULT_SKILL = {
    name: 'New Skill',
    maxLevel: 5,
    autoUnlock: 'Yes'
};

const Form = {
    REQUIREMENT: 'requirement'
};

const styles = theme => ({
    header: {
        marginTop: '24px'
    }
});

class SkillForm extends React.Component {
    state = {
        original: null,
        form: null,
        skill: {}
    };

    componentDidMount() {
        this.reload();
    }

    reload() {
        const name = this.props.match.params.name;
        const key = `${StorageKey.SKILL}_data_${name}`;
        this.setState({
            originalName: name,
            skill: (name && loadLocally(key)) || DEFAULT_SKILL
        });
    }

    render() {
        const {classes} = this.props;
        const {skill} = this.state;
        const {name, maxLevel, autoUnlock} = skill;
        return <Grid container spacing={24}>
            <Grid item xs={4}>
                <Card>
                    <CardContent>
                        <Typography variant="h5">Skill Details</Typography>
                        <Divider/>

                        <TextInput
                            fullWidth
                            label="Name"
                            value={name}
                            onChange={this.onChange}
                            context="name"
                            autoFocus/>

                        <NumberInput
                            fullWidth
                            label="Max Level"
                            value={maxLevel}
                            onChange={this.onChange}
                            context={"maxLevel"}
                            integer/>

                        <BooleanInput
                            fullWidth
                            label="Auto Unlock"
                            value={autoUnlock}
                            onChange={this.onChange}
                            context="autoUnlock"/>

                        <Typography variant="h5" className={classes.header}>Requirements</Typography>
                        <Divider/>
                        {/* TODO - add requirement list display */}
                        <FormButton icon="add" text="Add Requirement" onClick={this.showRequirementForm}/>

                    </CardContent>
                </Card>
            </Grid>
            <Grid item xs={8}>
            </Grid>
        </Grid>
    }

    onChange = (value, key) => {
        this.setState(({skill}) => ({skill: {...skill, [key]: value}}));
    };

    showRequirementForm = () => {
        this.setState({form: Form.REQUIREMENT});
    };
}

export default withStyles(styles)(SkillForm)