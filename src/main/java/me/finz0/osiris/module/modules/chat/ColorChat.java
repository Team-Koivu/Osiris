package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.ArrayList;

public class ColorChat extends Module {
    public ColorChat() {
        super("ColorChat", Category.CHAT, "Make your chat messages fancy colors");
    }

    Setting mode;

    public void setup(){
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Green");
        modes.add("Blue");
        OsirisMod.getInstance().settingsManager.rSetting(mode = new Setting("Color", this, "Green", modes, "ColorChatMode"));
    }

    @EventHandler
    private Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketChatMessage){
            if(((CPacketChatMessage) event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix())) return;
            String message = ((CPacketChatMessage) event.getPacket()).getMessage();
            String prefix = "";
            if(mode.getValString().equalsIgnoreCase("Green")) prefix = ">";
            if(mode.getValString().equalsIgnoreCase("Blue")) prefix = "`";
            String s = prefix + message;
            if(s.length() > 255) return;
            ((CPacketChatMessage) event.getPacket()).message = s;
        }
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }

    public String getHudInfo(){
        return mode.getValString().toUpperCase();
    }
}
