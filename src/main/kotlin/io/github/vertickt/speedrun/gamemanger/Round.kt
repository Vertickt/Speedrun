package io.github.vertickt.speedrun.gamemanger

import io.github.vertickt.speedrun.games.BowCrystal
import io.github.vertickt.speedrun.games.CraftingRace
import io.github.vertickt.speedrun.games.EndPortalSearch
import io.github.vertickt.speedrun.games.Portal
import io.github.vertickt.speedrun.util.generator.EndPortalSearchGenerator
import io.github.vertickt.speedrun.util.generator.VoidWorldGenerator
import io.github.vertickt.speedrun.util.mm
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.server
import net.axay.kspigot.runnables.task
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.TitlePart
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.WorldCreator
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

object Round {
    private var countdown = Duration.ZERO
    var forceStart = false
    var inGame = false
    //val activeModes = listOf(EndPortalSearch, BowCrystal, CraftingRace, Portal)
    val activeModes = listOf(EndPortalSearch)

    private val maxPlayers get() = server.maxPlayers
    private val currentPlayers get() = onlinePlayers.size

    private fun schedule() {
        task(true, 0, 20) {
            when {
                shouldWaitForPlayers() -> handleWaiting()
                countdown > Duration.ZERO -> handleCountdown()
                !inGame -> handleGame()
            }
        }
    }

    private fun shouldWaitForPlayers() =
        currentPlayers < maxPlayers && !forceStart

    private fun handleWaiting() {
        countdown = 10.seconds
        onlinePlayers.forEach { player ->
            player.sendActionBar(mm("<red>Waiting for players... (${currentPlayers}/${maxPlayers})"))
        }
    }

    private fun handleCountdown() {
        onlinePlayers.forEach { player ->
            player.apply {
                playSound(location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                sendActionBar(mm("<yellow>Game is starting in <b>$countdown"))
            }
        }
        countdown -= 1.seconds
    }

    private fun handleGame() {
        val game = activeModes.random()
        inGame = true

        sendGameTitle(game)
        task(delay = 5) {
            game.onEnable()
        }
    }

    fun defaultTeleportPlayers(game: GameManager) {
        val location = Location(server.getWorld(game.name), 0.5, 100.0, 0.5, 0f, 0f)
        onlinePlayers.forEach { player ->
            player.teleportAsync(location)
        }
    }

    private fun sendGameTitle(game: GameManager) {
        val titleTimes = Title.Times.times(
            0.seconds.toJavaDuration(),
            4.seconds.toJavaDuration(),
            1.seconds.toJavaDuration()
        )

        onlinePlayers.forEach { player ->
            player.apply {
                sendActionBar(mm("<green>The game begins"))
                playSound(player.location, Sound.BLOCK_BEACON_ACTIVATE, 1f, 1f)
                sendTitlePart(TitlePart.TIMES, titleTimes)
                sendTitlePart(TitlePart.TITLE, mm("<green><b>PLAYING"))
                sendTitlePart(TitlePart.SUBTITLE, mm("<green><b>${game.name}"))
            }
        }
    }

    private fun loadGameWorlds() {
        activeModes.forEach { game ->
            val creator = WorldCreator(game.name)
            when(game) {
                EndPortalSearch -> creator.generator(EndPortalSearchGenerator())
                else -> creator.generator(VoidWorldGenerator())
            }
            creator.createWorld()
        }
    }

    init {
        loadGameWorlds()
        schedule()
    }
}