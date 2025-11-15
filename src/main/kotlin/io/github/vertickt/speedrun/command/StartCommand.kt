package io.github.vertickt.speedrun.command

import io.github.vertickt.speedrun.gamemanger.Round
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs

class StartCommand {
    val startCommand = command("start") {
        requiresPermission("speedrun.command.start")
        runs {
            Round.forceStart = true
        }
    }
}