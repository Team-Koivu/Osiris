package me.finz0.osiris.module.modules.combat;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.ArrayList;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.command.Command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

//NotGhostTypes
public class CombatInfo extends Module {
    public CombatInfo() {
        super("CombatInfo", Category.COMBAT, "Customizable alerts for combat stuff");
    }

    private boolean hasAlertedWeak = false;
    private Set<EntityPlayer> strPlayers = Collections.newSetFromMap(new WeakHashMap());
    private Set<EntityPlayer> spdPlayers = Collections.newSetFromMap(new WeakHashMap());
    private Set<EntityPlayer> invisPlayers = Collections.newSetFromMap(new WeakHashMap());
    private Set<EntityPlayer> weakPlayers = Collections.newSetFromMap(new WeakHashMap());
    public static final Minecraft mc = Minecraft.getMinecraft();
    Setting alertForStr;
    Setting alertForSpd;
    Setting alertForInvis;
    Setting alertForWeak;

    public void setup() {
        OsirisMod.getInstance().settingsManager.rSetting(alertForStr = new Setting("Strength", this, true, "CombatInfoStrengthAlerts"));
        OsirisMod.getInstance().settingsManager.rSetting(alertForSpd = new Setting("Speed", this, false, "CombatInfoSpeedAlerts"));
        OsirisMod.getInstance().settingsManager.rSetting(alertForInvis = new Setting("Invis", this, false, "CombatInfoInvisAlerts"));
        //OsirisMod.getInstance().settingsManager.rSetting(alertForWeak = new Setting("Weakness", this, true, "CombatInfoWeaknessAlerts"));
        ArrayList<String> weaknessModes = new ArrayList<>();
        weaknessModes.add("Self");
        weaknessModes.add("All");
        alertForWeak = new Setting("Weakness", this, "Self", weaknessModes, "CombatInfoWeaknessAlerts");
    }

    public void onUpdate() {
        //Invis Check
        if (alertForInvis.getValBoolean()) {
            for (EntityPlayer player : CombatInfo.mc.world.playerEntities) {
                if (player.equals(CombatInfo.mc.player)) continue;
                if (player.isPotionActive(MobEffects.INVISIBILITY) && !this.invisPlayers.contains(player)) {
                    Command.sendClientMessage(ChatFormatting.GREEN.toString() + player.getDisplayNameString() + ChatFormatting.AQUA.toString() + " has Invisibility");
                    this.invisPlayers.add(player);
                }
                if (!this.invisPlayers.contains(player) || player.isPotionActive(MobEffects.INVISIBILITY)) continue;
                Command.sendClientMessage(ChatFormatting.GREEN.toString() + player.getDisplayNameString() + ChatFormatting.AQUA.toString() + " has ran out of Invisibility");
                this.invisPlayers.remove(player);
            }
        }
        //Speed Check
        if (alertForSpd.getValBoolean()) {
            for (EntityPlayer player : CombatInfo.mc.world.playerEntities) {
                if (player.equals(CombatInfo.mc.player)) continue;
                if (player.isPotionActive(MobEffects.SPEED) && !this.spdPlayers.contains(player)) {
                    Command.sendClientMessage(ChatFormatting.GREEN.toString() + player.getDisplayNameString() + ChatFormatting.AQUA.toString() + " has Speed");
                    this.spdPlayers.add(player);
                }
                if (!this.spdPlayers.contains(player) || player.isPotionActive(MobEffects.SPEED)) continue;
                Command.sendClientMessage(ChatFormatting.GREEN.toString() + player.getDisplayNameString() + ChatFormatting.AQUA.toString() + " has ran out of Speed");
                this.spdPlayers.remove(player);
            }
        }
        //Str Check
        if (alertForStr.getValBoolean()) {
            for (EntityPlayer player : CombatInfo.mc.world.playerEntities) {
                if (player.equals(CombatInfo.mc.player)) continue;
                if (player.isPotionActive(MobEffects.STRENGTH) && !this.strPlayers.contains(player)) {
                    Command.sendClientMessage(ChatFormatting.GREEN.toString() + player.getDisplayNameString() + ChatFormatting.AQUA.toString() + " has Strength");
                    this.strPlayers.add(player);
                }
                if (!this.strPlayers.contains(player) || player.isPotionActive(MobEffects.STRENGTH)) continue;
                Command.sendClientMessage(ChatFormatting.GREEN.toString() + player.getDisplayNameString() + ChatFormatting.AQUA.toString() + " has ran out of Strength");
                this.strPlayers.remove(player);
            }
        }
        //Weak Check
        if (alertForWeak.getValBoolean()) {
            if (alertForWeak.getValString().equalsIgnoreCase("Self") || alertForWeak.getValString().equalsIgnoreCase("All")) {
                if (mc.player.isPotionActive(MobEffects.WEAKNESS) && !hasAlertedWeak) {
                    PotionEffect weakness = mc.player.getActivePotionEffect(MobEffects.WEAKNESS);
                    int lvl = weakness.getAmplifier() + 1;
                    Command.sendClientMessage(ChatFormatting.GOLD.toString() + "You have Weakness " + lvl);
                    hasAlertedWeak = true;
                }
                if (!mc.player.isPotionActive(MobEffects.WEAKNESS) && hasAlertedWeak) {
                    hasAlertedWeak = false;
                    Command.sendClientMessage(ChatFormatting.GOLD.toString() + "You no longer have Weakness");
                }
            }
            if (alertForWeak.getValString().equalsIgnoreCase("All")) {
                for (EntityPlayer player : CombatInfo.mc.world.playerEntities) {
                    if (player.equals(CombatInfo.mc.player)) continue;
                    if (player.isPotionActive(MobEffects.WEAKNESS) && !this.weakPlayers.contains(player)) {
                        Command.sendClientMessage(ChatFormatting.GREEN.toString() + player.getDisplayNameString() + ChatFormatting.AQUA.toString() + " has Weakness");
                        this.weakPlayers.add(player);
                    }
                    if (!this.weakPlayers.contains(player) || player.isPotionActive(MobEffects.WEAKNESS)) continue;
                    Command.sendClientMessage(ChatFormatting.GREEN.toString() + player.getDisplayNameString() + ChatFormatting.AQUA.toString() + " no longer has Weakness");
                    this.weakPlayers.remove(player);
                }
            }
        }
    }
}
