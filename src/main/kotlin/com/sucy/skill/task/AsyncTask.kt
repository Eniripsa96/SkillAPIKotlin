package com.sucy.skill.task

import com.sucy.skill.util.log.Logger
import java.lang.Exception

open class AsyncTask(
        delay: Int = 0,
        private val interval: Int = -1,
        private val runnable: () -> Unit
) {
    private var print = true
    private var expired = false
    private var time = -delay

    fun stop() {
        expired = true
    }

    fun run() = runnable()

    fun tick(): Boolean {
        try {
            time++
            if (time > 0 && (interval < 0 || time % interval == 0)) {
                runnable()
                time = 0
            }
            return expired || interval < 0
        } catch (ex: ConcurrentModificationException) {
            return expired
        } catch (ex: Exception) {
            if (print) {
                Logger.warn("${this::class.simpleName} failed unexpectedly")
                ex.printStackTrace()
                print = false
            }
            return expired || interval < 0
        }
    }
}