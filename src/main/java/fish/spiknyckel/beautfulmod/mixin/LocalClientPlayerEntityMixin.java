package fish.spiknyckel.beautfulmod.mixin;

import com.mojang.authlib.GameProfile;
import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalClientPlayerEntity.class)
public abstract class LocalClientPlayerEntityMixin extends ClientPlayerEntity {
	@Shadow
	@Final
	public ClientPlayNetworkHandler networkHandler;

	public LocalClientPlayerEntityMixin(World world, GameProfile gameProfile) {
		super(world, gameProfile);
	}

	@Inject(method = "mobTick", at = @At(value = "FIELD", target = "Lnet/minecraft/network/packet/c2s/play/PlayerMovementActionC2SPacket$Action;START_FALL_FLYING:Lnet/minecraft/network/packet/c2s/play/PlayerMovementActionC2SPacket$Action;"))
	public void deployElytra(CallbackInfo ci) {
		if (Config.elytraFix.get()) {
			this.setFlag(7, true);
		}
	}

	@Inject(method = "mobTick", at = @At("HEAD"))
	private void cancelElytra(final CallbackInfo ci) {
		if (Config.elytraCancellation.get() && this.isFallFlying() && Screen.isShiftDown() && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			this.setFlag(7, false);
		}
	}

	@Inject(method = "sendChat", at = @At("HEAD"), cancellable = true)
	public void sendChat(String message, CallbackInfo ci) {
		while (message.length() > 256) {
			this.networkHandler.sendPacket(new ChatMessageC2SPacket(message.substring(0, 256)));
			message = message.substring(256);
		}
		if (!message.isEmpty()) {
			this.networkHandler.sendPacket(new ChatMessageC2SPacket(message));

		}
		ci.cancel();
	}
}
