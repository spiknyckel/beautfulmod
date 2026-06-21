package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.server.command.AbstractCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCommand.class)
public class AbstractCommandMixin {
	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void alwaysAllowCommand(CallbackInfoReturnable<Boolean> cir) {
		if (Config.alwaysSingleplayerCheats.get()) {
			cir.setReturnValue(true);
		}
	}
}
