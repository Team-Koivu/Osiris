package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.chat.Announcer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class OsirisPlusGUIModule extends Module {
    Setting shadow;
    Setting smooth;
    Setting animation;

    Setting category_name_r;
    Setting category_name_g;
    Setting category_name_b;

    Setting category_background_r;
    Setting category_background_g;
    Setting category_background_b;
    Setting category_background_a;

    Setting frame_background_r;
    Setting frame_background_g;
    Setting frame_background_b;
    Setting frame_background_a;

    Setting button_name_r;
    Setting button_name_g;
    Setting button_name_b;

    Setting button_background_r;
    Setting button_background_g;
    Setting button_background_b;
    Setting button_background_a;

    Setting button_highlight_r;
    Setting button_highlight_g;
    Setting button_highlight_b;
    Setting button_highlight_a;

    Setting button_pressed_r;
    Setting button_pressed_g;
    Setting button_pressed_b;
    Setting button_pressed_a;

    public OsirisPlusGUIModule INSTANCE;
    public OsirisPlusGUIModule() {
        super("GUI", Category.GUI, "Opens the ClickGUI");
        setBind(Keyboard.KEY_P);
        INSTANCE = this;

        OsirisMod.getInstance().settingsManager.rSetting(shadow = new Setting("Shadow", this, false, "GUIShadow"));
        OsirisMod.getInstance().settingsManager.rSetting(smooth = new Setting("SmoothFont", this, true, "GUISmoothFont"));
        
        OsirisMod.getInstance().settingsManager.rSetting(new Setting("info", this, "Category Name", "GUINameColor"));

        OsirisMod.getInstance().settingsManager.rSetting(category_name_r = new Setting("Red", this, 255, 0, 255, true, "GUICategoryNameR"));
        OsirisMod.getInstance().settingsManager.rSetting(category_name_g = new Setting("Green", this, 255, 0, 255, true, "GUICategoryNameG"));
        OsirisMod.getInstance().settingsManager.rSetting(category_name_b = new Setting("Blue", this, 255, 0, 255, true, "GUICategoryNameB"));

        OsirisMod.getInstance().settingsManager.rSetting(new Setting("info", this, "Background", "GUINameBackgroundColor"));
        
        OsirisMod.getInstance().settingsManager.rSetting(category_background_r = new Setting("Red", this, 255, 0, 255, true, "GUICategoryNameBackgroundR"));
        OsirisMod.getInstance().settingsManager.rSetting(category_background_g = new Setting("Green", this, 0, 0, 255, true, "GUICategoryNameBackgroundG"));
        OsirisMod.getInstance().settingsManager.rSetting(category_background_b = new Setting("Blue", this, 0, 0, 255, true, "GUICategoryNameBackgroundB"));
        OsirisMod.getInstance().settingsManager.rSetting(category_background_a = new Setting("Alpha", this, 106, 0, 255, true, "GUICategoryNameBackgroundA"));

        OsirisMod.getInstance().settingsManager.rSetting(new Setting("info", this, "Frame Background", "GUIFrameBackgroundColor"));

        OsirisMod.getInstance().settingsManager.rSetting(frame_background_r = new Setting("Red", this, 7, 0, 255, true, "GUIFrameBackgroundR"));
        OsirisMod.getInstance().settingsManager.rSetting(frame_background_g = new Setting("Green", this, 0, 0, 255, true, "GUIFrameBackgroundG"));
        OsirisMod.getInstance().settingsManager.rSetting(frame_background_b = new Setting("Blue", this, 0, 0, 255, true, "GUIFrameBackgroundB"));
        OsirisMod.getInstance().settingsManager.rSetting(frame_background_a = new Setting("Alpha", this, 255, 0, 255, true, "GUIFrameBackgroundA")); 
        
        OsirisMod.getInstance().settingsManager.rSetting(new Setting("info", this, "Button Name", "GUIButtonNameColor"));

        OsirisMod.getInstance().settingsManager.rSetting(button_name_r = new Setting("Red", this, 255, 0, 255, true, "GUIButtonNameR"));
        OsirisMod.getInstance().settingsManager.rSetting(button_name_g = new Setting("Green", this, 255, 0, 255, true, "GUIButtonNameG"));
        OsirisMod.getInstance().settingsManager.rSetting(button_name_b = new Setting("Blue", this, 255, 0, 255, true, "GUIButtonNameB"));
        
        OsirisMod.getInstance().settingsManager.rSetting(new Setting("info", this, "Background", "GUIButtonBackgroundcolor"));

        OsirisMod.getInstance().settingsManager.rSetting(button_background_r = new Setting("Red", this, 72, 0, 255, true, "GUIButtonBackgroundR"));
        OsirisMod.getInstance().settingsManager.rSetting(button_background_g = new Setting("Green", this, 0, 0, 255, true, "GUIButtonBackgroundG"));
        OsirisMod.getInstance().settingsManager.rSetting(button_background_b = new Setting("Blue", this, 0, 0, 255, true, "GUIButtonBackgroundB"));
        OsirisMod.getInstance().settingsManager.rSetting(button_background_a = new Setting("Alpha", this, 255, 0, 255, true, "GUIButtonBackgroundA"));

        OsirisMod.getInstance().settingsManager.rSetting(new Setting("info", this, "Highlight", "GUIButtonhighlightColor"));

        OsirisMod.getInstance().settingsManager.rSetting(button_highlight_r = new Setting("Red", this, 255, 0, 255, true, "GUIButtonHighlightR"));
        OsirisMod.getInstance().settingsManager.rSetting(button_highlight_g = new Setting("Green", this, 0, 0, 255, true, "GUIButtonHighlightG"));
        OsirisMod.getInstance().settingsManager.rSetting(button_highlight_b = new Setting("Blue", this, 0, 0, 255, true, "GUIButtonHighlightB"));
        OsirisMod.getInstance().settingsManager.rSetting(button_highlight_a = new Setting("Alpha", this, 100, 0, 255, true, "GUIButtonHighlightA"));

        OsirisMod.getInstance().settingsManager.rSetting(new Setting("info", this, "Pressed", "GUIButtonPressedColor"));

        OsirisMod.getInstance().settingsManager.rSetting(button_pressed_r = new Setting("Red", this, 255, 0, 255, true, "GUIButtonPressedR"));
        OsirisMod.getInstance().settingsManager.rSetting(button_pressed_g = new Setting("Green", this, 0, 0, 255, true, "GUIButtonPressedG"));
        OsirisMod.getInstance().settingsManager.rSetting(button_pressed_b = new Setting("Blue", this, 0, 0, 255, true, "GUIButtonPressedB"));
        OsirisMod.getInstance().settingsManager.rSetting(button_pressed_a = new Setting("Alpha", this, 150, 0, 255, true, "GUIButtonPressedA"));
    }

    public void onEnable(){
        mc.displayGuiScreen(OsirisMod.getInstance().guiscreen_modules);
        if(((Announcer)ModuleManager.getModuleByName("Announcer")).clickGui.getValBoolean() && ModuleManager.isModuleEnabled("Announcer") && mc.player != null)
            if(((Announcer)ModuleManager.getModuleByName("Announcer")).clientSide.getValBoolean()){
                Command.sendClientMessage(Announcer.guiMessage);
            } else {
                mc.player.sendChatMessage(Announcer.guiMessage);
            }
        disable();
    }
}
