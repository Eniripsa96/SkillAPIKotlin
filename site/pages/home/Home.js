import * as React from "react";
import {Card, CardContent, Divider, Typography} from "@material-ui/core";

class Home extends React.PureComponent {
    render() {
        return <div style={{maxWidth: '600px', margin: '0 auto'}}>
            <Card>
                <CardContent>
                    <Typography variant="h4">
                        SkillAPI Dynamic Editor
                    </Typography>
                    <Divider/>
                    <Typography>
                        This is the official skill editor for the SkillAPI plugin.
                        You can create custom skills and professions through this tool, download the configuration files,
                        and drop them in the SkillAPI plugin folder to avoid manually creating/editing the files.
                    </Typography>
                    <br/><br/>
                    <Typography variant="h4">
                        Using this tool
                    </Typography>
                    <Divider/>
                    <Typography>
                        <ol>
                            <li>Configure the editor using the gear icon in the top right</li>
                            <li>Use the menu icon in the top left to navigate through the tool</li>
                        </ol>
                        Most options provided in the tool have tooltips to try to better explain what the option is,
                        so hover over anything that you find confusing. Any further questions can be asked in the
                        support Discord, accessible via the menu in the top left.
                    </Typography>
                </CardContent>
            </Card>
        </div>
    }
}

export default Home;