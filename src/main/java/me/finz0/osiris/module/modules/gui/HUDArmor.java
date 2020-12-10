package me.finz0.osiris.module.modules.gui;

// Minecraft.
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
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
 * Created by Rina in 14/08/2020.
 *
 **/
public class HUDArmor extends OsirisPlusHUD {
	Setting background_alpha;

	public HUDArmor() {
		super("Armor", "Draw armor slot.");

		background_alpha = addSetting(new Setting("BackgroundAlpha", (Module) this, 100d, 0d, 255d, true, "HUDArmorBackgroundAlpha"));

		releaseHUDAsModule();
	}

	@Override
	public void onRenderHUD() {
		if (mc.player != null) {
			drawGUIRect(0, 0, this.w, this.h, 0, 0, 0, background_alpha.getValInt());

			this.w = 64;
			this.h = 19;

			GlStateManager.pushMatrix();
			RenderHelper.enableGUIStandardItemLighting();

			InventoryPlayer inventory = mc.player.inventory;

			ItemStack boots      = inventory.armorItemInSlot(0);
			ItemStack leggings   = inventory.armorItemInSlot(1);
			ItemStack chestplace = inventory.armorItemInSlot(2);
			ItemStack helmet     = inventory.armorItemInSlot(3);

			mc.getRenderItem().zLevel = 200f;

			if (helmet != null) {
				mc.getRenderItem().renderItemAndEffectIntoGUI(helmet, this.x + 48, this.y);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, helmet, this.x + 48, this.y, "");				
			}

			if (chestplace != null) {
				mc.getRenderItem().renderItemAndEffectIntoGUI(chestplace, this.x + 32, this.y);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, chestplace, this.x + 32, this.y, "");
			}

			if (leggings != null) {
				mc.getRenderItem().renderItemAndEffectIntoGUI(leggings, this.x + 16, this.y);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, leggings, this.x + 16, this.y, "");
			}

			if (boots != null) {
				mc.getRenderItem().renderItemAndEffectIntoGUI(boots, this.x, this.y);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, boots, this.x, this.y, "");
			}

			mc.getRenderItem().zLevel = 0.0f;

			RenderHelper.disableStandardItemLighting();		
			
			GlStateManager.popMatrix();
		}
	}
}