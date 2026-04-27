package net.ugi.wildsprout_plains.world.gen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.structure.Structure;
import net.ugi.wildsprout_plains.WildSproutPlains;

import java.util.List;

public class ModStructureSets {
    public static final RegistryKey<StructureSet> FLOATING_ISLAND = registerKey("floating_island");
    public static final RegistryKey<StructureSet> GLASS_BALL_CLUSTER = registerKey("glass_ball_cluster");

    public static void bootstrap(Registerable<StructureSet> context) {
        RegistryEntry<Structure> floatingIsland = context.getRegistryLookup(RegistryKeys.STRUCTURE)
                .getOrThrow(ModStructures.FLOATING_ISLAND);
        RegistryEntry<Structure> glassBallCluster = context.getRegistryLookup(RegistryKeys.STRUCTURE)
                .getOrThrow(ModStructures.GLASS_BALL_CLUSTER);

        StructureSet floatingIslandSet = new StructureSet(
                List.of(StructureSet.createEntry(floatingIsland, 1)),
                new RandomSpreadStructurePlacement(30, 12, SpreadType.LINEAR, 14640215)
        );

        StructureSet glassBallClusterSet = new StructureSet(
                List.of(StructureSet.createEntry(glassBallCluster, 1)),
                new RandomSpreadStructurePlacement(52, 22, SpreadType.LINEAR, 14781234)
        );

        context.register(FLOATING_ISLAND, floatingIslandSet);
        context.register(GLASS_BALL_CLUSTER, glassBallClusterSet);
    }

    private static RegistryKey<StructureSet> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.STRUCTURE_SET, Identifier.of(WildSproutPlains.MOD_ID, name));
    }
}

