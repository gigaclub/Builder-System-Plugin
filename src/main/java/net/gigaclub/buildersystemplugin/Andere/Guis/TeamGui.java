package net.gigaclub.buildersystemplugin.Andere.Guis;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class TeamGui implements Listener {

    HeadDatabaseAPI api = new HeadDatabaseAPI();
    Translation t = Main.getTranslation();

    BuilderSystem builderSystem = Main.getBuilderSystem();

    //Lore list für BS_gui
    public ArrayList<String> TeamloreList() {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.GOLD + "--------------");
        loreList.add(ChatColor.GOLD + "Open Team Menu");
        loreList.add(ChatColor.GOLD + "--------------");
        return loreList;
    }

    public void TeamGui(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        String playerUUID = player.getUniqueId().toString();

        ItemStack backtoMain = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED + "To Main Menu")).setLore((ChatColor.AQUA + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();
        int size = 9 * 3;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.RED + "Team Gui"));
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
        }
        try {
            JSONArray teams = builderSystem.getTeamsByMember(playerUUID);
            JSONObject team = teams.getJSONObject(1);
            String teamname = team.getString("name");

        } catch (Exception e) {
            inventory.setItem(11, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("Team_Create").setDisplayName(ChatColor.GRAY + "Team Create").setGlow(true).build());
            inventory.setItem(15, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("invite_list_Opener").setDisplayName(ChatColor.GRAY + "Invites List").build());
            inventory.setItem(size - 1, backtoMain);
            player.openInventory(inventory);
            return;
        }

        JSONArray teams = builderSystem.getTeamsByMember(playerUUID);
        JSONObject team = teams.getJSONObject(1);

        //User mit Team
        if (team.length() >= 0) {
            inventory.setItem(16, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("invite_list_Opener").setDisplayName(ChatColor.GRAY + "Invites List").build());
            inventory.setItem(13, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("list_projecks").setDisplayName(ChatColor.GRAY + "Projeckt List").build());
            inventory.setItem(10, new ItemBuilder(Material.PAPER).setGui(true).addIdentifier("team_manager").setDisplayName(ChatColor.GRAY + "Team Manager").build());
            inventory.setItem(size - 1, backtoMain);
        }
        player.openInventory(inventory);
    }

    public void TeamInvite(Player player) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        String playerUUID = player.getUniqueId().toString();

        try {


        } catch (Exception e) {
            return;
        }

        ItemStack backtoTeam = new ItemBuilder(api.getItemHead("9334")).setDisplayName((ChatColor.RED + "To Team Menu")).setLore((ChatColor.AQUA + "Open The Team Gui")).setGui(true).addIdentifier("Team_Opener").build();
        int size = 9 * 6;
        Inventory inventory = Bukkit.createInventory(null, size, (ChatColor.GOLD + "Team Gui"));
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

        for (int i = 10; i <= 16; i++) {
            inventory.setItem(i, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());

            if (i == 16) {
                for (int i2 = 19; i2 <= 25; i2++) {
                    inventory.setItem(i2, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());

                    if (i2 == 25) {
                        for (int i3 = 28; i3 <= 34; i3++) {
                            inventory.setItem(i3, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());

                            if (i3 == 34) {
                                for (int i4 = 37; i4 <= 44; i4++) {
                                    if (i4 <= 43) {
                                        inventory.setItem(i4, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName(" ").setGui(true).build());
                                    }
                                    if (i4 == 44) {
                                        inventory.setItem(51, new ItemBuilder(Material.ARROW).setDisplayName(" ").setGui(true).build());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        inventory.setItem(size - 1, backtoTeam);
        player.openInventory(inventory);
    }

    public String TeamName;
    public String TeamDesc;
    public AnvilGUI anvilgui;

    public void TeamCreatename(Player player) {
        anvilgui = new AnvilGUI.Builder()
                .onClose(this::TeamGui
                ).onComplete(((player1, output) -> {
                    if (Objects.equals(output, " ") || Objects.equals(output, "Dein Team Heist") || Objects.equals(output, "D") || Objects.equals(output, "")) {
                        player.sendMessage("Geben sie eine Namen ein");
                        TeamGui(player);

                    } else {


                        player1.sendMessage("Team Name: " + output);
                        TeamCreateDesc(player, output);
                    }
                    return AnvilGUI.Response.close();
                }))
                .itemLeft(new ItemBuilder(Material.PAPER).setDisplayName("Team Name").build())

                .title(ChatColor.GREEN + "Team Name(PflichtFeld)").plugin(Main.getPlugin()).text("Dein Team Heist").open(player);
    }

    public void TeamCreateDesc(Player player, String teamName) {
        String playerUUID = player.getUniqueId().toString();
        anvilgui = new AnvilGUI.Builder()
                .onClose(this::TeamGui
                ).onComplete(((player1, output) -> {
                    if (Objects.equals(output, " ") || Objects.equals(output, "Beschreibung") || Objects.equals(output, "B")) {
                        builderSystem.createTeam(playerUUID, teamName);
                        player.sendMessage("Team Erstellt ohne Desc");
                        TeamGui(player);


                    } else {

                        player.sendMessage("Team Beschreibung: " + output);
                        builderSystem.createTeam(playerUUID, teamName, output);
                        player.sendMessage("Team Erstellt Mit Desc");
                        TeamGui(player);
                    }
                    return AnvilGUI.Response.close();
                }))
                .itemLeft(new ItemBuilder(Material.PAPER).setDisplayName("Team Bescheigung").build())

                .title(ChatColor.GREEN + "Team Bescheigung").plugin(Main.getPlugin()).text("Beschreibung").open(player);
    }

}
