package com.sucy.skill.dynamic.fakes

import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.api.data.inventory.Item

object FakeInventory : ActorInventory {
    override var mainHand: Item? = null
    override var offHand: Item? = null
    override var helmet: Item? = null
    override var chestplate: Item? = null
    override var leggings: Item? = null
    override var boots: Item? = null
    override var heldItemSlot: Int = 0
    override val size: Int = 0
    override val contents: List<Item?> = emptyList()
    override fun get(index: Int): Item? = null
    override fun set(index: Int, item: Item?) {}
    override fun give(item: Item): Item? = null
}