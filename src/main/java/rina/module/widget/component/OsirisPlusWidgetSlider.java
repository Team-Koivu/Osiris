package rina.module.widget.component;

// Minecraft Utils.
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// Java.
import java.math.*;
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
 * Created by Rina in 31/07/2020.
 *
 **/
public class OsirisPlusWidgetSlider extends OsirisPlusWidgetAbstract {
	private OsirisPlusWidgetContainer container;

	private Setting setting;

	private TurokRect rect;

	private int save_y;

	private int color_r;
	private int color_g;
	private int color_b;
	private int color_a;

	private int anim_width;
	private int anim_alpha;

	private String color_slider_name;

	private int height_offset;

	// Render.
	public boolean render_button;

	// Optional.
	public boolean optional_color;

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

	public OsirisPlusWidgetSlider(OsirisPlusWidgetContainer container, Setting setting, int next_y) {
		this.container = container;
		this.setting   = setting;

		this.rect = new TurokRect(this.setting.getDisplayName(), container.getX() + 2, container.getY() + next_y);

		this.rect.width  = this.container.getWidth() - 2;
		this.rect.height = 2 + TurokString.getStringHeight(this.rect.getTag(), true) + 2;
	
		this.save_y = next_y;

		this.event_mouse       = false;
		this.event_mouse_click = false;

		this.color_r = 0;
		this.color_g = 0;
		this.color_b = 0;
		this.color_a = 255;

		this.optional_color = false;

		this.anim_width = 0;
		this.anim_alpha = 0;

		this.color_slider_name = "no";

		if ((boolean) this.setting.getId().contains("Red")) {
			this.color_slider_name = this.setting.getId().replace("Red", "");
		}

		if ((boolean) this.setting.getId().contains("Green")) {
			this.color_slider_name = this.setting.getId().replace("Green", "");
		}

		if ((boolean) this.setting.getId().contains("Blue")) {
			this.color_slider_name = this.setting.getId().replace("Blue", "");
		}

		if ((boolean) this.setting.getId().contains("Alpha")) {
			this.color_slider_name = this.setting.getId().replace("Alpha", "");
		}

		initColors("Sorry");

		this.height_offset = 1;
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

	public boolean isColorSlider() {
		return this.optional_color;
	}

	@Override
	public void click(int x, int y, int mouse) {
		if (mouse == 0) {
			if (isMousePassing()) {
				setClick(true);
			}
		}
	}

	@Override
	public void release(int x, int y, int mouse) {
		if (mouse == 0) {
			setClick(false);
		}
	}

	@Override
	public void render() {
		update(this.container.absolute.getMouseX(), this.container.absolute.getMouseY());

		updateColors();

		if (this.render_button) {
			String value = this.setting.onlyInt() == true ? Integer.toString(this.setting.getValInt()) : Double.toString(this.setting.getValDouble());

			if (isClicked()) {
				if (isMousePassing()) {
					TurokRenderGL.color(this.button_pressed_r, this.button_pressed_g, this.button_pressed_b, this.button_pressed_a);
					TurokRenderGL.drawSolidRect(this.rect.x, this.rect.y, this.rect.x + ((int) ((this.rect.width) * (this.setting.getValDouble() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()))) - 2, this.rect.y + this.rect.height);

					if (isColorSlider() && this.container.getMaster().cam) {
						TurokRenderGL.color(this.color_r, this.color_g, this.color_b, this.anim_alpha);
						TurokRenderGL.drawSolidRect(this.container.getX() + this.container.getWidth(), this.container.getY(), this.container.getX() + this.container.getWidth() + this.anim_width, this.container.getY() + this.container.getHeight());
					}
				} else {
					TurokRenderGL.color(this.button_pressed_r, this.button_pressed_g, this.button_pressed_b, this.button_pressed_a);
					TurokRenderGL.drawSolidRect(this.rect.x, this.rect.y, this.rect.x + ((int) ((this.rect.width) * (this.setting.getValDouble() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()))) - 2, this.rect.y + this.rect.height);
				}

				TurokString.renderString(value, (this.rect.x + (this.rect.width / 2)) - ((TurokString.getStringWidth(value, true)) / 2) - 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);
			} else {
				if (isMousePassing()) {
					TurokRenderGL.color(this.button_highlight_r, this.button_highlight_g, this.button_highlight_b, this.button_highlight_a);
					TurokRenderGL.drawSolidRect(this.rect.x, this.rect.y, this.rect.x + ((int) ((this.rect.width) * (this.setting.getValDouble() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()))) - 2, this.rect.y + this.rect.height);

					TurokString.renderString(value, (this.rect.x + (this.rect.width / 2)) - ((TurokString.getStringWidth(value, true)) / 2) - 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);

					if (isColorSlider()) {
						TurokRenderGL.color(this.color_r, this.color_g, this.color_b, this.anim_alpha);
						TurokRenderGL.drawSolidRect(this.container.getX() + this.container.getWidth(), this.container.getY(), this.container.getX() + this.container.getWidth() + this.anim_width, this.container.getY() + this.container.getHeight());
					}
				} else {
					TurokRenderGL.color(this.button_background_r, this.button_background_g, this.button_background_b, this.button_background_a);
					TurokRenderGL.drawSolidRect(this.rect.x, this.rect.y, this.rect.x + ((int) ((this.rect.width) * (this.setting.getValDouble() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()))) - 2, this.rect.y + this.rect.height);

					TurokString.renderString(this.rect.getTag() + ":", this.rect.x + 2, this.rect.y + this.height_offset, this.button_name_r, this.button_name_g, this.button_name_b, this.shadow, this.smooth);
				}
			}
		}

		animation();
	}

	public void animation() {
		if (this.animation) {
			if (isMousePassing()) {
				if (this.anim_width != 20) {
					this.anim_width += 2;
				}

				if (this.anim_alpha != this.color_a) {
					this.anim_alpha += 10;
				}

				if (this.anim_alpha >= this.color_a) {
					this.anim_alpha = this.color_a;
				}

				if (this.anim_width >= 20) {
					this.anim_width = 20;
				}
			} else {
				this.anim_width = 0;

				if (this.anim_alpha != 0) {
					this.anim_alpha -= 10;
				}

				if (this.anim_alpha <= 0) {
					this.anim_alpha = 0;
				}
			}

			if (this.container.getMaster().state_menu_level == TurokGUIState.GUI_MBUTTON_MENU_CLOSING) {
				this.render_button = false;
			}

			if (this.container.getMaster().state_menu_level == TurokGUIState.GUI_MBUTTON_MENU_OPEN) {
				this.render_button = true;
			}
		} else {
			if (isMousePassing()) {
				this.anim_width = 20;
				this.anim_alpha = this.color_a;
			} else {
				this.anim_width = 0;
				this.anim_alpha = 0;
			}

			if (this.container.getMaster().state_menu_level == TurokGUIState.GUI_MBUTTON_MENU_CLOSED) {
				this.render_button = false;
			}

			if (this.container.getMaster().state_menu_level == TurokGUIState.GUI_MBUTTON_MENU_OPEN) {
				this.render_button = true;
			}
		}
	}

	public void update(int x, int y) {
		this.rect.x = (this.container.getX() + 2);
		this.rect.y = (this.container.getY() + this.save_y);

		double mouse = Math.min(this.rect.width, Math.max(0, this.container.absolute.getMouseX() - this.rect.x));

		if (this.smooth) {
			this.height_offset = 2;
		} else {
			this.height_offset = 1;
		}

		if (isClicked()) {
			if (mouse != 0) {
				this.setting.setValDouble(round((mouse / this.rect.width) * (this.setting.getMax() - this.setting.getMin() + this.setting.getMin())));
			} else {
				this.setting.setValDouble(this.setting.getMin());
			}
		}

		if (this.rect.collide(x, y) && this.render_button && this.container.getMaster().cam) {
			setMousePassing(true);
		} else {
			setMousePassing(false);
		}

		boolean r       = false;
		boolean g       = false;
		boolean b       = false;
		boolean is_this = false;

		if (!(this.equals("no"))) {
			if (OsirisMod.getInstance().settingsManager.getSettingByID(this.color_slider_name + "Red") != null) {
				r = true;
			}
	
			if (OsirisMod.getInstance().settingsManager.getSettingByID(this.color_slider_name + "Green") != null) {		
				g = true;
			}
	
			if (OsirisMod.getInstance().settingsManager.getSettingByID(this.color_slider_name + "Blue") != null) {
				b = true;
			}
	
			if ((this.color_slider_name + "Red").equals(this.setting.getId()) || (this.color_slider_name + "Green").equals(this.setting.getId()) || (this.color_slider_name + "Blue").equals(this.setting.getId()) || (this.color_slider_name + "Alpha").equals(this.setting.getId())) {
				is_this = true;
			}
	
			if (r && g && b && is_this) {
				this.color_r = OsirisMod.getInstance().settingsManager.getSettingByID(this.color_slider_name + "Red").getValInt();			
				this.color_g = OsirisMod.getInstance().settingsManager.getSettingByID(this.color_slider_name + "Green").getValInt();
				this.color_b = OsirisMod.getInstance().settingsManager.getSettingByID(this.color_slider_name + "Blue").getValInt();
	
				if (OsirisMod.getInstance().settingsManager.getSettingByID(this.color_slider_name + "Alpha") != null) {
					this.color_a = OsirisMod.getInstance().settingsManager.getSettingByID(this.color_slider_name + "Alpha").getValInt();
				} else {
					this.color_a = 255;
				}
	
				this.optional_color = true;
			}
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


	// Util.
	public double round(double var_double) {
		BigDecimal decimal = new BigDecimal(var_double);

		decimal = decimal.setScale(2, RoundingMode.HALF_UP);

		return decimal.doubleValue();
	}
}