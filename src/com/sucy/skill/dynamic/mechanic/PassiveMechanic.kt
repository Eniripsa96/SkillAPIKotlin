package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.managers.Task
import com.sucy.skill.util.math.toTicks
import java.util.*

class PassiveMechanic : Effect() {
    override val key = "passive"
    override val type = EffectType.MECHANIC

    private var delay = 40L
    private var interval = 40L

    private val tasks = HashMap<UUID, Task>()

    override fun initialize() {
        delay = metadata.getDouble("delay", 2.0).toTicks()
        interval = metadata.getDouble("interval", 2.0).toTicks()
    }

    override fun doCleanUp(caster: Actor) {
        tasks.remove(caster.uuid)?.cancel()
    }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        doCleanUp(context.caster)

        var task: Task? = null

        val taskTargets = ArrayList(targets)
        task = SkillAPI.server.taskManager.schedule(delay, interval) {
            taskTargets.removeIf { it.dead || !it.exists }
            if (taskTargets.isEmpty()) {
                task?.cancel()
                return@schedule
            }
            executeChildren(context.copy(cancellable = task), targets)
        }
        tasks[context.caster.uuid] = task

        return true
    }
}