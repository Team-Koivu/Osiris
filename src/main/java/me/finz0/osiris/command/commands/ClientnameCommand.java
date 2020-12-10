package me.finz0.osiris.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import org.lwjgl.opengl.Display;

public class ClientnameCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{
                "name", "modname", "clientname", "suffix", "watermark"
        };
    }

    @Override
    public String getSyntax() {
        return ChatFormatting.RED + "Usage: " + ChatFormatting.WHITE + Command.prefix + "name <new name>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(!args[0].replace("__", " ").equalsIgnoreCase("")) {
            OsirisMod.MODNAME = args[0].replace("__", " ");
            Display.setTitle(OsirisMod.MODNAME + " " + OsirisMod.MODVER);
            sendClientMessage(ChatFormatting.GREEN + "set client name " + ChatFormatting.WHITE + "to " + ChatFormatting.RED + args[0].replace("__", " "));
        }else
            sendClientMessage(getSyntax());
    }
}
