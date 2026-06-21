package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.BeautfulMod;
import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.living.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.world.HitResult;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public LocalClientPlayerEntity player;

	@Shadow
	public ClientPlayerInteractionManager interactionManager;

	@Shadow
	public HitResult crosshairTarget;

	@Shadow
	public ClientWorld world;



	@Redirect(
		method = "doPick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/player/PlayerInventory;selectSlot(Lnet/minecraft/item/ItemStack;)V"
		)
	)
	private void setMaxSizeSlot(
		PlayerInventory inventory,
		ItemStack stack
	) {
		inventory.selectSlot(stack);
		if (Config.alwaysPickBlockMaxStack.get()) {
			stack.setSize(stack.getMaxSize());
			inventory.items.set(inventory.selectedSlot, stack);
		}
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;tick()V", shift = At.Shift.AFTER))
	private void tick(CallbackInfo ci) {
		if (Config.noFall.get() && player.fallDistance > 2F && !player.isFallFlying()) {
			player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
		}
	}
}
