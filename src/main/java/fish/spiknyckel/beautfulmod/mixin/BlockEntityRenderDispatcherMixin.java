package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

	@Redirect(method = "render(Lnet/minecraft/block/entity/BlockEntity;FI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntity;squaredDistanceTo(DDD)D"))
	private double alwaysRenderTileEntities(BlockEntity be, final double x, final double y, final double z) {
		return Config.alwaysRenderTileEntities.get() ? 0D : be.squaredDistanceTo(x, y, z);
	}
}
