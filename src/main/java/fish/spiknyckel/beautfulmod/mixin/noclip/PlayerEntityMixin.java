package fish.spiknyckel.beautfulmod.mixin.noclip;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.living.player.PlayerAbilities;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
	public PlayerEntityMixin(final World world) {
		super(world);
	}

	@Shadow
	public abstract boolean isCreative();

	@Shadow
	public PlayerAbilities abilities;

	@Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;isSpectator()Z"))
	private boolean updateNoClipping(final PlayerEntity player) {
		return player.isSpectator() || (Config.noClip.get() && player.isCreative() && player.abilities.flying);
	}

	@Override
	public void move(final MoverType type, final double x, final double y, final double z) {
		if (type == MoverType.SELF || !(Config.noClip.get() && this.isCreative() && this.abilities.flying)) {
			super.move(type, x, y, z);
		}
	}
}
