package com.sucy.skill.api.values

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.task.Task
import com.sucy.skill.util.math.toTicks
import java.util.*
import java.util.function.Consumer

/**
 * Handles keeping tack of stacks for a [Value], whether it is a base, bonus, or
 * multiplicative part. Each time the stacks change, whether that's due to a new
 * one being added or a previous one expiring, [onChange] will be invoked with the
 * updated stack value.
 */
class ValueStacks(val default: Double, val onChange: Consumer<Double>) {
    private val tasks = HashMap<UUID, Task>()
    private val values = LinkedHashMap<UUID, Double>()
    private var value = default
    private var maxStacks = Int.MAX_VALUE

    private var timerType: TimerType = TimerType.OVERWRITE

    /**
     * Adds a stack for the value. Using different [TimerType] options on the same
     * [ValueStacks] will use the most recent one.
     */
    fun apply(value: Double, duration: Double, type: TimerType, maxStacks: Int = Int.MAX_VALUE) {
        this.timerType = type
        this.maxStacks = maxStacks

        val id = UUID.randomUUID()
        values[id] = value
        tasks[id] = SkillAPI.server.taskManager.run(Runnable {
            tasks.remove(id)
            values.remove(id)
            resolve()
        }, duration.toTicks())

        resolve()
    }

    /**
     * Resolves active stacks to a single value for applying to [Value] data.
     * See [TimerType] for details on what each one is meant to do.
     */
    fun resolve() {
        value = when (timerType) {
            TimerType.OVERWRITE -> {
                trim(1)
                values.values.first()
            }
            TimerType.HIGHEST -> trim(true)
            TimerType.LOWEST -> trim(true)
            TimerType.SEPARATE -> {
                trim(maxStacks)
                values.values.sum()
            }
            TimerType.SEPARATE_HIGHEST -> values.values.max() ?: default
            TimerType.SEPARATE_LOWEST -> values.values.min() ?: default
            TimerType.SEPARATE_OLDEST -> {
                trim(maxStacks)
                values.values.first()
            }
        }
        onChange.accept(value)
    }

    /**
     * Finds the highest or lowest value stacks, depending on [high], removes the
     * rest, and returns the remaining value.
     */
    fun trim(high: Boolean): Double {
        val target = if (high) values.values.max() else values.values.min()
        values.keys.forEach {
            if (values[it] != target) {
                values.remove(it)
                tasks.remove(it)?.cancel()
            }
        }
        return target ?: default
    }

    /**
     * Removes stacks to reach the specified max stack size
     */
    fun trim(size: Int) {
        while (tasks.size > size) {
            val id = tasks.keys.first()
            tasks.remove(id)?.cancel()
            values.remove(id)
        }
    }
}