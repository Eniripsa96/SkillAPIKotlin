package com.sucy.skill.facade.api.dependency

import com.sucy.skill.facade.api.dependency.api.PlaceholderAPI

class Dependency<T> {
    companion object {
        val PLACEHOLDER_API = Dependency<PlaceholderAPI>()
    }
}