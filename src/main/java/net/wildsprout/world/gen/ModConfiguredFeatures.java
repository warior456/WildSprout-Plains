package net.wildsprout.world.gen;

import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TargetBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.wildsprout.WildSproutPlains;

import java.util.List;

public class ModConfiguredFeatures {
    //-------------------------
    public static final RegistryKey<ConfiguredFeature<?,?>> BOULDERS_KEY = registerKey("boulders");
    public static final RegistryKey<ConfiguredFeature<?,?>> ROCKS_KEY = registerKey("rocks");
    public static final RegistryKey<ConfiguredFeature<?,?>> WHEAT_PATCH_KEY = registerKey("wheat_patch");

    public static final RegistryKey<ConfiguredFeature<?,?>> DIRT_PATCH_KEY = registerKey("dirt_patch");
    public static final RegistryKey<ConfiguredFeature<?,?>> BUSHES_KEY = registerKey("bushes");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {

        RuleTest isGrassBlockTest = new BlockMatchRuleTest(Blocks.GRASS_BLOCK);
        //-------------------------
        register(context,BOULDERS_KEY, ModFeatures.BOULDERS, new DefaultFeatureConfig());
        register(context,ROCKS_KEY, ModFeatures.ROCKS, new DefaultFeatureConfig());
        register(context,WHEAT_PATCH_KEY, ModFeatures.WHEAT_PATCH, new DefaultFeatureConfig());

        register(context, DIRT_PATCH_KEY, Feature.ORE, new OreFeatureConfig(isGrassBlockTest, Blocks.COARSE_DIRT.getDefaultState(), 64));
        register(context, BUSHES_KEY, ModFeatures.BUSHES, new DefaultFeatureConfig());
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(WildSproutPlains.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}