package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class FoodMechanic : Mechanic() {
    override val key = "food"

    private lateinit var food: DynamicFormula
    private lateinit var saturation: DynamicFormula

    override fun initialize() {
        super.initialize()

        food = metadata.getFormula("food", 1.0)
        saturation = metadata.getFormula("saturation", 1.0)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val foodChange = compute(food, context, target)
        val saturationChange = compute(saturation, context, target)

        recipient.food += foodChange
        recipient.saturation += saturationChange

        return true
    }
}
