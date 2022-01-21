package com.github.carthax08.simpleshop.commands;

import com.github.carthax08.simpleshop.util.PluginUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
            return true;
        }
        Player player = (Player)sender;
        if(!player.hasPermission("shop.open")){
            player.sendMessage(ChatColor.RED + "You do not have the permissions required to run this command!\nIf you think this is an error please contact a moderator");
            return true;
        }
        PluginUtil.openShopGui(player);
        return true;
    }
}
