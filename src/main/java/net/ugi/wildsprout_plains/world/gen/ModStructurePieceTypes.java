package net.ugi.wildsprout_plains.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.*;
import net.ugi.wildsprout_plains.WildSproutPlains;
import net.ugi.wildsprout_plains.world.gen.structure.GlassBallClusterGenerator;
import net.ugi.wildsprout_plains.world.gen.structure.FloatingIslandGenerator;
import net.ugi.wildsprout_plains.world.gen.structure.RiverGenerator;

public class ModStructurePieceTypes {
    public static StructurePieceType RIVER_SPRING = register(RiverGenerator.Spring::new, "river_spring");
    public static StructurePieceType RIVER_RIVER = register(RiverGenerator.River::new, "river_river");
    public static StructurePieceType RIVER_LAKE = register(RiverGenerator.Lake::new, "river_lake");
    public static StructurePieceType FLOATING_ISLAND_PIECE = register(FloatingIslandGenerator.Piece::new, "floating_island_piece");
    public static StructurePieceType GLASS_BALL_CLUSTER_PIECE = register(GlassBallClusterGenerator.Piece::new, "glass_ball_cluster_piece");

    private static StructurePieceType register(StructurePieceType type, String id) {
        return (StructurePieceType) Registry.register(Registries.STRUCTURE_PIECE, WildSproutPlains.identifier(id), type);
    }

    private static StructurePieceType register(StructurePieceType.Simple type, String id) {
        return register((StructurePieceType)type, id);
    }

    public static void init() {
    }
}

