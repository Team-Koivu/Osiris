package me.finz0.osiris.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.enemy.Enemies;

public class EnemyCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{
                "enemy", "enemies", "e"
        };
    }

    @Override
    public String getSyntax() {
        return ChatFormatting.RED + "Usage: " + ChatFormatting.WHITE + Command.prefix + "enemy <add or del> <name>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(args[0].equalsIgnoreCase("add")) {
            if (!Enemies.getEnemies().contains(Enemies.getEnemyByName(args[1]))){
                Enemies.addEnemy(args[1]);
                sendClientMessage(ChatFormatting.GREEN + "Added" + ChatFormatting.WHITE + " enemy with name " + ChatFormatting.RED + args[1]);
            } else {
                sendClientMessage(ChatFormatting.RED + args[1] + ChatFormatting.WHITE + " is already an enemy!");
            }
        } else if(args[0].equalsIgnoreCase("del") || (args[0].equalsIgnoreCase("remove"))){
            Enemies.delEnemy(args[1]);
            sendClientMessage(ChatFormatting.RED + "Removed" + ChatFormatting.WHITE + " enemy with name " + ChatFormatting.RED + args[1]);
        } else {
            sendClientMessage(getSyntax());
        }
    }
}
