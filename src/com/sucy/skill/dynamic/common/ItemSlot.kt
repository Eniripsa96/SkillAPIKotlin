package com.sucy.skill.dynamic.common

import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.entity.Actor

enum class ItemSlot(private val accessor: (Actor, Int) -> Item?, private val setter: (Actor, Item?, Int) -> Unit) {
    MAIN_HAND(
            { it, _ -> it.inventory.mainHand },
            { actor, item, _ -> actor.inventory.mainHand = item }
    ),
    OFF_HAND(
            { it, _ ->  it.inventory.offHand },
            { actor, item, _ -> actor.inventory.offHand = item }
    ),
    HELMET(
            { it, _ -> it.inventory.helmet },
            { actor, item, _ -> actor.inventory.helmet = item }
    ),
    CHESTPLATE(
            { it, _ -> it.inventory.chestplate },
            { actor, item, _ -> actor.inventory.chestplate = item }
    ),
    LEGGINGS(
            { it, _ -> it.inventory.leggings },
            { actor, item, _ -> actor.inventory.leggings = item }
    ),
    BOOTS(
            { it, _ -> it.inventory.boots },
            { actor, item, _ -> actor.inventory.boots = item }
    ),
    SLOT(
            { it, slot -> it.inventory[slot] },
            { actor, item, slot -> actor.inventory[slot] = item }
    ),
    ANY({ _, _ -> null }, { _, _, _ -> });

    fun getItem(actor: Actor, slot: Int): Item? = accessor.invoke(actor, slot)
    fun setItem(actor: Actor, item: Item?, slot: Int) = setter.invoke(actor, item, slot)
}
