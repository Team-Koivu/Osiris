package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

//also kami skid
public class AutoOffhand extends Module {
    public AutoOffhand() {
        super("AutoOffHand", Category.COMBAT, "Keeps an item in your offhand");
    }
    public int totems;
    public int crystals;
    public int gapples;
    boolean moving = false;
    boolean returnI = false;
    public Setting mode;
    Setting soft;
    ArrayList<String> modes;

    public void setup(){
        modes = new ArrayList<>();
        modes.add("Totem");
        modes.add("Crystal");
        modes.add("Gapple");
        OsirisMod.getInstance().settingsManager.rSetting(mode = new Setting("Mode", this, "Totem", modes, "AutoOffhandMode"));
        OsirisMod.getInstance().settingsManager.rSetting(soft = new Setting("Soft", this, false, "AutoOffhandSoft"));
    }

    public void onUpdate() {
        if(mc.currentScreen instanceof GuiContainer) return;

        if (returnI) {
            int t = -1;
            for (int i = 0; i < 45; i++)
                if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                    t = i;
                    break;
                }
            if (t == -1) return;
            mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
            returnI = false;
        }
        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        gapples = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) totems++;
        else if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) crystals += mc.player.getHeldItemOffhand().getCount();
        else if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) gapples += mc.player.getHeldItemOffhand().getCount();
        else {
            if(soft.getValBoolean() && !mc.player.getHeldItemOffhand().isEmpty()) return;
            if(mode.getValString().equalsIgnoreCase("Totem") && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) return;
            if(mode.getValString().equalsIgnoreCase("Crystal") && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) return;
            if(mode.getValString().equalsIgnoreCase("Gapple") && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) return;
            if (moving) {
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                moving = false;
                if (!mc.player.inventory.getItemStack().isEmpty()) returnI = true;
                return;
            }
            if (mc.player.inventory.getItemStack().isEmpty()) {
                if(mode.getValString().equalsIgnoreCase("Totem") && totems == 0) return;
                if(mode.getValString().equalsIgnoreCase("Crystal") && crystals == 0) return;
                if(mode.getValString().equalsIgnoreCase("Gapple") && crystals == 0) return;
                int t = -1;
                if(mode.getValString().equalsIgnoreCase("Totem")) {
                    for (int i = 0; i < 45; i++)
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                            t = i;
                            break;
                        }
                }
                if(mode.getValString().equalsIgnoreCase("Crystal")) {
                    for (int i = 0; i < 45; i++)
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                            t = i;
                            break;
                        }
                }
                if(mode.getValString().equalsIgnoreCase("Gapple")) {
                    for (int i = 0; i < 45; i++)
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.GOLDEN_APPLE) {
                            t = i;
                            break;
                        }
                }
                if (t == -1) return; // Should never happen!
                mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
                moving = true;
            } else if (!soft.getValBoolean()) {
                int t = -1;
                for (int i = 0; i < 45; i++)
                    if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                        t = i;
                        break;
                    }
                if (t == -1) return;
                mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
            }
        }
    }

    public String getHudInfo() {
        String t = "";
        if(mode.getValString().equalsIgnoreCase("Totem")) t = "T " + totems;
        if(mode.getValString().equalsIgnoreCase("Crystal")) t = "C " + crystals;
        if(mode.getValString().equalsIgnoreCase("Gapple")) t = "G " + gapples;
        return t;
    }
}
