package pw.cinque.waypoints.gui;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.GL11;

public class GuiColorPicker extends GuiButton {

	/**
	 * Array of colors that the ColorPicker will 'scroll' through
	 */
	private static final int[] COLORS = { 0xFFCF000F, 0xFFF22613, 0xFFDB0A5B, 0xFF9B59B6, 0xFF3A539B, 0xFF59ABE3, 0xFF1F3A93, 0xFF1BA39C, 0xFF3FC380, 0xFFE9D460, 0xFFF9690E };
	private int colorIndex;
	
	public GuiColorPicker(int id, int x, int y, int width, int height) {
		super(id, x, y, width, height, "");
		this.colorIndex = new Random().nextInt(COLORS.length - 1);
	}

	@Override
	public void drawButton(Minecraft mc, int x, int y, float partialTicks) {
		if (!this.visible) {
			return;
		}

		FontRenderer fontrenderer = mc.fontRenderer;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.hovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
		int hoverState = this.getHoverState(this.hovered);

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		this.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, 0xFFA0A0A0);
		this.drawRect(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, getSelectedColor());

		this.mouseDragged(mc, x, y);
	}
	
	public void nextColor() {
		colorIndex++;
		
		if (colorIndex == COLORS.length) {
			colorIndex = 0;
		}
	}
	
	public int getSelectedColor() {
		return COLORS[colorIndex];
	}

}
