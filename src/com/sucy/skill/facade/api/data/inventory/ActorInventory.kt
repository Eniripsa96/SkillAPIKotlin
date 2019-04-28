package com.sucy.skill.facade.api.data.inventory

import com.sucy.skill.facade.api.data.Item

interface ActorInventory {
    var mainHand: Item?
    var offHand: Item?
    var helmet: Item?
    var chestplate: Item?
    var leggings: Item?
    var boots: Item?

    var heldItemSlot: Int

    val size: Int

    val contents: Array<Item?>

    operator fun get(index: Int): Item?
    operator fun set(index: Int, item: Item?)
}