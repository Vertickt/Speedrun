package io.github.vertickt.speedrun.util.generator

import org.bukkit.generator.ChunkGenerator

class EndPortalSearchGenerator : ChunkGenerator() {
    override fun shouldGenerateNoise() = true
    override fun shouldGenerateSurface() = false
    override fun shouldGenerateCaves() = false
    override fun shouldGenerateStructures() = true
    override fun shouldGenerateDecorations() = true
    override fun shouldGenerateMobs() = false
}