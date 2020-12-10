package me.finz0.osiris.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.modules.chat.AutoGG;

public class AutoGgCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"autogg", "autoez"};
    }

    @Override
    public String getSyntax() {
        return ChatFormatting.RED + "Usage: " + ChatFormatting.WHITE + Command.prefix + "autogg (add or del) <message>, (Use \"{name}\" for the player's name and use \"_\" for spaces)";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        String s = args[1].replace("_", " ");
        if(args[0].equalsIgnoreCase("add")) {
            if (!AutoGG.getAutoGgMessages().contains(s)) {
                AutoGG.addAutoGgMessage(s);
                Command.sendClientMessage(ChatFormatting.GREEN + "Added " + ChatFormatting.WHITE + "AutoGG Message: " + ChatFormatting.RED + s);
            } else {
                Command.sendClientMessage(ChatFormatting.AQUA + "AutoGG list " + ChatFormatting.WHITE + "does " + ChatFormatting.RED + "NOT" + ChatFormatting.WHITE + " contain " + ChatFormatting.RED + s);
            }
        } else if (args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove")){
            AutoGG.getAutoGgMessages().remove(s);
            Command.sendClientMessage(ChatFormatting.RED + "Removed " + ChatFormatting.WHITE + "AutoGG Message: " + ChatFormatting.RED + s);
        }
    }
}
