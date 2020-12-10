package rina.module.widget;

// Minecraft Utils.
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// Java.
import java.awt.*;

// guiscreen.
import rina.module.OsirisPlusGUI;

// Frame.
import rina.module.frame.OsirisPlusFrame;

// Util.
import rina.util.TurokGUIState;
import rina.util.TurokRenderGL;
import rina.util.TurokString;
import rina.util.TurokRect;

// Osiris.
import me.finz0.osiris.module.Module;
import me.finz0.osiris.OsirisMod;

/**
 * @author Rina!
 *
 * Created by Rina in 28/07/2020.
 *
 **/
public class OsirisPlusWidgetMButton {
	private OsirisPlusFrame master;
	private OsirisPlusGUI absolute;

	private OsirisPlusWidgetContainer container;

	private Module module;

	private TurokRect rect;

	private int save_y;

	private int name_height;

	private int height_offset;

	// Render.
	public boolean render_button;

	// States.
	public TurokGUIState state_menu;
	public TurokGUIState state_menu_level;

	// Can.
	public boolean cam;

	// Events.
	public boolean event_mouse_passing;

	// IM SORRY FOR THIS.
	public boolean smooth;
	public boolean shadow;
	public boolean animation;

	public int category_name_r;
	public int category_name_g;
	public int category_name_b;

	public int category_background_r;
	public int category_background_g;
	public int category_background_b;
	public int category_background_a;

	public int frame_background_r;
	public int frame_background_g;
	public int frame_background_b;
	public int frame_background_a;

	public int button_name_r;
	public int button_name_g;
	public int button_name_b;

	public int button_background_r;
	public int button_background_g;
	public int button_background_b;
	public int button_background_a;

	public int button_highlight_r;
	public int button_highlight_g;
	public int button_highlight_b;
	public int button_highlight_a;

	public int button_pressed_r;
	public int button_pressed_g;
	public int button_pressed_b;
	public int button_pressed_a;

	public OsirisPlusWidgetMButton(OsirisPlusFrame master, Module module, int next_y) {
		this.master   = master;
		this.module   = module;
		this.absolute = this.master.getMaster(); 

		this.rect = new TurokRect(this.module.getName(), 10, 10);

		this.rect.x = this.master.getX();
		this.rect.y = next_y;

		this.container = new OsirisPlusWidgetContainer(this, module);

		// We save.
		this.save_y = next_y;

		this.name_height = 2 + TurokString.getStringHeight(this.rect.getTag(), true) + 2;

		this.rect.width  = this.master.getWidth();
		this.rect.height = this.name_height;

		this.render_button = (boolean) (this.master.state_maximize == TurokGUIState.GUI_FRAME_OPEN);
	
		this.state_menu       = TurokGUIState.GUI_MBUTTON_MENU_CLOSED;
		this.state_menu_level = TurokGUIState.GUI_MBUTTON_MENU_CLOSED;

		this.cam = true;

		this.height_offset = 1;

		initColors("Sorry");
	}

	public void initColors(String tag) {
		this.smooth    = false;
		this.shadow    = false;
		this.animation = true;

		this.category_name_r = 255;
		this.category_name_g = 255;
		this.category_name_b = 255;

		this.category_background_r = 0;
		this.category_background_g = 0;
		this.category_background_b = 0;
		this.category_background_a = 255;

		this.frame_background_r = 190;
		this.frame_background_g = 190;
		this.frame_background_b = 190;
		this.frame_background_a = 255;

		this.button_name_r = 255;
		this.button_name_g = 255;
		this.button_name_b = 255;

		this.button_background_r = 0;
		this.button_background_g = 0;
		this.button_background_b = 0;
		this.button_background_a = 100;

		this.button_highlight_r = 0;
		this.button_highlight_g = 0;
		this.button_highlight_b = 0;
		this.button_highlight_a = 200;

		this.button_pressed_r = 0;
		this.button_pressed_g = 0;
		this.button_pressed_b = 0;
		this.button_pressed_a = 255;
	}

	public void setX(int x) {
		this.rect.x = x;
	}

	public void setY(int y) {
		this.rect.y = y;
	}

	public void setSaveY(int save_y) {
		this.save_y = save_y;
	}

	public void remSaveY(int save_y) {
		this.save_y = this.rect.y - save_y;
	}

	public void addSaveY(int save_y) {
		this.save_y = this.rect.y + save_y;
	}

	public void setWidth(int width) {
		this.rect.width = width;
	}

	public void setHeight(int height) {
		this.rect.height = height;
	}

	public void setMousePassing(boolean value) {
		this.event_mouse_passing = value;
	}

	public Module getModule() {
		return this.module;
	}

	public OsirisPlusFrame getMaster() {
		return this.master;
	}

	public TurokRect getRect() {
		return this.rect;
	}

	public OsirisPlusWidgetContainer getContainer() {
		return this.container;
	}

	public int getX() {
		return this.rect.x;
	}

	public int getY() {
		return this.rect.y;
	}

	public int getWidth() {
		return this.rect.width;
	}

	public int getHeight() {
		return this.rect.height;
	}

	public boolean isMousePassing() {
		return this.event_mouse_passing;
	}

	public void keyboard(char char_, int key) {
		this.container.keyboard(char_, key);
	}

	public void conflict(int x, int y, int mouse) {
		if (mouse == 0) {
			if (!this.container.isMousePassing() && this.container.render_container == true) {
				this.container.setOpening(true);
			}
		}

		if (mouse == 1) {
			if (!this.container.isMousePassing() && this.container.render_container == true) {
				this.container.setOpening(true);
			}
		}
	}

	public void click(int x, int y, int mouse) {
		if (this.container.render_container) {
			this.container.click(x, y, mouse);
		}

		if (mouse == 0) {
			if (isMousePassing()) {
				this.absolute.refreshFrame();
				
				this.master.setAbleToDrag(false);

				this.module.toggle();
			}
		}

		if (mouse == 1) {
			if (isMousePassing() && this.container.render_container == false) {
				this.absolute.refreshFrame();

				this.container.setOpening(true);
			}
		}
	}

	public void release(int x, int y, int mouse) {
		if (this.container.render_container) {
			this.container.release(x, y, mouse);
		}

		if (mouse == 0) {
			if (isMousePassing()) {
				this.master.setAbleToDrag(true);
			}
		}
	}

	public void render() {
		update(this.absolute.getMouseX(), this.absolute.getMouseY());

		updateColors();

		if (this.render_button) {
			TurokRenderGL.color(this.button_background_r, this.button_background_g, this.button_background_b, this.button_background_a);
			TurokRenderGL.drawSolidRect(this.rect);

			if (this.module.isEnabled()) {
				TurokRenderGL.color(this.button_pressed_r, this.button_pressed_g, this.button_pressed_b, this.button_pressed_a);
				TurokRenderGL.drawSolidRect(this.rect);

				TurokString.renderString(this.rect.getTag(), this.rect.x + 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b,  this.shadow, this.smooth);
			
				if (isMousePassing()) {
					TurokString.renderString(this.module.getDescription(), 1, 2, this.button_name_r, this.button_name_r, this.button_name_r,  this.shadow, this.smooth);
				}
			} else {
				if (isMousePassing() && this.cam) {
					TurokRenderGL.color(this.button_highlight_r, this.button_highlight_g, this.button_highlight_b, this.button_highlight_a);
					TurokRenderGL.drawSolidRect(this.rect);

					TurokString.renderString(this.rect.getTag(), this.rect.x + 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);
					
					TurokString.renderString(this.module.getDescription(), 1, 2, this.button_name_r, this.button_name_g, this.button_name_b,  this.shadow, this.smooth);
				} else {
					TurokString.renderString(this.rect.getTag(), this.rect.x + 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);
				}
			}

			this.container.render();
		}

		animation();
	}

	public void animation() {
		if (this.animation) {
			if (this.master.state_maximize_level == TurokGUIState.GUI_FRAME_CLOSING) {
				if ((this.master.getRect().getY() + this.master.getRect().getHeight()) <= (this.rect.y + this.rect.height)) {
					this.render_button = false;
				}
			}

			if (this.master.state_maximize_level == TurokGUIState.GUI_FRAME_OPENING) {
				if ((this.master.getRect().getY() + this.master.getRect().getHeight()) + 10 >= (this.rect.y + this.rect.height)) {
					this.render_button = true;
				}
			}
		} else {
			if (this.master.state_maximize_level == TurokGUIState.GUI_FRAME_CLOSED) {
				this.render_button = false;
			}

			if (this.master.state_maximize_level == TurokGUIState.GUI_FRAME_OPEN) {
				this.render_button = true;
			}
		}
	}

	public void update(int x, int y) {
		this.rect.x = this.master.getX();
		this.rect.y = (this.master.getY() + this.save_y);

		if (this.smooth) {
			this.height_offset = 2;
		} else {
			this.height_offset = 1;
		}
	}

	public void refresh() {
		this.cam = true;

		if (this.rect.collide(this.absolute.getMouseX(), this.absolute.getMouseY()) && this.master.state_maximize == TurokGUIState.GUI_FRAME_OPEN) {
			setMousePassing(true);

			this.master.setPassingSomeModule(true);
		} else {
			setMousePassing(false);

			this.master.setPassingSomeModule(false);
		}
	}

	public void disableRefresh() {
		setMousePassing(false);
		this.master.setPassingSomeModule(false);
		this.cam = false;

		if (this.container.render_container) {
			this.container.setOpening(true);
		}
	}

	public void updateColors() {
		this.smooth    = OsirisMod.getInstance().settingsManager.getSettingByID("GUISmoothFont").getValBoolean();
		this.shadow    = OsirisMod.getInstance().settingsManager.getSettingByID("GUIShadow").getValBoolean();
		this.animation = true;

		this.category_name_r = OsirisMod.getInstance().settingsManager.getSettingByID("GUICategoryNameR").getValInt();
		this.category_name_g = OsirisMod.getInstance().settingsManager.getSettingByID("GUICategoryNameG").getValInt();
		this.category_name_b = OsirisMod.getInstance().settingsManager.getSettingByID("GUICategoryNameB").getValInt();

		this.category_background_r = OsirisMod.getInstance().settingsManager.getSettingByID("GUICategoryNameBackgroundR").getValInt();
		this.category_background_g = OsirisMod.getInstance().settingsManager.getSettingByID("GUICategoryNameBackgroundG").getValInt();
		this.category_background_b = OsirisMod.getInstance().settingsManager.getSettingByID("GUICategoryNameBackgroundB").getValInt();
		this.category_background_a = OsirisMod.getInstance().settingsManager.getSettingByID("GUICategoryNameBackgroundA").getValInt();

		this.frame_background_r = OsirisMod.getInstance().settingsManager.getSettingByID("GUIFrameBackgroundR").getValInt();
		this.frame_background_g = OsirisMod.getInstance().settingsManager.getSettingByID("GUIFrameBackgroundG").getValInt();
		this.frame_background_b = OsirisMod.getInstance().settingsManager.getSettingByID("GUIFrameBackgroundB").getValInt();
		this.frame_background_a = OsirisMod.getInstance().settingsManager.getSettingByID("GUIFrameBackgroundA").getValInt();

		this.button_name_r = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonNameR").getValInt();
		this.button_name_g = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonNameG").getValInt();
		this.button_name_b = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonNameB").getValInt();

		this.button_background_r = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonBackgroundR").getValInt();
		this.button_background_g = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonBackgroundG").getValInt();
		this.button_background_b = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonBackgroundB").getValInt();
		this.button_background_a = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonBackgroundA").getValInt();

		this.button_highlight_r = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonHighlightR").getValInt();
		this.button_highlight_g = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonHighlightG").getValInt();
		this.button_highlight_b = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonHighlightB").getValInt();
		this.button_highlight_a = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonHighlightA").getValInt();

		this.button_pressed_r = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonPressedR").getValInt();
		this.button_pressed_g = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonPressedG").getValInt();
		this.button_pressed_b = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonPressedB").getValInt();
		this.button_pressed_a = OsirisMod.getInstance().settingsManager.getSettingByID("GUIButtonPressedA").getValInt();
	}
}