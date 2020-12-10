package me.finz0.osiris;

public class ShutDownHookerino extends Thread {
    @Override
    public void run(){
        saveConfig();
    }

    public static void saveConfig(){
        OsirisMod.getInstance().configUtils.saveMods();
        OsirisMod.getInstance().configUtils.saveSettingsList();
        OsirisMod.getInstance().configUtils.saveBinds();
        OsirisMod.getInstance().configUtils.saveDrawn();
        OsirisMod.getInstance().configUtils.saveFriends();
        OsirisMod.getInstance().configUtils.saveGui();
        OsirisMod.getInstance().configUtils.saveHudCustom();
        OsirisMod.getInstance().configUtils.savePrefix();
        OsirisMod.getInstance().configUtils.saveRainbow();
        OsirisMod.getInstance().configUtils.saveMacros();
        OsirisMod.getInstance().configUtils.saveMsgs();
        OsirisMod.getInstance().configUtils.saveAutoGG();
        OsirisMod.getInstance().configUtils.saveSpammer();
        OsirisMod.getInstance().configUtils.saveAnnouncer();
        OsirisMod.getInstance().configUtils.saveWaypoints();
        OsirisMod.getInstance().configUtils.saveFont();
        OsirisMod.getInstance().configUtils.saveEnemies();
        OsirisMod.getInstance().configUtils.saveClientname();
    }
}
