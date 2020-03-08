package com.sucy.skill.data.store

import java.lang.RuntimeException

class DataLockedException(message: String) : RuntimeException(message)