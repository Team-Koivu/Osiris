package me.finz0.osiris.module.modules.gui;

// Minecraft.
import net.minecraft.util.math.MathHelper;

// Java.
import java.util.function.Function;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Created by Rina in 13/08/2020.
 *
 **/
public class HUDPvpInfo extends OsirisPlusHUD {
	Setting rgb_effect;

	int r;
	int g;
	int b;

	public HUDPvpInfo() {
		super("PvpInfo", "Draw info pvp.");

		rgb_effect = addSetting(new Setting("RGB", (Module) this, false, "HUDPvpInfoHUDRGB"));

		releaseHUDAsModule();
	}

	@Override
	public void onRenderHUD() {
		String text = new SimpleDateFormat("k:mm ").format(new Date());

		if (rgb_effect.getValBoolean()) {
			r = r_rgb;
			g = g_rgb;
			b = b_rgb;
		} else {
			r = r_hud;
			g = g_hud;
			b = b_hud;
		}

		if (ModuleManager.isModuleEnabled("AutoCrystal")) {
			renderString("CA ON", 0, 0, r, g, b);
		} else {
			renderString("CA OFF", 0, 0, r, g, b);
		}

		if (ModuleManager.isModuleEnabled("KillAura")) {
			renderString("KA ON", 0, 0 + getStringHeight(""), r, g, b);
		} else {
			renderString("KA OFF", 0, 0 + getStringHeight(""), r, g, b);
		}

		if (ModuleManager.isModuleEnabled("AutoFeetPlace")) {
			renderString("SU ON", 0, 0 + getStringHeight("") + getStringHeight(""), r, g, b);
		} else {
			renderString("SU OFF", 0, 0 + getStringHeight("") + getStringHeight(""), r, g, b);
		}

		if (ModuleManager.isModuleEnabled("AutoTrap")) {
			renderString("AT ON", 0, 0 + getStringHeight("") + getStringHeight("") + getStringHeight(""), r, g, b);
		} else {
			renderString("AT OFF", 0, 0 + getStringHeight("") + getStringHeight("") + getStringHeight(""), r, g, b);
		}

		if (ModuleManager.isModuleEnabled("HoleFill")) {
			renderString("HF ON", 0, 0 + getStringHeight("") + getStringHeight("") + getStringHeight("") + getStringHeight(""), r, g, b);
		} else {
			renderString("HF OFF", 0, 0 + getStringHeight("") + getStringHeight("") + getStringHeight("") + getStringHeight(""), r, g, b);
		}

		this.w = getStringWidth("HA OFF");
		this.h = getStringHeight("") + getStringHeight("") + getStringHeight("") + getStringHeight("") + getStringHeight("");
	}
}