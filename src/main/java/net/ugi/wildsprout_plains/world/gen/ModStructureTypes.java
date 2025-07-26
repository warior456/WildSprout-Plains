package net.ugi.wildsprout_plains.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.gen.structure.StructureType;
import net.ugi.wildsprout_plains.WildSproutPlains;
import net.ugi.wildsprout_plains.world.gen.structure.RiverStructure;

public class ModStructureTypes<S extends Structure> {

    public static StructureType<RiverStructure> RIVER = () -> RiverStructure.CODEC;

    public static void init(){

        Registry.register(Registries.STRUCTURE_TYPE, WildSproutPlains.identifier( "river"), RIVER);
    }
}

