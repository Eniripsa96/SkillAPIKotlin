package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.data.inventory.ItemType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.internal.data.InternalItem
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.text.color

class ItemGiveMechanic : Mechanic() {
    override val key = "item give"

    private lateinit var item: InternalItem
    private lateinit var amount: DynamicFormula

    override fun initialize() {
        super.initialize()

        val custom = metadata.getBoolean("custom", false)
        val material = metadata.getString("material", "arrow")
        val data = metadata.getInt("data")
        val durability = metadata.getInt("durability")
        val name = metadata.getString("name")?.color()
        val lore = metadata.getStringList("lore").color()

        amount = metadata.getFormula("amount")

        item = InternalItem(
                type = ItemType.of(material),
                durability = durability.toShort(),
                data = data.toByte(),
                amount = 1,
                name = if (custom) name else null,
                lore = if (custom) lore else emptyList()
        )
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val number = compute(amount, context, target).toInt()
        val copy = item.copy(amount = number)
        return recipient.inventory.give(copy)?.amount != copy.amount
    }
}