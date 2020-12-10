package rina.module.widget;

// Minecraft Utils.
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// Java.
import java.util.*;

// guiscreen.
import rina.module.OsirisPlusGUI;

// Widget.
import rina.module.widget.OsirisPlusWidgetAbstract;
import rina.module.widget.OsirisPlusWidgetMButton;

// Component.
import rina.module.widget.component.*;

// Frame.
import rina.module.frame.OsirisPlusFrame;

// Util.
import rina.util.TurokGUIState;
import rina.util.TurokRenderGL;
import rina.util.TurokString;
import rina.util.TurokRect;

// Osiris.
import me.finz0.osiris.settings.SettingsManager;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.OsirisMod;

/**
 * @author Rina!
 *
 * Created by Rina in 28/07/2020.
 *
 **/
public class OsirisPlusWidgetContainer {
	private OsirisPlusWidgetMButton master;
	public  OsirisPlusGUI           absolute;
	private OsirisPlusFrame         frame;

	private ArrayList<OsirisPlusWidgetAbstract> settings_widget;

	private Module module;

	private TurokRect rect;

	private int save_y;

	private int container_width;
	private int container_height;

	// Render.
	public boolean render_container;

	// Events.
	public boolean event_opening;
	public boolean event_mouse_passing;

	// Private count.
	private int count;

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

	public OsirisPlusWidgetContainer(OsirisPlusWidgetMButton master, Module module) {
		this.master   = master;
		this.frame    = this.master.getMaster();
		this.absolute = this.master.getMaster().getMaster();

		this.settings_widget = new ArrayList<>();

		this.container_width  = 100;
		this.container_height = 1;

		this.module = module;

		this.rect = new TurokRect(this.module.getName(), (this.frame.getX() + this.frame.getWidth()), this.master.getY());
	
		this.rect.width  = this.container_width;
		this.rect.height = this.container_height;

		this.event_opening       = false;
		this.event_mouse_passing = false;

		this.count = 0;

		for (Setting settings : OsirisMod.getInstance().settingsManager.getSettingsByMod(this.module)) {
			if (settings.isCheck()) {
				OsirisPlusWidgetButton widget = new OsirisPlusWidgetButton(this, settings, this.container_height);

				this.container_height += widget.getHeight() + 1;

				this.settings_widget.add(widget);

				this.count++;
			}

			if (settings.isCombo()) {
				OsirisPlusWidgetCombo widget = new OsirisPlusWidgetCombo(this, settings, this.container_height);

				this.container_height += widget.getHeight() + 1;

				this.settings_widget.add(widget);

				this.count++;
			}

			if (settings.isSlider()) {
				OsirisPlusWidgetSlider widget = new OsirisPlusWidgetSlider(this, settings, this.container_height);

				this.container_height += widget.getHeight() + 1;

				this.settings_widget.add(widget);

				this.count++;
			}

			if (settings.isCustomString()) {
				OsirisPlusWidgetString widget = new OsirisPlusWidgetString(this, settings, this.container_height);

				this.container_height += widget.getHeight() + 1;

				this.settings_widget.add(widget);

				this.count++;
			}
		}

		if (this.count >= OsirisMod.getInstance().settingsManager.getSettingsByMod(this.module).size()) {
			OsirisPlusWidgetBind widget = new OsirisPlusWidgetBind(this, this.module, this.container_height);

			this.container_height += widget.getHeight() + 1;

			this.settings_widget.add(widget);
		}

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

	public void setWidth(int width) {
		this.rect.width = width;
	}

	public void setHeight(int height) {
		this.rect.height = height;
	}

	public void setOpening(boolean value) {
		this.event_opening = value;
	}

	public void setMousePassing(boolean value) {
		this.event_mouse_passing = value;
	}

	public OsirisPlusWidgetMButton getMaster() {
		return this.master;
	}

	public TurokRect getRect() {
		return this.rect;
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

	public boolean isContainerOpen() {
		return this.render_container;
	}

	public boolean isMousePassing() {
		return this.event_mouse_passing;
	}

	public boolean isOpening() {
		return this.event_opening;
	}

	public void keyboard(char char_, int key) {
		for (OsirisPlusWidgetAbstract widgets : this.settings_widget) {
			widgets.keyboard(char_, key);
		}
	}

	public void click(int x, int y, int mouse) {
		for (OsirisPlusWidgetAbstract widgets : this.settings_widget) {
			widgets.click(x, y, mouse);
		}
	}

	public void release(int x, int y, int mouse) {
		for (OsirisPlusWidgetAbstract widgets : this.settings_widget) {
			widgets.release(x, y, mouse);
		}
	}

	public void render() {
		update(this.absolute.getMouseX(), this.absolute.getMouseY());

		updateColors();

		if (this.render_container) {
			TurokRenderGL.color(this.frame_background_r, this.frame_background_g, this.frame_background_b, this.frame_background_a);
			TurokRenderGL.drawSolidRect(this.rect);
		
			for (OsirisPlusWidgetAbstract widgets : this.settings_widget) {
				widgets.render();
			}
		}

		animation();
	}

	public void update(int x, int y) {
		fixScreen(x, y, this.absolute.getScreenWidth(), this.absolute.getScreenHeight());

		if (this.rect.collide(x, y)) {
			setMousePassing(true);
		} else {
			setMousePassing(false);
		}
	}

	public void fixScreen(int x, int y, int w, int h) {
		this.rect.x = (this.frame.getX() + this.frame.getWidth());
		this.rect.y = this.master.getY() - 1;
	}

	public void animation() {
		int speed = 30;

		if (isOpening() && this.animation == true) {
			boolean height_finished = false;
			boolean width_finished  = false;

			if (this.master.state_menu == TurokGUIState.GUI_MBUTTON_MENU_CLOSED) {
				this.render_container = true;

				this.rect.width  += speed;
				this.rect.height += speed;

				this.master.state_menu_level = TurokGUIState.GUI_MBUTTON_MENU_OPENING;
			} else if (this.master.state_menu == TurokGUIState.GUI_MBUTTON_MENU_OPEN) {
				this.rect.width  -= speed;
				this.rect.height -= speed;

				this.master.state_menu_level = TurokGUIState.GUI_MBUTTON_MENU_CLOSING;
			}

			if (this.rect.width >= this.container_width) {
				this.rect.width = this.container_width;

				width_finished = true;
			}

			if (this.rect.height >= this.container_height) {
				this.rect.height = this.container_height;

				height_finished = true;
			}

			if (this.rect.width <= 0 || this.rect.height <= 0) {
				this.rect.width  = 0;
				this.rect.height = 0;

				this.master.state_menu       = TurokGUIState.GUI_MBUTTON_MENU_CLOSED;
				this.master.state_menu_level = TurokGUIState.GUI_MBUTTON_MENU_CLOSED;

				this.render_container = false;

				setOpening(false);
			}

			if (width_finished && height_finished) {
				this.master.state_menu       = TurokGUIState.GUI_MBUTTON_MENU_OPEN;
				this.master.state_menu_level = TurokGUIState.GUI_MBUTTON_MENU_OPEN;

				width_finished  = false;
				height_finished = false;

				setOpening(false);
			}
		} else if (isOpening() && this.animation == false) {
			if (this.master.state_menu == TurokGUIState.GUI_MBUTTON_MENU_CLOSED) {
				this.render_container = true;

				this.rect.width  = this.container_width;
				this.rect.height = this.container_height;

				this.master.state_menu       = TurokGUIState.GUI_MBUTTON_MENU_OPEN;
				this.master.state_menu_level = TurokGUIState.GUI_MBUTTON_MENU_OPEN;

				setOpening(false);
			} else if (this.master.state_menu == TurokGUIState.GUI_MBUTTON_MENU_OPEN) {
				this.rect.width  = 0;
				this.rect.height = 0;

				this.master.state_menu       = TurokGUIState.GUI_MBUTTON_MENU_CLOSED;
				this.master.state_menu_level = TurokGUIState.GUI_MBUTTON_MENU_CLOSED;

				setOpening(false);
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
}