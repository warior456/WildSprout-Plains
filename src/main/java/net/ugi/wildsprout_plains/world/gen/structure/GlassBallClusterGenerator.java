package net.ugi.wildsprout_plains.world.gen.structure;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.ugi.wildsprout_plains.world.gen.ModStructurePieceTypes;

/**
 * Minimal structure pieces: one ball per piece, one bridge per piece.
 *
 * <p>Planning happens in the structure start, which adds a ball piece for each center and a
 * bridge piece between neighbors.</p>
 */
public final class GlassBallClusterGenerator {
    private GlassBallClusterGenerator() {
    }

    public static class Piece extends StructurePiece {
        private static final int MAX_HORIZONTAL_RADIUS = 28;
        private static final int MAX_UP = 16;
        private static final int MAX_DOWN = 12;
        private static final int BALL_RADIUS = 4;

        private final int centerX;
        private final int centerY;
        private final int centerZ;
        private final long shapeSeed;

        public Piece(int centerX, int centerY, int centerZ, long shapeSeed, BlockBox boundingBox) {
            super(ModStructurePieceTypes.GLASS_BALL_CLUSTER_PIECE, 0, boundingBox);
            this.centerX = centerX;
            this.centerY = centerY;
            this.centerZ = centerZ;
            this.shapeSeed = shapeSeed;
        }

        public Piece(NbtCompound nbt) {
            super(ModStructurePieceTypes.GLASS_BALL_CLUSTER_PIECE, nbt);
            this.centerX = nbt.getInt("CenterX");
            this.centerY = nbt.getInt("CenterY");
            this.centerZ = nbt.getInt("CenterZ");
            this.shapeSeed = nbt.getLong("ShapeSeed");
        }

        public static BlockBox createBoundingBox(int centerX, int centerY, int centerZ) {
            return new BlockBox(
                    centerX - MAX_HORIZONTAL_RADIUS,
                    centerY - MAX_DOWN,
                    centerZ - MAX_HORIZONTAL_RADIUS,
                    centerX + MAX_HORIZONTAL_RADIUS,
                    centerY + MAX_UP,
                    centerZ + MAX_HORIZONTAL_RADIUS
            );
        }

        @Override
        protected void writeNbt(StructureContext context, NbtCompound nbt) {
            nbt.putInt("CenterX", this.centerX);
            nbt.putInt("CenterY", this.centerY);
            nbt.putInt("CenterZ", this.centerZ);
            nbt.putLong("ShapeSeed", this.shapeSeed);
        }

        @Override
        public void generate(
                StructureWorldAccess world,
                StructureAccessor structureAccessor,
                ChunkGenerator chunkGenerator,
                Random random,
                BlockBox chunkBox,
                ChunkPos chunkPos,
                BlockPos pivot
        ) {
            // This is the live world access point: read blocks, place blocks, and adapt to terrain.
            BlockBox islandBox = this.getBoundingBox();
            int minX = Math.max(islandBox.getMinX(), chunkBox.getMinX());
            int maxX = Math.min(islandBox.getMaxX(), chunkBox.getMaxX());
            int minY = Math.max(islandBox.getMinY(), chunkBox.getMinY());
            int maxY = Math.min(islandBox.getMaxY(), chunkBox.getMaxY());
            int minZ = Math.max(islandBox.getMinZ(), chunkBox.getMinZ());
            int maxZ = Math.min(islandBox.getMaxZ(), chunkBox.getMaxZ());

            if (minX > maxX || minY > maxY || minZ > maxZ) {
                return;
            }

            // --- Generation phase: this piece only draws the ball at its own center. ---
            ChunkRandom detailRandom = new ChunkRandom(new CheckedRandom(this.shapeSeed ^ 0x6A09E667F3BCC909L));
            BlockPos.Mutable mutablePos = new BlockPos.Mutable();
            BlockPos center = new BlockPos(this.centerX, this.centerY, this.centerZ);
            int radius = BALL_RADIUS;

            placeSphere(world, mutablePos, center, radius, minX, maxX, minY, maxY, minZ, maxZ);
        }

        private static void placeSphere(
                StructureWorldAccess world,
                BlockPos.Mutable mutablePos,
                BlockPos center,
                int radius,
                int minX,
                int maxX,
                int minY,
                int maxY,
                int minZ,
                int maxZ
        ) {
            int innerRadius = Math.max(1, radius - 1);
            int radiusSq = radius * radius;
            int innerRadiusSq = innerRadius * innerRadius;

            for (int x = Math.max(minX, center.getX() - radius); x <= Math.min(maxX, center.getX() + radius); x++) {
                int dx = x - center.getX();
                for (int y = Math.max(minY, center.getY() - radius); y <= Math.min(maxY, center.getY() + radius); y++) {
                    int dy = y - center.getY();
                    for (int z = Math.max(minZ, center.getZ() - radius); z <= Math.min(maxZ, center.getZ() + radius); z++) {
                        int dz = z - center.getZ();
                        int distSq = (dx * dx) + (dy * dy) + (dz * dz);
                        if (distSq > radiusSq || distSq < innerRadiusSq) {
                            continue;
                        }

                        mutablePos.set(x, y, z);
                        BlockState existing = world.getBlockState(mutablePos);
                        if (!existing.isAir() && !existing.isReplaceable()) {
                            continue;
                        }

                        world.setBlockState(mutablePos, Blocks.LIGHT_BLUE_STAINED_GLASS.getDefaultState(), 2);
                    }
                }
            }
        }
    }

    /**
     * Separate piece that only draws the glass bridge between two ball centers.
     */
    public static class BridgePiece extends StructurePiece {
        private final BlockPos start;
        private final BlockPos end;

        public BridgePiece(BlockPos start, BlockPos end, BlockBox boundingBox) {
            super(ModStructurePieceTypes.GLASS_BALL_CLUSTER_PIECE, 0, boundingBox);
            this.start = start;
            this.end = end;
        }

        public BridgePiece(NbtCompound nbt) {
            super(ModStructurePieceTypes.GLASS_BALL_CLUSTER_PIECE, nbt);
            this.start = new BlockPos(nbt.getInt("StartX"), nbt.getInt("StartY"), nbt.getInt("StartZ"));
            this.end = new BlockPos(nbt.getInt("EndX"), nbt.getInt("EndY"), nbt.getInt("EndZ"));
        }

        public static BlockBox createBoundingBox(BlockPos start, BlockPos end) {
            int minX = Math.min(start.getX(), end.getX()) - 2;
            int minY = Math.min(start.getY(), end.getY()) - 2;
            int minZ = Math.min(start.getZ(), end.getZ()) - 2;
            int maxX = Math.max(start.getX(), end.getX()) + 2;
            int maxY = Math.max(start.getY(), end.getY()) + 2;
            int maxZ = Math.max(start.getZ(), end.getZ()) + 2;
            return new BlockBox(minX, minY, minZ, maxX, maxY, maxZ);
        }

        @Override
        protected void writeNbt(StructureContext context, NbtCompound nbt) {
            nbt.putInt("StartX", this.start.getX());
            nbt.putInt("StartY", this.start.getY());
            nbt.putInt("StartZ", this.start.getZ());
            nbt.putInt("EndX", this.end.getX());
            nbt.putInt("EndY", this.end.getY());
            nbt.putInt("EndZ", this.end.getZ());
        }

        @Override
        public void generate(
                StructureWorldAccess world,
                StructureAccessor structureAccessor,
                ChunkGenerator chunkGenerator,
                Random random,
                BlockBox chunkBox,
                ChunkPos chunkPos,
                BlockPos pivot
        ) {
            BlockBox bridgeBox = this.getBoundingBox();
            int minX = Math.max(bridgeBox.getMinX(), chunkBox.getMinX());
            int maxX = Math.min(bridgeBox.getMaxX(), chunkBox.getMaxX());
            int minY = Math.max(bridgeBox.getMinY(), chunkBox.getMinY());
            int maxY = Math.min(bridgeBox.getMaxY(), chunkBox.getMaxY());
            int minZ = Math.max(bridgeBox.getMinZ(), chunkBox.getMinZ());
            int maxZ = Math.min(bridgeBox.getMaxZ(), chunkBox.getMaxZ());

            if (minX > maxX || minY > maxY || minZ > maxZ) {
                return;
            }

            BlockPos.Mutable mutablePos = new BlockPos.Mutable();
            placeBridge(world, mutablePos, this.start, this.end, minX, maxX, minY, maxY, minZ, maxZ);
        }
    }

    private static void placeBridge(
            StructureWorldAccess world,
            BlockPos.Mutable mutablePos,
            BlockPos start,
            BlockPos end,
            int minX,
            int maxX,
            int minY,
            int maxY,
            int minZ,
            int maxZ
    ) {
        int steps = Math.max(1, Math.max(Math.max(Math.abs(end.getX() - start.getX()), Math.abs(end.getY() - start.getY())), Math.abs(end.getZ() - start.getZ())) * 2);

        for (int step = 0; step <= steps; step++) {
            double t = (double) step / (double) steps;
            int x = MathHelper.floor(MathHelper.lerp(t, start.getX(), end.getX()));
            int y = MathHelper.floor(MathHelper.lerp(t, start.getY(), end.getY()));
            int z = MathHelper.floor(MathHelper.lerp(t, start.getZ(), end.getZ()));

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > 1) {
                            continue;
                        }

                        int px = x + dx;
                        int py = y + dy;
                        int pz = z + dz;
                        if (px < minX || px > maxX || py < minY || py > maxY || pz < minZ || pz > maxZ) {
                            continue;
                        }

                        mutablePos.set(px, py, pz);
                        BlockState existing = world.getBlockState(mutablePos);
                        if (!existing.isAir() && !existing.isReplaceable()) {
                            continue;
                        }

                        world.setBlockState(mutablePos, Blocks.GLASS.getDefaultState(), 2);
                    }
                }
            }
        }
    }
}
