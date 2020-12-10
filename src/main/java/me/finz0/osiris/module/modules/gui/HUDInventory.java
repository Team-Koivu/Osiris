package me.finz0.osiris.module.modules.gui;

// Minecraft.
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

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
public class HUDInventory extends OsirisPlusHUD {
	Setting background_alpha;

	public HUDInventory() {
		super("Inventory", "Draw inventory.");

		background_alpha = addSetting(new Setting("BackgroundAlpha", (Module) this, 100d, 0d, 255d, true, "HUDInventoryBackgroundAlpha"));

		releaseHUDAsModule();
	}

	@Override
	public void onRenderHUD() {
		if (mc.player != null) {
			drawGUIRect(0, 0, this.w, this.h, 0, 0, 0, background_alpha.getValInt());

			this.w = 16 * 9;
			this.h = 16 * 3;

			GlStateManager.pushMatrix();
			RenderHelper.enableGUIStandardItemLighting();

			for (int i = 0; i < 27; i++) {
				ItemStack item_stack = mc.player.inventory.mainInventory.get(i + 9);

				int item_position_x = (int) this.x - 2 + (i % 9) * 16;
				int item_position_y = (int) this.y + (i / 9) * 16;

				mc.getRenderItem().renderItemAndEffectIntoGUI(item_stack, item_position_x, item_position_y);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, item_stack, item_position_x, item_position_y, null);
			}

			mc.getRenderItem().zLevel = - 5.0f;

			RenderHelper.disableStandardItemLighting();			
		
			GlStateManager.popMatrix();
		}
	}
}