package com.sucy.skill.data.loader.impl

import com.sucy.skill.api.LevelProgress
import com.sucy.skill.api.Levelable
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.util.io.Data

abstract class LevelProgressDataLoader<T : LevelProgress<L>, L : Levelable> : DataLoader<T> {
    fun load(result: T, data: Data) {
        result.exp = data.getDouble(EXP, 0.0)
        result.totalExp = data.getDouble(TOTAL_EXP, result.projectedTotalExp)
        result.level = data.getInt(LEVEL, 1)
    }

    override fun serialize(data: T): Data {
        val result = Data()
        result.set(EXP, data.exp)
        result.set(TOTAL_EXP, data.totalExp)
        result.set(LEVEL, data.level)
        return result
    }

    companion object {
        const val EXP = "exp"
        const val TOTAL_EXP = "totalExp"
        const val LEVEL = "level"
    }
}