package me.finz0.osiris.module.modules.gui;

// Minecraft.
import net.minecraft.client.gui.ScaledResolution;

// Java.
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;

// Finz0.
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.OsirisMod;

// Rina.
import rina.hud.OsirisPlusHUD;
import rina.util.TurokString;

/**
 * @author Rina!
 *
 * Created by Rina in 09/08/2020.
 *
 **/
public class OsirisPlusHUDModule extends Module {
	public ArrayList<OsirisPlusHUD> HUD_DOCK_LEFT_UP;
	public ArrayList<OsirisPlusHUD> HUD_DOCK_LEFT_DOWN;
	public ArrayList<OsirisPlusHUD> HUD_DOCK_RIGHT_UP;
	public ArrayList<OsirisPlusHUD> HUD_DOCK_RIGHT_DOWN;

	// New event.
	int off_set_chat = 0;

	public OsirisPlusHUDModule() {
		super("HUD", Category.GUI, "HUD Config.");

		OsirisMod.getInstance().settingsManager.rSetting(new Setting("info", this, "HUD String", "GUINameColor"));
		OsirisMod.getInstance().settingsManager.rSetting(new Setting("Red", this, 255, 0, 255, true, "HUDStringRed"));
		OsirisMod.getInstance().settingsManager.rSetting(new Setting("Green", this, 255, 0, 255, true, "HUDStringGreen"));
		OsirisMod.getInstance().settingsManager.rSetting(new Setting("Blue", this, 255, 0, 255, true, "HUDStringBlue"));

		this.HUD_DOCK_LEFT_UP    = new ArrayList<>();
		this.HUD_DOCK_LEFT_DOWN  = new ArrayList<>();
		this.HUD_DOCK_RIGHT_UP   = new ArrayList<>();
		this.HUD_DOCK_RIGHT_DOWN = new ArrayList<>();
	}

	@Override
	public void onUpdate() {
		ScaledResolution scl_minecraft_screen = new ScaledResolution(mc);

		int scr_width  = scl_minecraft_screen.getScaledWidth();
		int scr_height = scl_minecraft_screen.getScaledHeight();

		if (mc.ingameGUI.getChatGUI().getChatOpen()) {
			off_set_chat = 16;
		} else {
			off_set_chat = 0;
		}

		for (OsirisPlusHUD huds : ModuleManager.getHUDList()) {
			if (((Module) huds).isEnabled() && huds.isDockIn("LeftUp") && getHUDLeftUp(huds.getName()) == null) {
				this.HUD_DOCK_LEFT_UP.add(huds);
			} else if (((Module) huds).isEnabled() && !huds.isDockIn("LeftUp") && getHUDLeftUp(huds.getName()) != null) {
				this.HUD_DOCK_LEFT_UP.remove(huds);
			} else if (!((Module) huds).isEnabled() && (huds.isDockIn("LeftUp") || !huds.isDockIn("LeftUp")) && getHUDLeftUp(huds.getName()) != null) {
				this.HUD_DOCK_LEFT_UP.remove(huds);
			}

			if (((Module) huds).isEnabled() && huds.isDockIn("LeftDown") && getHUDLeftDown(huds.getName()) == null) {
				this.HUD_DOCK_LEFT_DOWN.add(huds);
			} else if (((Module) huds).isEnabled() && !huds.isDockIn("LeftDown") && getHUDLeftDown(huds.getName()) != null) {
				this.HUD_DOCK_LEFT_DOWN.remove(huds);
			} else if (!((Module) huds).isEnabled() && (huds.isDockIn("LeftDown") || !huds.isDockIn("LeftDown")) && getHUDLeftDown(huds.getName()) != null) {
				this.HUD_DOCK_LEFT_DOWN.remove(huds);
			}

			if (((Module) huds).isEnabled() && huds.isDockIn("RightUp") && getHUDRightUp(huds.getName()) == null) {
				this.HUD_DOCK_RIGHT_UP.add(huds);
			} else if (((Module) huds).isEnabled() && !huds.isDockIn("RightUp") && getHUDRightUp(huds.getName()) != null) {
				this.HUD_DOCK_RIGHT_UP.remove(huds);
			} else if (!((Module) huds).isEnabled() && (huds.isDockIn("RightUp") || !huds.isDockIn("RightUp")) && getHUDRightUp(huds.getName()) != null) {
				this.HUD_DOCK_RIGHT_UP.remove(huds);
			}

			if (((Module) huds).isEnabled() && huds.isDockIn("RightDown") && getHUDRightDown(huds.getName()) == null) {
				this.HUD_DOCK_RIGHT_DOWN.add(huds);
			} else if (((Module) huds).isEnabled() && !huds.isDockIn("RightDown") && getHUDRightDown(huds.getName()) != null) {
				this.HUD_DOCK_RIGHT_DOWN.remove(huds);
			} else if (!((Module) huds).isEnabled() && (huds.isDockIn("RightDown") || !huds.isDockIn("RightDown")) && getHUDRightDown(huds.getName()) != null) {
				this.HUD_DOCK_RIGHT_DOWN.remove(huds);
			}
		}

		int y_dock_left_up = 1;

		for (OsirisPlusHUD huds : this.HUD_DOCK_LEFT_UP) {
			if (huds.isCustom()) {
				continue;
			}			

			huds.setX(1);
			huds.setY(y_dock_left_up);

			y_dock_left_up = huds.getY() + huds.getHeight() + 1;
		}

		int y_dock_left_down = scr_height - 1 - off_set_chat;

		for (OsirisPlusHUD huds : this.HUD_DOCK_LEFT_DOWN) {
			if (huds.isCustom()) {
				continue;
			}

			huds.setX(1);
			huds.setY(y_dock_left_down - huds.getHeight());

			y_dock_left_down = huds.getY() - 1;
		}

		int y_dock_right_up = 1;

		for (OsirisPlusHUD huds : this.HUD_DOCK_RIGHT_UP) {
			if (huds.isCustom()) {
				continue;
			}

			huds.setX(scr_width -huds.getWidth() - 1);
			huds.setY(y_dock_right_up);

			y_dock_right_up = huds.getY() + huds.getHeight() + 1;
		}

		int y_dock_right_down = scr_height - 1 - off_set_chat;

		for (OsirisPlusHUD huds : this.HUD_DOCK_RIGHT_DOWN) {
			if (huds.isCustom()) {
				continue;
			}

			huds.setX(scr_width - huds.getWidth() - 1);
			huds.setY(y_dock_right_down - huds.getHeight());

			y_dock_right_down = huds.getY() - 1;
		}
	}

	public OsirisPlusHUD getHUDLeftUp(String name) {
		OsirisPlusHUD hud_requested = null;

		for (OsirisPlusHUD huds : this.HUD_DOCK_LEFT_UP) {
			if (huds.getName().equals(name)) {
				hud_requested = huds;

				break;
			}
		}

		return hud_requested;
	}

	public OsirisPlusHUD getHUDLeftDown(String name) {
		OsirisPlusHUD hud_requested = null;

		for (OsirisPlusHUD huds : this.HUD_DOCK_LEFT_DOWN) {
			if (huds.getName().equals(name)) {
				hud_requested = huds;

				break;
			}
		}

		return hud_requested;
	}

	public OsirisPlusHUD getHUDRightUp(String name) {
		OsirisPlusHUD hud_requested = null;

		for (OsirisPlusHUD huds : this.HUD_DOCK_RIGHT_UP) {
			if (huds.getName().equals(name)) {
				hud_requested = huds;

				break;
			}
		}

		return hud_requested;
	}

	public OsirisPlusHUD getHUDRightDown(String name) {
		OsirisPlusHUD hud_requested = null;

		for (OsirisPlusHUD huds : this.HUD_DOCK_RIGHT_DOWN) {
			if (huds.getName().equals(name)) {
				hud_requested = huds;

				break;
			}
		}

		return hud_requested;
	}

	public void click(int x, int y, int mouse) {
		for (OsirisPlusHUD huds : ModuleManager.getHUDList()) {
			if (!OsirisMod.getInstance().guiscreen_modules.mode) {
				huds.click(x, y, mouse);
			}
		}
	}

	public void release(int x, int y, int mouse) {
		for (OsirisPlusHUD huds : ModuleManager.getHUDList()) {
			if (!OsirisMod.getInstance().guiscreen_modules.mode) {
				huds.release(x, y, mouse);
			}
		}
	}

	public void update(int x, int y) {
		for (OsirisPlusHUD huds : ModuleManager.getHUDList()) {
			if (!OsirisMod.getInstance().guiscreen_modules.mode) {
				huds.update(x, y);
			}
		}
	}
}