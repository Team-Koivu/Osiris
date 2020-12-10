package me.finz0.osiris.module.modules.player;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class FastUse extends Module {
    public FastUse() {
        super("FastUse", Category.PLAYER, "Sets right click / block break delay to 0");
    }

    Setting xp;
    Setting crystals;
    Setting all;
    Setting breakS;
    Setting fastBow;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(xp = new Setting( "EXP", this, true, "FastUseEXP"));
        OsirisMod.getInstance().settingsManager.rSetting(crystals = new Setting("Crystals", this, true, "FastUseCrystals"));
        OsirisMod.getInstance().settingsManager.rSetting(all = new Setting("Everything", this, false, "FastUseEverything"));
        OsirisMod.getInstance().settingsManager.rSetting(breakS = new Setting("FastBreak", this, true, "FastUseFastBreak"));
        OsirisMod.getInstance().settingsManager.rSetting(fastBow = new Setting("FastBow", this, true, "FastUseFastBow"));
    }

    public void onUpdate() {
        if(fastBow.getValBoolean()) {
            if(mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
                if(mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                    mc.player.stopActiveHand();
                }
            }
        }

        if(xp.getValBoolean()) {
            if (mc.player != null && (mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE)) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if(crystals.getValBoolean()) {
            if (mc.player != null && (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if(all.getValBoolean()) {
            mc.rightClickDelayTimer = 0;
        }

        if(breakS.getValBoolean()){
            mc.playerController.blockHitDelay = 0;
        }
    }
}
