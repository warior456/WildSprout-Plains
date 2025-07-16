package net.wildsprout.world.gen;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.gen.structure.StructureType;
import net.wildsprout.WildSproutPlains;
import net.wildsprout.world.gen.feature.*;
import net.wildsprout.world.gen.structure.RiverStructure;

public class ModStructureTypes<S extends Structure> {

    public static StructureType<RiverStructure> RIVER = () -> RiverStructure.CODEC;

    public static void init(){

        Registry.register(Registries.STRUCTURE_TYPE, WildSproutPlains.identifier( "river"), RIVER);
    }
}

