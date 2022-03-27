package net.gigaclub.buildersystemplugin.Commands;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static org.bukkit.Bukkit.getLogger;

public class Worlds implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();


        if (sender instanceof Player) {

            switch (args[0].toLowerCase()) {


                case "createasteam":
                    String ownteamname = builderSystem.getTeamNameByMember(playerUUID).getString("name");

                    getLogger().info("teamname: " + ownteamname);
                    if (args.length == 2) {
                        JSONArray allTasks = builderSystem.getAllTasks();
                        for (int i = 0; i < allTasks.length(); i++) {
                            int taskID = allTasks.getJSONObject(i).getInt("id");
                            if (taskID == Integer.parseInt(args[1])) {
                                JSONObject task = builderSystem.getTask(taskID);
                                String TaskName = task.getString("name");
                                builderSystem.createWorldAsTeam(playerUUID, Integer.parseInt(args[1]), TaskName, "default");
                                player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_team_succses", playerUUID));
                            }
                        }
                    } else if (args.length == 3) {
                        JSONArray worldTypes = builderSystem.getAllWorldTypes();
                        for (int i = 0; i < worldTypes.length(); i++) {
                            JSONObject worldType = worldTypes.getJSONObject(i);
                            String worldTyp = worldType.getString("name");
                            boolean defaultworldtyp = worldType.getBoolean("default");

                            if (defaultworldtyp == true) {
                                if (isInt(args[1])) {
                                    builderSystem.createWorldAsTeam(playerUUID, Integer.parseInt(args[1]), args[2], worldTyp);
                                    player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_team_succses", playerUUID));
                                }
                            }
                        }
                    } else if (args.length == 4) {
                        JSONArray worldTypes = builderSystem.getAllWorldTypes();
                        for (int i = 0; i < worldTypes.length(); i++) {
                            JSONObject worldType = worldTypes.getJSONObject(i);
                            String worldTyp = worldType.getString("name");
                            String sworldTyp = stackworldtyp(args, 4);
                            if (sworldTyp.toLowerCase().equals(worldTyp.toLowerCase())) {
                                if (isInt(args[1])) {
                                    builderSystem.createWorldAsTeam(playerUUID, Integer.parseInt(args[1]), args[2], worldTyp);
                                    player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_team_succses", playerUUID));
                                }
                            }
                        }
                    } else if (args.length >= 5) {
                        player.sendMessage(t.t("BuilderSystem.toomany_Arguments", playerUUID));
                        return false;
                    }
                    break;

                case "createasuser":

                    if (args.length == 2) {
                        // task auswahl per id oder name
                        JSONArray allTasks = builderSystem.getAllTasks();
                        for (int i = 0; i < allTasks.length(); i++) {
                            JSONObject taskObject = (JSONObject) allTasks.get(i);
                            int Taskid = taskObject.getInt("id");
                            if (Taskid == Integer.parseInt(args[1])) {
                                JSONObject task = builderSystem.getTask(Taskid);
                                String TaskName = task.getString("name");
                                getLogger().info("Taskname: " + TaskName);
                                builderSystem.createWorldAsUser(playerUUID, Integer.parseInt(args[1]), TaskName, "default");
                                player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_team_succses", playerUUID));

                            }
                        }
                    } else if (args.length == 3) {
                        JSONArray worldTypes = builderSystem.getAllWorldTypes();
                        for (int i = 0; i < worldTypes.length(); i++) {
                            JSONObject worldType = worldTypes.getJSONObject(i);
                            String worldTyp = worldType.getString("name");
                            boolean defaultworldtyp = worldType.getBoolean("default");
                            if (defaultworldtyp) {
                                if (isInt(args[1])) {
                                    builderSystem.createWorldAsUser(playerUUID, Integer.parseInt(args[1]), args[2], worldTyp);
                                    player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_user_succses", playerUUID));

                                }
                            }
                        }
                    } else if (args.length >= 4) {
                        JSONArray worldTypes = builderSystem.getAllWorldTypes();
                        for (int i = 0; i < worldTypes.length(); i++) {
                            JSONObject worldType = worldTypes.getJSONObject(i);
                            String worldTyp = worldType.getString("name");
                            String sworldTyp = stackworldtyp(args, 4);
                            if (sworldTyp.equalsIgnoreCase(worldTyp.toLowerCase())) {
                                if (isInt(args[1])) {
                                    builderSystem.createWorldAsUser(playerUUID, Integer.parseInt(args[1]), args[2], worldTyp);
                                    player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.create_user_succses", playerUUID));

                                }
                            }
                        }

                    } else if (args.length >= 5) {
                        player.sendMessage(t.t("BuilderSystem.toomany_Arguments", playerUUID));
                        return false;
                    }
                    break;

                case "removeteam":
                    // add function to remove other team
                    String ownteamname3 = builderSystem.getTeamNameByMember(playerUUID).getString("name");
                    if (args.length == 2) {
                        
                    }
                    if (ownteamname3 != null) {
                        if (!(ownteamname3.equalsIgnoreCase(args[1]))) {
                            String Teamname = builderSystem.getTeam(args[1].toLowerCase()).getString("name");
                            if (isInt(args[2])) {
                                builderSystem.removeTeamFromWorld(playerUUID, Teamname, Integer.parseInt(args[2]));
                                player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.remove_succses", playerUUID));
                            }
                        }
                    }
                    break;

                case "removeuser":


                    if (args[3].toLowerCase(Locale.ROOT) == Bukkit.getOfflinePlayer(args[1]).getName().toLowerCase(Locale.ROOT)) {
                        if (isInt(args[2])) {
                            builderSystem.removeUserFromWorld(playerUUID,args[3], Integer.parseInt(args[2]));
                            player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.remove_succses", playerUUID));
                        }
                    }
                    break;

                case "addteam":
                    String ownteamname1 = builderSystem.getTeamNameByMember(playerUUID).getString("name");
                    if (!(ownteamname1.equalsIgnoreCase(args[1]))) {
                        String Teamname = builderSystem.getTeam(args[1]).getString("name");
                        if (isInt(args[2])) {

                            builderSystem.addTeamToWorld(playerUUID, Teamname, Integer.parseInt(args[2]));
                            player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.addteam_succses", playerUUID));
                        }
                    }
                    break;

                case "adduser":
                    if (isInt(args[2])) {
                        String addetuser = Bukkit.getPlayer(args[1]).toString();

                        builderSystem.addTeamToWorld(playerUUID, addetuser, Integer.parseInt(args[2]));
                        player.sendMessage(ChatColor.GREEN + t.t("BuilderSystem.world.adduser_succses", playerUUID));

                    }
                    break;

                case "list":
                    JSONArray worlds = builderSystem.getAllWorlds();
                    for (int i = 0; i < worlds.length(); i++) {
                        JSONObject world = worlds.getJSONObject(i);
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.id_list", playerUUID) + " " + ChatColor.WHITE + world.getString("world_id"));
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.name_list", playerUUID) + " " + ChatColor.WHITE + world.getString("name"));
                        player.sendMessage(ChatColor.GRAY + t.t("BuilderSystem.world.world_typ_list", playerUUID) + " " + ChatColor.WHITE + world.getString("world_type"));
                        player.sendMessage("");
                        player.sendMessage(t.t("BuilderSystem.world.team_list", playerUUID));
                        JSONArray teams = world.getJSONArray("team_manager_ids");

                        for (int j = 0; j < teams.length(); j++) {
                            JSONObject team = teams.getJSONObject(j);
                            if (team.get("team_manager_id") != null) {
                                String teamname = team.getString("name");
                                StringBuilder res = new StringBuilder();
                                res.append(ChatColor.GRAY + "");
                                res.append(teamname).append("" + ChatColor.WHITE + " , " + ChatColor.GRAY + "");

                                res.toString();

                                player.sendMessage(res.toString());
                            }
                        }
                        player.sendMessage("");

                        player.sendMessage(t.t("BuilderSystem.world.user_list", playerUUID));
                        JSONArray users = world.getJSONArray("user_manager_ids");
                        for (int j = 0; j < users.length(); j++) {

                            JSONObject user = users.getJSONObject(j);
                            if (user.get("name") != null) {

                                Player player11 = Bukkit.getPlayer(user.getString("name"));
                                String player21 = player11.toString();
                                StringBuilder res1 = new StringBuilder();

                                res1.append(player21).append(ChatColor.WHITE + " , " + ChatColor.GRAY);
                                String strValue = "ChatColor.GRAY +";

                                res1.append(new StringBuilder(strValue).reverse());
                                res1.toString();

                                player.sendMessage(res1.toString());
                            }
                        }
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "----------------------------------");
                            player.sendMessage(" ");
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
                        createname.add("<" + t.t("builder_team.tab_task_id", playerUUID) + ">");
                        return createname;

                    } else if (args.length == 3) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.world.tab_world_name", playerUUID) + ">");
                        return createname;

                    } else if (args.length == 4) {
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


    private String stackworldtyp(String[] args, int at) {
        StringBuilder res = new StringBuilder();
        for (int i = at; i < args.length; i++) {
            if (i < 2 + at) {
                res.append(args[i]).append(" ");
            } else
                break;
        }
        return res.toString();
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


