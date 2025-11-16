package io.github.vertickt.speedrun.games

import io.github.vertickt.speedrun.gamemanger.GameManager
import io.github.vertickt.speedrun.gamemanger.Round
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.server
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.runnables.task
import org.bukkit.Material
import org.bukkit.generator.structure.StructureType

object EndPortalSearch : GameManager() {
    override val name = "EndPortalSearch"

    override fun onEnable() {
        Round.defaultTeleportPlayers(this)
        teleportStronghold()

        onlinePlayers.forEach { player ->
            player.inventory.addItem(itemStack(Material.IRON_PICKAXE) {})
            player.inventory.addItem(itemStack(Material.ENDER_PEARL) { amount = 12 })
            player.inventory.addItem(itemStack(Material.BLAZE_ROD) { amount = 6 })
        }

    }

    override fun onDisable() {
    }



    private fun teleportStronghold() {
        task(delay = 3) {
            val world = server.getWorld(name) ?: return@task
            val stronghold = world.locateNearestStructure(
                world.spawnLocation,
                StructureType.STRONGHOLD,
                3000,
                false
            )

            if (stronghold == null) {
                broadcast("error")
                return@task
            }

            val baseLocation = stronghold.location.add(4.0, 0.0, 4.0)
            var strongholdY: Int? = null

            for (y in world.maxHeight downTo world.minHeight) {
                val block = world.getBlockAt(baseLocation.blockX, y, baseLocation.blockZ)
                if (block.type == Material.STONE_BRICKS ||
                    block.type == Material.MOSSY_STONE_BRICKS ||
                    block.type == Material.CRACKED_STONE_BRICKS
                ) {
                    val blockAbove = world.getBlockAt(block.location.add(0.0, 1.0, 0.0))
                    if (blockAbove.type == Material.AIR || !blockAbove.type.isSolid) {
                        strongholdY = y + 1
                        break
                    }
                }
            }

            if (strongholdY == null) {
                broadcast("error")
                return@task
            }

            val teleportLocation = baseLocation.clone()
            teleportLocation.x = baseLocation.blockX + 0.5
            teleportLocation.y = strongholdY.toDouble()
            teleportLocation.z = baseLocation.blockZ + 0.5

            onlinePlayers.forEach { player ->
                player.teleportAsync(teleportLocation)
            }
        }
    }
}