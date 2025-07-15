package net.wildsprout.world.gen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.wildsprout.WildSproutPlains;

import java.util.List;

public class ModPlacedFeatures {

    //-------------------------
    public static final RegistryKey<PlacedFeature> BOULDERS_PLACED_KEY = registerKey("boulders");
    public static final RegistryKey<PlacedFeature> WHEAT_PATCH_PLACED_KEY = registerKey("wheat_patch");
    public static final RegistryKey<PlacedFeature> DIRT_PATCH_PLACED_KEY = registerKey("dirt_patch");
    public static final RegistryKey<PlacedFeature> BUSHES_PLACED_KEY = registerKey("bushes");


    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        //-------------------------
        register(context,BOULDERS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BOULDERS_KEY), RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,DIRT_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DIRT_PATCH_KEY), RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        register(context,WHEAT_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.WHEAT_PATCH_KEY), CountPlacementModifier.of(20), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,BUSHES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BUSHES_KEY), RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(WildSproutPlains.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}