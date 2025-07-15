package net.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
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

import java.util.Arrays;
import java.util.Properties;

public class WheatPatch extends Feature<DefaultFeatureConfig> {

    public WheatPatch(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();

        int featureSize = 5;

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler doublePerlinNoiseSampler = DoublePerlinNoiseSampler.create(chunkRandom, -10, new double[]{1.0, 0.0,1.0,4.0});
        Chunk chunk = structureWorldAccess.getChunk(blockPos);

        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        for (int i = -(featureSize/2);i< (featureSize+1)/2;i++){
            for (int k = -(featureSize/2);k< (featureSize+1)/2;k++){
                int j = structureWorldAccess.getChunk(new BlockPos(x + i,y,z + k)).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+i+x%16)%16, (32+k+z%16)%16);
                BlockPos pos = new BlockPos(x+i,j,z+k);

                if (!(structureWorldAccess.getBiome(pos).getKey().get() ==  BiomeKeys.PLAINS)) continue;
                if (!(structureWorldAccess.getBlockState(pos.down()).equals(Blocks.GRASS_BLOCK.getDefaultState()))) continue;

                double noiseSample = doublePerlinNoiseSampler.sample(pos.getX(), pos.getY(), pos.getZ()) - 0.5;
                if (chunkRandom.nextDouble() < noiseSample){
                    this.setBlockState(structureWorldAccess, pos, Blocks.WHEAT.getDefaultState().with(CropBlock.AGE, 7));
                }
            }
        }





        return true;

    }
}
