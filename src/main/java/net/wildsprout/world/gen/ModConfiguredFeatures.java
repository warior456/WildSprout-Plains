package net.wildsprout.world.gen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.wildsprout.WildSproutPlains;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> ROCKS_KEY = registerKey("rocks");



    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context,ROCKS_KEY, ModFeatures.ROCKS, new DefaultFeatureConfig());

    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(WildSproutPlains.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}