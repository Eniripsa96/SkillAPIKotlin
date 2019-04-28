package com.sucy.skill.data.loader.impl

import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.internal.data.InternalLocation
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.math.Vector3

object LocationDataLoader : DataLoader<Location> {
    const val WORLD = "workd"
    const val X = "x"
    const val Y = "y"
    const val Z = "z"
    const val YAW = "yaw"
    const val PITCH = "pitch"

    override val requiredKeys: Array<String> = arrayOf()
    override val transformers: Map<Int, DataTransformer> = mapOf()

    override fun load(data: Data): Location {
        return InternalLocation(
                world = data.getString(WORLD, "world"),
                coords = Vector3(
                        x = data.getDouble(X, 0.0),
                        y = data.getDouble(Y, 0.0),
                        z = data.getDouble(Z, 0.0)
                ),
                yaw = data.getDouble(YAW, 0.0),
                pitch = data.getDouble(PITCH, 0.0)
        )
    }
}