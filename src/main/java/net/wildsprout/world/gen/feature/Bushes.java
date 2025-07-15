package net.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.List;

public class Bushes extends Feature<DefaultFeatureConfig> {

    public Bushes(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos center = context.getOrigin();
        Random random = context.getRandom();

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        List<BlockPos> bushPlacements = getBushPlacements(random, structureWorldAccess, center);
        for (BlockPos pos : bushPlacements) {
            if (!(structureWorldAccess.getBlockState(center.down()).equals(Blocks.GRASS_BLOCK.getDefaultState()))) continue;
            setBlockState(structureWorldAccess, pos, Blocks.OAK_LOG.getDefaultState());
            setBlockState(structureWorldAccess, pos.up(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1));
            setBlockState(structureWorldAccess, pos.north(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1));
            setBlockState(structureWorldAccess, pos.east(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1));
            setBlockState(structureWorldAccess, pos.south(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1));
            setBlockState(structureWorldAccess, pos.west(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1));





        }

        return true;
    };

    private List<BlockPos> getBushPlacements(Random rand, StructureWorldAccess structureWorldAccess, BlockPos pos) {

        List<BlockPos> placements = new ArrayList<BlockPos>();
        for (int i = 0; i < rand.nextBetween(3, 7); i++) {
            int x = pos.getX() + rand.nextBetween(-10, 10);
            int z = pos.getZ() + rand.nextBetween(-10, 10);
            int y = pos.getY() + rand.nextBetween(-10, 10);
            y = structureWorldAccess.getChunk(new BlockPos(x,y,z)).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+x%16)%16, (32+z%16)%16);
            placements.add(new BlockPos(x,y,z));
        }

        return placements;
    }
}
