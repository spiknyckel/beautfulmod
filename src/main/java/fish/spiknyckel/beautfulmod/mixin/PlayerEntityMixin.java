package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	public PlayerEntityMixin(World world) {
		super(world);
	}

	@Inject(method = "getEyeHeight", at = @At("HEAD"), cancellable = true)
	private void fixElytraSneakEyeHeight(CallbackInfoReturnable<Float> cir) {
		if (Config.elytraSneakEyeHeightFix.get() && (this.isFallFlying() || this.height == 0.6F)) {
			cir.setReturnValue(0.4F);
		}
	}

	@ModifyConstant(method = "getEyeHeight", constant = @Constant(floatValue = 0.08F))
	private float changeSneakEyeHeight(float constant) {
		return 1.62F - Config.sneakEyeHeight.get();
	}
}
