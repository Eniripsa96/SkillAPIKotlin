package com.sucy.skill.facade.api.dependency

class Dependencies {
    private val registered = mutableMapOf<Dependency<*>, Any>()

    internal fun <T : Any> register(dependency: Dependency<T>, handler: T) {
        registered[dependency] = handler
    }

    fun <T> get(dependency: Dependency<T>): T? {
        return registered[dependency] as T?
    }
}