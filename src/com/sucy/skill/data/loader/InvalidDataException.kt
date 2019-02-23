package com.sucy.skill.data.loader

import java.lang.RuntimeException

class InvalidDataException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)