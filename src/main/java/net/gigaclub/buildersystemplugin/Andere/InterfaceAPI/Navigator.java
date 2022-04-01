package net.gigaclub.buildersystemplugin.Andere.InterfaceAPI;

import net.gigaclub.buildersystemplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Navigator implements Listener {

    ItemStack GuiOpener = new ItemBuilder(Material.NETHER_STAR).setDisplayName((ChatColor.BLUE.toString() + "BuilderGui")).setGlow(false).setLore((ChatColor.AQUA.toString() + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
    private String GUI_NAME = "Test Gui";

    public void mainGui(Player player) {
        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + GUI_NAME));
        for (int j = 0; j < size; j++) {
            inventory.setItem(j, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setGlow(true).setDisplayName("").setGui(true).build());
        }


        ItemStack apple = new ItemBuilder(Material.APPLE).setDisplayName((ChatColor.BLUE.toString() + "TEST")).setGlow(true).setLore((ChatColor.LIGHT_PURPLE.toString() + argument())).setGui(true).addIdentifier("Opener").build();


        inventory.setItem(11, apple);
        inventory.setItem(15, new ItemStack(Material.GOLDEN_APPLE));
        player.openInventory(inventory);
    }

    public List<String> argument() {
        List<String> arguments = new ArrayList<>();
        arguments.add(ChatColor.LIGHT_PURPLE.toString() + "test");
        arguments.add(ChatColor.UNDERLINE.toString() + "jop das ist es");
        return arguments;
    }


    @EventHandler
    public void handleGuiOpener(PlayerInteractEvent event) {
        ItemStack GuiOpener = new ItemBuilder(Material.NETHER_STAR).setDisplayName((ChatColor.BLUE.toString() + "BuilderGui")).setGlow(false).setLore((ChatColor.AQUA.toString() + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.getPlayer().sendMessage("Klick");
            PersistentDataContainer data = event.getItem().getItemMeta().getPersistentDataContainer();
            if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"))) {
                event.getPlayer().sendMessage("true");
                String identifie = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);
                event.getPlayer().sendMessage("Identifier: " + identifie);
                if (identifie == "Gui_Opener") {
                    mainGui(event.getPlayer());
                }
            }
        }
    }


}
