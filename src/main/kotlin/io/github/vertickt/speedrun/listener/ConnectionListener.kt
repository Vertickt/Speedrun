package io.github.vertickt.speedrun.listener

import io.github.vertickt.speedrun.util.spawn
import net.axay.kspigot.event.listen
import org.bukkit.GameMode
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object ConnectionListener {
    val onJoin = listen<PlayerJoinEvent> {
        val player = it.player
        player.apply {
            gameMode = GameMode.SURVIVAL
            inventory.clear()
            teleportAsync(spawn)
        }

        it.joinMessage(null)
    }
    val onQuit = listen<PlayerQuitEvent> {
        it.quitMessage(null)
    }
}