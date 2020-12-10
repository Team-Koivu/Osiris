package me.finz0.osiris.module.modules.gui;

// Java.
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;

// Finz0.
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.TpsUtils;
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
public class HUDServerInfo extends OsirisPlusHUD {
	Setting rgb_effect;

	public HUDServerInfo() {
		super("ServerInfo", "Draw server info.");

		rgb_effect = addSetting(new Setting("RGB", (Module) this, false, "HUDServerInfoHUDRGB"));

		releaseHUDAsModule();
	}

	@Override
	public void onRenderHUD() {
		String text = "TPS " + gray_color + String.format("%.1f", (double) TpsUtils.getTickRate()) + reset_color + " Ping " + gray_color + getPing() + "ms";

		if (rgb_effect.getValBoolean()) {
			renderString(text, 0, 0, r_rgb, g_rgb, b_rgb);
		} else {
			renderString(text, 0, 0);
		}

		this.w = getStringWidth(text);
		this.h = getStringHeight(text);
	}

	public int getPing() {
		int ping = -1;

		if (mc.player == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getName()) == null) {
			return -1;
		} else {
			ping = mc.getConnection().getPlayerInfo(mc.player.getName()).getResponseTime();
		}

		return ping;
	}
}