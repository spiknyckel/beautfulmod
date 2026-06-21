package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.client.gui.GameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameGui.class)
public class GameGuiMixin {
	@ModifyConstant(method = "renderScoreboardObjective", constant = @Constant(intValue = 15))
	private int changeScoreboardSize(int _length) {
		return Config.scoreBoardLength.get();
	}
}
