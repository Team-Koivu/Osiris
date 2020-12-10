package rina.hud;

// Minecraft.
import net.minecraft.client.renderer.GlStateManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.ScaledResolution;

// OpenGL.
import org.lwjgl.opengl.GL11;

// Util.
import rina.util.TurokScreenUtil;
import rina.util.TurokGUIState;
import rina.util.TurokRenderGL;
import rina.util.TurokString;
import rina.util.TurokRect;

// Java.
import java.util.*;
import java.awt.*;

// Osiris.
import me.finz0.osiris.module.Module.Category;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.OsirisMod;

/**
 * @author Rina!
 *
 * Created by Rina in 08/08/2020.
 *
 **/
public class OsirisPlusHUD extends Module {
	public String name;
	public String description;

	public int x;
	public int y;

	public int move_x;
	public int move_y;

	public int w;
	public int h;

	public int screen_width;
	public int screen_height;

	public int r_rgb;
	public int g_rgb;
	public int b_rgb;

	public int r_hud;
	public int g_hud;
	public int b_hud;

	public Setting setting_smooth;
	public Setting setting_shadow;
	public Setting setting_custom;
	public Setting setting_side;

	public DockValue dock;

	// Formating colors to help.
	public ChatFormatting reset_color        = ChatFormatting.RESET;
	public ChatFormatting black_color        = ChatFormatting.BLACK;
	public ChatFormatting red_color          = ChatFormatting.RED;
	public ChatFormatting aqua_color         = ChatFormatting.AQUA;
	public ChatFormatting blue_color         = ChatFormatting.BLUE;
	public ChatFormatting gold_color         = ChatFormatting.GOLD;
	public ChatFormatting gray_color         = ChatFormatting.GRAY;
	public ChatFormatting white_color        = ChatFormatting.WHITE;
	public ChatFormatting green_color        = ChatFormatting.GREEN;
	public ChatFormatting yellow_color       = ChatFormatting.YELLOW;
	public ChatFormatting dark_red_color     = ChatFormatting.DARK_RED;
	public ChatFormatting dark_aqua_color    = ChatFormatting.DARK_AQUA;
	public ChatFormatting dark_blue_color    = ChatFormatting.DARK_BLUE;
	public ChatFormatting dark_gray_color    = ChatFormatting.DARK_GRAY;
	public ChatFormatting dark_green_color   = ChatFormatting.DARK_GREEN;
	public ChatFormatting dark_purple        = ChatFormatting.DARK_PURPLE;
	public ChatFormatting light_purple_color = ChatFormatting.LIGHT_PURPLE;

	// Events.
	public boolean custom_xy;
	public boolean event_passing;
	public boolean event_dragging;
	public boolean event_is_in_editor;

	public OsirisPlusHUD(String name, String description) {
		super(name, Category.GUI, description);

		this.name        = name;
		this.description = description;

		this.dock = DockValue.HUD_DOCK_LEFT_UP;

		this.screen_width  = 0;
		this.screen_height = 0;

		this.move_x = 0;
		this.move_y = 0;

		this.r_rgb = 0;
		this.g_rgb = 0;
		this.b_rgb = 0;

		this.r_hud = 0;
		this.g_hud = 0;
		this.b_hud = 0;

		this.setting_smooth = addSetting(new Setting("Smooth", this, false, this.name + "HUDSmooth"));
		this.setting_shadow = addSetting(new Setting("Shadow", this, true, this.name + "HUDShadow"));
		this.setting_custom = addSetting(new Setting("Custom", this, false, this.name + "HUDCustom"));

		this.custom_xy      = false;
		this.event_passing  = false;
		this.event_dragging = false;
	}

	public void releaseHUDAsModule() {
		this.setting_side = addSetting(new Setting("Side", this, "LeftUp", createList("LeftUp", "LeftDown", "RightUp", "RightDown"), this.name + "HUDSide"));
	}

	public void onRenderHUD() {}

	@Override
	public void onRender() {
		ScaledResolution scl_minecraft_screen = new ScaledResolution(mc);

		float[] tick_color = {
			(System.currentTimeMillis() % (360 * 32)) / (360f * 32)
		};
	
		int color_rgb = Color.HSBtoRGB(tick_color[0], 1, 1);

		this.screen_width  = scl_minecraft_screen.getScaledWidth();
		this.screen_height = scl_minecraft_screen.getScaledHeight();

		this.r_rgb = ((color_rgb >> 16) & 0xFF);
		this.g_rgb = ((color_rgb >> 8) & 0xFF);
		this.b_rgb = (color_rgb & 0xFF);

		this.r_hud = getSettingByID("HUDStringRed").getValInt();
		this.g_hud = getSettingByID("HUDStringGreen").getValInt();
		this.b_hud = getSettingByID("HUDStringBlue").getValInt();

		if (this.setting_side.getValString().equals("LeftUp")) {
			this.dock = DockValue.HUD_DOCK_LEFT_UP;
		} else if (this.setting_side.getValString().equals("LeftDown")) {
			this.dock = DockValue.HUD_DOCK_LEFT_DOWN;
		} else if (this.setting_side.getValString().equals("RightUp")) {
			this.dock = DockValue.HUD_DOCK_RIGHT_UP;
		} else if (this.setting_side.getValString().equals("RightDown")) {
			this.dock = DockValue.HUD_DOCK_RIGHT_DOWN;
		}

		this.custom_xy = this.setting_custom.getValBoolean();

		this.event_is_in_editor = getModuleByDisplayName("HUDEditor").isEnabled();

		TurokRenderGL.fixScreen(screen_width, screen_height);

		if (getModuleByDisplayName("HUD").isEnabled()) {
			onRenderHUD();

			if (isPassing() && !OsirisMod.getInstance().guiscreen_modules.mode) {
				drawOutlineRect(0, 0, this.w, this.h, 0, 0, 0, 200);
			}
		}

		// Prepare to render.
		TurokRenderGL.init2D();
		TurokRenderGL.release2D();
	}

	protected ArrayList<String> createList(String... list) {
		ArrayList<String> list_created = new ArrayList<>();

		for (String values : list) {
			list_created.add(values);
		}

		return list_created;
	}

	protected Setting addSetting(Setting setting) {
		OsirisMod.getInstance().settingsManager.rSetting(setting);

		return setting;
	}

	protected Setting getSettingByID(String id) {
		return OsirisMod.getInstance().settingsManager.getSettingByID(id);
	}

	protected Module getModuleByDisplayName(String display_name) {
		return ModuleManager.getModuleByName(display_name);
	}

	// We render string default no color.
	protected void renderString(String string, int x, int y) {
		TurokString.renderStringHUD(string, this.x + verifyDocking(getStringWidth(string), x), this.y + y, this.r_hud, this.g_hud, this.b_hud, this.setting_shadow.getValBoolean(), this.setting_smooth.getValBoolean());
	}

	// Render with the color.
	protected void renderString(String string, int x, int y, int r, int g, int b) {
		TurokString.renderStringHUD(string, this.x + verifyDocking(getStringWidth(string), x), this.y + y, r, g, b, this.setting_shadow.getValBoolean(), this.setting_smooth.getValBoolean());
	}

	protected void drawOutlineRect(float x, float y, float width, float height, int r, int g, int b, int a) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		TurokRenderGL.color(r, g, b, a);
		TurokRenderGL.drawOutlineRect(this.x + x, this.y + y, this.x + x + width, this.y + y + height);
	
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GlStateManager.color(1, 1, 1);
	}

	protected void drawSolidRect(float x, float y, float width, float height, int r, int g, int b, int a) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		TurokRenderGL.color(r, g, b, a);
		TurokRenderGL.drawSolidRect(this.x + x, this.y + y, this.x + x + width, this.y + y + height);
	
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GlStateManager.color(1, 1, 1);
	}

	protected void drawGUIRect(int x, int y, int width, int height, int r, int g, int b, int a) {
		TurokScreenUtil.drawGUIRect(this.x + x, this.y + y, this.x + x + width, this.y + y + height, r, g, b, a);
	}

	protected void drawSpecifySolidRect(float x, float y, float width, float height, int r, int g, int b, int a) {
		TurokRenderGL.color(r, g, b, a);
		TurokRenderGL.drawSolidRect(x, y, x + width,  y + height);
	}

	protected int getStringWidth(String string) {
		return TurokString.getStringHUDWidth(string, this.setting_smooth.getValBoolean());
	}

	protected int getStringHeight(String string) {
		if (this.setting_smooth.getValBoolean()) {
			return TurokString.getStringHUDHeight(string, this.setting_smooth.getValBoolean()) + 2;
		}

		return TurokString.getStringHUDHeight(string, this.setting_smooth.getValBoolean());
	}

	protected int verifyDocking(int width, int x) {
		int final_position = x;

		if (dock == DockValue.HUD_DOCK_LEFT_UP) {
			final_position = x;
		}

		if (dock == DockValue.HUD_DOCK_LEFT_DOWN) {
			final_position = x;
		}

		if (dock == DockValue.HUD_DOCK_RIGHT_UP) {
			final_position = this.w - width - x;
		}

		if (dock == DockValue.HUD_DOCK_RIGHT_DOWN) {
			final_position = this.w - width - x;
		}

		return final_position;
	}

	protected boolean verifySpace(OsirisPlusHUD hud) {
		for (OsirisPlusHUD huds : ModuleManager.getHUDList()) {
			if (((Module) huds).isEnabled() && huds.getName().equals(hud.getName()) == false && huds.collide(hud) && isDockIn("LeftUp")) {
				return true;
			}
		}

		return false;
	}

	public boolean isDockIn(String side) {
		if (dock == DockValue.HUD_DOCK_LEFT_UP && side.equalsIgnoreCase("LeftUp")) {
			return true;
		}

		if (dock == DockValue.HUD_DOCK_LEFT_DOWN && side.equalsIgnoreCase("LeftDown")) {
			return true;
		}

		if (dock == DockValue.HUD_DOCK_RIGHT_UP && side.equalsIgnoreCase("RightUp")) {
			return true;
		}

		if (dock == DockValue.HUD_DOCK_RIGHT_DOWN && side.equalsIgnoreCase("RightDown")) {
			return true;
		}

		return false;
	}

	public boolean isCustom() {
		return this.custom_xy;
	}

	public boolean isPassing() {
		return this.event_passing;
	}

	public boolean isDragging() {
		return this.event_dragging;
	}

	public boolean isInEditor() {
		return this.event_is_in_editor;
	}

	public void click(int x, int y, int mouse) {
		if (verifyClick(x, y) && mouse == 0) {
			this.event_dragging = true;

			this.move_x = x - this.x;
			this.move_y = y - this.y;
		}
	}

	public void release(int x, int y, int mouse) {
		if (mouse == 0) {
			if (isDragging()) {
				this.event_dragging = false;
			}
		}
	}

	public void update(int x, int y) {
		if (verifyClick(x, y) && isCustom()) {
			this.event_passing = true;
		} else {
			this.event_passing = false;
		}

		if (isDragging() && isCustom()) {
			this.x = x - this.move_x;
			this.y = y - this.move_y;
		}

		if (isCustom()) {
			if (this.x + (this.w / 2) <= (this.screen_width / 2) && this.y + (this.h / 2) <= (this.screen_height / 2)) {
				this.setting_side.setValString("LeftUp");
			} else if (this.x + (this.w / 2) <= (this.screen_width / 2) && this.y + (this.h / 2) >= (this.screen_height / 2)) {
				this.setting_side.setValString("LeftDown");
			} else if (this.x + (this.w / 2) >= (this.screen_width / 2) && this.y + (this.h / 2) <= (this.screen_height / 2)) {
				this.setting_side.setValString("RightUp");
			} else if (this.x + (this.w / 2) >= (this.screen_width / 2) && this.y + (this.h / 2) >= (this.screen_height / 2)) {
				this.setting_side.setValString("RightDown");
			}

			if (this.x <= 0) {
				this.x = 1;
			}

			if (this.x + this.w >= this.screen_width) {
				this.x = (this.screen_width - this.w - 1);
			}

			if (this.y <= 0) {
				this.y = 1;
			}

			if (this.y + this.h >= this.screen_height) {
				this.y = (this.screen_height - this.h - 1);
			}
		}
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.w = width;
	}

	public void setHeight(int height) {
		this.h = height;
	}

	public String getName() {
		return this.name;
	}

	public boolean verifyClick(int x, int y) {
		if (x >= this.x && y >= this.y && x <= this.x + this.w && y <= this.y + this.h) {
			return true;
		}

		return false;
	}

	public boolean collide(OsirisPlusHUD hud) {
		if (hud.x + hud.w >= this.x && hud.y + hud.h >= this.y && hud.x + hud.h <= this.x + this.w && hud.y + hud.h <= this.y + this.h) {
			return true;
		}

		return false;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.w;
	}

	public int getHeight() {
		return this.h;
	}

	public enum DockValue {
		HUD_DOCK_LEFT_UP,
		HUD_DOCK_LEFT_DOWN,

		HUD_DOCK_RIGHT_UP,
		HUD_DOCK_RIGHT_DOWN;
	}
}