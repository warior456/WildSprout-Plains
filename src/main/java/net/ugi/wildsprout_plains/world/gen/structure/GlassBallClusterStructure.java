package net.ugi.wildsprout_plains.world.gen.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.ugi.wildsprout_plains.world.gen.ModStructureTypes;

import java.util.Optional;

/**
 * Small example structure start that spawns a short chain of glass balls.
 *
 * <p>This class is intentionally compact so it can be used as a template for your own structure
 * starts. The start chooses a single origin inside the chunk, then emits one piece that performs
 * the actual block placement. If you want a different look, usually the two things to change are:
 *
 * <ul>
 *   <li>the segment count and step distances in {@link #getStructurePosition(Context)}</li>
 *   <li>the block palette and shape code inside the piece's {@code generate(...)} method</li>
 * </ul>
 *
 * <p>Because the shape is driven by structure pieces, each piece receives a live
 * {@code StructureWorldAccess} during generation. That is the point where you can inspect existing
 * blocks, place new blocks, and adapt the shape to the world.</p>
 */
public class GlassBallClusterStructure extends Structure {
    public static final MapCodec<GlassBallClusterStructure> CODEC = createCodec(GlassBallClusterStructure::new);
    private static final int MIN_Y = 198;
    private static final int MAX_Y = 232;

    public GlassBallClusterStructure(Structure.Config config) {
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
            // Planning: compute the full path here (once), then add pieces along it.
            // Store each segment endpoint in piece NBT so chunks don't re-decide the path.
            // NBT path example (store x/y/z triples in an int array):
            // int[] path = new int[] {x0, y0, z0, x1, y1, z1, x2, y2, z2};
            // nbt.putIntArray("Path", path);
            // int[] saved = nbt.getIntArray("Path"); // read back
            // NBT list example (one compound per point):
            // NbtList points = new NbtList();
            // NbtCompound p0 = new NbtCompound();
            // p0.putInt("X", x0);
            // p0.putInt("Y", y0);
            // p0.putInt("Z", z0);
            // points.add(p0);
            // nbt.put("PathPoints", points);
            int segmentCount = 3;
            double heading = context.random().nextDouble() * Math.PI * 2.0D;
            int stepDistance = 12;
            int verticalStep = -2;

            int x = startX;
            int y = startY;
            int z = startZ;
            BlockPos previousCenter = null;

            for (int i = 0; i < segmentCount; i++) {
                if (i > 0) {
                    x += MathHelper.floor(Math.cos(heading) * stepDistance);
                    z += MathHelper.floor(Math.sin(heading) * stepDistance);
                    y = MathHelper.clamp(y + verticalStep, MIN_Y, MAX_Y);
                }

                long pieceSeed = baseSeed + (341873128712L * i);
                BlockPos center = new BlockPos(x, y, z);
                //create bounding box
                BlockBox box = GlassBallClusterGenerator.Piece.createBoundingBox(x, y, z);

                //adds piece to be generated
                collector.addPiece(new GlassBallClusterGenerator.Piece(x, y, z, pieceSeed, box));

                //connectors
                if (previousCenter != null) {
                    BlockBox bridgeBox = GlassBallClusterGenerator.BridgePiece.createBoundingBox(previousCenter, center);
                    //adds piece to be generated
                    collector.addPiece(new GlassBallClusterGenerator.BridgePiece(previousCenter, center, bridgeBox));
                }

                previousCenter = center;
            }
        }));
    }

    @Override
    public StructureType<?> getType() {
        return ModStructureTypes.GLASS_BALL_CLUSTER;
    }
}