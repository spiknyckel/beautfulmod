package fish.spiknyckel.beautfulmod.mixin.noclip;

import com.llamalad7.mixinextras.sugar.Local;
import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(World.class)
public abstract class WorldMixin {
	@ModifyVariable(method = "canPlace", at = @At("HEAD"), argsOnly = true)
	private boolean ignoreEntityWhenPlacing(boolean skipCollisionChecks,
											@Local(argsOnly = true) @Nullable Entity source
	) {
		return Config.noClip.get() && source instanceof PlayerEntity && ((PlayerEntity) source).isCreative() || skipCollisionChecks;
	}
}
