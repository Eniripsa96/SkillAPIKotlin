package com.sucy.skill.api.gui.hud

interface HUDNumberDisplay : HUDDisplay {
    val value: Double
    val maximum: Double
    val unit: String?
}