package com.github.carthax08.simpleshop;

import com.github.carthax08.simpleshop.commands.ShopCommand;
import com.github.carthax08.simpleshop.events.GuiClickEvent;
import com.github.carthax08.simpleshop.util.PluginUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class PluginMain extends JavaPlugin {

    //Currently, un-used. But I may need it in the future
    public HashMap<String, Inventory> guis;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Save Default Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        guis = PluginUtil.readGuisFromConfig(getConfig());

        getCommand("shop").setExecutor(new ShopCommand());
        getServer().getPluginManager().registerEvents(new GuiClickEvent(this), this);

        if(!getServer().getPluginManager().getPlugin("SimpleCurrencies").isEnabled()) {
            getServer().getLogger().severe(ChatColor.RED + "SimpleCurrencies MUST be installed for this plugin to work, Disabling");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
