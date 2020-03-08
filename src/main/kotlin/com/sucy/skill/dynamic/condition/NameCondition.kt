package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match
import java.util.regex.Pattern

class NameCondition : Condition() {
    override val key = "name"

    private var matchType = MatchType.EXACT
    private var name = "Boss"
    private var pattern = Pattern.compile(name)

    override fun initialize() {
        super.initialize()

        matchType = MatchType::class.match(metadata.getString("comparison", "exact"), MatchType.EXACT)
        name = metadata.getString("name", name)
        if (matchType == MatchType.REGEX || matchType == MatchType.NOT_REGEX) {
            pattern = Pattern.compile(name)
        }
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return when (matchType) {
            MatchType.EXACT -> recipient.name == name
            MatchType.CONTAINS -> recipient.name.contains(name)
            MatchType.NOT_CONTAINS -> !recipient.name.contains(name)
            MatchType.REGEX -> pattern.matcher(recipient.name).find()
            MatchType.NOT_REGEX -> !pattern.matcher(recipient.name).find()
        }
    }

    enum class MatchType {
        EXACT,
        CONTAINS,
        NOT_CONTAINS,
        REGEX,
        NOT_REGEX
    }
}