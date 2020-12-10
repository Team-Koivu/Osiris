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
public class OsirisPlusHUDEditor extends Module {
	public OsirisPlusHUDEditor() {
		super("HUDEditor", Category.GUI, "HUD Config.");
	}

	@Override
	public void onEnable() {
		if (!ModuleManager.getModuleByName("GUI").isEnabled()) {
			ModuleManager.getModuleByName("GUI").toggle();
		}

		if (!ModuleManager.getModuleByName("HUD").isEnabled()) {
			ModuleManager.getModuleByName("HUD").toggle();
		}

		OsirisMod.getInstance().guiscreen_modules.mode = false;
	}

	@Override
	public void onDisable() {
		OsirisMod.getInstance().guiscreen_modules.mode = true;
	}
}