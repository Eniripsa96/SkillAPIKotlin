# SkillAPIKotlin
A flexible RPG plugin for Minecraft allowing for custom skills and classes - recoded in Kotlin

## Skill and Class Editor
The UI for editing skills and classes can be viewed [HERE](https://eniripsa96.github.io/SkillAPIKotlin). Note that this is currently in the works and several features are not operational quite yet.

## Changes
This recoded version of SkillAPI brings a number of changes, including (but not limited to):

### Major
- Classes now support multiple parents for flexible profession chains
- Values and attribute bonuses without a set duration now persist through relogging 
- Formulas can be used on most effect options
- Formulas now support values and entity data (health, mana, level, etc.)
- Sponge support (in progress)

### Minor
- Skills now support leveling up via experience
- Several effect components have been merged/replaced
- Formulas now follow operation precedence
- Commands now show all message options in the config file immediately
- Improved handling loading data from SQL for multi-server use cases
- Commands can now have their full path changed (e.g. change "/class cast" to "/spell")

### Editor
- Added folders for organizing skills
- Arrow keys now move the selected effect
- Added copy/paste functionality for effects, even across skills
- General look and feel has been redesigned
