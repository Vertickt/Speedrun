package io.github.vertickt.speedrun.games

import io.github.vertickt.speedrun.gamemanger.GameManager
import io.github.vertickt.speedrun.gamemanger.round.Round
import net.axay.kspigot.event.listen
import net.axay.kspigot.event.register
import net.axay.kspigot.event.unregister
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.server
import net.axay.kspigot.items.itemStack
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.player.PlayerMoveEvent

object Portal : GameManager() {
    override val name = "Portal"
    val world get() = server.getWorld(this.name)

    val blockList = listOf(
        Material.PACKED_MUD,
        Material.TARGET,
        Material.RESIN_BLOCK,
        Material.WAXED_CUT_COPPER,
        Material.WARPED_HYPHAE,
        Material.PRISMARINE,
        Material.CLAY,
        Material.SNOW_BLOCK,
        Material.SMOOTH_BASALT,
        Material.DEEPSLATE_EMERALD_ORE,
        Material.MUSHROOM_STEM,
        Material.WET_SPONGE,
        Material.PEARLESCENT_FROGLIGHT,
        Material.HONEYCOMB_BLOCK,
        Material.AMETHYST_BLOCK
        )

    override fun onEnable() {
        var index = 0
        val block = blockList.random()
        onlinePlayers.forEach { player ->
            player.teleport(Location(world, 13.5, 100.0, 13.5, 135f, 0f).add(0.0, 0.0, 16.0 * index))
            index++

            player.apply {
                inventory.addItem(itemStack(Material.IRON_PICKAXE) {})
                inventory.addItem(itemStack(Material.WATER_BUCKET) {})
                inventory.addItem(itemStack(Material.FLINT_AND_STEEL) {})
                inventory.addItem(itemStack(block) { amount = 64 })
            }
        }
        netherPortal.register()
    }

    private val netherPortal = listen<PlayerMoveEvent>(register = false) {
        if (it.to.block.type != Material.NETHER_PORTAL) return@listen
        if(it.player.gameMode != GameMode.SURVIVAL) return@listen
        Round.win(it.player)
    }

    override fun onDisable() {
        netherPortal.unregister()
    }
}