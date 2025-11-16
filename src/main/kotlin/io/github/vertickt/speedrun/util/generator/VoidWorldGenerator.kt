package io.github.vertickt.speedrun.util.generator

import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.Random

class VoidWorldGenerator : ChunkGenerator() {
    private val biomeProvider = object : BiomeProvider() {
        override fun getBiome(worldInfo: WorldInfo, x: Int, y: Int, z: Int): Biome {
            return Biome.THE_VOID
        }

        override fun getBiomes(worldInfo: WorldInfo): List<Biome> {
            return listOf(Biome.THE_VOID)
        }
    }

    override fun getDefaultBiomeProvider(worldInfo: WorldInfo): BiomeProvider {
        return biomeProvider
    }

    override fun generateNoise(
        worldInfo: WorldInfo,
        random: Random,
        chunkX: Int,
        chunkZ: Int,
        chunkData: ChunkData
    ) {
    }

    override fun generateSurface(
        worldInfo: WorldInfo,
        random: Random,
        chunkX: Int,
        chunkZ: Int,
        chunkData: ChunkData
    ) {
        val blockX = chunkX * 16
        val blockZ = chunkZ * 16

        if (blockX <= 0 && blockX + 15 >= 0 && blockZ <= 0 && blockZ + 15 >= 0) {
            val relX = 0
            val relZ = 0

            chunkData.setBlock(relX, 99, relZ, Material.BEDROCK)
        }
    }

    override fun shouldGenerateNoise(): Boolean = false
    override fun shouldGenerateSurface(): Boolean = true
    override fun shouldGenerateCaves(): Boolean = false
    override fun shouldGenerateDecorations(): Boolean = false
    override fun shouldGenerateMobs(): Boolean = false
    override fun shouldGenerateStructures(): Boolean = false
}