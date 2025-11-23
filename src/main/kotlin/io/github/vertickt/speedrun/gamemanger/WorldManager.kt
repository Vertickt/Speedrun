package io.github.vertickt.speedrun.gamemanger

import io.github.vertickt.speedrun.gamemanger.round.Round.activeModes
import io.github.vertickt.speedrun.games.BowCrystal
import io.github.vertickt.speedrun.games.EndPortalSearch
import io.github.vertickt.speedrun.games.Portal
import io.github.vertickt.speedrun.util.generator.EndPortalSearchGenerator
import io.github.vertickt.speedrun.util.generator.VoidWorldGenerator
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.server
import org.bukkit.WorldCreator
import org.codehaus.plexus.util.FileUtils

object WorldManager {
    fun loadGameWorlds() {
        activeModes.forEach { game ->
            val creator = WorldCreator(game.name)
            when (game) {
                EndPortalSearch -> return
                Portal -> return
                BowCrystal -> return
                else -> creator.createWorld()
            }
        }
    }

    fun createGameWorld(game: GameManager) {
        FileUtils.deleteDirectory(game.name)
        val creator = WorldCreator(game.name)
        when (game) {
            EndPortalSearch -> creator.generator(EndPortalSearchGenerator())
            else -> creator.generator(VoidWorldGenerator())
        }
        creator.createWorld()
    }

    fun deleteGameWorld(game: GameManager) {
        server.unloadWorld(game.name, false)
        FileUtils.deleteDirectory(game.name)
    }
}