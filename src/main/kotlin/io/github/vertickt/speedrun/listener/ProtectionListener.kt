package io.github.vertickt.speedrun.listener

import net.axay.kspigot.event.listen
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerDropItemEvent

object ProtectionListener {
    val hungerEvent = listen<FoodLevelChangeEvent> {
        it.entity.foodLevel = 40
        it.isCancelled = true
    }

    val damageEvent = listen<EntityDamageEvent> {
        it.isCancelled = true
    }

    val dropEvent = listen<PlayerDropItemEvent> {
        it.isCancelled = true
    }

    val spawnMob = listen<EntitySpawnEvent> {
        it.isCancelled = true
    }
}