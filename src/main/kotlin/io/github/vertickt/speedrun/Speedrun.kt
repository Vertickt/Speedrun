package io.github.vertickt.speedrun

import io.github.vertickt.speedrun.command.StartCommand
import io.github.vertickt.speedrun.gamemanger.Round
import io.github.vertickt.speedrun.listener.ConnectionListener
import net.axay.kspigot.main.KSpigot

class Speedrun : KSpigot() {

    override fun startup() {
        //listener
        ConnectionListener

        //gamemanager
        Round

        //command
        StartCommand()
    }

    override fun shutdown() {
        // Plugin shutdown logic
    }
}
