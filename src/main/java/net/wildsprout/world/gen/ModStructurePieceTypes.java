package net.wildsprout.world.gen;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.*;
import net.wildsprout.WildSproutPlains;
import net.wildsprout.world.gen.structure.RiverGenerator;

import java.util.Locale;

public class ModStructurePieceTypes {
    public static StructurePieceType RIVER_SPRING = register(RiverGenerator.Spring::new, "river_spring");
    public static StructurePieceType RIVER_RIVER = register(RiverGenerator.River::new, "river_river");
    public static StructurePieceType RIVER_LAKE = register(RiverGenerator.Lake::new, "river_lake");

    private static StructurePieceType register(StructurePieceType type, String id) {
        return (StructurePieceType) Registry.register(Registries.STRUCTURE_PIECE, WildSproutPlains.identifier(id), type);
    }

    private static StructurePieceType register(StructurePieceType.Simple type, String id) {
        return register((StructurePieceType)type, id);
    }

    public static void init() {
    }
}
