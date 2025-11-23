package io.github.vertickt.speedrun.util

import net.axay.kspigot.extensions.worlds
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.Firework
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

val spawn = Location(worlds.first(), 0.5, 100.0, 0.5, 0f, 0f)

fun mm(input: String): Component {
    return MiniMessage.miniMessage().deserialize(input)
}

fun spawnRandomFireworkAbove(location: Location) {
    val world = location.world ?: return
    val firework = world.spawn(location, Firework::class.java)

    val meta = firework.fireworkMeta
    meta.power = 0


    val primary = Color.fromRGB(
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )

    val fade = Color.fromRGB(
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )

    val effect = FireworkEffect.builder()
        .with(FireworkEffect.Type.BURST)
        .withColor(primary)
        .withFade(fade)
        .trail(true)
        .flicker(true)
        .build()

    meta.addEffect(effect)
    firework.fireworkMeta = meta
}

val titleTimes = Title.Times.times(
    0.seconds.toJavaDuration(),
    4.seconds.toJavaDuration(),
    1.seconds.toJavaDuration()
)