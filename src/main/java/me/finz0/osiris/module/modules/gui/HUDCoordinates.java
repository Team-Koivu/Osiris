package me.finz0.osiris.module.modules.gui;

// Miencraft.
import net.minecraft.util.EnumFacing;

// Java.
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;

// Finz0.
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.module.Module;

// Rina.
import rina.hud.OsirisPlusHUD;
import rina.util.TurokString;

/**
 * @author Rina!
 *
 * Created by Rina in 09/08/2020.
 *
 **/
public class HUDCoordinates extends OsirisPlusHUD {
	Setting rgb_effect;
	Setting direction;

	public HUDCoordinates() {
		super("Coordinates", "Draw coordinates.");

		rgb_effect = addSetting(new Setting("RGB", (Module) this, false, "CoordinatesHUDRGB"));
		direction  = addSetting(new Setting("Direction", (Module) this, "XZ", createList("XZ", "NSWE", "off"), "CoordinatesHUDDirection"));

		releaseHUDAsModule();
	}

	@Override
	public void onRenderHUD() {
		String x = String.format("%.1f", (double) (mc.player.posX));
		String y = String.format("%.1f", (double) (mc.player.posY));
		String z = String.format("%.1f", (double) (mc.player.posZ));

		String x_nether = String.format("%.1f", (Double) (mc.player.posX * 0.125f));
		String z_nether = String.format("%.1f", (Double) (mc.player.posZ * 0.125f));

		String dirc = "";

		if (direction.getValString().equals("XZ")) {
			dirc = getFaceDirect(true, false);
		} else if (direction.getValString().equals("NSWE")) {
			dirc = getFaceDirect(false, true);
		} else {
			dirc = "";
		}

		String coordinates = dirc + "XYZ " + gray_color + x + ", " + y + ", " + z + reset_color + " [" + gray_color + x_nether + ", " + z_nether + reset_color + "]";

		if (rgb_effect.getValBoolean()) {
			renderString(coordinates, 0, 0, r_rgb, g_rgb, b_rgb);
		} else {
			renderString(coordinates, 0, 0);
		}

		this.w = getStringWidth(coordinates);
		this.h = getStringHeight(coordinates);
	}

	public String getFaceDirect(boolean xz, boolean nswe) {
		EnumFacing enum_facing = mc.getRenderViewEntity().getHorizontalFacing();

		String value = "Invalid";

		String l = reset_color + "[" + gray_color;
		String r = reset_color + "]";

		switch (enum_facing) {
			case NORTH: value = xz ? l + "-Z" + r : nswe ? l + "N" + r : "North " + l + "-Z" + r; break;
			case SOUTH: value = xz ? l + "+Z" + r : nswe ? l + "S" + r : "South " + l + "+Z" + r; break;
			case WEST: value  = xz ? l + "-X" + r : nswe ? l + "W" + r : "West " + l + "-X" + r; break;
			case EAST: value  = xz ? l + "+X" + r : nswe ? l + "E" + r : "East " + l + "+X" + r;
		}

		return value + " ";
	}
}