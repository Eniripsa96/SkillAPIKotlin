package com.sucy.skill.api

/**
 *
 * Interface for plugins that define new classes and skills
 *
 * Make sure to only add the appropriate type in each method
 * (e.g. adding classes in the registerClasses method and skills
 * in the registerSkills method). It keeps the API working nicely!
 */
interface SkillPlugin {
    /**
     * Method to add new skills to the game
     *
     * Use api.addSkills(Skill ... skills) to add them
     *
     * This is called before registerClasses so if you want to keep
     * a reference of the API, you can store the api reference into one
     * of your own fields
     */
    fun getSkills(): Iterable<RPGElement>

    /**
     * Method to add new classes to the game
     *
     * Use api.addClasses(RPGClass ... classes) to add them
     *
     * This is called after registerSkills
     */
    fun getProfessions(): Iterable<RPGElement>
}

