package io.github.vertickt.speedrun.gamemanger.round

import io.github.vertickt.speedrun.gamemanger.GameManager
import io.github.vertickt.speedrun.gamemanger.WorldManager
import io.github.vertickt.speedrun.games.BowCrystal
import io.github.vertickt.speedrun.games.EndPortalSearch
import io.github.vertickt.speedrun.games.Portal
import io.github.vertickt.speedrun.util.mm
import io.github.vertickt.speedrun.util.spawnRandomFireworkAbove
import io.github.vertickt.speedrun.util.titleTimes
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.server
import net.axay.kspigot.runnables.task
import net.kyori.adventure.title.TitlePart
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player
import kotlin.time.Duration

object Round {
    var countdown = Duration.ZERO
    var timeLeft = Duration.ZERO
    var forceStart = false
    var inGame = false

    //val activeModes = listOf(EndPortalSearch, BowCrystal, CraftingRace, Portal)
    val activeModes = listOf(BowCrystal, EndPortalSearch, Portal)
    //val activeModes = listOf(Portal)

    val maxPlayers get() = server.maxPlayers
    val currentPlayers get() = onlinePlayers.size

    private fun schedule() {
        task(true, 0, 20) {
            when {
                shouldWaitForPlayers() -> Idle.handleWaiting()
                countdown > Duration.ZERO -> Idle.handleCountdown()
                !inGame -> InGame.handleGame()
                timeLeft > Duration.ZERO -> InGame.handleInGame()
                inGame -> InGame.handleGameEnd()
            }
        }
    }

    private fun shouldWaitForPlayers() =
        currentPlayers < maxPlayers / 2 && !forceStart


    fun defaultTeleportPlayers(game: GameManager) {
        val location = Location(server.getWorld(game.name), 0.5, 100.0, 0.5, 0f, 0f)
        onlinePlayers.forEach { player ->
            player.teleportAsync(location)
        }
    }

    fun win(player: Player) {
        val display = mm("<green><b>${player.name}")
        onlinePlayers.forEach { p ->
            p.apply {
                playSound(p.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f)
                sendTitlePart(TitlePart.TIMES, titleTimes)
                sendTitlePart(TitlePart.TITLE, display)
                sendTitlePart(TitlePart.SUBTITLE, mm("<green><b>WON"))
                spawnRandomFireworkAbove(p.location)
                p.gameMode = GameMode.SPECTATOR
            }
        }
        task(delay = 120) {
            InGame.handleGameEnd()
        }
    }

    init {
        WorldManager.loadGameWorlds()
        schedule()
    }
}