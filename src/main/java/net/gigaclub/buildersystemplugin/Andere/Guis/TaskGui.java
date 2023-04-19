package net.gigaclub.buildersystemplugin.Andere.Guis;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.GuiLayoutBuilder;
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

import java.util.ArrayList;
import java.util.Objects;

public class TaskGui implements Listener {
    GuiLayoutBuilder guiLayout = new GuiLayoutBuilder();

    public int invWatName = 0;
    public Integer taskinv = 0;
    public int taskID;
    HeadDatabaseAPI api = new HeadDatabaseAPI();
    Translation t = Main.getTranslation();
    BuilderSystem builderSystem = Main.getBuilderSystem();
    Boolean worldTypSelectoractiv = false;
    String worldTypeselectet;
    final ItemStack ON = new ItemBuilder(Material.GREEN_CONCRETE_POWDER).setGui(true).setDisplayName(ChatColor.GREEN + "ON").build();
    final ItemStack OFF = new ItemBuilder(Material.RED_CONCRETE_POWDER).setGui(true).setDisplayName(ChatColor.RED + "OFF").build();
    ItemStack on;
    ItemStack off;

    public TaskGui() {
        this.on = new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.GREEN + "Activate")).setLore("").setGui(true).addIdentifier("on").build();
        this.off = new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.RED + "Deactivate")).setLore("").setGui(true).addIdentifier("off").build();
    }
    public ArrayList<String> taskloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD + "--------------");
        loreList.add(ChatColor.GOLD + "Open Task List");
        loreList.add(ChatColor.GOLD + "get your  Task");
        loreList.add(ChatColor.GOLD + "--------------");
        return loreList;
    }

    public void taskGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack backtoMain = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED + "To Main Menu")).setLore((ChatColor.AQUA + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
        int size = 9 * 5;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD + "Task Gui"));

        inventory = guiLayout.guiFullBuilder(inventory,size);

        inventory.setItem(10, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("task_list").setDisplayName("Task List").build());
        inventory.setItem(size - 1, backtoMain);
        player.openInventory(inventory);
    }

    public void taskList(Player player, int index) {
        ItemStack backtoTask = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED + "To Task Menu")).setLore((ChatColor.AQUA + "Back to Task Gui")).setGui(true).addIdentifier("Task_Opener").build();
        Inventory inventory = Main.getTaskCache().getInventory(index);
        int size = 9 * 6;
        inventory.setItem(size - 1, backtoTask);
        player.openInventory(inventory);
    }

    public ArrayList<String> createAsTeamlore() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD + "Create Projeckt as Team");
        return loreList;
    }

    public ArrayList<String> createAsUserlore() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD + "Create Projeckt as User");
        return loreList;
    }

    public void taskSelect(Player player, int taskid) {
        int size = 9 * 1;
        Inventory inventory = Bukkit.createInventory(null, size, ("Create a Projeckt"));
        inventory = guiLayout.guiFullBuilder(inventory,size);

        inventory.setItem(2, new ItemBuilder(api.getItemHead("9386")).setDisplayName((ChatColor.RED + "Create as Team")).setLore(createAsTeamlore()).setGui(true).addIdentifier("createProjecktasTeam").addID(taskid).build());
        inventory.setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setHead(player.getDisplayName()).setDisplayName("Create as User").setLore(createAsUserlore()).setGui(true).addIdentifier("createProjecktasUser").addID(taskid).build());

    }
    public void worldtypSelect(Player player) {
        worldTypSelectoractiv = true;
        int size = 9 * 4;
        String worldTyp = "flat";
        Inventory inventory = Bukkit.createInventory(null, size, ("Witch world type"));
        inventory = guiLayout.guiFullBuilder(inventory,size);
        JSONArray worldTypes = builderSystem.getAllWorldTypes();

        for (int i = 0; i < worldTypes.length(); i++) {
            JSONObject worldType = worldTypes.getJSONObject(i);
            worldType.getString("name");
            int slot = i+10;
            if(slot < 15||slot>18||slot<24)
                inventory.setItem(slot, new ItemBuilder(Material.PAPER).setDisplayName((ChatColor.RED + worldType.getString("name"))).setLore("Select " + worldType.getString("name")).setGui(true).addIdentifier(worldType.getString("name")).build());


            }
    }




    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
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
                        worldTypeselectet = worldTyp;
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
