package com.sucy.skill.api.values

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.managers.Task
import com.sucy.skill.util.Constants.NO_OP_TASK

/**
 * SkillAPIKotlin © 2018
 */
class FlagSet {
    private val flags = HashMap<String, Task>()

    fun isActive(flag: String): Boolean {
        return flags.contains(flag)
    }

    fun set(flag: String) {
        flags.put(flag, NO_OP_TASK)?.cancel()
    }

    fun set(flag: String, duration: Long): Task {
        val task = SkillAPI.server.taskManager.run(duration) { clear(flag) }
        flags.put(flag, task)?.cancel()
        return task
    }

    fun clear(flag: String): Boolean {
        val task = flags.remove(flag)
        task?.cancel()
        return task != null
    }
}
