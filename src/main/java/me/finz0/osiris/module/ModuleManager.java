package me.finz0.osiris.module;

import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.module.modules.combat.*;
import me.finz0.osiris.module.modules.movement.*;
import me.finz0.osiris.module.modules.misc.*;
import me.finz0.osiris.module.modules.chat.*;
import me.finz0.osiris.module.modules.gui.*;
import me.finz0.osiris.module.modules.player.*;
import me.finz0.osiris.module.modules.render.*;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Collectors;

import rina.hud.OsirisPlusHUD;

public class ModuleManager {
    public static ArrayList<Module> modules;
    public static ArrayList<OsirisPlusHUD> hud_list;

    public ModuleManager(){
        modules  = new ArrayList<>();
        hud_list = new ArrayList<>();

        //Combat
        addMod(new AutoArmor());
        addMod(new AutoCrystal());
        addMod(new AutoCrystalPlus());
        addMod(new AutoFeetPlace());
        addMod(new AutoOffhand());
        addMod(new AutoTotem());
        addMod(new AutoTrap());
        addMod(new AutoWeb());
        addMod(new BedAura());
        addMod(new CombatInfo());
        addMod(new Criticals());
        addMod(new HoleFill());
        addMod(new HoleTP());
        addMod(new KillAura());
        addMod(new MultiTask());
        addMod(new SmartOffhand());
        addMod(new Surround());

        //Player
		addMod(new AutoReplanish());
        addMod(new Blink());
        addMod(new FastUse());
        addMod(new Freecam());
        addMod(new NoInteract());
        addMod(new NoSwing());
        addMod(new PortalGodMode());
        addMod(new SpeedMine());

        //Movement
        addMod(new ElytraFly());
        addMod(new GuiMove());
        addMod(new Jesus());
        addMod(new NoPush());
        addMod(new NoSlow());
        addMod(new Speed());
        addMod(new Sprint());
        addMod(new Velocity());

        //Misc
        addMod(new AutoNomadHut());
        addMod(new AutoRespawn());
        addMod(new BreakTweaks());
        addMod(new ClinetTimer());
        addMod(new DeathWaypoint());
        addMod(new FakePlayer());
        addMod(new LogoutSpots());
        addMod(new MiddleClickFriends());
        addMod(new NoEntityTrace());
        addMod(new Notifications());
        addMod(new RpcModule());
        addMod(new Timer());
        addMod(new TotemPopCounter());
        addMod(new XCarry());

        //Chat
        addMod(new Announcer());
        addMod(new AutoGG());
        addMod(new AutoReply());
        addMod(new BetterChat());
        addMod(new ChatSuffix());
        addMod(new ChatTimeStamps());
        addMod(new ColorChat());
        addMod(new DotGodSpammer());
        addMod(new KettuLinuxDupe());
        addMod(new Spammer());
        addMod(new ToggleMsgs());
        addMod(new UwuChat());
        addMod(new VisualRange());
        addMod(new Welcomer());

        //Render
        addMod(new BlockHighlight());
        addMod(new BoxESP());
        addMod(new Brightness());
        addMod(new CameraClip());
        addMod(new CapesModule());
        addMod(new CityESP());
        addMod(new CsgoESP());
        addMod(new FovModule());
        addMod(new GlowESP());
        addMod(new HitboxESP());
        addMod(new HoleESP());
        addMod(new LowHands());
        addMod(new NoRender());
        addMod(new ShulkerPreview());
        addMod(new StorageESP());
        addMod(new TabGui());
        addMod(new Tracers());

        // Gui.
        addMod(new OsirisPlusGUIModule());
        addMod(new OsirisPlusHUDEditor());
        addMod(new OsirisPlusHUDModule());

        // HUD.
        addHUD(new HUDArmor());
        addHUD(new HUDArrayList());
        addHUD(new HUDBps());
        addHUD(new HUDCoordinates());
        addHUD(new HUDCrystal());
        addHUD(new HUDExperienceBottle());
        addHUD(new HUDFPS());
        addHUD(new HUDGoldenApple());
        addHUD(new HUDGUIWatermark());
        addHUD(new HUDInventory());
        addHUD(new HUDPlayer());
        addHUD(new HUDPvpInfo());
        addHUD(new HUDServerInfo());
        addHUD(new HUDTotem());
        addHUD(new HUDWatermark());
    }

    public static void addMod(Module m){
        modules.add(m);
    }

    public static void addHUD(OsirisPlusHUD hud){
        modules.add((Module) hud);
        hud_list.add(hud);
    }

    public static void onUpdate() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }

    public static void onRender() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onRender);
    }

    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("osiris");

        Minecraft.getMinecraft().profiler.startSection("setup");
//        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();

        GlStateManager.glLineWidth(1f);
        Vec3d renderPos = Surround.getInterpolatedPos(Minecraft.getMinecraft().player, event.getPartialTicks());

        RenderEvent e = new RenderEvent(OsirisTessellator.INSTANCE, renderPos, event.getPartialTicks());
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();

        modules.stream().filter(module -> module.isEnabled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");
        GlStateManager.glLineWidth(1f);

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
//        GlStateManager.popMatrix();
        OsirisTessellator.releaseGL();
        Minecraft.getMinecraft().profiler.endSection();

        Minecraft.getMinecraft().profiler.endSection();
    }


    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<OsirisPlusHUD> getHUDList() {
        return hud_list;
    }

    public static ArrayList<Module> getModulesInCategory(Module.Category c){
        ArrayList<Module> list = (ArrayList<Module>) getModules().stream().filter(m -> m.getCategory().equals(c)).collect(Collectors.toList());
        return list;
    }

    public static void onBind(int key) {
        if (key == 0 || key == Keyboard.KEY_NONE) return;
        modules.forEach(module -> {
            if(module.getBind() == key){
                module.toggle();
            }
        });
    }

    public static OsirisPlusHUD getHUDByName(String name) {
        OsirisPlusHUD hud_requested = null;

        for (OsirisPlusHUD huds : getHUDList()) {
            if (huds.getName().equals(name)) {
                hud_requested = huds;

                break;
            }
        }

        return hud_requested;
    }

    public static Module getModuleByName(String name){
        Module m = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m;
    }

    public static boolean isModuleEnabled(String name){
        Module m = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m.isEnabled();
    }
    public static boolean isModuleEnabled(Module m){
        return m.isEnabled();
    }
}
