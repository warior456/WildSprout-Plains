package net.wildsprout.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BuddingAmethystBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class Rocks extends Feature<DefaultFeatureConfig> {

    private static final Direction[] DIRECTIONS = Direction.values();

    public Rocks(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        int i = 2;
        int j = 5;
        List<Pair<BlockPos, Integer>> list = Lists.newLinkedList();
        int k = 3;
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler doublePerlinNoiseSampler = DoublePerlinNoiseSampler.create(chunkRandom, -4, new double[]{(double)1.0F});
        List<BlockPos> list2 = Lists.newLinkedList();
        double d = (double)k / (double)2.366;
        double e = (double)1.0F / Math.sqrt(1);
        double f = (double)1.0F / Math.sqrt(1 + d);
        double g = (double)1.0F / Math.sqrt(1 + d);
        double h = (double)1.0F / Math.sqrt(1 + d);
        double l = (double)1.0F / Math.sqrt(1 + random.nextDouble() / (double)2.0F + (k > 3 ? d : (double)0.0F));
        boolean bl = (double)random.nextFloat() < 1;
        int m = 0;

        for(int n = 0; n < k; ++n) {
            int o = 5;
            int p = 5;
            int q = 5;
            BlockPos blockPos2 = blockPos.add(o, p, q);
            BlockState blockState = structureWorldAccess.getBlockState(blockPos2);
            if (blockState.isAir() || blockState.isIn(BlockTags.GEODE_INVALID_BLOCKS)) {
                ++m;
                if (m > 8) {
                    return false;
                }
            }

            list.add(Pair.of(blockPos2, 2));
        }

        if (bl) {
            int n = random.nextInt(4);
            int o = k * 2 + 1;
            if (n == 0) {
                list2.add(blockPos.add(o, 7, 0));
                list2.add(blockPos.add(o, 5, 0));
                list2.add(blockPos.add(o, 1, 0));
            } else if (n == 1) {
                list2.add(blockPos.add(0, 7, o));
                list2.add(blockPos.add(0, 5, o));
                list2.add(blockPos.add(0, 1, o));
            } else if (n == 2) {
                list2.add(blockPos.add(o, 7, o));
                list2.add(blockPos.add(o, 5, o));
                list2.add(blockPos.add(o, 1, o));
            } else {
                list2.add(blockPos.add(0, 7, 0));
                list2.add(blockPos.add(0, 5, 0));
                list2.add(blockPos.add(0, 1, 0));
            }
        }

        List<BlockPos> list3 = Lists.newArrayList();

        for(BlockPos blockPos3 : BlockPos.iterate(blockPos.add(i, i, i), blockPos.add(j, j, j))) {
            double r = doublePerlinNoiseSampler.sample((double)blockPos3.getX(), (double)blockPos3.getY(), (double)blockPos3.getZ()) * 3;
            double s = (double)0.0F;
            double t = (double)0.0F;

            for(Pair<BlockPos, Integer> pair : list) {
                s += MathHelper.inverseSqrt(blockPos3.getSquaredDistance((Vec3i)pair.getFirst()) + (double)(Integer)pair.getSecond()) + r;
            }

            for(BlockPos blockPos4 : list2) {
                t += MathHelper.inverseSqrt(blockPos3.getSquaredDistance(blockPos4) + (double)0.1) + r;
            }

            if (!(s < h)) {
                if (bl && t >= l && s < e) {
                    this.setBlockState(structureWorldAccess, blockPos3, Blocks.AIR.getDefaultState());

                    for(Direction direction : DIRECTIONS) {
                        BlockPos blockPos5 = blockPos3.offset(direction);
                        FluidState fluidState = structureWorldAccess.getFluidState(blockPos5);
                        if (!fluidState.isEmpty()) {
                            structureWorldAccess.scheduleFluidTick(blockPos5, fluidState.getFluid(), 0);
                        }
                    }
                } else if (s >= e) {
                    this.setBlockState(structureWorldAccess, blockPos3, Blocks.STONE.getDefaultState());
                } else if (s >= f) {
                    boolean bl2 = (double)random.nextFloat() < 0.1;
                    if (bl2) {
                        this.setBlockState(structureWorldAccess, blockPos3, Blocks.GOLD_BLOCK.getDefaultState());
                    } else {
                        this.setBlockState(structureWorldAccess, blockPos3, Blocks.STONE.getDefaultState());
                    }

                    if ((!true || bl2) && (double)random.nextFloat() < 0.6) {
                        list3.add(blockPos3.toImmutable());
                    }
                } else if (s >= g) {
                    this.setBlockState(structureWorldAccess, blockPos3, Blocks.GOLD_BLOCK.getDefaultState());
                } else if (s >= h) {
                    this.setBlockState(structureWorldAccess, blockPos3, Blocks.STONE.getDefaultState());
                }
            }
        }

        List<BlockState> list4 = new ArrayList<>();
        list4.add(Blocks.STONE.getDefaultState());

        for(BlockPos blockPos2 : list3) {
            BlockState blockState = (BlockState) Util.getRandom(list4, random);

            for(Direction direction2 : DIRECTIONS) {
                if (blockState.contains(Properties.FACING)) {
                    blockState = (BlockState)blockState.with(Properties.FACING, direction2);
                }

                BlockPos blockPos6 = blockPos2.offset(direction2);
                BlockState blockState2 = structureWorldAccess.getBlockState(blockPos6);
                if (blockState.contains(Properties.WATERLOGGED)) {
                    blockState = (BlockState)blockState.with(Properties.WATERLOGGED, blockState2.getFluidState().isStill());
                }

                if (BuddingAmethystBlock.canGrowIn(blockState2)) {
                    this.setBlockState(structureWorldAccess, blockPos6, Blocks.STONE.getDefaultState());
                    break;
                }
            }
        }

        return true;
    }
}