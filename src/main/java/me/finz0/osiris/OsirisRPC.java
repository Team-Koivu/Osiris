package me.finz0.osiris;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class OsirisRPC {
    private static final String ClientId = "728460198284361750";
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    public static DiscordRichPresence presence = new DiscordRichPresence();
    private static String details;
    private static String state;

    public static void init() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        rpc.Discord_Initialize(ClientId, handlers, true, "");
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.details = OsirisMod.FORGENAME + OsirisMod.MODVER;
        presence.state = "discord.gg/8pYjUGk";
        presence.largeImageKey = "osirisplus";
        presence.largeImageText = OsirisMod.FORGENAME + OsirisMod.MODVER;
        presence.smallImageKey = "default";
        presence.smallImageText = mc.getSession().getUsername();

        rpc.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    rpc.Discord_RunCallbacks();
                    details = OsirisMod.FORGENAME + OsirisMod.MODVER;
                    state = "discord.gg/8pYjUGk";
                    if (mc.isIntegratedServerRunning()) {
                        details = "Singleplayer";
                    }
                    else if (mc.getCurrentServerData() != null) {
                        if (!mc.getCurrentServerData().serverIP.equals("")) {
                            details = mc.getCurrentServerData().serverIP;
                        }

                    } else {
                        details = "Main Menu";
                    }
                    if (!details.equals(presence.details) || !state.equals(presence.state)) {
                        presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    presence.details = details;
                    presence.state = state;
                    rpc.Discord_UpdatePresence(presence);
                } catch(Exception e2){
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                } catch(InterruptedException e3){
                    e3.printStackTrace();
                }
            }
            return;
        }, "Discord-RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        rpc.Discord_Shutdown();
    }
}
