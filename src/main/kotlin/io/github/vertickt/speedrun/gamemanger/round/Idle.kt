package io.github.vertickt.speedrun.gamemanger.round

import io.github.vertickt.speedrun.gamemanger.round.Round.countdown
import io.github.vertickt.speedrun.gamemanger.round.Round.currentPlayers
import io.github.vertickt.speedrun.gamemanger.round.Round.maxPlayers
import io.github.vertickt.speedrun.util.mm
import net.axay.kspigot.extensions.onlinePlayers
import org.bukkit.Sound
import kotlin.time.Duration.Companion.seconds

object Idle {
    fun handleWaiting() {
        countdown = 10.seconds
        val display = mm("<red>Waiting for players... (${currentPlayers}/${maxPlayers})")
        onlinePlayers.forEach { player ->
            player.sendActionBar(display)
        }
    }

    fun handleCountdown() {
        val display = mm("<yellow>Game is starting in <b>$countdown")
        onlinePlayers.forEach { player ->
            player.apply {
                playSound(location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                sendActionBar(display)
            }
        }
        countdown -= 1.seconds
    }
}