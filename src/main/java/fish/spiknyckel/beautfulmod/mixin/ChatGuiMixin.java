package fish.spiknyckel.beautfulmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import fish.spiknyckel.beautfulmod.Config;
import fish.spiknyckel.beautfulmod.exts.ChatGuiExt;
import net.minecraft.client.gui.ChatMessage;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatGui.class)
public abstract class ChatGuiMixin extends GuiElement implements ChatGuiExt {
	@Shadow
	public abstract @Nullable Text getMessageAt(int x, int y);

	@Unique
	private boolean earlyReturn = false;

	@Inject(method = "getMessageAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatMessage;getText()Lnet/minecraft/text/Text;"), cancellable = true)
	private void returnEntireMessage(
		int x, int y, CallbackInfoReturnable<Text> cir, @Local ChatMessage chatMessage
	) {
		if (earlyReturn) {
			cir.setReturnValue(chatMessage.getText());
		}
	}

	@ModifyConstant(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", constant = @Constant(intValue = 100))
	private int changeChatLength(int constant) {
		return Config.chatScrollLength.get();
	}

	@Override
	@Unique
	public Text getFullMessageAt(int x, int y) {
		earlyReturn = true;
		try {
			return getMessageAt(x, y);
		} finally {
			earlyReturn = false;
		}
	}
}
