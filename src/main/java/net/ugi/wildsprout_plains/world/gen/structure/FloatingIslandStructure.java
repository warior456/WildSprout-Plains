package net.ugi.wildsprout_plains.world.gen.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.ugi.wildsprout_plains.world.gen.ModStructureTypes;

import java.util.Optional;

public class FloatingIslandStructure extends Structure {
    public static final MapCodec<FloatingIslandStructure> CODEC = createCodec(FloatingIslandStructure::new);
    private static final int MIN_Y = 205;
    private static final int MAX_Y = 262;
    private static final int MIN_CHAIN_LENGTH = 3;
    private static final int MAX_CHAIN_LENGTH = 6;

    public FloatingIslandStructure(Structure.Config config) {
        super(config);
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Context context) {
        int startX = context.chunkPos().getStartX() + context.random().nextInt(16);
        int startZ = context.chunkPos().getStartZ() + context.random().nextInt(16);
        int startY = MIN_Y + context.random().nextInt((MAX_Y - MIN_Y) + 1);
        long baseSeed = context.random().nextLong();
        BlockPos start = new BlockPos(startX, startY, startZ);

        return Optional.of(new StructurePosition(start, collector -> {
            int chainLength = MIN_CHAIN_LENGTH + context.random().nextInt((MAX_CHAIN_LENGTH - MIN_CHAIN_LENGTH) + 1);
            double heading = context.random().nextDouble() * Math.PI * 2.0D;

            int x = startX;
            int y = startY;
            int z = startZ;

            for (int i = 0; i < chainLength; i++) {
                if (i > 0) {
                    heading += (context.random().nextDouble() - 0.5D) * 0.9D;
                    int stepDistance = 26 + context.random().nextInt(18);
                    x += MathHelper.floor(Math.cos(heading) * stepDistance);
                    z += MathHelper.floor(Math.sin(heading) * stepDistance);

                    int verticalStep = -11 + context.random().nextInt(23);
                    y = MathHelper.clamp(y + verticalStep, MIN_Y, MAX_Y + 14);
                }

                long shapeSeed = baseSeed + (341873128712L * i);
                BlockBox box = FloatingIslandGenerator.Piece.createBoundingBox(x, y, z);
                collector.addPiece(new FloatingIslandGenerator.Piece(x, y, z, shapeSeed, box));
            }
        }));
    }

    @Override
    public StructureType<?> getType() {
        return ModStructureTypes.FLOATING_ISLAND;
    }
}


