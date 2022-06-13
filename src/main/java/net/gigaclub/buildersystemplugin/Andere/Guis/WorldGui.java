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
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorldGui {


    HeadDatabaseAPI api = new HeadDatabaseAPI();
    Translation t = Main.getTranslation();

    BuilderSystem builderSystem = Main.getBuilderSystem();

    public ArrayList<String> worldloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD + "-----------------");
        loreList.add(ChatColor.GOLD + "Open Project Menu");
        loreList.add(ChatColor.GOLD + "-----------------");
        return loreList;
    }

    public void worldGui(Player player) {
        ItemStack backtoMain = new ItemBuilder(this.api.getItemHead("9334")).setDisplayName((ChatColor.RED + "To Main Menu")).setLore((ChatColor.AQUA + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();

        ItemStack WorldListAll = new ItemBuilder(Material.PAPER).setGui(true).setDisplayName((ChatColor.RED + "World List all")).setLore((ChatColor.AQUA + "Open The Complet World List")).addIdentifier("WorldlistAll").build();
        ItemStack WorldListUser = new ItemBuilder(Material.PAPER).setGui(true).setDisplayName((ChatColor.RED + "World List des Userers")).setLore((ChatColor.AQUA + "Open The World List of the user")).addIdentifier("WorldlistUser").build();

        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD + "Projeckt Gui"));
        GuiLayoutBuilder guiLayout = new GuiLayoutBuilder();
        inventory = guiLayout.guiFullBuilder(inventory,size);

        inventory.setItem(10, WorldListAll);
        inventory.setItem(16, WorldListUser);


        inventory.setItem(size - 1, backtoMain);
        player.openInventory(inventory);
    }

    public void worldListAll(Player player, int index) {
        ItemStack backtoTask = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED + "To World Menu")).setLore((ChatColor.AQUA + "Back to World Gui")).setGui(true).addIdentifier("World_Opener").build();

        Inventory inventory = Main.getWorldCache().getInventory(index);

        int size = 9 * 6;
        inventory.setItem(size - 1, backtoTask);
        player.openInventory(inventory);
    }

    public  void worldListUser(Player player,int index) {
        JSONArray worlds = builderSystem.getAllWorlds();
        HeadDatabaseAPI api = new HeadDatabaseAPI();

        ItemStack backtoTask = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED + "To Task Menu")).setLore((ChatColor.AQUA + "Back to Task Gui")).setGui(true).addIdentifier("Task_Opener").build();



        Integer taskCont = worlds.length();
        int fullCount = 0;
        int index1 = 1;
        int taskinv = 0;
        int size = 9 * 6;

        while (fullCount < taskCont) {

            Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD + "World User List"));

            GuiLayoutBuilder guiLayout = new GuiLayoutBuilder();

            if (index1 > 1){
                inventory.setItem(47, new ItemBuilder(Material.ARROW).setDisplayName("seite "+ (index1 - 1)).setGui(true).addIdentifier("task_l").addIndex(index1-1).setAmount(index1 - 1).build());
            }

            int iworld = 0;
            if (index1 > 1) {
                iworld = taskinv * 28;
            }
            int cont = 0;

            while (true) {
                if (iworld > taskCont) {
                    if (taskinv ==1){
                        player.openInventory(inventory);
                    }
                    taskinv = 0;
                    break;
                } else if (cont == 44) {
                    inventory.setItem(51, new ItemBuilder(Material.ARROW).setDisplayName("seite "+ (index1 + 1)).setGui(true).addIdentifier("task_l").addIndex(index1+1).setAmount(index1 +1).build());
                    if (taskinv ==1){
                        player.openInventory(inventory);
                    }
                    taskinv++;
                    break;
                } else if (cont >= 10 && cont <= 16 || cont >= 19 && cont <= 25 || cont >= 28 && cont <= 34 || cont >= 37 && cont <= 43) {
                    worldInfoUser(iworld, inventory, cont, worlds);
                    iworld++;
                    fullCount++;
                    if (cont == 43 && taskinv ==1){
                        player.openInventory(inventory);
                    }

                }
                cont++;
            }
            index1++;

        }

    }

    public void worldInfoUser(int iworld, Inventory inventory, int i, JSONArray worlds) {

        try {
            JSONObject world = worlds.getJSONObject(iworld);
        } catch (Exception e) {
            return;
        }
        JSONObject world = worlds.getJSONObject(iworld);
        Integer worldCont = worlds.length();
        if (iworld == worldCont + 1) {
            return;
        }
        ArrayList<String> worldlore = new ArrayList<>();
        worldlore.add(ChatColor.GRAY + "ID: " + ChatColor.WHITE + world.getInt("world_id"));

        worldlore.add(ChatColor.GRAY + "builder_team.world.list.name" + " " + ChatColor.WHITE + world.getString("name"));
        worldlore.add(ChatColor.GRAY + "builder_team.world.list.world_type" + " " + ChatColor.WHITE + world.getString("world_type"));
        worldlore.add(ChatColor.WHITE + " " );
        worldlore.add(ChatColor.WHITE + "builder_team.world.list.world_manager_teams" );
        JSONArray teams = world.getJSONArray("team_ids");

        for (int j = 0; j < teams.length(); j++) {
            JSONObject team = teams.getJSONObject(j);
            String teamname = team.getString("name");
            StringBuilder res = new StringBuilder();
            res.append(ChatColor.GRAY + "");
            res.append(teamname).append("" + ChatColor.WHITE + " , " + ChatColor.GRAY + "");

            res.toString();
            worldlore.add(ChatColor.WHITE + res.toString());


        }
        worldlore.add(ChatColor.WHITE + " " );
        worldlore.add(ChatColor.WHITE + "builder_team.world.list.world_manager_user" );

        JSONArray users = world.getJSONArray("user_ids");
        for (int j = 0; j < users.length(); j++) {

            JSONObject user = users.getJSONObject(j);

            Player player11 = Bukkit.getPlayer(user.getString("name"));
            String player21 = player11.toString();
            StringBuilder res1 = new StringBuilder();
            res1.append(ChatColor.GRAY + "");
            res1.append(player21).append(ChatColor.WHITE + " , " + ChatColor.GRAY);


            res1.toString();

            worldlore.add(ChatColor.WHITE + res1.toString());

        }
        inventory.setItem(i, new ItemBuilder(Material.GREEN_CONCRETE).setDisplayName(ChatColor.GRAY + "Name: " + ChatColor.GREEN + world.getString("name")).addID(world.getInt("id")).addIdentifier("world").setGui(true).setLore(worldlore).build());

    }


}
