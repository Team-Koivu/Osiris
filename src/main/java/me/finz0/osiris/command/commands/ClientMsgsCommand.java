package me.finz0.osiris.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.command.Command;

public class ClientMsgsCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"messages", "clientmessages"};
    }

    @Override
    public String getSyntax() {
        return ChatFormatting.RED + "Usage: " + ChatFormatting.WHITE + Command.prefix + "messages (color or watermark) <color (true or false)>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(args[0].equalsIgnoreCase("color")){
            if(ChatFormatting.getByName(args[1]) != null) {
                Command.cf = ChatFormatting.getByName(args[1]);
                Command.sendClientMessage(ChatFormatting.WHITE + "Message color " + ChatFormatting.GREEN + "set to" + ChatFormatting.RED + args[1]);
            } else Command.sendClientMessage(ChatFormatting.RED + getSyntax());
        } else if(args[0].equalsIgnoreCase("watermark")){
            Command.MsgWaterMark = Boolean.parseBoolean(args[1]);
            Command.sendClientMessage(ChatFormatting.GREEN + "Message watermark " + ChatFormatting.WHITE + "= " + ChatFormatting.RED + args[1]);
        } else {
            Command.sendClientMessage(getSyntax());
        }
    }
}
