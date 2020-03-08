package com.sucy.skill.facade.api

import com.sucy.skill.task.Task
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

interface Scheduler {

    fun run(task: () -> Unit) = run(0, task)
    fun run(delayInTicks: Long, task: () -> Unit)
    fun run(delayInTicks: Long, intervalInTicks: Long, task: () -> Unit)

    fun clearTasks()

    fun runAsync(task: () -> Unit) {
        AsyncHandler.register(Task(runnable = task))
    }

    fun runAsync(delayInTicks: Int, task: () -> Unit) {
        AsyncHandler.register(Task(delay = delayInTicks, runnable = task))
    }

    fun runAsync(delayInTicks: Int, intervalInTicks: Int, task: () -> Unit) {
        AsyncHandler.register(Task(delay = delayInTicks, interval = intervalInTicks, runnable = task))
    }

    fun runAsync(task: Task) {
        AsyncHandler.register(task)
    }

    fun initialize() {
        AsyncHandler.initialize()
    }

    fun tearDown() {
        AsyncHandler.tearDown()
    }

    /**
     * Handles spawning a separate thread and running tasks on it with the
     * ability to force all tasks to run before exiting for purposes such as
     * clean up being guaranteed to execute before the server shuts down.
     */
    private object AsyncHandler {
        private val lock = ReentrantLock()
        private var time = System.currentTimeMillis()
        private var enabled = false
        private val tasks = mutableListOf<Task>()
        private var thread: Thread? = null

        fun register(task: Task) {
            tasks += task
        }

        /**
         * Creates and starts the thread if one isn't already running
         */
        fun initialize() {
            if (thread == null) {
                thread = Thread(this::run, "SkillAPIAsync")
                thread?.start()
            }
        }

        /**
         * Runs any tasks that haven't been executed and then stops the thread.
         */
        fun tearDown() {
            thread?.let {
                lock.withLock {
                    tasks.forEach { it.run() }
                    tasks.clear()
                    enabled = false
                    thread = null
                }
            }
        }

        private fun run() {
            while (true) {
                lock.withLock {
                    if (!enabled) return

                    val iterator = tasks.iterator()
                    while (iterator.hasNext()) {
                        val task = iterator.next()
                        if (task.tick()) {
                            iterator.remove()
                        }
                    }
                }

                val currentTime = System.currentTimeMillis()
                time += 50 // one tick
                if (time - currentTime >= 1) {
                    Thread.sleep(time - currentTime)
                }
            }
        }
    }
}