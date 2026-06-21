package fish.spiknyckel.beautfulmod.gui;

import fish.spiknyckel.beautfulmod.Config;
import fish.spiknyckel.beautfulmod.gui.config.BeautfulConfigList;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class BeautfulGUI extends Screen {
	private static int slotHeight = 24;
	private static String carpetServerVersion;
	public static void setServerVersion(String version) { carpetServerVersion = version;}

	private final Screen parent;
	private ConfigList list = null;

	public BeautfulGUI(Screen parent) {
		this.parent = parent;
	}

	public void showList(ConfigList list) {
		this.list = list;

		this.list.setBounds(this.width, this.height, 39, this.height - 32);
		this.list.initGui();
	}

	public void init() {
		this.addButton(new ButtonWidget(100, this.width / 2 - 100, this.height - 29, "Back"));

		showList(new BeautfulConfigList(minecraft, 24));
	}

	@Override
	protected void buttonClicked(ButtonWidget button) {
		if (button.id == 100) {
			list.onClose();
			this.minecraft.openScreen(this.parent);
		}
	}

	// ===== RENDERING ===== //
	//region rendering
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		list.render(mouseX, mouseY, partialTicks);
		this.drawTooltip(mouseX, mouseY, partialTicks);

		final int startY = 8;
		this.drawCenteredString(this.textRenderer, "Beautful Mod", width / 2, startY, 0xFFFFFF);
		super.render(mouseX, mouseY, partialTicks);
	}

	public void drawTooltip(int mouseX, int mouseY, float partialTicks) {
		//list.drawTooltip(mouseX, mouseY, partialTicks);
	}
	//endregion

	// ===== EVENTS ===== //
	//region events
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		list.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		list.mouseReleased(mouseX, mouseY, state);
		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void keyPressed(char typedChar, int keyCode) {
		//list.keyDown(typedChar, keyCode);
		if (keyCode == Keyboard.KEY_ESCAPE) {
			list.onClose();
		}
		super.keyPressed(typedChar, keyCode);
	}

	@Override
	public void handleMouse() {
		super.handleMouse();
		list.handleMouse();
	}
	//endregion
}
