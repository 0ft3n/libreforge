package com.willfp.libreforge.effects.arguments.impl

import com.willfp.eco.core.map.nestedMap
import com.willfp.libreforge.ConfigurableElement
import com.willfp.libreforge.NoCompileData
import com.willfp.libreforge.effects.arguments.EffectArgument
import com.willfp.libreforge.getIntFromExpression
import com.willfp.libreforge.triggers.DispatchedTrigger
import java.util.UUID

object ArgumentEveryValue: EffectArgument<NoCompileData>("every_value") {
    private val everyHandler = nestedMap<UUID, UUID, Double>()

    override fun isMet(element: ConfigurableElement, trigger: DispatchedTrigger, compileData: NoCompileData): Boolean {
        val current = everyHandler[element.uuid][trigger.dispatcher.uuid] ?: 1.0

        return current == 0.0
    }

    override fun ifMet(element: ConfigurableElement, trigger: DispatchedTrigger, compileData: NoCompileData) {
        increment(element, trigger)
    }

    override fun ifNotMet(element: ConfigurableElement, trigger: DispatchedTrigger, compileData: NoCompileData) {
        increment(element, trigger)
    }

    private fun increment(element: ConfigurableElement, trigger: DispatchedTrigger) {
        val every = element.config.getIntFromExpression("every_value", trigger.data)

        var current = everyHandler[element.uuid][trigger.dispatcher.uuid] ?: 0.0

        current += trigger.data.value

        if (current >= every) {
            current = 0.0
        }

        everyHandler[element.uuid][trigger.dispatcher.uuid] = current
    }
}
