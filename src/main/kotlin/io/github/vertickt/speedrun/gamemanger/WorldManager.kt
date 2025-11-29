package io.github.vertickt.speedrun.gamemanger

import io.github.vertickt.speedrun.gamemanger.round.Round.activeModes
import io.github.vertickt.speedrun.games.BowCrystal
import io.github.vertickt.speedrun.games.EndPortalSearch
import io.github.vertickt.speedrun.games.Portal
import io.github.vertickt.speedrun.util.generator.EndPortalSearchGenerator
import io.github.vertickt.speedrun.util.generator.VoidWorldGenerator
import io.papermc.paper.registry.entry.RegistryEntryMeta
import net.axay.kspigot.extensions.server
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.craftbukkit.CraftWorld
import org.codehaus.plexus.util.FileUtils
import java.io.File

object WorldManager {
    fun loadGameWorlds() {
        activeModes.forEach { game ->
            val creator = WorldCreator(game.name)
            when (game) {
                EndPortalSearch -> return
                BowCrystal -> return
                Portal -> return
                else -> creator.createWorld()
            }
        }
    }

    fun createGameWorld(game: GameManager) {
        FileUtils.deleteDirectory(game.name)
        val creator = WorldCreator(game.name)
        when (game) {
            EndPortalSearch -> creator.generator(EndPortalSearchGenerator())
            BowCrystal -> creator.environment(World.Environment.THE_END)
            Portal -> FileUtils.copyDirectoryStructure(File("template_portal"), File("Portal"))
            else -> creator.generator(VoidWorldGenerator())
        }
        val world = creator.createWorld()
        if (game == BowCrystal) { (world as CraftWorld).handle.dragonFight = null }
    }

    fun deleteGameWorld(game: GameManager) {
        server.unloadWorld(game.name, false)
        FileUtils.deleteDirectory(game.name)
    }
}