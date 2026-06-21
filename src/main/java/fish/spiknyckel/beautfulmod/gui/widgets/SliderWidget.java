package fish.spiknyckel.beautfulmod.gui.widgets;

import fish.spiknyckel.beautfulmod.BeautfulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class SliderWidget extends ButtonWidget {
	private float value;
	public boolean dragging;

	public SliderWidget(int id, int x, int y) {
		this(id, x, y, 0.0F);
	}

	public SliderWidget(int id, int x, int y, float defaultValue) {
		this(id, x, y, 200, 20, defaultValue);
	}

	public SliderWidget(int id, int x, int y, int width, int height, float defaultValue) {
		super(id, x, y, width, height, "");
		BeautfulMod.LOGGER.info("dv: " + defaultValue);
		this.value = defaultValue;
		BeautfulMod.LOGGER.info("t.v: " + this.value);
	}

	public float getValue() {
		return this.value;
	}


		@Override
	protected int getYImage(boolean hovered) {
		return 0;
	}

	@Override
	protected void renderBackground(Minecraft minecraft, int mouseX, int mouseY) {
		if (this.visible) {
			if (this.dragging) {
				this.value = (float)(mouseX - (this.x + 4)) / (this.width - 8);
				this.value = MathHelper.clamp(this.value, 0.0F, 1.0F);
			}

			minecraft.getTextureManager().bind(WIDGETS_LOCATION);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexture(this.x + (int)(this.value * (this.width - 8)), this.y, 0, 66, 4, height);
			this.drawTexture(this.x + (int)(this.value * (this.width - 8)) + 4, this.y, 196, 66, 4, height);
		}
	}

	@Override
	public boolean mouseClicked(Minecraft minecraft, int mouseX, int mouseY) {
		if (super.mouseClicked(minecraft, mouseX, mouseY)) {
			this.value = (float)(mouseX - (this.x + 4)) / (this.width - 8);
			this.value = MathHelper.clamp(this.value, 0.0F, 1.0F);
			this.dragging = true;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		this.dragging = false;
	}
}
