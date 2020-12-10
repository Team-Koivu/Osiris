package me.finz0.osiris.module.modules.gui;

// Java.
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;

// Finz0.
import me.finz0.osiris.module.Module.Category;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.module.Module;

// Rina.
import rina.hud.OsirisPlusHUD;
import rina.util.TurokString;

/**
 * @author Rina!
 *
 * Created by Rina in 08/08/2020.
 *
 **/
public class HUDArrayList extends OsirisPlusHUD {
	Setting rgb_effect;
	Setting hud_info_separator;
	Setting dock_separator;
	Setting module_separator;

	List<Module> pretty_modules;

	int offset_x_dock;
	int offset_x_dock_background;

	boolean rendering_background;

	public HUDArrayList() {
		super("ArrayList", "Show modules enabled.");

		rgb_effect         = addSetting(new Setting("RGB", (Module) this, true, "ArrayListHUDRGB"));
		hud_info_separator = addSetting(new Setting("Module[HUDInfo]", this, false, "ArrayListHUDModuleHUDInfo"));
		dock_separator     = addSetting(new Setting("|Dock", (Module) this, true, "ArrayListHUDDockSeparator"));
		module_separator   = addSetting(new Setting("Module|", (Module) this, false, "ArrayListHUDModuleSeparator"));

		pretty_modules = new ArrayList<>();

		// Make this for side setting add for last setting. And use this.
		releaseHUDAsModule();
	}

	@Override
	public void onRenderHUD() {
		Comparator<Module> comparator = (module_1, module_2) -> {
			String module_1_string = module_1.getName() + (module_1.getHudInfo().equals("") == true ? "" : gray_color + " [" + reset_color + module_1.getHudInfo() + gray_color + "]" + reset_color);
			String module_2_string = module_2.getName() + (module_2.getHudInfo().equals("") == true ? "" : gray_color + " [" + reset_color + module_2.getHudInfo() + gray_color + "]" + reset_color);

			float diff = getStringWidth(module_2_string) - getStringWidth(module_1_string);

			if (isDockIn("LeftUp") || isDockIn("RightUp")) {
				return diff != 0 ? (int) diff : module_2_string.compareTo(module_1_string);
			} else {
				return (int) diff;
			}
		};

		if (isDockIn("LeftUp") || isDockIn("RightUp")) {
			pretty_modules = ModuleManager.getModules().stream().filter(module -> module.isEnabled()).sorted(comparator).collect(Collectors.toList());
		} else if (isDockIn("LeftDown") || isDockIn("RightDown")) {
			pretty_modules = ModuleManager.getModules().stream().filter(module -> module.isEnabled()).sorted(Comparator.comparing(module -> getStringWidth(module.getName() + (module.getHudInfo().equals("") == true ? "" : gray_color + " [" + reset_color + module.getHudInfo() + gray_color + "]" + reset_color)))).collect(Collectors.toList());
		}

		if (dock_separator.getValBoolean()) {
			if (isDockIn("LeftUp") || isDockIn("LeftDown")) {
				if (rgb_effect.getValBoolean()) {
					drawGUIRect(0, 0, 1, this.h, r_rgb, g_rgb, b_rgb, 255);
				} else {
					drawGUIRect(0, 0, 1, this.h, r_hud, g_hud, b_hud, 255);
				}

				offset_x_dock            = 2;
				offset_x_dock_background = 1;
			} else if (isDockIn("RightUp") || isDockIn("RightDown")) {
				if (rgb_effect.getValBoolean()) {
					drawGUIRect(this.w - 1, 0, 1, this.h, r_rgb, g_rgb, b_rgb, 255);
				} else {
					drawGUIRect(this.w - 1, 0, 1, this.h, r_hud, g_hud, b_hud, 255);
				}

				offset_x_dock            = 3;
				offset_x_dock_background = 2;
			}
		} else {
			if (module_separator.getValBoolean()) {
				if (isDockIn("LeftUp") || isDockIn("LeftDown")) {
					offset_x_dock            = 2;
					offset_x_dock_background = 1;
				} else if (isDockIn("RightUp") || isDockIn("RightDown")) {
					offset_x_dock            = 3;
					offset_x_dock_background = 2;
				}
			} else {
				offset_x_dock            = 0;
				offset_x_dock_background = 0;
			}
		}

		int position_update_y = 0;

		for (Module modules : pretty_modules) {
			if (modules.getCategory() == Category.GUI) {
				continue;
			}

			String module_name = (
				hud_info_separator.getValBoolean() == true ? (modules.getName() + (modules.getHudInfo().equals("") == true ? "" : gray_color + " [" + reset_color + modules.getHudInfo() + gray_color + "]" + reset_color)) :
				modules.getName() + (modules.getHudInfo().equals("") == true ? "" : gray_color + " " + modules.getHudInfo())
			);

			if (dock_separator.getValBoolean()) {
				if (isDockIn("LeftUp") || isDockIn("LeftDown")) {
					drawGUIRect(offset_x_dock_background, position_update_y, getStringWidth(module_name) + offset_x_dock + offset_x_dock_background, getStringHeight(module_name), 0, 0, 0, 100);
				} else if (isDockIn("RightUp") || isDockIn("RightDown")) {
					drawGUIRect(verifyDocking(getStringWidth(module_name), offset_x_dock_background) - 2, position_update_y, getStringWidth(module_name) + offset_x_dock + 1, getStringHeight(module_name), 0, 0, 0, 100);
				}

				rendering_background = true;
			} else {
				rendering_background = false;
			}

			if (module_separator.getValBoolean()) {
				if (isDockIn("LeftUp") || isDockIn("LeftDown")) {
					if (!rendering_background) {
						drawGUIRect(offset_x_dock_background, position_update_y, getStringWidth(module_name) + offset_x_dock + offset_x_dock_background, getStringHeight(module_name), 0, 0, 0, 100);
					}

					if (rgb_effect.getValBoolean()) {
						drawGUIRect(getStringWidth(module_name) + offset_x_dock + 2, position_update_y, 1, getStringHeight(module_name), r_rgb, g_rgb, b_rgb, 255);
					} else {
						drawGUIRect(getStringWidth(module_name) + offset_x_dock + 2, position_update_y, 1, getStringHeight(module_name), r_hud, g_hud, b_hud, 255);
					}
				} else if (isDockIn("RightUp") || isDockIn("RightDown")) {
					if (!rendering_background) {
						drawGUIRect(verifyDocking(getStringWidth(module_name), offset_x_dock_background) - 2, position_update_y, getStringWidth(module_name) + offset_x_dock + 1, getStringHeight(module_name), 0, 0, 0, 100);
					}

					if (rgb_effect.getValBoolean()) {
						drawGUIRect(verifyDocking(getStringWidth(module_name), offset_x_dock_background) - 3, position_update_y, 1, getStringHeight(module_name), r_rgb, g_rgb, b_rgb, 255);
					} else {
						drawGUIRect(verifyDocking(getStringWidth(module_name), offset_x_dock_background) - 3, position_update_y, 1, getStringHeight(module_name), r_hud, g_hud, b_hud, 255);
					}
				}
			}

			if (rgb_effect.getValBoolean()) {
				renderString(module_name, offset_x_dock, position_update_y, r_rgb, g_rgb, b_rgb);
			} else {
				renderString(module_name, offset_x_dock, position_update_y);
			};

			position_update_y += getStringHeight(module_name);

			if (getStringWidth(module_name) >= this.w) {
				this.w = getStringWidth(module_name) + 2;
			}

			this.h = position_update_y;
		}
	}
}