package com.sucy.skill.facade.api.data.inventory

interface ActorInventory : Inventory {
    var mainHand: Item?
    var offHand: Item?
    var helmet: Item?
    var chestplate: Item?
    var leggings: Item?
    var boots: Item?

    var heldItemSlot: Int
}