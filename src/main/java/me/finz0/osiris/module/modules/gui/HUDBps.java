package me.finz0.osiris.module.modules.gui;

// Minecraft.
import net.minecraft.util.math.MathHelper;

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
 * Created by Rina in 13/08/2020.
 *
 **/
public class HUDBps extends OsirisPlusHUD {
	Setting rgb_effect;

	public HUDBps() {
		super("BPS", "Draw speed/km.");

		rgb_effect = addSetting(new Setting("RGB", (Module) this, false, "HUDBpsHUDRGB"));

		releaseHUDAsModule();
	}

	@Override
	public void onRenderHUD() {
		double delta_x = mc.player.posX - mc.player.prevPosX;
		double detal_z = mc.player.posZ - mc.player.prevPosZ;

		float tick_rate = (mc.timer.tickLength / 1000.0f);

		String text = "Speed " + gray_color + String.format("%.1f", (double) (MathHelper.sqrt(delta_x * delta_x + detal_z * detal_z) / tick_rate)) + "bp/s";

		if (rgb_effect.getValBoolean()) {
			renderString(text, 0, 0, r_rgb, g_rgb, b_rgb);
		} else {
			renderString(text, 0, 0);
		}

		this.w = getStringWidth(text);
		this.h = getStringHeight(text);
	}
}