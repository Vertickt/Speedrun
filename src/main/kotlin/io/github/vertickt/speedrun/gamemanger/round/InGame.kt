package io.github.vertickt.speedrun.gamemanger.round

import io.github.vertickt.speedrun.gamemanger.GameManager
import io.github.vertickt.speedrun.gamemanger.WorldManager
import io.github.vertickt.speedrun.gamemanger.round.Round.activeModes
import io.github.vertickt.speedrun.gamemanger.round.Round.inGame
import io.github.vertickt.speedrun.gamemanger.round.Round.timeLeft
import io.github.vertickt.speedrun.util.mm
import io.github.vertickt.speedrun.util.spawn
import io.github.vertickt.speedrun.util.titleTimes
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.task
import net.kyori.adventure.title.TitlePart
import org.bukkit.GameMode
import org.bukkit.Sound
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object InGame {
    lateinit var game: GameManager
    private var nextGameIndex = 0
    var forceGame = false

    fun handleGame() {
        if (!forceGame) {
            game = activeModes[nextGameIndex]
            nextGameIndex++
            if (nextGameIndex == activeModes.size) nextGameIndex = 0
        }
        inGame = true
        timeLeft = 3.minutes

        sendGameTitle(game)
        WorldManager.createGameWorld(game).run {
            task(delay = 5) {
                game.onEnable()
            }
        }
    }

    fun handleInGame() {
        val display = mm("<green>Time left: <b>$timeLeft")
        onlinePlayers.forEach { player ->
            player.sendActionBar(display)
        }
        timeLeft -= 1.seconds
    }

    fun handleGameEnd() {
        Round.forceStart = false
        forceGame = false
        game.onDisable()
        Round.countdown = 10.seconds
        onlinePlayers.forEach { player ->
            player.apply {
                teleport(spawn)
                inventory.clear()
                gameMode = GameMode.SURVIVAL
            }
        }
        WorldManager.deleteGameWorld(game)
        inGame = false
    }

    private fun sendGameTitle(game: GameManager) {
        val display = mm("<green><b>${game.name}")

        onlinePlayers.forEach { player ->
            player.apply {
                sendActionBar(mm("<green>The game begins"))
                playSound(player.location, Sound.BLOCK_BEACON_ACTIVATE, 1f, 1f)
                sendTitlePart(TitlePart.TIMES, titleTimes)
                sendTitlePart(TitlePart.TITLE, mm("<green><b>PLAYING"))
                sendTitlePart(TitlePart.SUBTITLE, display)
            }
        }
    }
}