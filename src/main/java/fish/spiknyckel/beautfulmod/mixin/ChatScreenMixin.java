package fish.spiknyckel.beautfulmod.mixin;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
			final Text component = chat.getMessageAt(mouseX, mouseY);
			if (component != null) {
				if (Screen.isShiftDown()) {
					final String text = Screen.isControlDown() ? component.getFormattedString() : component.getString();
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
					Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Copied chat message to clipboard!" + (GuiScreen.isCtrlKeyDown() ? " (With formatting)" : ""), false);
					ci.cancel();
				}
			}
		}
	}

	@Unique
	private static ITextComponent cutelessmod$getChatLine() {
		final Minecraft mc = Minecraft.getMinecraft();
		final GuiNewChat chat = mc.ingameGUI.getChatGUI();
		if (!chat.getChatOpen()) {
			return null;
		}

		final ScaledResolution scaledresolution = new ScaledResolution(mc);
		final int factor = scaledresolution.getScaleFactor();
		final float scale = chat.getChatScale();
		final int scaledX = MathHelper.floor((float) (Mouse.getX() / factor - 2) / scale);
		final int scaledY = MathHelper.floor((float) (Mouse.getY() / factor - 40) / scale);
		if (scaledX < 0 || scaledY < 0) {
			return null;
		}

		final List<ChatLine> drawnChatLines = ((IGuiNewChat) chat).getDrawnChatLines();
		final int lineCount = Math.min(chat.getLineCount(), drawnChatLines.size());
		final int fontHeight = mc.fontRenderer.FONT_HEIGHT;
		if (scaledX > MathHelper.floor((float) chat.getChatWidth() / chat.getChatScale()) || scaledY >= fontHeight * lineCount + lineCount) {
			return null;
		}

		final int idc = scaledY / fontHeight + ((IGuiNewChat) chat).getScrollPos();
		if (idc >= 0 && idc < drawnChatLines.size()) {
			return drawnChatLines.get(idc).getChatComponent();
		}

		return null;
	}
}
