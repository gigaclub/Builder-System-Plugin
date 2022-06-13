package net.gigaclub.buildersystemplugin.Commands;


import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Worlds implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();
        JSONArray ownteamname;
        try {
             ownteamname = builderSystem.getTeamsByMember(playerUUID);

        } catch (Exception e) {
            return false;
        }


        if (sender instanceof Player) {

            switch (args[0].toLowerCase()) {


                case "createasteam":
                    
                    if (args.length == 1 || args.length == 2) {
                        player.sendMessage(ChatColor.RED + t.t("builder_team.to_less_arguments", playerUUID));
                    }
                    if (args.length == 3) {

                        JSONObject task = builderSystem.getTask(Integer.getInteger(args[2]));
                        String TaskName = task.getString("name");

                        builderSystem.createWorldAsTeam(playerUUID, Integer.parseInt(args[1]), Integer.parseInt(args[2]), TaskName, "");
                        player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_team_succses", playerUUID));
                        break;


                    } else if (args.length == 4) {
                        JSONArray worldTypes = builderSystem.getAllWorldTypes();

                        if (isInt(args[1])) {
                            builderSystem.createWorldAsTeam(playerUUID, Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3], "");
                            player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_team_succses", playerUUID));
                            break;
                        }

                    } else if (args.length == 5) {


                        if (isInt(args[1])) {

                            builderSystem.createWorldAsTeam(playerUUID, Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3], args[4]);
                            player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_team_succses", playerUUID));
                            break;
                        }


                    } else if (args.length >= 6) {
                        player.sendMessage(t.t("BuilderSystem.toomany_Arguments", playerUUID));
                        return false;
                    }
                    break;

                case "createasuser":

                    if (args.length == 2) {
                        // task auswahl per id oder name
                        JSONArray allTasks = builderSystem.getAllTasks();
                        JSONObject task = builderSystem.getTask(Integer.getInteger(args[2]));
                        String TaskName = task.getString("name");


                        if (isInt(args[1])) {
                            builderSystem.createWorldAsUser(playerUUID, Integer.parseInt(args[1]), TaskName, "");
                            player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_team_succses", playerUUID));
                            break;
                        }


                    } else if (args.length == 3) {
                        JSONArray worldTypes = builderSystem.getAllWorldTypes();


                        if (isInt(args[1])) {
                            builderSystem.createWorldAsUser(playerUUID, Integer.parseInt(args[1]), args[2], "");
                            player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_user_succses", playerUUID));
                                    break;
                                }
                    } else if (args.length >= 4) {


                        if (isInt(args[1])) {
                            builderSystem.createWorldAsUser(playerUUID, Integer.parseInt(args[1]), args[2], args[3]);
                            player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_user_succses", playerUUID));
                            break;
                        }


                    } else if (args.length >= 5) {
                        player.sendMessage(t.t("BuilderSystem.toomany_Arguments", playerUUID));
                        return false;
                    }
                    break;


                case "removeteam":
                    // add function to remove other team
                    
                    if (args.length == 2) {

                    }
                    if (ownteamname != null) {
                        for (int i = 0; i < ownteamname.length(); i++) {
                            if (i == Integer.parseInt(args[1])) {
                                if (isInt(args[2])) {
                                    builderSystem.removeTeamFromWorld(playerUUID, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                                    player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.remove_succses", playerUUID));
                                }
                            }
                        }
                    }
                    break;

                case "removeuser":


                    if (args[3].toLowerCase(Locale.ROOT) == Bukkit.getOfflinePlayer(args[1]).getName().toLowerCase(Locale.ROOT)) {
                        if (isInt(args[2])) {
                            builderSystem.removeUserFromWorld(playerUUID, args[3], Integer.parseInt(args[2]));
                            player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.remove_succses", playerUUID));
                        }
                    }
                    break;

                case "addteam":
                    for (int i = 0; i < ownteamname.length(); i++) {
                        if (i != Integer.parseInt(args[1])) {
                            int Teamname = i;
                            if (isInt(args[2])) {

                                builderSystem.addTeamToWorld(playerUUID, Teamname, Integer.parseInt(args[2]));
                                player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.addteam_succses", playerUUID));
                            }
                        }
                    }

                    break;

                case "adduser":
                    if (isInt(args[2])) {
                        String addetuser = Bukkit.getPlayer(args[1]).toString();

                        builderSystem.addUserToWorld(playerUUID, addetuser, Integer.parseInt(args[2]));
                        player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.adduser_succses", playerUUID));

                    }
                    break;

                case "list":
                    JSONArray worlds = builderSystem.getAllWorlds();
                    for (int i = 0; i < worlds.length(); i++) {
                        JSONObject world = worlds.getJSONObject(i);
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.id_list", playerUUID) + " " + ChatColor.WHITE + world.getInt("world_id"));
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.name_list", playerUUID) + " " + ChatColor.WHITE + world.getString("name"));
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.world_typ_list", playerUUID) + " " + ChatColor.WHITE + world.getString("world_type"));
                        player.sendMessage("");
                        player.sendMessage(t.t("BuilderSystem.world.team_list", playerUUID));
                        JSONArray teams = world.getJSONArray("team_manager_ids");

                        for (int j = 0; j < teams.length(); j++) {
                            JSONObject team = teams.getJSONObject(j);
                            String teamname = team.getString("name");
                            StringBuilder res = new StringBuilder();
                            res.append(ChatColor.GRAY + "");
                            res.append(teamname).append("" + ChatColor.WHITE + " , " + ChatColor.GRAY + "");

                            res.toString();

                            player.sendMessage(res.toString());

                        }
                        player.sendMessage("");

                        player.sendMessage(t.t("BuilderSystem.world.user_list", playerUUID));
                        JSONArray users = world.getJSONArray("user_manager_ids");
                        for (int j = 0; j < users.length(); j++) {

                            JSONObject user = users.getJSONObject(j);

                            UUID uuid = UUID.fromString(user.getString("mc_uuid"));
                            OfflinePlayer player11 = Bukkit.getOfflinePlayer(uuid);
                            String player21 = player11.getName();
                            StringBuilder res1 = new StringBuilder();

                            res1.append(ChatColor.GRAY + "");
                            res1.append(player21).append(ChatColor.WHITE + " , " + ChatColor.GRAY);

                            res1.toString();

                            player.sendMessage(res1.toString());

                        }

                        player.sendMessage(ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "----------------------------------");

                    }
                    break;

            }


        }
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("CreateAsTeam");
            arguments.add("CreateAsUser");
            arguments.add("Remove");
            arguments.add("addTeam");
            arguments.add("addUser");
            arguments.add("List");

            return arguments;

        } else if (args.length >= 2) {
            switch (args[0].toLowerCase()) {

                case "createasteam", "createasuser":

                    if (args.length == 2) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.world.tab_team_id", playerUUID) + ">");
                        return createname;
                    }
                    if (args.length == 3) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.tab_task_id", playerUUID) + ">");
                        return createname;

                    } else if (args.length == 4) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.world.tab_world_name", playerUUID) + ">");
                        return createname;

                    } else if (args.length == 5) {
                        // WorldTyps
                        List<String> createname = new ArrayList<>();
                        JSONArray getWorldTypesList = builderSystem.getAllWorldTypes();
                        for (int i = 0; i < getWorldTypesList.length(); i++) {

                            String worldTyp = getWorldTypesList.getJSONObject(i).getString("name");

                            createname.add(worldTyp);

                        }
                        return createname;
                    }
                    break;
            }

        }


        return null;
    }


    private static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}


