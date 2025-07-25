package net.ugi.wildsprout.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.ugi.wildsprout.WildSproutPlains;

import java.util.ArrayList;
import java.util.List;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        List<RegistryKey<Biome>> allEnabled = new ArrayList<>();
        List<RegistryKey<Biome>> snowyIfEnabled = new ArrayList<>();
        List<RegistryKey<Biome>> normalIfEnabled = new ArrayList<>();

        if(WildSproutPlains.CONFIG.PlainsEnabled) {
            allEnabled.add(BiomeKeys.PLAINS);
            normalIfEnabled.add(BiomeKeys.PLAINS);
        }
        if(WildSproutPlains.CONFIG.SnowyPlainsEnabled) {
            allEnabled.add(BiomeKeys.SNOWY_PLAINS);
            snowyIfEnabled.add(BiomeKeys.SNOWY_PLAINS);
        }
        if(WildSproutPlains.CONFIG.SunFlowerPlainsEnabled) {
            allEnabled.add(BiomeKeys.SUNFLOWER_PLAINS);
            normalIfEnabled.add(BiomeKeys.SUNFLOWER_PLAINS);
        }


        //RAW GENERATION
        //LAKES
        if(WildSproutPlains.CONFIG.LakesEnabled){
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(normalIfEnabled), GenerationStep.Feature.RAW_GENERATION, ModPlacedFeatures.LAKE_PLACED_KEY);
        }

        //LOCAL MODIFICATIONS
        if(WildSproutPlains.CONFIG.BouldersEnabled) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(allEnabled), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.BOULDERS_PLACED_KEY);
        }
        if(WildSproutPlains.CONFIG.RocksEnabled) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(allEnabled), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.ROCKS_PLACED_KEY);
        }

        //UNDERGOUNDS STRUCTURES

        //SURFACE STRUCTURES

        //STRONGHOLDS

        //UNDERGOUND ORES
        if(WildSproutPlains.CONFIG.DirtPatchesEnabled){
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(allEnabled), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.DIRT_PATCH_PLACED_KEY);
        }
        //UNDERGROUND DECORATION

        //FLUID SPRINGS

        //VEGETAL DECORATION
        if(WildSproutPlains.CONFIG.BushesEnabled){
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(allEnabled), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BUSHES_PLACED_KEY);
        }
        if(WildSproutPlains.CONFIG.WheatFieldsEnabled) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(normalIfEnabled), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WHEAT_PATCH_PLACED_KEY);
        }
        if(WildSproutPlains.CONFIG.RandomPathsEnabled){
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(normalIfEnabled), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RANDOM_PATH_PLACED_KEY);
        }
        if(WildSproutPlains.CONFIG.BerryPatchesEnabled){
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(snowyIfEnabled), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BERRY_PATCH_PLACED_KEY);
        }


        //TOP LAYER MODIFICATION
        if(WildSproutPlains.CONFIG.LayeredSnowEnabled) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(snowyIfEnabled), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.FLUFFY_SNOW_PLACED_KEY);
        }
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.SMALL_RIVER_PLACED_KEY);

        //MODIFY FEATURES
        if(WildSproutPlains.CONFIG.ImprovedPumpkinPatch) {
            BiomeModifications.create(WildSproutPlains.identifier("pumpkin_patch"))
                    .add(ModificationPhase.REPLACEMENTS,
                            BiomeSelectors.includeByKey(normalIfEnabled),
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
        }


        // REMOVE FEATURES
        if(!WildSproutPlains.CONFIG.AllowLavaGenInPlains) {
            BiomeModifications.create(WildSproutPlains.identifier("no_lava_spring")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(allEnabled),
                    context -> {
                        context.getGenerationSettings().removeFeature(
                                GenerationStep.Feature.FLUID_SPRINGS, MiscPlacedFeatures.SPRING_LAVA);});

            BiomeModifications.create(WildSproutPlains.identifier("no_lava_lake_surface")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(allEnabled),
                    context -> {
                        context.getGenerationSettings().removeFeature(
                                GenerationStep.Feature.LAKES, MiscPlacedFeatures.LAKE_LAVA_SURFACE);});

            BiomeModifications.create(WildSproutPlains.identifier("no_lava_lake_underground")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(allEnabled),
                    context -> {
                        context.getGenerationSettings().removeFeature(
                                GenerationStep.Feature.LAKES, MiscPlacedFeatures.LAKE_LAVA_UNDERGROUND);});

        }

        if(!WildSproutPlains.CONFIG.AllowFlowerGenInSnowyPlains) {
            BiomeModifications.create(WildSproutPlains.identifier("no_flowers")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(snowyIfEnabled),
                    context -> {
                        context.getGenerationSettings().removeFeature(
                                GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FLOWER_DEFAULT);});


            }
        }
}