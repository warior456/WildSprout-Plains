package net.wildsprout.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.wildsprout.WildSproutPlains;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> ROCKS = new Rocks(DefaultFeatureConfig.CODEC);

    public static void init(){
        Registry.register(Registries.FEATURE, WildSproutPlains.identifier( "rocks"), ROCKS);
    }
}
