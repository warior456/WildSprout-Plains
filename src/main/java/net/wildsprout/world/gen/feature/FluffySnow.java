package net.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;


public class FluffySnow extends Feature<DefaultFeatureConfig> {

    public FluffySnow(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();

        int featureSize = 5;

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler snowNoise = DoublePerlinNoiseSampler.create(chunkRandom, -4, new double[]{1});

        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        for (int i = -(featureSize/2);i< (featureSize+1)/2;i++){
            for (int k = -(featureSize/2);k< (featureSize+1)/2;k++){
                int j = structureWorldAccess.getChunk(new BlockPos(x + i,y,z + k)).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+i+x%16)%16, (32+k+z%16)%16);
                BlockPos pos = new BlockPos(x+i,j,z+k);

                if (!(structureWorldAccess.getBlockState(pos).equals(Blocks.SNOW.getDefaultState()) ||structureWorldAccess.getBlockState(pos).equals(Blocks.AIR.getDefaultState()))) continue;

                int snowlayers = (int)Math.round(Math.clamp(snowNoise.sample(pos.getX(), pos.getY(), pos.getZ())+0.5,0,2)*3) + random.nextInt(2)+1;

                this.setBlockState(structureWorldAccess, pos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, snowlayers));

            }
        }

        for (int i = -(featureSize/2);i< (featureSize+1)/2;i++){
            for (int k = -(featureSize/2);k< (featureSize+1)/2;k++){
                int j = structureWorldAccess.getChunk(new BlockPos(x + i,y,z + k)).getHeightmap(Heightmap.Type.MOTION_BLOCKING).get((32+i+x%16)%16, (32+k+z%16)%16);
                BlockPos pos = new BlockPos(x+i,j,z+k);

                if (!(structureWorldAccess.getBlockState(pos).equals(Blocks.SNOW.getDefaultState()) || structureWorldAccess.getBlockState(pos).equals(Blocks.AIR.getDefaultState()))) continue;

                int snowlayers = (int)Math.round(Math.clamp(snowNoise.sample(pos.getX(), pos.getY(), pos.getZ())+0.5,0,2)*3) + random.nextInt(2)+1;

                this.setBlockState(structureWorldAccess, pos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, snowlayers));

            }
        }



        return true;

    }
}
