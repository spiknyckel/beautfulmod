package fish.spiknyckel.beautfulmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import fish.spiknyckel.beautfulmod.Config;
import fish.spiknyckel.beautfulmod.KeyBindings;
import net.minecraft.block.*;
import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	@ModifyVariable(
		method = "useBlock",
		at = @At(
			"STORE"
		),
		ordinal = 0
	)
	private float modifyX(float f,
						  @Local(argsOnly = true) BlockPos pos,
						  @Local(argsOnly = true) Direction face,
						  @Local(argsOnly = true) InteractionHand hand
		) {
		if (Config.carpetAccurateBlockPlacement.get()) {
			return beautful$encodeBlockRotation(pos, face, hand, f);
		}
		return f;
	}

	@ModifyConstant(method = "tickBlockMining", constant = @Constant(intValue = 5))
	private int postBlockMine(int miningCooldown) {
		return Config.clickBlockMining.get() ? 0 : miningCooldown;
	}

	@Unique
	private float beautful$encodeBlockRotation(final BlockPos pos, final Direction dir, final InteractionHand hand, final float fX) {
		final PlayerEntity player = Minecraft.getInstance().player;
		final Block item = Block.byItem(player.getItemInHand(hand).getItem());
		if (!beautful$shouldRotate(fX, item)) {
			return fX;
		}

		Direction face = dir;
		if (KeyBindings.carpetFaceInto.isPressed() && beautful$isPiston(item)) {
			face = face.getOpposite();
		} else {
			if (beautful$isRedstoneTorch(item) || beautful$isGlazedTerracotta(item)) {
				face = player.getHorizontalFacing().getOpposite();
			} else {
				face = Direction.nearest(pos.offset(face), player);
			}

			if (beautful$isObserver(item)) {
				face = face.getOpposite();
			}
		}

		if (KeyBindings.carpetFlipFace.isPressed()) {
			face = face.getOpposite();
		}

		return 2F + face.getId();
	}

	/**
	 * Checks for the item types that should be accurate placed, skips everything else.
	 * If f value is above 1 then the protocol is already being used and also returns false to skip rotation.
	 */
	@Unique
	private boolean beautful$shouldRotate(final float f, final Block block) {
		if (f > 1) {
			return false;
		}

		return beautful$isDispenser(block) || beautful$isGlazedTerracotta(block) || beautful$isObserver(block) || beautful$isPiston(block) || beautful$isRedstoneTorch(block);
	}

	@Unique
	private boolean beautful$isDispenser(final Block block) {
		return block instanceof DispenserBlock;
	}

	@Unique
	private boolean beautful$isGlazedTerracotta(final Block block) {
		return block instanceof GlazedTerracottaBlock;
	}

	@Unique
	public boolean beautful$isObserver(final Block block) {
		return block instanceof ObserverBlock;
	}

	@Unique
	private boolean beautful$isPiston(final Block block) {
		return block instanceof PistonBaseBlock;
	}

	@Unique
	private boolean beautful$isRedstoneTorch(final Block block) {
		return block instanceof RedstoneTorchBlock;
	}
}

