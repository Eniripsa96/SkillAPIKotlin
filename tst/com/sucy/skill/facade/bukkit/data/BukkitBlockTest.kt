package com.sucy.skill.facade.bukkit.data

import com.sucy.skill.util.text.titleCase
import org.bukkit.Material
import org.junit.Test

class BukkitBlockTest {
    @Test
    fun mats() {
        val blocks = Material.values()
                .filter { it.isItem }
                .map { it.name.titleCase() }
                .filter { !it.startsWith("Legacy") }
                .joinToString("\",\n\"", "ITEMS: [\n\"", "\"],")
        System.out.println(blocks)
    }
}