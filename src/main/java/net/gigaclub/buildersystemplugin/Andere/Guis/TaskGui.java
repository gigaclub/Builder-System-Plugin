package net.gigaclub.buildersystemplugin.Andere.Guis;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskGui {

    HeadDatabaseAPI api = new HeadDatabaseAPI();
    Translation t = Main.getTranslation();

    BuilderSystem builderSystem = Main.getBuilderSystem();

    public int invWatName = 0;
    public Integer taskinv = 0;

    public int taskID;

    public ArrayList<String> TaskloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD.toString() + "--------------");
        loreList.add(ChatColor.GOLD.toString() + "Open Task Menu");
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



//    public void TaskListmain(Player player) {
//        HeadDatabaseAPI api = new HeadDatabaseAPI();
//        String playerUUID = player.getUniqueId().toString();
//
//        try {
//            JSONArray tasks = builderSystem.getAllTasks();
//
//        } catch (Exception e) {
//            return;
//        }
//        JSONArray tasks = builderSystem.getAllTasks();
//        Integer taskCont = tasks.length();
//
//        ItemStack backtoTask = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED.toString() + "To Task Menu")).setLore((ChatColor.AQUA.toString() + "Back to Task Gui")).setGui(true).addIdentifier("Task_Opener").build();
//
//        int size = 9 * 6;
//        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD.toString() + "Task List"));
//        ItemStack p =new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build();
//        for (int i = 0; i <= 8; i++) {
//            inventory.setItem(i, p);
//        }
//        for (int i = 45; i <= 52; i++) {
//            inventory.setItem(i, p);
//        }
//        inventory.setItem(9, p);
//        inventory.setItem(18, p);
//        inventory.setItem(27, p);
//        inventory.setItem(36, p);
//        inventory.setItem(17, p);
//        inventory.setItem(26, p);
//        inventory.setItem(35, p);
//        inventory.setItem(44, p);
//
//
//        inventory.setItem(size - 1, backtoTask);
//        player.openInventory(inventory);
//        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
//            int cont = 1;
//            int itask = 0;
//
//            @Override
//            public void run() {
//                if (itask > taskCont) {
//                    player.openInventory(inventory);
//                    Bukkit.getScheduler().cancelTask(taskID);
//                    taskinv = 0 ;
//                } else if (cont == 44) {
//                    inventory.setItem(51, new ItemBuilder(Material.ARROW).setDisplayName("Naechste seite").setGui(true).addIdentifier("task_l").setAmount(taskinv + 1).build());
//                    player.openInventory(inventory);
//                    taskinv++;
//                    Bukkit.getScheduler().cancelTask(taskID);
//                } else if (cont >= 10 && cont <= 16 || cont >= 19 && cont <= 25 || cont >= 28 && cont <= 34 || cont >= 37 && cont <= 43) {
//                    taskinfo(itask, inventory, playerUUID, cont);
//                    itask++;
//                }
//                cont++;
//
//            }
//        }, 0, 0);
//
//    }

    public void TaskList(Player player, int index) {



//        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
//            int cont = 0;
//            int itask = itask1[0];
//
//
//
//            @Override
//
//            public void run() {
//                if (itask > taskCont) {
//                    Bukkit.getScheduler().cancelTask(taskID);
//                    taskinv = 0 ;
//                } else if (cont == 44) {
//                    inventory.setItem(51, new ItemBuilder(Material.ARROW).setDisplayName("Naechste seite").setGui(true).addIdentifier("task_l").setAmount(taskinv + 1).build());
//                    taskinv++;
//                    Bukkit.getScheduler().cancelTask(taskID);
//                } else if (cont >= 10 && cont <= 16 || cont >= 19 && cont <= 25 || cont >= 28 && cont <= 34 || cont >= 37 && cont <= 43) {
//                    taskinfo(itask, inventory, playerUUID, cont, tasks);
//
//                    itask++;
//                }
//                cont++;
//            }
//        }, 0, 0);

        ItemStack backtoTask = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED.toString() + "To Task Menu")).setLore((ChatColor.AQUA.toString() + "Back to Task Gui")).setGui(true).addIdentifier("Task_Opener").build();
        Inventory inventory = Main.getTaskCache().getInventory(index);
        int size = 9 * 6;
        inventory.setItem(size - 1, backtoTask);
        player.openInventory(inventory);


    }

}
