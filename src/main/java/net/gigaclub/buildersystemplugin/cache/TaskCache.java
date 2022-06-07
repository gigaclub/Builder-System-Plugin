package net.gigaclub.buildersystemplugin.cache;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskCache {

    public JSONArray taskCache;
    public ArrayList<Inventory> inventories;

    BuilderSystem builderSystem = Main.getBuilderSystem();

    public TaskCache() {
        this.taskCache = new JSONArray();
        this.inventories = new ArrayList<>();
    }

    public void invalidateCache() {
        this.taskCache = this.builderSystem.getAllTasks();
    }

    public Inventory getInventory(int index) {
        return inventories.get(index);
    }

    public void invalidateInventoryCache() {
        this.inventories = new ArrayList<>();
        JSONArray tasks = Main.getTaskCache().taskCache;
        HeadDatabaseAPI api = new HeadDatabaseAPI();


        Integer taskCont = tasks.length();
        int fullCount = 0;
        int index = 1;
        int taskinv = 0;
        int size = 9 * 6;

        while (fullCount < taskCont) {

            Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Task List"));
            ItemStack p = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build();
            for (int i = 0; i <= 8; i++) {
                inventory.setItem(i, p);
            }
            for (int i = 45; i <= 52; i++) {
                inventory.setItem(i, p);
            }
            inventory.setItem(9, p);
            inventory.setItem(18, p);
            inventory.setItem(27, p);
            inventory.setItem(36, p);
            inventory.setItem(17, p);
            inventory.setItem(26, p);
            inventory.setItem(35, p);
            inventory.setItem(44, p);

            if (index > 1){
                System.out.println(index);
                inventory.setItem(47, new ItemBuilder(Material.ARROW).setDisplayName("seite "+ (index - 1)).setGui(true).addIdentifier("task_l").addIndex(index-1).setAmount(index - 1).build());
            }

            int itask = 0;
            if (index > 1) {
                itask = taskinv * 28;
            }
            int cont = 0;

            while (true) {
                if (itask > taskCont) {
                    taskinv = 0;
                    break;
                } else if (cont == 44) {
                    inventory.setItem(51, new ItemBuilder(Material.ARROW).setDisplayName("seite "+ (index + 1)).setGui(true).addIdentifier("task_l").addIndex(index+1).setAmount(index +1).build());
                    taskinv++;
                    break;
                } else if (cont >= 10 && cont <= 16 || cont >= 19 && cont <= 25 || cont >= 28 && cont <= 34 || cont >= 37 && cont <= 43) {
                    taskinfo(itask, inventory, cont, tasks);
                    itask++;
                    fullCount++;
                }
                cont++;
            }
            index++;
            this.inventories.add(inventory);
        }
    }

    public void taskinfo(int itask, Inventory inventory, int i, JSONArray tasks) {

        try {
            JSONObject task = tasks.getJSONObject(itask);
        } catch (Exception e) {
            return;
        }
        JSONObject task = tasks.getJSONObject(itask);
        Integer taskCont = tasks.length();
        if (itask == taskCont + 1) {
            return;
        }
        ArrayList<String> tasklore = new ArrayList<>();
        tasklore.add(ChatColor.GRAY + "ID: " + ChatColor.WHITE + task.getInt("id"));
        try {
            task.getBoolean("description");
        } catch (Exception e) {
            tasklore.add(ChatColor.GRAY + "builder_team.task.list.Description" + " " + ChatColor.WHITE + task.getString("description"));
        }
        tasklore.add(ChatColor.GRAY + "builder_team.task.list.build_size" + " " + ChatColor.WHITE + task.getInt("build_width") + " x " + task.getInt("build_length"));
        JSONArray worlds = task.getJSONArray("world_ids");
        tasklore.add(ChatColor.GRAY + "builder_team.task.list.projeckt_count" + " " + ChatColor.WHITE + worlds.length());


        if(worlds.length() <= 1 ){
            inventory.setItem(i, new ItemBuilder(Material.GREEN_CONCRETE).setDisplayName(ChatColor.GRAY + "Name: " + ChatColor.GREEN + task.getString("name")).addID(task.getInt("id")).addIdentifier("task").setGui(true).setLore(tasklore).build());
        }else
        if(worlds.length() >= 2 && worlds.length() <= 7){
            inventory.setItem(i, new ItemBuilder(Material.ORANGE_CONCRETE).setDisplayName(ChatColor.GRAY + "Name: " + ChatColor.GOLD + task.getString("name")).addID(task.getInt("id")).addIdentifier("task").setGui(true).setLore(tasklore).build());
        }else
        if(worlds.length() >= 8){
            inventory.setItem(i, new ItemBuilder(Material.RED_CONCRETE).setDisplayName(ChatColor.GRAY + "Name: " + ChatColor.DARK_RED + task.getString("name")).addID(task.getInt("id")).addIdentifier("task").setGui(true).setLore(tasklore).build());
        }

    }


}
