package me.finz0.osiris.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;
// niggas could not even fucking put make sneak only -CryroByte
import static me.finz0.osiris.util.BlockInteractionHelper.*;


public class AutoFeetPlace extends Module {
    public AutoFeetPlace() {
        super("AutoFeetPlace", Category.COMBAT, "Places obsidian at your feet");
    }
    private List<Block> whiteList = Arrays.asList(new Block[] {
            Blocks.OBSIDIAN,
            Blocks.ENDER_CHEST,
    });
    Setting sneak;
    Setting rotate;
    Setting triggerable;
    Setting timeoutTicks;
    Setting announceUsage;
    Setting autoCenter;
    Setting blocksPerTick;
    Setting tickDelay;   
    Setting jumpDisable;
    private int totalTicksRunning = 0;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int offsetStep = 0;
    private int delayStep = 0;
    private boolean firstRun;
    private boolean isSneaking = false;
    /* Autocenter */

    private Vec3d playerPos;
    private BlockPos basePos;
    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(timeoutTicks = new Setting("TimeoutTicks", this, 2, 0, 20, true, "SurroundTimeoutTicks"));
        OsirisMod.getInstance().settingsManager.rSetting(tickDelay = new Setting("TickDelay", this, 0, 0, 20, true, "SurroundTickDelay"));
        OsirisMod.getInstance().settingsManager.rSetting(blocksPerTick = new Setting("BlocksPerTick", this, 20, 0, 20, true, "SurroundBlocksPerTick"));
        OsirisMod.getInstance().settingsManager.rSetting(sneak = new Setting("SneakOnly", this, false, "SurroundSneakOnly"));
        OsirisMod.getInstance().settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "SurroundRotate"));
        OsirisMod.getInstance().settingsManager.rSetting(announceUsage = new Setting("AnnounceUsage", this, true, "SurroundAnnounceUsage"));
        OsirisMod.getInstance().settingsManager.rSetting(autoCenter = new Setting("AutoCenter", this, false, "SurroundAutoCenter"));
        OsirisMod.getInstance().settingsManager.rSetting(triggerable = new Setting("Triggerable", this, true, "triggerable"));
        OsirisMod.getInstance().settingsManager.rSetting(jumpDisable = new Setting("JumpDisable", this, true, "SurroundJumpDisable"));

    }

    private void centerPlayer(double x, double y, double z) {
        {

        }
        mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, true));
        mc.player.setPosition(x, y, z);
    }
    double getDst(Vec3d vec) {
        return playerPos.distanceTo(vec);
    }
    /* End of Autocenter */
    @Override
    protected void onEnable() {
        if (mc.player == null) {
            this.disable();
            return;
        }
        /* Autocenter */
        BlockPos centerPos = mc.player.getPosition();
        playerPos = mc.player.getPositionVector();
        double y = centerPos.getY();
        double x = centerPos.getX();
        double z = centerPos.getZ();

        final Vec3d plusPlus = new Vec3d(x + 0.5, y, z + 0.5);
        final Vec3d plusMinus = new Vec3d(x + 0.5, y, z - 0.5);
        final Vec3d minusMinus = new Vec3d(x - 0.5, y, z - 0.5);
        final Vec3d minusPlus = new Vec3d(x - 0.5, y, z + 0.5);

        if (autoCenter.getValBoolean()) {
            if (getDst(plusPlus) < getDst(plusMinus) && getDst(plusPlus) < getDst(minusMinus) && getDst(plusPlus) < getDst(minusPlus)) {
                x = centerPos.getX() + 0.5;
                z = centerPos.getZ() + 0.5;
                centerPlayer(x, y, z);
            } if (getDst(plusMinus) < getDst(plusPlus) && getDst(plusMinus) < getDst(minusMinus) && getDst(plusMinus) < getDst(minusPlus)) {
                x = centerPos.getX() + 0.5;
                z = centerPos.getZ() - 0.5;
                centerPlayer(x, y, z);
            } if (getDst(minusMinus) < getDst(plusPlus) && getDst(minusMinus) < getDst(plusMinus) && getDst(minusMinus) < getDst(minusPlus)) {
                x = centerPos.getX() - 0.5;
                z = centerPos.getZ() - 0.5;
                centerPlayer(x, y, z);
            } if (getDst(minusPlus) < getDst(plusPlus) && getDst(minusPlus) < getDst(plusMinus) && getDst(minusPlus) < getDst(minusMinus)) {
                x = centerPos.getX() - 0.5;
                z = centerPos.getZ() + 0.5;
                centerPlayer(x, y, z);
            }
        }
        /* End of Autocenter*/
        firstRun = true;

        // save initial player hand
        playerHotbarSlot = mc.player.inventory.currentItem;
        lastHotbarSlot = -1;

        if (announceUsage.getValBoolean()) {
            Command.sendClientMessage("[AutoFeetPlace] " + ChatFormatting.GREEN.toString() + "Enabled!");
        }
    }

    @Override
    protected void onDisable() {

        if (mc.player == null) {
            return;
        }

        // load initial player hand
        if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = playerHotbarSlot;
        }

        if (isSneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            isSneaking = false;
        }

        playerHotbarSlot = -1;
        lastHotbarSlot = -1;

        if (announceUsage.getValBoolean()) {
            Command.sendClientMessage("[AutoFeetPlace] " + ChatFormatting.RED.toString() + "Disabled!");
        }
    }

    @Override
    public void onUpdate() {
        if(sneak.getValBoolean() && !mc.gameSettings.keyBindSneak.isKeyDown()) return;
        if (triggerable.getValBoolean() && totalTicksRunning >= timeoutTicks.getValInt()) {
            totalTicksRunning = 0;
            this.disable();
            return;
        }

        if(jumpDisable.getValBoolean() && !mc.player.onGround) {
            this.disable();
            return;
        }

        if (!firstRun) {
            if (delayStep < tickDelay.getValInt()) {
                delayStep++;
                return;
            } else {
                delayStep = 0;
            }
        }

        if (firstRun) {
            firstRun = false;
        }

        int blocksPlaced = 0;

        while (blocksPlaced < blocksPerTick.getValInt()) {

            Vec3d[] offsetPattern = new Vec3d[0];
            int maxSteps = 0;

             {
                offsetPattern = Offsets.SURROUND;
                maxSteps = Offsets.SURROUND.length;
            }

            if (offsetStep >= maxSteps) {
                offsetStep = 0;
                break;
            }

            BlockPos offsetPos = new BlockPos(offsetPattern[offsetStep]);
            BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

            if (placeBlock(targetPos)) {
                blocksPlaced++;
            }
            offsetStep++;
        }

        if (blocksPlaced > 0) {
            if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
                mc.player.inventory.currentItem = playerHotbarSlot;
                lastHotbarSlot = playerHotbarSlot;
            }

            if (isSneaking) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                isSneaking = false;
            }
        }
        totalTicksRunning++;
    }

    private boolean placeBlock(BlockPos pos) {

        // check if block is already placed
        Block block = mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }

        // check if entity blocks placing
        for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return false;
            }
        }

        EnumFacing side = getPlaceableSide(pos);

        // check if we have a block adjacent to blockpos to click at
        if (side == null) {
            return false;
        }

        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();

        // check if neighbor can be right clicked
        if (!canBeClicked(neighbour)) {
            return false;
        }

        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();

        int obiSlot = findObiInHotbar();

        if (obiSlot == -1) {
            this.disable();
        }

        if (lastHotbarSlot != obiSlot) {
            mc.player.inventory.currentItem = obiSlot;
            lastHotbarSlot = obiSlot;
        }

        if (!isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            isSneaking = true;
        }

        if (rotate.getValBoolean()) {
            faceVectorPacketInstant(hitVec);
        }

        mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.rightClickDelayTimer = 4;

        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));

        return true;
    }

    private int findObiInHotbar() {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockObsidian) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private enum Mode {
        SURROUND, FULL
    }

    private static class Offsets {
        private static final Vec3d[] SURROUND = {
                new Vec3d(1, 0, 0),
                new Vec3d(0, 0, 1),
                new Vec3d(-1, 0, 0),
                new Vec3d(0, 0, -1),
                new Vec3d(1, -1, 0),
                new Vec3d(0, -1, 1),
                new Vec3d(-1, -1, 0),
                new Vec3d(0, -1, -1)
        };

        private static final Vec3d[] FULL = {
                new Vec3d(1, 0, 0),
                new Vec3d(0, 0, 1),
                new Vec3d(-1, 0, 0),
                new Vec3d(0, 0, -1),
                new Vec3d(1, -1, 0),
                new Vec3d(0, -1, 1),
                new Vec3d(-1, -1, 0),
                new Vec3d(0, -1, -1),
                new Vec3d(0, -1, 0)
        };
    }
}
