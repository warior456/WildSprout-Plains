package net.wildsprout.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModWorldGeneration {
    public static void generateModWorldGen() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ROCKS_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WHEAT_PATCH_PLACED_KEY);


        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.DIRT_PATCH_PLACED_KEY);
    }
}