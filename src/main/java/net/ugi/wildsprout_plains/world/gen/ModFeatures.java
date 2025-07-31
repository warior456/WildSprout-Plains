package net.ugi.wildsprout_plains.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.ugi.wildsprout_plains.WildSproutPlains;
import net.ugi.wildsprout_plains.world.gen.feature.*;

/*
ONLY USE THESE HEIGHTMAPS IN WORLD GEN, IF OTHER ARE USED, IT WILL BUG ON WORLD SAVING / RELOADING
Heightmap.Type.MOTION_BLOCKING
Heightmap.Type.MOTION_BLOCKING_NO_LEAVES
Heightmap.Type.OCEAN_FLOOR
Heightmap.Type.WORLD_SURFACE

NO HEIGHTMAPS WITH _WG, THEY ARE REMOVED AFTER CARVERS
https://minecraft.wiki/w/Heightmap
 */

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> BOULDERS = new Boulders(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig>ROCKS = new Rocks(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> WHEAT_PATCH = new WheatPatch(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> DIRT_PATCH = new DirtPatch(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> BUSHES = new Bushes(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> SMALL_RIVER = new SmallRiver(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> LAKE = new Lake(DefaultFeatureConfig.CODEC);
    //public static final Feature<DefaultFeatureConfig> SMALL_RIVER = new SmallRiver(DefaultFeatureConfig.CODEC);
    public static final Feature<RandomPatchFeatureConfig> PUMPKIN_PATCH = new PumpkinPatch(RandomPatchFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> RANDOM_PATH = new RandomPath(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FLUFFY_SNOW = new FluffySnow(DefaultFeatureConfig.CODEC);

    public static void init(){

        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "boulders"), BOULDERS);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "rocks"), ROCKS);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "wheat_patch"), WHEAT_PATCH);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "dirt_patch"), DIRT_PATCH);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "bushes"), BUSHES);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "small_river"), SMALL_RIVER);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "lake"), LAKE);
        //Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "small_river"), SMALL_RIVER);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "pumpkin_patch"), PUMPKIN_PATCH);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "random_path"), RANDOM_PATH);
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "fluffy_snow"), FLUFFY_SNOW);
    }
}
