package net.gigaclub.buildersystemplugin.Andere.Guis;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
//import net.wesjd.anvilgui.AnvilGUI;
import net.wesjd.anvilgui.AnvilGUI;
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
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Navigator implements Listener {

    HeadDatabaseAPI api = new HeadDatabaseAPI();
    Translation t = Main.getTranslation();

    BuilderSystem builderSystem = Main.getBuilderSystem();

    TeamGui teamGui;
    TaskGui taskGui;
    WorldGui worldGui;
    JSONArray tasks;

    public Navigator() {
        this.teamGui = new TeamGui();
        this.taskGui = new TaskGui();
        this.worldGui = new WorldGui();
    }

    public void mainGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        String playerUUID = player.getUniqueId().toString();
        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.DARK_AQUA.toString() + "Builder System Gui"));
        ItemStack p =new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build();
        for (int j = 0; j < size; j++) {
            inventory.setItem(j, p);
        }


        ItemStack TeamGui = new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.RED.toString() + "Team")).setLore(this.teamGui.TeamloreList()).setGui(true).addIdentifier("Team_Opener").build();
        ItemStack TaskGui = new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("Task_Opener").setDisplayName((ChatColor.GREEN.toString()) + "Task").setLore(this.taskGui.TaskloreList()).build();
        ItemStack WorldGui = new ItemBuilder(api.getItemHead("32442")).setGui(true).addIdentifier("World_Opener").setDisplayName((ChatColor.BLUE.toString() + "Project")).setLore(this.worldGui.WorldloreList()).build();

        inventory.setItem(10, TeamGui);
        inventory.setItem(13, TaskGui);
        inventory.setItem(16, WorldGui);
        player.openInventory(inventory);
    }

    @EventHandler
    public void ClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null) {
            return;
        } else {
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
        }
        String taskl = "task_l" + this.taskGui.taskinv;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING)) {
            String gui = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);
            int taskInv = 0;
            if (data.has(new NamespacedKey(Main.getPlugin(), "index"), PersistentDataType.INTEGER)) {

                taskInv = data.get(new NamespacedKey(Main.getPlugin(), "index"), PersistentDataType.INTEGER)-1;
                System.out.println("task seite "+taskInv );
            }
            switch (gui) {
                case "Team_Opener" -> this.teamGui.TeamGui(player);
                case "Task_Opener" -> this.taskGui.TaskGui(player);
                case "World_Opener" -> this.worldGui.WorldGui(player);
                case "Gui_Opener" -> mainGui(player);
                case "invite_list_Opener" -> this.teamGui.TeamInvite(player);
                case "Team_Create" -> this.teamGui.TeamCreatename(player);
                case "task_list" -> this.taskGui.TaskList(player, taskInv);
                case "task_l" -> this.taskGui.TaskList(player, taskInv);






            }


        }

    }


    @EventHandler
    public void ClickInvCancel(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        } else {
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
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
