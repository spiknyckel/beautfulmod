package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.BeautfulMod;
import fish.spiknyckel.beautfulmod.Config;
import fish.spiknyckel.beautfulmod.exts.ChatGuiExt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	private void clickToCopyChat(final int mouseX, final int mouseY, final int mouseButton, final CallbackInfo ci) {
		if (!Config.clickToCopyChat.get()) {
			return;
		}
		if (mouseButton == 0) {
			final Minecraft mc = Minecraft.getInstance();
			final ChatGui chat = mc.gui.getChat();
			// ha ha mc function does not use same coordinate system as mc mouse?
			final Text component = ((ChatGuiExt) chat).getFullMessageAt(Mouse.getX(), Mouse.getY());
			if (component != null) {
				if (Screen.isShiftDown()) {
					final String text = Screen.isControlDown() ? component.getFormattedString() : component.getString();
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
					mc.gui.setOverlayMessage("Copied chat message to clipboard!" + (Screen.isControlDown() ? " (With formatting)" : ""), false);
					ci.cancel();
				}
			}
		}
	}

	@ModifyConstant(method = "init", constant = @Constant(intValue = 256))
	private int changeInputLength(int constant) {
		return Config.chatInputLength.get();
	}
}
