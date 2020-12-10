package me.finz0.osiris.module.modules.gui;

// Minecraft.
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.Gui;

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
public class HUDPlayer extends OsirisPlusHUD {
	public HUDPlayer() {
		super("Player", "Draw player.");

		releaseHUDAsModule();
	}

	@Override
	public void onRenderHUD() {
		if (mc.player != null) {

			float mouse_x = mc.getRenderViewEntity().rotationYaw * -1;
			float mouse_y = mc.getRenderViewEntity().rotationPitch * -1;

			GlStateManager.color(1, 1, 1, 1);
		
			drawEntity(this.x + this.w, this.y + this.h, 30, mouse_x, mouse_y, mc.player);

			this.w = 21;
			this.h = 60;
		}
	}

	// Osiris.
	public void drawEntity(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)posX, (float)posY, 50.0F);
		GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		float f3 = ent.prevRotationYawHead;
		float f4 = ent.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		//ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
		//ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
		//ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
		//ent.rotationYawHead = ent.rotationYaw;
		//ent.prevRotationYawHead = ent.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = mc.getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		//ent.renderYawOffset = f;
		//ent.rotationYaw = f1;
		//ent.rotationPitch = f2;
		//ent.prevRotationYawHead = f3;
		//ent.rotationYawHead = f4;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}