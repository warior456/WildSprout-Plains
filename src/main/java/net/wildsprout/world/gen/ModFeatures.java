package net.wildsprout.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.wildsprout.WildSproutPlains;
import net.wildsprout.world.gen.feature.*;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> BOULDERS = new Boulders(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig>ROCKS = new Rocks(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> WHEAT_PATCH = new WheatPatch(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> BUSHES = new Bushes(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> SMALL_RIVER = new SmallRiver(DefaultFeatureConfig.CODEC);

    public static void init(){

        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "boulders"), BOULDERS);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "rocks"), ROCKS);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "wheat_patch"), WHEAT_PATCH);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "bushes"), BUSHES);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "small_river"), SMALL_RIVER);
    }
}
