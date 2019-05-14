package com.sucy.skill.api.player

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.data.GameMode
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.entity.Player
import java.lang.IllegalStateException

class SkillBar(private val account: PlayerAccount) {
    val slots = HashMap<Int, Slot>()
    var enabled = true
        private set
    var shown = false
        private set

    fun getSlotType(slot: Int): SlotType {
        return slots[slot]?.type ?: SlotType.WEAPON
    }

    fun reserve(slot: Int) {
        slots[slot] = Slot(SlotType.RESERVED)
    }

    fun firstWeaponSlot(): Int {
        return (0..8).find { getSlotType(it) == SlotType.WEAPON }
                ?: throw IllegalStateException("Found no weapon slots for skill bar")
    }

    fun toggle(player: Player) {
        enabled = !enabled
        if (enabled) {
            show(player)
        } else {
            hide(player)
        }
    }

    fun hide(player: Player, itemHandler: (Item) -> Unit = { }) {
        if (!shown) return

        val inventory = player.inventory
        forEachSlot { index, _ ->
            inventory[index]?.let(itemHandler)
            inventory[index] = null
        }
        shown = false
    }

    fun show(player: Player): Boolean {
        if (!enabled || player.gameMode == GameMode.CREATIVE) return false

        // Verify space exists
        val inventory = player.inventory
        val openSlots = inventory.getOpenSlots()
        val itemsInSkillSlots = slots.count { !it.value.type.item && !inventory.isEmpty(it.key) }
        if (openSlots < itemsInSkillSlots) {
            enabled = false
            return false
        }

        // Move active held item slot
        if (getType(inventory.heldItemSlot) != SlotType.WEAPON) {
            slots.keys.find { getType(it) == SlotType.WEAPON }?.let {
                inventory.heldItemSlot = it
            }
        }

        // Populate indicators
        forEachSlot { index, _ ->
            val item = inventory[index]
            inventory[index] = SkillAPI.settings.skillBar.unassignedIcon
            item?.let { inventory.give(it) }
        }

        shown = true
        update(player)
        return true
    }

    fun update(player: Player) {
        if (!shown) {
            show(player)
            return
        }

        val inventory = player.inventory
        forEachSlot { index, slot ->
            val skill = slot.skill?.let { player.skills[it] }
            val icon = if (skill == null || !skill.isUnlocked) {
                SkillAPI.settings.skillBar.unassignedIcon
            } else {
                skill.iconFor(player)
            }
            inventory[index] = icon
        }
    }

    fun getType(slot: Int): SlotType {
        return slots[slot]?.type ?: SlotType.WEAPON
    }

    fun reset() {
        forEachSlot { index, _ -> slots[index] = Slot(SlotType.UNASSIGNED) }
    }

    private fun forEachSlot(item: Boolean = false, handler: (Int, Slot) -> Unit) {
        for (i in 0..8) {
            val slot = slots[i] ?: Slot(SlotType.WEAPON)
            if (item == slot.type.item) {
                handler.invoke(i, slot)
            }
        }
    }

    enum class SlotType(val item: Boolean) { SKILL(false), UNASSIGNED(false), RESERVED(true), WEAPON(true) }
    data class Slot(val type: SlotType, val skill: String? = null)
}