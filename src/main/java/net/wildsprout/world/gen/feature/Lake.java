package net.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

public class Lake extends Feature<DefaultFeatureConfig> {

    public Lake(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos center = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();

        int j = structureWorldAccess.getChunk(new BlockPos(center.getX(),center.getY(),center.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+center.getX()%16)%16, (32+center.getZ()%16)%16);

        center = new BlockPos(center.getX(),j,center.getZ());

        if (!(structureWorldAccess.getBlockState(center.down()).equals(Blocks.GRASS_BLOCK.getDefaultState()))) return false;


        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -3, new double[]{1});

        int waterradius = 6 + random.nextInt(4);
        int dirtradius = waterradius + 3;
        int airradius = dirtradius + 1;


        int[] heightmap = new int[airradius*airradius*3*4];
        int i = 0;
        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(airradius*1.5), 0, -(int)Math.round(airradius*1.5)), center.add((int)Math.round(airradius*1.5), 0, (int)Math.round(airradius*1.5)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= airradius * airradius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*airradius*14) {
                heightmap[i] = structureWorldAccess.getChunk(new BlockPos(pos.getX(),pos.getY(),pos.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+pos.getX()%16)%16, (32+pos.getZ()%16)%16);
                if (structureWorldAccess.getBlockState(new BlockPos(pos.getX(),heightmap[i]-1,pos.getZ())).equals(Blocks.WATER.getDefaultState()))  heightmap[i] = -65;
                i++;
            }
        }

        int lowest = 320;
        int highest = 0;
        for (int a = 0; a < heightmap.length; a++) {
            if (heightmap[a] == 0) continue;
            if (heightmap[a] < lowest) lowest = heightmap[a];
        }
        for (int a = 0; a < heightmap.length; a++) {
            if (heightmap[a] > highest) highest = heightmap[a];
        }

       //System.out.println("center " + center.getY() + "highest " + highest + " lowest " + lowest);

        if (highest > center.getY()+2) return false;
        if (lowest < center.getY()-1) return false;



        airradius = dirtradius + 2;

        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(airradius*1.5), 0, -(int)Math.round(airradius*1.5)), center.add((int)Math.round(airradius*1.5), 0, (int)Math.round(airradius*1.5)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= airradius * airradius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*airradius*14) {
                this.setBlockState(structureWorldAccess,pos.up(),Blocks.AIR.getDefaultState());
                this.setBlockState(structureWorldAccess,pos.up(2),Blocks.AIR.getDefaultState());
                this.setBlockState(structureWorldAccess,pos.up(3),Blocks.AIR.getDefaultState());

            }
        }

        airradius = dirtradius + 1;

        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(airradius*1.5), 0, -(int)Math.round(airradius*1.5)), center.add((int)Math.round(airradius*1.5), 0, (int)Math.round(airradius*1.5)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= airradius * airradius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*airradius*14) {
                this.setBlockState(structureWorldAccess,pos,Blocks.AIR.getDefaultState());
            }
        }

        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(dirtradius*1.5), 0, -(int)Math.round(dirtradius*1.5)), center.add((int)Math.round(dirtradius*1.5), 0, (int)Math.round(dirtradius*1.5)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= dirtradius * dirtradius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*dirtradius*14) {
                this.setBlockState(structureWorldAccess,pos.down(),Blocks.DIRT.getDefaultState());
            }
        }

        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(waterradius*1.5), 0, -(int)Math.round(waterradius*1.5)), center.add((int)Math.round(waterradius*1.5), 0, (int)Math.round(waterradius*1.5)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= waterradius * waterradius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*waterradius*14) {
                this.setBlockState(structureWorldAccess,pos.down(),Blocks.WATER.getDefaultState());
                this.setBlockState(structureWorldAccess,pos.down(2),Blocks.DIRT.getDefaultState());
            }
        }

        waterradius = waterradius/2;

        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(waterradius*1.5), 0, -(int)Math.round(waterradius*1.5)), center.add((int)Math.round(waterradius*1.5), 0, (int)Math.round(waterradius*1.5)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= waterradius * waterradius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*waterradius*14) {
                this.setBlockState(structureWorldAccess,pos.down(2),Blocks.WATER.getDefaultState());
                this.setBlockState(structureWorldAccess,pos.down(3),Blocks.DIRT.getDefaultState());
            }
        }

        return true;
    }
}
