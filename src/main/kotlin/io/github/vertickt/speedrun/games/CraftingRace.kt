package io.github.vertickt.speedrun.games

import io.github.vertickt.speedrun.gamemanger.GameManager
import io.github.vertickt.speedrun.gamemanger.round.Round

object CraftingRace : GameManager() {
    override val name = "CraftingRace"

    override fun onEnable() {
        Round.defaultTeleportPlayers(this)
    }

    override fun onDisable() {
    }
}