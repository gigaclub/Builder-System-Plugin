package net.gigaclub.buildersystemplugin.Andere.Guis;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.json.JSONArray;
import org.json.JSONObject;


import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.Objects;

public class TaskGui implements Listener {

    public int invWatName = 0;
    public Integer taskinv = 0;
    public int taskID;
    HeadDatabaseAPI api = new HeadDatabaseAPI();
    Translation t = Main.getTranslation();
    BuilderSystem builderSystem = Main.getBuilderSystem();
    Boolean worldTypSelectoractiv = false;
    String WorldTypeselectet;
    ItemStack ON = new ItemBuilder(Material.GREEN_CONCRETE_POWDER).setGui(true).setDisplayName(ChatColor.GREEN + "ON").build();
    ItemStack OFF = new ItemBuilder(Material.RED_CONCRETE_POWDER).setGui(true).setDisplayName(ChatColor.RED + "OFF").build();

    public ArrayList<String> TaskloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        loreList.add(ChatColor.GOLD.toString() + "Open Task List");
        loreList.add(ChatColor.GOLD.toString() + "get your  Task");
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        return loreList;
    }

    public void TaskGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack backtoMain = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED.toString() + "To Main Menu")).setLore((ChatColor.AQUA.toString() + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
        int size = 9 * 5;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Task Gui"));
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }
        inventory.setItem(10, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("task_list").setDisplayName("Task List").build());
        inventory.setItem(size - 1, backtoMain);
        player.openInventory(inventory);
    }

    public void TaskList(Player player, int index) {
        ItemStack backtoTask = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED.toString() + "To Task Menu")).setLore((ChatColor.AQUA.toString() + "Back to Task Gui")).setGui(true).addIdentifier("Task_Opener").build();
        Inventory inventory = Main.getTaskCache().getInventory(index);
        int size = 9 * 6;
        inventory.setItem(size - 1, backtoTask);
        player.openInventory(inventory);
    }

    public ArrayList<String> createAsTeamlore() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "Create Projeckt as Team");
        return loreList;
    }

    public ArrayList<String> createAsUserlore() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "Create Projeckt as User");
        return loreList;
    }

    public void TaskSelect(Player player, int taskid) {
        int size = 9 * 1;
        Inventory inventory = Bukkit.createInventory(null, size, ("Create a Projeckt"));
        inventory.setItem(2, new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.RED.toString() + "Create as Team")).setLore(createAsTeamlore()).setGui(true).addIdentifier("createProjecktasTeam").addID(taskid).build());
        inventory.setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setHead(player.getDisplayName()).setDisplayName("Create as User").setLore(createAsUserlore()).setGui(true).addIdentifier("createProjecktasUser").addID(taskid).build());

    }

    ItemStack on = new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.GREEN.toString() + "Activate")).setLore("").setGui(true).addIdentifier("on").build();
    ItemStack off = new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.RED.toString() + "Deactivate")).setLore("").setGui(true).addIdentifier("off").build();
    public void WorldtypSelect(Player player) {
        worldTypSelectoractiv = true;
        int size = 9 * 4;
        String worldTyp = "flat";
        Inventory inventory = Bukkit.createInventory(null, size, ("Witch world type"));
        JSONArray worldTypes = builderSystem.getAllWorldTypes();

        for (int i = 0; i < worldTypes.length(); i++) {
            JSONObject worldType = worldTypes.getJSONObject(i);
            worldType.getString("name");
            int slot = i+10;
            if(slot < 15||slot>18||slot<24)
            inventory.setItem(slot,new ItemBuilder(Material.PAPER).setDisplayName((ChatColor.RED.toString() + worldType.getString("name"))).setLore("Select "+worldType.getString("name")).setGui(true).addIdentifier(worldType.getString("name")).build());


            }
    }



    public ArrayList<String> PluginSelector() {return PluginSelector();}

    public void Switch(Inventory inventory,String tag,Boolean Default,Boolean Switch,int Slot){
        if(Default = true){
              inventory.setItem(Slot,on);
        }else inventory.setItem(Slot,off);

        while (inventory.getItem(Slot) == on ||inventory.getItem(Slot) == off) {
            if (inventory.getItem(Slot) == on) {
                PluginSelector().add(tag);

            } else {
                for (int i = 0; i < PluginSelector().size(); i++) {
                    if (PluginSelector().get(i) == tag)
                        PluginSelector().remove(i);
                }
            }
        }
    }


    @EventHandler
    public void ClickEvent(InventoryClickEvent event) {
        BuilderSystem builderSystem = Main.getBuilderSystem();
        if (worldTypSelectoractiv = true) {

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
                String input = data.get(new NamespacedKey(Main.getPlugin(), "identifie"), PersistentDataType.STRING);

                JSONArray worldTypes = builderSystem.getAllWorldTypes();
                for (int i = 0; i < worldTypes.length(); i++) {
                    JSONObject worldType = worldTypes.getJSONObject(i);
                    String worldTyp = worldType.getString("name");
                    if (input.equalsIgnoreCase(worldTyp.toLowerCase())) {
                        WorldTypeselectet = worldTyp;
                    }
                }
              int clickSlot = event.getSlot();
               Inventory inventory = event.getClickedInventory();

                switch (Objects.requireNonNull(input)) {


                    case "on" -> inventory.setItem(clickSlot,off);
                    case "off"-> inventory.setItem(clickSlot,on);
                }
            }

        }
    }





    public void createProjecktasTeam(Player player, int taskid) {

    }

    public void createProjecktasUser(Player player, int taskid) {

    }


}
