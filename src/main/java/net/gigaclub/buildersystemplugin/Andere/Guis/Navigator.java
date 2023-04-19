package net.gigaclub.buildersystemplugin.Andere.Guis;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.GuiLayoutBuilder;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.json.JSONArray;

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
        this.worldGui = new WorldGui();
        this.taskGui = new TaskGui();
    }

    public void mainGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        String playerUUID = player.getUniqueId().toString();
        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.DARK_AQUA + "Builder System Gui"));
        GuiLayoutBuilder guiLayout = new GuiLayoutBuilder();
        inventory = guiLayout.guiFullBuilder(inventory, size);


        ItemStack TeamGui = new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.RED + "Team")).setLore(this.teamGui.teamloreList()).setGui(true).addIdentifier("Team_Opener").build();
        ItemStack TaskList = new ItemBuilder(api.getItemHead("10142")).setGui(true).addIdentifier("task_list").setDisplayName((ChatColor.AQUA) + "Task List").setLore(this.taskGui.taskloreList()).build();
        ItemStack ProjectList = new ItemBuilder(api.getItemHead("32442")).setGui(true).addIdentifier("World_Opener").setDisplayName((ChatColor.BLUE + " your Project List")).setLore(this.worldGui.worldloreList()).build();

        inventory.setItem(10, TeamGui);
        inventory.setItem(13, TaskList);
        inventory.setItem(16, ProjectList);
        player.openInventory(inventory);
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
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
        if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING)) {
            String gui = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);
            int taskID = 0;
            int taskInv = 0;
            int worldInv = 0;
            if (data.has(new NamespacedKey(Main.getPlugin(), "index"), PersistentDataType.INTEGER))
                worldInv = data.get(new NamespacedKey(Main.getPlugin(), "index"), PersistentDataType.INTEGER) - 1;


            if (data.has(new NamespacedKey(Main.getPlugin(), "ID"), PersistentDataType.INTEGER))
                taskID = data.get(new NamespacedKey(Main.getPlugin(), "id"), PersistentDataType.INTEGER) - 1;

            switch (Objects.requireNonNull(gui)) {

                case "Gui_Opener" -> mainGui(player);
                //Main Gui
                case "Team_Opener" -> this.teamGui.teamGui(player);
                case "Task_Opener" -> this.taskGui.taskGui(player);
                case "World_Opener" -> this.worldGui.worldGui(player);


                //Team Gui
                case "invite_list_Opener" -> this.teamGui.teamInvite(player);
                case "Team_Create" -> this.teamGui.teamCreatename(player);

                //Task Gui
                case "task_list" -> this.taskGui.taskList(player, taskInv);
                case "task" -> this.taskGui.taskSelect(player, taskID);
                case "createProjecktasTeam" -> this.taskGui.createProjecktasTeam(player, taskID);
                case "createProjecktasUser" -> this.taskGui.createProjecktasUser(player, taskID);

                //World Gui
                case "WorldlistAll" -> this.worldGui.worldListAll(player, worldInv);
                case "WorldlistUser" -> this.worldGui.worldListAll(player, worldInv);

            }


        }


    }


    @EventHandler
    public void clickInvCancel(InventoryClickEvent event) {
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
            ItemStack item = event.getItem();
            if (item == null) {
                return;
            } else {
                ItemMeta meta = item.getItemMeta();
                if (meta == null) {
                    return;
                }
            }
            PersistentDataContainer data = event.getItem().getItemMeta().getPersistentDataContainer();
            if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"))) {
                String identifie = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);
                if (Objects.equals(identifie, "Gui_Opener")) {
                    mainGui(event.getPlayer());
                }
            }
        }
    }
    @EventHandler
    public void handleItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();

        if (item == null) {
            return;
        } else {
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
        }
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        if (data.has(new NamespacedKey(Main.getPlugin(), "identifie"))) {
            String identifie = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);
            if (Objects.equals(identifie, "Gui_Opener")) {
                event.setCancelled(true);

            }
        }


    }

}
