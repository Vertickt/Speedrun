package io.github.vertickt.speedrun.games

import io.github.vertickt.speedrun.gamemanger.GameManager
import io.github.vertickt.speedrun.gamemanger.Round

object Portal : GameManager() {
    override val name = "Portal"

    override fun onEnable() {
        Round.defaultTeleportPlayers(this)
    }

    override fun onDisable() {
    }
}