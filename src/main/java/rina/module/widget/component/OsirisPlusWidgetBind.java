package rina.module.widget.component;

// Minecraft Utils.
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// lwjgl.
import org.lwjgl.input.Keyboard;

// Java.
import java.util.*;

// guiscreen.
import rina.module.OsirisPlusGUI;

// Frame.
import rina.module.frame.OsirisPlusFrame;

// Widget.
import rina.module.widget.OsirisPlusWidgetContainer;
import rina.module.widget.OsirisPlusWidgetAbstract;

// Util.
import rina.util.TurokGUIState;
import rina.util.TurokRenderGL;
import rina.util.TurokString;
import rina.util.TurokRect;

// Osiris.
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.OsirisMod;

/**
 * @author Rina!
 *
 * Created by Rina in 01/07/2020.
 *
 **/
public class OsirisPlusWidgetBind extends OsirisPlusWidgetAbstract {
	private OsirisPlusWidgetContainer container;

	private Module module;

	private TurokRect rect;

	private int save_y;

	private int tick;

	private int height_offset;

	// String animation.
	private String anime_lol_gay;

	// Render.
	public boolean render_button;

	// Events.
	public boolean event_mouse;
	public boolean event_mouse_click;

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

	public OsirisPlusWidgetBind(OsirisPlusWidgetContainer container, Module module, int next_y) {
		this.container = container;
		this.module   = module;

		this.rect = new TurokRect(this.module.getName(), container.getX() + 2, container.getY() + next_y);

		this.rect.width  = this.container.getWidth() - 2;
		this.rect.height = 2 + TurokString.getStringHeight(this.rect.getTag(), true) + 2;
	
		this.save_y = next_y;

		this.event_mouse       = false;
		this.event_mouse_click = false;

		this.anime_lol_gay = "|"; // | / - \;
		this.tick          = 0;

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

	@Override
	public void setX(int x) {
		this.rect.x = x;
	}

	@Override
	public void setY(int y) {
		this.rect.y = y;
	}

	@Override
	public void setWidth(int width) {
		this.rect.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.rect.height = height;
	}

	@Override
	public TurokRect getRect() {
		return this.rect;
	}

	@Override
	public int getX() {
		return this.rect.x;
	}

	@Override
	public int getY() {
		return this.rect.y;
	}

	@Override
	public int getWidth() {
		return this.rect.width;
	}

	@Override
	public int getHeight() {
		return this.rect.height;
	}

	public void setMousePassing(boolean value) {
		this.event_mouse = value;
	}

	public void setClick(boolean value) {
		this.event_mouse_click = value;
	}

	// Events.
	public boolean isMousePassing() {
		return this.event_mouse;
	}

	public boolean isClicked() {
		return this.event_mouse_click;
	}

	@Override
	public void keyboard(char char_, int key) {
		if (isClicked() && this.render_button) {
			switch (key) {
				case Keyboard.KEY_ESCAPE : {
					setClick(false);

					break;
				}

				case Keyboard.KEY_DELETE : {
					this.module.setBind(0);

					setClick(false);

					break;
				}

				default : {
					this.module.setBind(key);

					setClick(false);

					break;
				}
			}
		}
	}

	@Override
	public void click(int x, int y, int mouse) {
		if (mouse == 0) {
			if (isMousePassing()) {
				setClick(true);
			}

			if (!isMousePassing() && isClicked()) {
				setClick(false);
			}
		}

		if (mouse == 1) {
			if (!isMousePassing() && isClicked()) {
				setClick(false);
			}
		}
	}

	@Override
	public void render() {
		update(this.container.absolute.getMouseX(), this.container.absolute.getMouseY());

		updateColors();

		if (this.render_button) {
			if (isClicked()) {
				TurokRenderGL.color(this.button_pressed_r, this.button_pressed_g, this.button_pressed_b, this.button_pressed_a);
				TurokRenderGL.drawSolidRect(this.rect);

				TurokString.renderString("Listening", this.rect.x + 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);
				TurokString.renderString(this.anime_lol_gay, (this.rect.x + this.rect.width) - (TurokString.getStringWidth(this.anime_lol_gay, true)) - 3, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);
			
				this.container.absolute.setBinding(true);
			} else {
				if (isMousePassing()) {
					TurokRenderGL.color(this.button_highlight_r, this.button_highlight_g, this.button_highlight_b, this.button_highlight_a);
					TurokRenderGL.drawSolidRect(this.rect);
				} else {
					TurokRenderGL.color(this.button_background_r, this.button_background_g, this.button_background_b, this.button_background_a);
					TurokRenderGL.drawSolidRect(this.rect);
				}

				TurokString.renderString("Bind", this.rect.x + 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);
				TurokString.renderString(Keyboard.getKeyName(this.module.getBind()), (this.rect.x + this.rect.width) - (TurokString.getStringWidth(Keyboard.getKeyName(this.module.getBind()), true)) - 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);
			
				this.container.absolute.setBinding(false);
			}
		}

		animation();
	}

	public void animation() {
		if (this.animation) {
			if (this.container.getMaster().state_menu_level == TurokGUIState.GUI_MBUTTON_MENU_CLOSING) {
				this.render_button = false;
			}

			if (this.container.getMaster().state_menu_level == TurokGUIState.GUI_MBUTTON_MENU_OPEN) {
				this.render_button = true;
			}

			if (isClicked()) {
				this.tick++;

				if (this.tick >= 6) {
					this.anime_lol_gay = "/";
				}

				if (this.tick >= 12) {
					this.anime_lol_gay = "-";
				}

				if (this.tick >= 18) {
					this.anime_lol_gay = "\\";
				}

				if (this.tick >= 24) {
					this.anime_lol_gay = "|";

					this.tick = 0;
				}
			}
		} else {
			if (this.container.getMaster().state_menu_level == TurokGUIState.GUI_MBUTTON_MENU_CLOSED) {
				this.render_button = false;
			}

			if (this.container.getMaster().state_menu_level == TurokGUIState.GUI_MBUTTON_MENU_OPEN) {
				this.render_button = true;
			}

			if (isClicked()) {
				this.tick = 0;
				
				this.anime_lol_gay = "?";	
			}
		}
	}

	public void update(int x, int y) {
		this.rect.x = (this.container.getX() + 2);
		this.rect.y = (this.container.getY() + this.save_y);

		this.rect.width = this.container.getWidth() - 4;

		if (this.smooth) {
			this.height_offset = 2;
		} else {
			this.height_offset = 1;
		}

		if (this.rect.collide(x, y) && this.render_button && this.container.getMaster().cam) {
			setMousePassing(true);
		} else {
			setMousePassing(false);
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