package com.sucy.skill.data.loader.impl.common

import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.internal.data.InternalLocation
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.math.Vector3

object LocationDataLoader : DataLoader<Location> {
    private const val WORLD = "world"
    private const val X = "x"
    private const val Y = "y"
    private const val Z = "z"
    private const val YAW = "yaw"
    private const val PITCH = "pitch"

    override val requiredKeys: Array<String> = arrayOf()
    override val transformers: Map<Int, DataTransformer> = mapOf()

    override fun load(key: String, data: Data): Location {
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

    override fun serialize(data: Location): Data {
        val coords = data.coords
        val result = Data()
        result.set(WORLD, data.world)
        result.set(X, coords.x)
        result.set(Y, coords.y)
        result.set(Z, coords.z)
        result.set(YAW, data.yaw)
        result.set(PITCH, data.pitch)
        return result
    }
}