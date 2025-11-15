package io.github.vertickt.speedrun.gamemanger

abstract class GameManager() {
    abstract val name: String

    abstract fun onEnable()
    abstract fun onDisable()
}