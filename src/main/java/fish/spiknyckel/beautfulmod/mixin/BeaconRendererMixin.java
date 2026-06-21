package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.render.block.entity.BeaconRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BeaconRenderer.class)
public class BeaconRendererMixin {
	@Inject(method = "render(DDDDDLjava/util/List;D)V", at = @At("HEAD"), cancellable = true)
	private void disableBeamRendering(double x, double y, double z, double tickDelta, double beamAngle, List<BeaconBlockEntity.BeamSection> sections, double time, CallbackInfo ci) {
		if (!Config.showBeaconBeam.get()) {
			ci.cancel();
		}
	}
}
