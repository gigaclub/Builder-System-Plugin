package net.gigaclub.buildersystemplugin.Andere.InterfaceAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Navigator implements Listener {


    private String GUI_NAME = "Test Gui";

    public void openGui(Player player) {
        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString()+GUI_NAME));
        for (int j = 0; j < size; j++) {
            inventory.setItem(j, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setEnchantEffeckt(true).setDisplayName(" ").build());
        }
        ItemStack apple = new ItemBuilder(Material.APPLE).setDisplayName((ChatColor.BLUE.toString()+"TEST")).setEnchantEffeckt(true).setLore((ChatColor.LIGHT_PURPLE.toString()+"Das ISt ein Test")).addMetaData(1).build();
        inventory.setItem(11, apple);
        inventory.setItem(15, new ItemStack(Material.GOLDEN_APPLE));
        player.openInventory(inventory);
    }


    @EventHandler
    public void handleNavigatorOpener(PlayerInteractEvent event){

    }




}

