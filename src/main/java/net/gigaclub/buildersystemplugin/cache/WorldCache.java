package net.gigaclub.buildersystemplugin.cache;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorldCache {

    public JSONArray worldCache;
    public ArrayList<Inventory> inventories;

    BuilderSystem builderSystem = Main.getBuilderSystem();




    public WorldCache() {
        this.worldCache = new JSONArray();
        this.inventories = new ArrayList<>();
    }

    public void invalidateCache() {
        this.worldCache = this.builderSystem.getAllWorlds();
    }

    public Inventory getInventory(int index) {
        return inventories.get(index);
    }

    public void invalidateInventoryCache() {
        this.inventories = new ArrayList<>();
        JSONArray worlds = Main.getWorldCache().worldCache;
        HeadDatabaseAPI api = new HeadDatabaseAPI();


        Integer taskCont = worlds.length();
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
                inventory.setItem(47, new ItemBuilder(Material.ARROW).setDisplayName("seite "+ (index - 1)).setGui(true).addIdentifier("world_l").addIndex(index-1).setAmount(index - 1).build());
            }

            int iworld = 0;
            if (index > 1) {
                iworld = taskinv * 28;
            }
            int cont = 0;

            while (true) {
                if (iworld > taskCont) {
                    taskinv = 0;
                    break;
                } else if (cont == 44) {
                    inventory.setItem(51, new ItemBuilder(Material.ARROW).setDisplayName("seite "+ (index + 1)).setGui(true).addIdentifier("world_l").addIndex(index+1).setAmount(index +1).build());
                    taskinv++;
                    break;
                } else if (cont >= 10 && cont <= 16 || cont >= 19 && cont <= 25 || cont >= 28 && cont <= 34 || cont >= 37 && cont <= 43) {
                    worldinfo(iworld, inventory, cont, worlds);
                    iworld++;
                    fullCount++;
                }
                cont++;
            }
            index++;

        }
    }

    public void worldinfo(int iworld, Inventory inventory, int i, JSONArray worlds) {

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
        JSONArray teams = world.getJSONArray("team_manager_ids");

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

        JSONArray users = world.getJSONArray("user_manager_ids");
        for (int j = 0; j < users.length(); j++) {

            JSONObject user = users.getJSONObject(j);

            Player player11 = Bukkit.getPlayer(user.getString("name"));
            String player21 = player11.toString();
            StringBuilder res1 = new StringBuilder();

            res1.append(player21).append(ChatColor.WHITE + " , " + ChatColor.GRAY);
            String strValue = "ChatColor.GRAY +";

            res1.append(new StringBuilder(strValue).reverse());
            res1.toString();

            worldlore.add(ChatColor.WHITE + res1.toString());

        }
            inventory.setItem(i, new ItemBuilder(Material.GREEN_CONCRETE).setDisplayName(ChatColor.GRAY + "Name: " + ChatColor.GREEN + world.getString("name")).addID(world.getInt("id")).addIdentifier("world").setGui(true).setLore(worldlore).build());

    }




}
