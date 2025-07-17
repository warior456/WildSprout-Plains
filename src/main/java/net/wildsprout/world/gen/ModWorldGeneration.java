package net.wildsprout.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.wildsprout.WildSproutPlains;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        //RAW GENERATION
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.RAW_GENERATION, ModPlacedFeatures.LAKE_PLACED_KEY);

        //LAKES

        //LOCAL MODIFICATIONS
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.BOULDERS_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.ROCKS_PLACED_KEY);

        //UNDERGOUNDS STRUCTURES

        //SURFACE STRUCTURES

        //STRONGHOLDS

        //UNDERGOUND ORES
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.DIRT_PATCH_PLACED_KEY);

        //UNDERGROUND DECORATION

        //FLUID SPRINGS

        //VEGETAL DECORATION
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BUSHES_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WHEAT_PATCH_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RANDOM_PATH_PLACED_KEY);

        //TOP LAYER MODIFICATION
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.SMALL_RIVER_PLACED_KEY);

        //MODIFY FEATURES
        BiomeModifications.create(WildSproutPlains.identifier("pumpkin_patch"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS),
                        context -> {
                            // Identify the original feature to replace.
                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.VEGETAL_DECORATION,
                                    VegetationPlacedFeatures.PATCH_PUMPKIN
                            );
                            // Add new custom pumpkin patch feature.
                            context.getGenerationSettings().addFeature(
                                    GenerationStep.Feature.VEGETAL_DECORATION,
                                    ModPlacedFeatures.PUMPKIN_PATCH_PLACED_KEY
                            );
                        }
                );

        // REMOVE FEATURES
        BiomeModifications.create(WildSproutPlains.identifier("no_lava_spring")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.FLUID_SPRINGS, MiscPlacedFeatures.SPRING_LAVA);});

        BiomeModifications.create(WildSproutPlains.identifier("no_lava_lake")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.LAKES, MiscPlacedFeatures.LAKE_LAVA_SURFACE);});


    }
}