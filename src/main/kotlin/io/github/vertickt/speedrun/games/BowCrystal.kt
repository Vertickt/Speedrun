package io.github.vertickt.speedrun.games

import io.github.vertickt.speedrun.gamemanger.GameManager
import io.github.vertickt.speedrun.gamemanger.round.Round

object BowCrystal : GameManager() {
    override val name = "BowCrystal"

    override fun onEnable() {
        Round.defaultTeleportPlayers(this)
    }

    override fun onDisable() {
    }

}