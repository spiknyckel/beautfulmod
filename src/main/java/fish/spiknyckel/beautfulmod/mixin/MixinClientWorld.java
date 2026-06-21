package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FireworksEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldData;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.WorldStorage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientWorld.class)
public abstract class MixinClientWorld extends World {
	protected MixinClientWorld(WorldStorage storage, WorldData data, Dimension dimension, Profiler profiler, boolean isClient) {
		super(storage, data, dimension, profiler, isClient);
	}

	@Override
	public void tickEntity(final Entity entity) {
		if (Config.clientEntityUpdates.get() || entity instanceof PlayerEntity || entity instanceof FireworksEntity) {
			super.tickEntity(entity);
		}
	}
}
