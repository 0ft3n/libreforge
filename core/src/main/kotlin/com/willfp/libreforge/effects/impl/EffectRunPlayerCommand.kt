package com.willfp.libreforge.effects.impl

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.eco.core.placeholder.translatePlaceholders
import com.willfp.libreforge.*
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.plugin
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import com.willfp.libreforge.triggers.run
import org.bukkit.entity.Player

object EffectRunPlayerCommand : Effect<NoCompileData>("run_player_command") {
    override val parameters = setOf(
        TriggerParameter.PLAYER
    )

    override val arguments = arguments {
        require(listOf("commands", "command"), "You must specify the command to run!")
    }

    override fun onTrigger(config: Config, data: TriggerData, compileData: NoCompileData): Boolean {
        val player = data.player ?: return false
        val victim = data.victim as? Player

        val commands = config.getStrings("commands", "command")
            .map { it.replace("%player%", player.name)
                it.replace("%victim%", victim?.name ?: "")}
            .map { it.translatePlaceholders(config.toPlaceholderContext(data)) }

        val isOp = player.isOp

        plugin.scheduler.run(player) {
            commands.forEach {
                try {
                    if (!isOp) {
                        player.isOp = config.getBool("as_op")
                    }
                    player.performCommand(it)
                } finally {
                    player.isOp = isOp
                }
            }
        }

        return true
    }
}
