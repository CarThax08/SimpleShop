package com.github.carthax08.simpleshop.util;

import com.github.carthax08.simplecurrencies.api.Currencies;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PluginUtil {

    public static Map<ItemStack, String> onClick = new HashMap<>();
    public static Map<String, Inventory> guis = new HashMap<>();

    public static void openShopGui(Player player) {
        player.openInventory(guis.get("Shop"));
    }

    public static HashMap<String, Inventory> readGuisFromConfig(FileConfiguration config) {
        HashMap<String, Inventory> map = new HashMap<>();

        for (String string : config.getConfigurationSection("gui").getKeys(false)) {
            Inventory gui = Bukkit.createInventory(null, config.getInt("gui." + string + ".slots"), config.getString("gui." + string + ".name"));
            ConfigurationSection config2 = config.getConfigurationSection("gui." + string + ".items");
            for (int i = 0; i < gui.getType().getDefaultSize(); i++) {
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("");
                item.setItemMeta(meta);
                gui.setItem(i, item);
            }
            for (String item : config2.getKeys(false)) {
                ItemStack itemStack = new ItemStack(Material.getMaterial(config2.getString(item + ".itemtype").toUpperCase()), 1);
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(config2.getString(item + ".itemname"));
                itemStack.setItemMeta(meta);
                onClick.put(itemStack, config2.getString(item + ".onclick"));
                gui.setItem(Integer.parseInt(item), itemStack);
            }
            map.put(config.getString("gui." + string + ".name"), gui);
        }
        guis = map;
        return map;
    }

    public static void openGui(String onClick, Player player) {
        player.openInventory(guis.get(onClick));
    }

    public static Boolean chargePlayer(Player player, Double amount, String currency) {
        return Currencies.removeCurrency(currency, player, amount);
    }
}
