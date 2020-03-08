package com.sucy.skill.facade.internal.data

import com.sucy.skill.facade.api.data.inventory.Inventory
import com.sucy.skill.facade.api.data.inventory.Item

class InternalInventory(itemRows: Int) : Inventory {
    var rows: Int = itemRows
        set(value) {
            size = value * 9
            contents = contents.subList(0, size)
            field = value
        }

    override var size: Int = rows * 9
        private set
    override var contents: MutableList<Item?> = mutableListOf()
        private set

    override fun get(index: Int): Item? {
        return if (contents.size > index) return contents[index] else null
    }

    override fun set(index: Int, item: Item?) {
        require(index in 0..(size - 1)) { "Index $index is not between 0 and $size" }
        contents[index] = item
    }

    override fun give(item: Item): Item? {
        var toAdd = item
        contents.forEachIndexed { index, it ->
            if (it != null && it.type.maxStack > it.amount && it.canStack(item)) {
                if (it.type.maxStack >= it.amount + item.amount) {
                    contents[index] = it.copyWith(amount = it.amount + item.amount)
                    return@give null
                } else {
                    val remainder = toAdd.amount - (it.type.maxStack - it.amount)
                    contents[index] = it.copyWith(amount = it.type.maxStack)
                    toAdd = toAdd.copyWith(amount = remainder)
                }
            }
        }
        return toAdd
    }
}