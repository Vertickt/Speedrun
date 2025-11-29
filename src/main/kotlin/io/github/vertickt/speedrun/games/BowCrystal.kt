package io.github.vertickt.speedrun.games

import io.github.vertickt.speedrun.gamemanger.GameManager
import io.github.vertickt.speedrun.gamemanger.round.Round
import net.axay.kspigot.event.listen
import net.axay.kspigot.event.register
import net.axay.kspigot.event.unregister
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.server
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.runnables.task
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent

object BowCrystal : GameManager() {
    override val name = "BowCrystal"
    val world get() = server.getWorld(this.name)
    var progress = mutableMapOf<EnderCrystal, MutableSet<Player>>()

    override fun onEnable() {
        Round.defaultTeleportPlayers(this)
        onlinePlayers.forEach { player ->
            player.apply {
                inventory.addItem(itemStack(Material.BOW) { addEnchantment(Enchantment.INFINITY, 1) })
                inventory.addItem(itemStack(Material.ARROW) {})
            }
        }

        progress.clear()
        crystalHitEvent.register()
        world?.getEntitiesByClass(EnderCrystal::class.java)?.forEach { entity ->
            progress[entity] = mutableSetOf()
        }
    }

    override fun onDisable() {
        crystalHitEvent.unregister()
    }

    val crystalHitEvent = listen<EntityDamageEvent>(register = false) {
        val entity = it.entity
        if (entity.type != EntityType.END_CRYSTAL) return@listen
        if (it.damageSource.causingEntity !is Player) return@listen
        val player = it.damageSource.causingEntity as Player

        it.isCancelled = true
        val set = progress[it.entity] ?: return@listen
        if (!set.add(player)) return@listen
        replaceObsidianTower(player, it.entity.location)
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)

        if (progress.values.all { player in it }) {
            Round.win(player)
        }
    }

    private fun replaceObsidianTower(receiver: Player, loc: Location) {
        val world = loc.world ?: return
        val topY = loc.y.toInt() - 1
        val bottomY = 0
        val xRange = -6 until 6
        val zRange = -6 until 6

        var currentY = topY

        task(period = 1) {
            if (currentY < bottomY) {
                it.cancel()
                return@task
            }

            for (x in xRange) {
                for (z in zRange) {
                    val block = world.getBlockAt(loc.blockX + x, currentY, loc.blockZ + z)
                    if (block.type == Material.OBSIDIAN) {
                        receiver.sendBlockChange(
                            block.location,
                            Material.EMERALD_BLOCK.createBlockData()
                        )
                    }
                }
            }

            currentY--
        }
    }
}

