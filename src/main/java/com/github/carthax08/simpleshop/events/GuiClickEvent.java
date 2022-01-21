package com.github.carthax08.simpleshop.events;

import com.github.carthax08.simpleshop.PluginMain;
import com.github.carthax08.simpleshop.util.PluginUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiClickEvent implements Listener {
    PluginMain main;

    public GuiClickEvent(PluginMain pluginMain) {
        main = pluginMain;
    }

    @EventHandler
    public void guiClick(InventoryClickEvent event) {
        if (main.guis.containsKey(event.getInventory().getTitle())) {
            event.setCancelled(true);
            if (PluginUtil.onClick.containsKey(event.getCurrentItem())) {
                String onClick = PluginUtil.onClick.get(event.getCurrentItem());
                String[] parts = onClick.split(":");
                String chargeSuccess = null;
                for (String string : parts) {
                    if (string.startsWith("Open")) {
                        string = onClick.replace("Open Gui ", "");
                        PluginUtil.openGui(string, (Player) event.getWhoClicked());
                    }
                    if (string.startsWith("Charge")) {
                        string = string.replace("Charge ", "");
                        String[] chargeParts = string.split(" ");
                        if (chargeParts.length == 1) {
                            System.out.println(ChatColor.RED + "There is an error in the configuration! Please message an admin.");
                        }
                        chargeSuccess = String.valueOf(PluginUtil.chargePlayer((Player) event.getWhoClicked(), Double.parseDouble(chargeParts[0]), chargeParts[1]));
                    }
                    if (string.startsWith("Give")) {
                        string = string.replace("Give ", "");
                        if (string.startsWith("Item")) {
                            if(chargeSuccess == "True") {
                                string = string.replace("Item ", "");
                                String[] giveParts = string.split(" ");
                                System.out.println(giveParts[0] + ":" + giveParts[1]);
                                ItemStack item = new ItemStack(Material.getMaterial(giveParts[0].toUpperCase()), Integer.parseInt(giveParts[1]));
                                (event.getWhoClicked()).getInventory().addItem(item);
                            }
                        }
                    }
                    if(string.startsWith("Buy")){
                        string = string.replace("Buy ", "").replace("for ", "");

                        String[] buyP = string.split(" ");
                        System.out.println(buyP[0]);
                        if(PluginUtil.chargePlayer((Player) event.getWhoClicked(), Double.parseDouble(buyP[2]), buyP[3])){
                            ItemStack item = new ItemStack(Material.getMaterial(buyP[1].toUpperCase()), Integer.parseInt(buyP[0]));
                            (event.getWhoClicked()).getInventory().addItem(item);
                        }

                    }
                }
            }
        }
    }
}
