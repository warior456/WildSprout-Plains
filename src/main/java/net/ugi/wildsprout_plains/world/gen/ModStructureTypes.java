package net.ugi.wildsprout_plains.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.gen.structure.StructureType;
import net.ugi.wildsprout_plains.WildSproutPlains;
import net.ugi.wildsprout_plains.world.gen.structure.FloatingIslandStructure;
import net.ugi.wildsprout_plains.world.gen.structure.GlassBallClusterStructure;
import net.ugi.wildsprout_plains.world.gen.structure.RiverStructure;

public class ModStructureTypes<S extends Structure> {

    public static StructureType<RiverStructure> RIVER = () -> RiverStructure.CODEC;
    public static StructureType<FloatingIslandStructure> FLOATING_ISLAND = () -> FloatingIslandStructure.CODEC;
    public static StructureType<GlassBallClusterStructure> GLASS_BALL_CLUSTER = () -> GlassBallClusterStructure.CODEC;

    public static void init(){

        Registry.register(Registries.STRUCTURE_TYPE, WildSproutPlains.identifier( "river"), RIVER);
        Registry.register(Registries.STRUCTURE_TYPE, WildSproutPlains.identifier("floating_island"), FLOATING_ISLAND);
        Registry.register(Registries.STRUCTURE_TYPE, WildSproutPlains.identifier("glass_ball_cluster"), GLASS_BALL_CLUSTER);
    }
}