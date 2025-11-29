package io.github.vertickt.speedrun.command

import io.github.vertickt.speedrun.gamemanger.round.InGame
import io.github.vertickt.speedrun.gamemanger.round.Round
import io.github.vertickt.speedrun.games.EndPortalSearch
import io.github.vertickt.speedrun.games.Portal
import io.github.vertickt.speedrun.util.mm
import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs
import net.axay.kspigot.commands.suggestList

class StartCommand {
    val startCommand = command("start") {
        requiresPermission("speedrun.command.start")
        runs {
            Round.forceStart = true
        }
        argument<String>("mode") {
            suggestList { Round.activeModes.map { it.name } }
            runs {
                Round.forceStart = true
                InGame.forceGame = true
                val input = getArgument<String>("mode")
                var mode = Round.activeModes.firstOrNull { it.name == input }
                if (mode == null) {
                    player.sendMessage(mm("<red>Mode not found!"))
                    return@runs
                }
                InGame.game = mode
            }
        }
    }
}