package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.BeautfulMod;
import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerCombatS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	@Inject(method = "handlePlayerCombat", at = @At(value = "INVOKE", target="Lnet/minecraft/client/Minecraft;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
	private void deathHook(PlayerCombatS2CPacket packetIn, CallbackInfo ci) {
		if (Config.deathLocation.get()) {
			final Minecraft mc = Minecraft.getInstance();
			final BlockPos pos = new BlockPos(mc.player);
			final String formatted = String.format("You died @ %d %d %d", pos.getX(), pos.getY(), pos.getZ());
			final Text message = new LiteralText(formatted);
			message.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, formatted));
			mc.gui.getChat().addMessage(message);
		}

		if (Config.respawnOnDeath.get()) {
			final Minecraft mc = Minecraft.getInstance();
			mc.player.respawn();
		}
	}

	@Redirect(method = "handleWorldTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/WorldTimeS2CPacket;getTimeOfDay()J"))
	private long alwaysDay(WorldTimeS2CPacket packet) {
		final long time = packet.getTimeOfDay();
		if (Config.alwaysDay.get()) {
			long newTime = -(time - time % 24000L + 6000L);
			BeautfulMod.LOGGER.info(newTime);
			return time >= 0 ? newTime : time;
		}

		return time;
	}
}

