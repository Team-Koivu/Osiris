package rina.module;

// Minecraft Utils.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// OpenGL.
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;

// Java.
import java.io.IOException;
import java.util.*;
import java.awt.*;

// backend UI.
import rina.module.frame.OsirisPlusFrame;

// Util.
import rina.util.TurokRenderGL;
import rina.util.TurokRect;

// Finz0.
import me.finz0.osiris.module.Module.Category;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.OsirisMod;

/**
 * @author Rina!
 *
 * Created by Rina in 27/07/2020.
 *
 **/
public class OsirisPlusGUI extends GuiScreen {
	private ArrayList<OsirisPlusFrame> modules_category_frame;

	public OsirisPlusFrame actual_frame;
	public OsirisPlusFrame frame_gui;

	private int mouse_x;
	private int mouse_y;

	private int screen_w;
	private int screen_h;

	// Mode.
	public boolean mode;

	// Events.
	private boolean event_focusing_frames;
	private boolean event_binding;

	public OsirisPlusGUI() {
		this.modules_category_frame = new ArrayList<>();

		this.frame_gui = new OsirisPlusFrame(this, Category.GUI);

		this.mouse_x = 0;
		this.mouse_y = 0;

		this.event_focusing_frames = true;
		this.event_binding         = false;

		int count = 0;
		int size  = Category.values().length;

		for (Category category : Category.values()) {
			OsirisPlusFrame frames = new OsirisPlusFrame(this, category);

			if (this.actual_frame != null) {
				frames.setY(this.actual_frame.getY() + this.actual_frame.getHeight() + 2);
			} else {
				frames.setY(10);
			}

			this.actual_frame = frames;

			this.modules_category_frame.add(frames);

			count++;
		}

		this.screen_w = 0;
		this.screen_h = 0;

		this.mode = true;
	}

	@Override
	public void keyTyped(char char_, int key) {
		if (this.mode) {
			this.actual_frame.keyboard(char_, key);

			if (key == Keyboard.KEY_ESCAPE && !isBinding()) {
				Minecraft.getMinecraft().displayGuiScreen(null);
			}
		} else {
			this.frame_gui.keyboard(char_, key);

			if (key == Keyboard.KEY_ESCAPE && !isBinding()) {
				ModuleManager.getModuleByName("HUDEditor").toggle();

				Minecraft.getMinecraft().displayGuiScreen(null);
			}
		}
	}

	public void handleMouseInput() throws IOException {
		if (Mouse.getEventDWheel() > 0) {
			if (this.mode) {
				for (OsirisPlusFrame frames : this.modules_category_frame) {
					frames.getRect().y += 5;
				}
			} else {
				this.frame_gui.getRect().y += 5;
			}
		}

		if (Mouse.getEventDWheel() < 0) {
			if (this.mode) {
				for (OsirisPlusFrame frames : this.modules_category_frame) {
					frames.getRect().y -= 5;
				}
			} else {
				this.frame_gui.getRect().y -= 5;
			}
		}
		
		super.handleMouseInput();
	}

	@Override
	public void mouseClicked(int x, int y, int mouse) {
		if (this.mode) {
			for (OsirisPlusFrame frames : this.modules_category_frame) {
				frames.conflict(x, y, mouse);
			}

			this.actual_frame.click(x, y, mouse);

			if (mouse == 0) {
				if (this.actual_frame.getRect().collide(x, y) && this.actual_frame.isAbleToDrag()) {
					refreshFrame();

					this.actual_frame.setDragging(true);
		
					this.actual_frame.setMoveX(x - this.actual_frame.getX());
					this.actual_frame.setMoveY(y - this.actual_frame.getY());
				}
			}
		
			if (mouse == 1) {
				if (this.actual_frame.collide(x, y)) {
					refreshFrame();

					this.actual_frame.setMaximize(true);
				}
			}
		} else {
			this.frame_gui.conflict(x, y, mouse);
			this.frame_gui.click(x, y, mouse);
			this.frame_gui.clickHUD(x, y, mouse);

			if (mouse == 0) {
				if (this.frame_gui.getRect().collide(x, y) && this.frame_gui.isAbleToDrag()) {
					this.frame_gui.setDragging(true);
		
					this.frame_gui.setMoveX(x - this.frame_gui.getX());
					this.frame_gui.setMoveY(y - this.frame_gui.getY());
				}
			}

			if (mouse == 1) {
				if (this.frame_gui.collide(x, y)) {
					this.frame_gui.setMaximize(true);
				}
			}
		}
	}

	@Override
	public void mouseReleased(int x, int y, int mouse) {
		if (this.mode) {
			this.actual_frame.release(x, y, mouse);
		} else {
			this.frame_gui.release(x, y, mouse);
			this.frame_gui.releaseHUD(x, y, mouse);
		}
	}

	@Override
	public void drawScreen(int x, int y, float partial_ticks) {
		if (this.mode) this.drawDefaultBackground();

		ScaledResolution scaled_resolution = new ScaledResolution(Minecraft.getMinecraft());

		TurokRenderGL.fixScreen(scaled_resolution.getScaledWidth(), scaled_resolution.getScaledHeight());

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		refreshGUI(x, y, scaled_resolution);

		if (this.mode) {
			for (OsirisPlusFrame frames : this.modules_category_frame) {
				frames.render();

				if (isFocusingFrames()) {
					if (frames.verifyEvent(x, y)) {
						this.actual_frame = frames;
					}
				}

				if (!this.actual_frame.getRect().getTag().equalsIgnoreCase(frames.getRect().getTag())) {
					frames.disableRefresh();
				}
			}

			this.actual_frame.refresh();

			this.frame_gui.setX(getFrameByTag("GUI").getX());
			this.frame_gui.setY(getFrameByTag("GUI").getY());
		} else {
			this.frame_gui.render();
			this.frame_gui.refresh();
			this.frame_gui.updateHUD(x, y);

			getFrameByTag("GUI").setX(this.frame_gui.getX());
			getFrameByTag("GUI").setY(this.frame_gui.getY());
		}

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GlStateManager.color(1, 1, 1);
	}

	public void refreshFrame() {
		try {
			this.modules_category_frame.remove(this.actual_frame);
			this.modules_category_frame.add(this.actual_frame);
		} catch (Exception exc) {}
	}

	public void refreshGUI(int x, int y, ScaledResolution scl) {
		this.mouse_x = x;
		this.mouse_y = y;

		this.screen_w = scl.getScaledWidth();
		this.screen_h = scl.getScaledHeight();
	}

	public void setFocusingFrames(boolean value) {
		this.event_focusing_frames = value;
	}

	public void setBinding(boolean value) {
		this.event_binding = value;
	}

	public int getMouseX() {
		return this.mouse_x;
	}

	public int getMouseY() {
		return this.mouse_y;
	}

	public int getScreenWidth() {
		return this.screen_w;
	}

	public int getScreenHeight() {
		return this.screen_h;
	}

	public boolean isFocusingFrames() {
		return this.event_focusing_frames;
	}

	public boolean isBinding() {
		return this.event_binding;
	}

	public ArrayList<OsirisPlusFrame> getFrames() {
		return this.modules_category_frame;
	}

	public OsirisPlusFrame getFrameByTag(String tag) {
		for (OsirisPlusFrame frames : this.modules_category_frame) {
			if (frames.getRect().getTag().equalsIgnoreCase(tag)) {
				return frames;
			}
		}

		return null;
	}
}