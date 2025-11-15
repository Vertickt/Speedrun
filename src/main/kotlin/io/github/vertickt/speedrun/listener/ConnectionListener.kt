package io.github.vertickt.speedrun.listener

import net.axay.kspigot.event.listen
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object ConnectionListener {
    val onJoin = listen<PlayerJoinEvent> {
        it.joinMessage(null)
    }
    val onQuit = listen<PlayerQuitEvent> {
        it.quitMessage(null)
    }
}