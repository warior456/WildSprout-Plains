package net.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class Boulders extends Feature<DefaultFeatureConfig> {

    private static final Direction[] DIRECTIONS = Direction.values();

    public Boulders(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos center = context.getOrigin();
        Random random = context.getRandom();

        int j = structureWorldAccess.getChunk(new BlockPos(center.getX(),center.getY(),center.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+center.getX()%16)%16, (32+center.getZ()%16)%16);

        center = new BlockPos(center.getX(),j,center.getZ());

        if (!(structureWorldAccess.getBlockState(center.down()).equals(Blocks.GRASS_BLOCK.getDefaultState()))) return false;


        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -3, new double[]{1});

        int radius = 3 + random.nextInt(4);

        center = center.down((int)Math.round(radius/1.5));


        // Iterate a cube around the center
        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(radius*1.5), -(int)Math.round(radius*1.5), -(int)Math.round(radius*1.5)), center.add((int)Math.round(radius*1.5), (int)Math.round(radius*1.5), (int)Math.round(radius*1.5)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= radius * radius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*radius*14) {
                BlockState block = Blocks.STONE.getDefaultState();
                structureWorldAccess.setBlockState(pos, block,2);
            }
        }

        return true;
    }
}