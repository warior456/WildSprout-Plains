package net.ugi.wildsprout_plains.world.gen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.structure.Structure;

import net.ugi.wildsprout_plains.WildSproutPlains;
import net.ugi.wildsprout_plains.world.gen.structure.FloatingIslandStructure;
import net.ugi.wildsprout_plains.world.gen.structure.GlassBallClusterStructure;

import java.util.Map;

public class ModStructures {
    public static final RegistryKey<Structure> FLOATING_ISLAND = registerKey("floating_island");
    public static final RegistryKey<Structure> GLASS_BALL_CLUSTER = registerKey("glass_ball_cluster");

    public static void bootstrap(Registerable<Structure> context) {
        RegistryEntryList<Biome> overworld = context.getRegistryLookup(RegistryKeys.BIOME)
                .getOrThrow(BiomeTags.IS_OVERWORLD);

        Structure.Config config = new Structure.Config(overworld, Map.of(), GenerationStep.Feature.SURFACE_STRUCTURES, StructureTerrainAdaptation.NONE);

        context.register(FLOATING_ISLAND, new FloatingIslandStructure(config));
        context.register(GLASS_BALL_CLUSTER, new GlassBallClusterStructure(config));
    }

    private static RegistryKey<Structure> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, Identifier.of(WildSproutPlains.MOD_ID, name));
    }
}

