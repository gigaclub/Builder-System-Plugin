package net.gigaclub.buildersystemplugin.Andere.InterfaceAPI;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystemplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class Navigator implements Listener {


    int size = 9 * 3;

    public void mainGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();

        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.DARK_AQUA.toString() + "Builder System Gui"));
        for (int j = 0; j < size; j++) {
            inventory.setItem(j, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }

        ItemStack TeamGui = new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.RED.toString() + "Team")).setLore(TeamloreList()).setGui(true).addIdentifier("Team_Opener").build();
        ItemStack TaskGui = new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("Task_Opener").setDisplayName((ChatColor.GREEN.toString()) + "Task").setLore(TaskloreList()).build();
        ItemStack WorldGui = new ItemBuilder(api.getItemHead("32442")).setGui(true).addIdentifier("World_Opener").setDisplayName((ChatColor.BLUE.toString() + "Project")).setLore(WorldloreList()).build();

        inventory.setItem(10, TeamGui);
        inventory.setItem(13, TaskGui);
        inventory.setItem(16, WorldGui);
        player.openInventory(inventory);
    }

    //Lore list für BS_gui
    public ArrayList<String> TeamloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        loreList.add(ChatColor.GOLD.toString() + "Open Team Menu");
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        return loreList;
    }

    public ArrayList<String> TaskloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        loreList.add(ChatColor.GOLD.toString() + "Open Task Menu");
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        return loreList;
    }

    public ArrayList<String> WorldloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "-----------------");
        loreList.add(ChatColor.GOLD.toString() + "Open Project Menu");
        loreList.add(ChatColor.GOLD.toString() + "-----------------");
        return loreList;
    }
//Lore list für BS_gui   ENDE

    public void TeamGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Team Gui"));
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }


    }

    public void TaskGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Task Gui"));
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }


    }

    public void WorldGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Projeckt Gui"));
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }


    }


    @EventHandler
    public void ClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null) {return;}
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING)) {
            String gui = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);

            if (gui == "Team_Opener") {
                //Team GUI
                TeamGui(player);
            } else if (gui == "Task_Opener") {
                //Task Gui
                TaskGui(player);
            } else if (gui == "World_Opener") {
                //World
                WorldGui(player);
            }


        }

    }


    @EventHandler
    public void ClickInvCancel(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.has(new NamespacedKey(Main.getPlugin(), "gui"), PersistentDataType.INTEGER)) {
            int is_gui = data.get(new NamespacedKey(Main.getPlugin(), "gui"), PersistentDataType.INTEGER);
            if (is_gui == 1) {
                event.setCancelled(true);
            }

        }


    }

    @EventHandler
    public void handleGuiOpener(PlayerInteractEvent event) {
        ItemStack GuiOpener = new ItemBuilder(Material.NETHER_STAR).setDisplayName((ChatColor.BLUE.toString() + "BuilderGui")).setGlow(false).setLore((ChatColor.AQUA.toString() + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            PersistentDataContainer data = event.getItem().getItemMeta().getPersistentDataContainer();
            if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"))) {
                String identifie = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);
                if (identifie == "Gui_Opener") {
                    mainGui(event.getPlayer());
                }
            }
        }
    }


}
