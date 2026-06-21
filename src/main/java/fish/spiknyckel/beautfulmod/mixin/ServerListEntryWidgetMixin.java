package fish.spiknyckel.beautfulmod.mixin;

import net.minecraft.client.gui.widget.ServerListEntryWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(ServerListEntryWidget.class)
public class ServerListEntryWidgetMixin {
//	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet", remap = false))
//	private void alwaysShowPing(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected, final float partialTicks, final CallbackInfo ci) {
//		if (!Configuration.alwaysShowPing || this.server.pingToServer < 0) {
//			return;
//		}
//
//		final int dX = x + listWidth + (((IGuiMultiplayer) this.owner).getServerListSelector().getMaxScroll() > 0 ? 8 : 0);
//		Minecraft.getMinecraft().fontRenderer.drawString(this.server.pingToServer + "ms", dX, y, EnumDyeColor.SILVER.getColorValue());
//		GlStateManager.color(1F, 1F, 1F, 1F);
//	}
}
