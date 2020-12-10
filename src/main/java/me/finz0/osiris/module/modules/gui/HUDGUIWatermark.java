package me.finz0.osiris.module.modules.gui;

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
 * Created by Rina in 11/08/2020.
 *
 **/
public class HUDGUIWatermark extends OsirisPlusHUD {
	Setting rgb_effect;

	// We start the HUD.
	public HUDGUIWatermark() {
		//    Name            Description.
		super("GUIWatermark", "Draw credit to owner code of GUI.");

		// You can create setting using addSetting(new Setting(...));
		rgb_effect = addSetting(new Setting("RGB", (Module) this, false, "GUIWatermarkHUDRGB"));

		// This create the last settings to style seems good!
		releaseHUDAsModule();
	}

	// We use this override to render.
	@Override // No need opengl state to render 2D rect or something.
	public void onRenderHUD() {
		String rinaa = "Enjoy using GUI Osiris+ ";

		// You can request mc (Minecraft), as in module.
		String name = mc.player.getName();

		String watermark = rinaa + name + ", by Rina!";

		// You can use renderString to render text componemts.
		if (rgb_effect.getValBoolean()) { // Setting.
			// You can choose the color.
			renderString(watermark, 0, 0, r_rgb, g_rgb, b_rgb); // The X, Y, you can put, 0, 0, is bassicaly a offset space x, y.
		} else {
			// Or no, for autommatically color HUD.
			renderString(watermark, 0, 0);
		}

		// You need ever specefy the width and height, you can use, this.w, this.h, or getWidth()/getHeight()/setWidth()/setHeight();
		this.w = getStringWidth(watermark); // You can get string width or height using getStringWidth/getStringHeight();
		this.h = getStringHeight(watermark);

		// You can fix docking in render item using, verifyDocking(int width, int x);
		// And request dock using, isDockIn("RighUp") // LeftUp, LeftDown, RightUp and RightDown. (boolean);

		// End //
	}
}