package me.finz0.osiris.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.ModuleManager;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"bind", "b"};
    }

    @Override
    public String getSyntax() {
        return ChatFormatting.RED + "Usage: " + ChatFormatting.WHITE + Command.prefix + "bind <module> <key>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        int key = Keyboard.getKeyIndex(args[1].toUpperCase());
        ModuleManager.getModules().forEach(m ->{
            if(args[0].equalsIgnoreCase(m.getName())){
                m.setBind(key);
                Command.sendClientMessage(ChatFormatting.AQUA + args[0] + ChatFormatting.WHITE + " is now bound to " + ChatFormatting.RED + args[1].toUpperCase());
            }
        });
    }
}
