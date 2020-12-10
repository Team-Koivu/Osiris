package rina.module.frame;

// Minecraft Utils.
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// Java.
import java.util.*;

// Guiscreen.
import rina.module.OsirisPlusGUI;

// Widget.
import rina.module.widget.OsirisPlusWidgetMButton;

// Util.
import rina.util.TurokGUIState;
import rina.util.TurokRenderGL;
import rina.util.TurokString;
import rina.util.TurokRect;

// Osiris.
import me.finz0.osiris.module.Module.Category;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.OsirisMod;

// HUD.
import me.finz0.osiris.module.modules.gui.OsirisPlusHUDModule;

/**
 * @author Rina!
 *
 * Created by Rina in 27/07/2020.
 *
 **/
public class OsirisPlusFrame {
	private OsirisPlusGUI master;

	private ArrayList<OsirisPlusWidgetMButton> modules_button;

	private TurokRect rect;

	private int move_x;
	private int move_y;

	private int save_y;

	private int name_height;
	private int category_height;

	private int count;

	private int height_offset;

	private boolean open;
	private boolean first_time;

	// States.
	public TurokGUIState state_dragging;
	public TurokGUIState state_maximize;
	public TurokGUIState state_maximize_level;

	// Events.
	public boolean event_dragging;
	public boolean event_is_able_to_drag;
	public boolean event_maximize;
	public boolean event_passing_some_module;
	public boolean event_cancel_passing_mouse;

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

	public OsirisPlusFrame(OsirisPlusGUI master, Category cateogry) {
		this.master = master;

		this.modules_button = new ArrayList<>();

		this.rect = new TurokRect(cateogry.name(), 10, 10);

		this.rect.x = 0;
		this.rect.y = 10;

		this.save_y = this.rect.y;

		this.move_x = 0;
		this.move_y = 0;

		this.rect.width  = 100;
		this.rect.height = 12;

		this.name_height     = 12;
		this.category_height = 13;

		this.event_dragging             = false;
		this.event_is_able_to_drag      = true;
		this.event_maximize             = false;
		this.event_passing_some_module  = false;
		this.event_cancel_passing_mouse = false;

		this.state_dragging       = TurokGUIState.GUI_FRAME_STOPPED;
		this.state_maximize       = TurokGUIState.GUI_FRAME_CLOSED;
		this.state_maximize_level = TurokGUIState.GUI_FRAME_CLOSED;

		int size   = ModuleManager.getModulesInCategory(cateogry).size();
		this.count = 0;

		this.first_time = false;

		for (Module modules : ModuleManager.getModulesInCategory(cateogry)) {
			OsirisPlusWidgetMButton mbuttons = new OsirisPlusWidgetMButton(this, modules, this.category_height);

			this.count++;

			this.category_height += mbuttons.getHeight() + 1;

			this.modules_button.add(mbuttons);
		}

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

	public void resetButtons() {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			mbuttons.setMousePassing(false);
		}
	}

	public void setMoveX(int x) {
		this.move_x = x;
	}

	public void setMoveY(int y) {
		this.move_y = y;
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

	public void setDragging(boolean value) {
		this.event_dragging = value;
	}

	public void setMaximize(boolean value) {
		this.event_maximize = value;
	}

	public void setAbleToDrag(boolean value) {
		this.event_is_able_to_drag = value;
	}

	public void setPassingSomeModule(boolean value) {
		this.event_passing_some_module = value;
	}

	public void setCancelPassingMouse(boolean value) {
		this.event_cancel_passing_mouse = value;
	}

	public void setOpen(boolean value) {
		this.open = value;
	}

	public void setOpenDefault(boolean value) {
		this.open       = value;
		this.first_time = value;
	}

	public OsirisPlusGUI getMaster() {
		return this.master;
	}

	public TurokRect getRect() {
		return this.rect;
	}

	public int getMoveX() {
		return this.move_x;
	}

	public int getMoveY() {
		return this.move_y;
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

	public boolean isDragging() {
		return this.event_dragging;
	}

	public boolean isMaximizing() {
		return this.event_maximize;
	}

	public boolean isAbleToDrag() {
		return this.event_is_able_to_drag;
	}

	public boolean isPassingSomeModule() {
		return this.event_passing_some_module;
	}

	public boolean isCancelPassingMouse() {
		return this.event_cancel_passing_mouse;
	}

	public boolean isOpen() {
		return this.open;
	}

	public boolean verifyEvent(int x, int y) {
		boolean accept = false;

		if (this.rect.collide(x, y)) {
			accept = true;
		}

		if (isDragging()) {
			accept = true;
		}

		if (isMaximizing()) {
			accept = true;
		}

		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			if (mbuttons.isMousePassing()) {
				accept = true;
			}

			if (mbuttons.getContainer().render_container) {
				accept = true;
			}

			if (mbuttons.getContainer().getRect().collide(x, y)) {
				accept = true;
			}
		}

		return accept;
	}

	public boolean collide(int x, int y) {
		if (x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + this.name_height) {
			return true;
		}

		return false;
	}

	public void keyboard(char char_, int key) {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			mbuttons.keyboard(char_, key);
		}
	}

	public void conflict(int x, int y, int mouse) {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			mbuttons.conflict(x, y, mouse);
		}
	}

	public void click(int x, int y, int mouse) {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			mbuttons.click(x, y, mouse);
		}
	}

	public void release(int x, int y, int mouse) {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			mbuttons.release(x, y, mouse);
		}

		if (mouse == 0) {
			if (isAbleToDrag()) {
				setDragging(false);
			}
		}
	}

	public void clickHUD(int x, int y, int mouse) {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			if (mbuttons.getModule().getName().equals("HUD")) {
				((OsirisPlusHUDModule) mbuttons.getModule()).click(x, y, mouse);
			}
		}
	}

	public void releaseHUD(int x, int y, int mouse) {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			if (mbuttons.getModule().getName().equals("HUD")) {
				((OsirisPlusHUDModule) mbuttons.getModule()).release(x, y, mouse);
			}
		}
	}

	public void updateHUD(int x, int y) {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			if (mbuttons.getModule().getName().equals("HUD")) {
				((OsirisPlusHUDModule) mbuttons.getModule()).update(x, y);
			}
		}
	}

	public void render() {
		animation();

		update(this.master.getMouseX(), this.master.getMouseY());

		updateColors();

		TurokRenderGL.color(this.frame_background_r, this.frame_background_g, this.frame_background_b, this.frame_background_a);
		TurokRenderGL.drawSolidRect(this.rect);

		TurokRenderGL.color(this.category_background_r, this.category_background_g, this.category_background_b, this.category_background_a);
		TurokRenderGL.drawSolidRect(this.rect.x, this.rect.y, this.rect.x + this.rect.width, this.rect.y + this.name_height);

		TurokString.renderString(this.rect.getTag(), this.rect.x + ((this.rect.width / 2) - (TurokString.getStringWidth(this.rect.getTag(), this.smooth) / 2) - 1), this.rect.y + this.height_offset, this.category_name_r, this.category_name_g, this.category_name_b, this.shadow, this.smooth);

		TurokRenderGL.color(0, 0, 0, 50);
		TurokRenderGL.drawOutlineRect((float) (this.rect.x) - 0.3f, (float) (this.rect.y) - 0.3f, (float) (this.rect.x + this.rect.width) + 0.5f, (float) (this.rect.y + this.rect.height) + 0.5f);

		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			mbuttons.render();
		}
	}

	public void refresh() {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			mbuttons.refresh();
		}
	}

	public void disableRefresh() {
		for (OsirisPlusWidgetMButton mbuttons : this.modules_button) {
			mbuttons.disableRefresh();
		}
	}

	public void update(int x, int y) {
		if (this.smooth) {
			this.height_offset = 2;
		} else {
			this.height_offset = 1;
		}
	}

	// Frame animation.
	public void animation() {
		if (isDragging()) {
			this.rect.setX(this.master.getMouseX() - getMoveX());
			this.rect.setY(this.master.getMouseY() - getMoveY());

			this.state_dragging = TurokGUIState.GUI_FRAME_MOVING;
		} else {
			this.state_dragging = TurokGUIState.GUI_FRAME_STOPPED;
		}

		if ((isMaximizing() && this.animation == true) || this.first_time) {
			if (this.state_maximize == TurokGUIState.GUI_FRAME_CLOSED) {
				this.rect.height += 10;

				this.state_maximize_level = TurokGUIState.GUI_FRAME_OPENING;
			} else if (this.state_maximize == TurokGUIState.GUI_FRAME_OPEN) {
				this.rect.height -= 10;

				this.state_maximize_level = TurokGUIState.GUI_FRAME_CLOSING;
			}

			if (this.rect.height >= this.category_height) {
				this.rect.height = this.category_height;

				this.state_maximize       = TurokGUIState.GUI_FRAME_OPEN;
				this.state_maximize_level = TurokGUIState.GUI_FRAME_OPEN;

				setOpen(true);
				setMaximize(false);

				this.first_time = false;
			}

			if (this.rect.height <= this.name_height) {
				this.rect.height = this.name_height;

				this.state_maximize       = TurokGUIState.GUI_FRAME_CLOSED;
				this.state_maximize_level = TurokGUIState.GUI_FRAME_CLOSED;

				setOpen(false);
				setMaximize(false);

				this.first_time = false;
			}
		} else if ((isMaximizing() && this.animation == false) || this.first_time) {
			if (this.state_maximize == TurokGUIState.GUI_FRAME_CLOSED) {
				this.rect.height = this.category_height;

				this.state_maximize       = TurokGUIState.GUI_FRAME_OPEN;
				this.state_maximize_level = TurokGUIState.GUI_FRAME_OPEN;

				setMaximize(false);

				this.first_time = false;
			} else if (this.state_maximize == TurokGUIState.GUI_FRAME_OPEN) {
				this.rect.height = this.name_height;

				this.state_maximize       = TurokGUIState.GUI_FRAME_CLOSED;
				this.state_maximize_level = TurokGUIState.GUI_FRAME_CLOSED;

				setMaximize(false);

				this.first_time = false;
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