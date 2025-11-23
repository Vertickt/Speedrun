package io.github.vertickt.speedrun

import io.github.vertickt.speedrun.command.StartCommand
import io.github.vertickt.speedrun.gamemanger.round.Round
import io.github.vertickt.speedrun.listener.ConnectionListener
import io.github.vertickt.speedrun.listener.ProtectionListener
import net.axay.kspigot.main.KSpigot

class Speedrun : KSpigot() {

    override fun startup() {
        //listener
        ConnectionListener
        ProtectionListener

        //gamemanager
        Round

        //command
        StartCommand()
    }

    override fun shutdown() {
    }
}
