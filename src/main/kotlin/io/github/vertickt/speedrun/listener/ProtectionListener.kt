package io.github.vertickt.speedrun.listener

import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.worlds
import net.axay.kspigot.runnables.task
import org.bukkit.GameMode
import org.bukkit.entity.EntityType
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerDropItemEvent
import javax.swing.text.html.parser.Entity

object ProtectionListener {
    val blockBreak = listen<BlockBreakEvent> {
        val player = it.player
        if (it.block.world != worlds.first()) return@listen
        if (player.gameMode != GameMode.SURVIVAL) return@listen
        it.isCancelled = true
    }

    val blockPlace = listen<BlockPlaceEvent> {
        val player = it.player
        if (it.block.world != worlds.first()) return@listen
        if (player.gameMode != GameMode.SURVIVAL) return@listen
        it.isCancelled = true
    }

    val hungerEvent = listen<FoodLevelChangeEvent> {
        it.entity.foodLevel = 40
        it.isCancelled = true
    }

    val damageEvent = listen<EntityDamageEvent> {
        if (it.entity.type == EntityType.END_CRYSTAL || it.entity.type == EntityType.ENDER_DRAGON) return@listen
        it.isCancelled = true
    }

    val dropEvent = listen<PlayerDropItemEvent> {
        it.isCancelled = true
    }

    val spawnEntity = listen<EntitySpawnEvent> {
        if (it.entity.entitySpawnReason == CreatureSpawnEvent.SpawnReason.CUSTOM) return@listen
        if (it.entity.type == EntityType.END_CRYSTAL || it.entity.type == EntityType.ARROW || it.entity.type == EntityType.ENDER_DRAGON) return@listen
        it.isCancelled = true
    }
}