package me.finz0.osiris.module.modules.combat;

import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import me.finz0.osiris.module.Module;

public class MultiTask extends Module {
    public MultiTask() {
        super("MultiTask", Category.COMBAT, "Let's you use your mainhand and offhand manually at the same time");
    }
    
    @Override
    public void onUpdate() {
        if (mc.gameSettings.keyBindUseItem.isKeyDown() && mc.player.getActiveHand() == EnumHand.MAIN_HAND) {
            if (mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                final RayTraceResult r = mc.player.rayTrace(6.0, mc.getRenderPartialTicks());
                mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(r.getBlockPos(), r.sideHit, EnumHand.OFF_HAND, 0.0f, 0.0f, 0.0f));
            } else if (mc.player.getHeldItemOffhand().getItem() instanceof ItemFood && mc.gameSettings.keyBindUseItem.isKeyDown() && mc.gameSettings.keyBindAttack.isKeyDown()) {
                mc.player.setActiveHand(EnumHand.OFF_HAND);
                final RayTraceResult r = mc.player.rayTrace(6.0, mc.getRenderPartialTicks());
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
