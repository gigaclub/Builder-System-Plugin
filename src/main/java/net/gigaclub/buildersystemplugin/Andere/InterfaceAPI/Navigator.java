package net.gigaclub.buildersystemplugin.Andere.InterfaceAPI;

import net.gigaclub.buildersystemplugin.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Navigator implements Listener {


    private String GUI_NAME = "Test Gui";

    public void openGui(Player player) {
        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString()+GUI_NAME));
        for (int j = 0; j < size; j++) {
            inventory.setItem(j, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setGlow(true).setDisplayName(" ").build());
        }
        List<String> lore = new ArrayList<>();
        lore.add("Das ISt ein Test");



        ItemStack apple = new ItemBuilder(Material.APPLE).setDisplayName((ChatColor.BLUE.toString()+"TEST")).setGlow(true).setLore((ChatColor.LIGHT_PURPLE.toString()+"Das ISt ein Test")).addIdentifier("Opener").build();



        inventory.setItem(11, apple);
        inventory.setItem(15, new ItemStack(Material.GOLDEN_APPLE));
        player.openInventory(inventory);
    }


    @EventHandler
    public void handleNavigatorOpener(PlayerInteractEvent event){

    }




}
