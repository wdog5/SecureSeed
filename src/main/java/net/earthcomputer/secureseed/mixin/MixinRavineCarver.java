package net.earthcomputer.secureseed.mixin;

import net.earthcomputer.secureseed.Globals;
import net.earthcomputer.secureseed.IChunkRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.RavineCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;
import java.util.function.Function;

@Mixin(RavineCarver.class)
public class MixinRavineCarver {
    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "carveRavine", at = @At(value = "NEW", target = "(J)Ljava/util/Random;"))
    private Random redirectCreateRandom(long s, Chunk chunk, Function<BlockPos, Biome> postToBiome, long seed) {
        ChunkRandom rand = new ChunkRandom(0);
        //noinspection ConstantConditions
        ((IChunkRandom) rand).secureseed_setSeed(Globals.worldSeed, 0, 0, Globals.dimension.get(), Globals.RAVINE_CARVER_SALT, seed);
        return rand;
    }
}
