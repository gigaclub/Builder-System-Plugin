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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static net.gigaclub.buildersystemplugin.Config.Config.getConfig;

public class    Tasks implements CommandExecutor, TabCompleter {

   @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();
        FileConfiguration config = getConfig();


        if (player instanceof Player) {

            if (args.length >= 1) {

                switch (args[0].toLowerCase()) {

                    case "create":
                        if (args.length == 1) {
                            player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                            return false;
                        }
                        if (player.hasPermission("builderteam.admin")) {

                                if (args.length == 2) {
                                    // nur name
                                builderSystem.createTask(args[1], "false", config.getInt("Teams.task.Create.x"), config.getInt("Teams.task.Create.x"));
                                player.sendMessage(t.t("builder_team.task.create.task_name_succses", playerUUID));
                            } else if (args.length >= 3) {
                                // name + description
                                builderSystem.createTask(args[1], getDescription(args, 4), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                                player.sendMessage(t.t("builder_team.task.create.task_name_desc_succses", playerUUID));
                            }

                        }
                        break;
                    case "remove":
                        if (args.length == 1) {
                            player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                            return false;
                        }
                        if (player.hasPermission("builderteam.admin")) {
                                 if (args.length == 2) {
                                if (isInt(args[1])) {

                                    int i = Integer.parseInt(args[1]);
                                    builderSystem.removeTask(i);
                                    player.sendMessage(t.t("builder_team.task.remove_succses", playerUUID));

                                } else player.sendMessage(t.t("builder_team.wrong_arguments", playerUUID));
                            }
                            break;
                        }
                    case "list":
                        for (Object o : builderSystem.getAllTasks()) {
                            HashMap m = (HashMap) o;
                            player.sendMessage(ChatColor.GRAY + "ID: " + ChatColor.WHITE + m.get("id").toString());
                            player.sendMessage(ChatColor.GRAY + "Name: " + ChatColor.WHITE + m.get("name").toString());
                            if (!Objects.equals(m.get("description").toString(), "false"))
                                player.sendMessage(ChatColor.GRAY + "Description: " + ChatColor.WHITE + m.get("description").toString());
                            player.sendMessage(ChatColor.GRAY + "Build Size: " + ChatColor.WHITE + m.get("build_width").toString() + " x " + m.get("build_length").toString());
                           player.sendMessage("Teams :");
                            for (Object id : (Object[]) m.get("world_ids")) {
                                HashMap idMap = (HashMap) id;
                                for (Object world_info : (Object[]) builderSystem.getWorld(Integer.parseInt((String) idMap.get("id")))) {
                                    HashMap world_infoMap = (HashMap) world_info;

                            for (Object team : (Object[]) idMap.get("team_manager_ids")) {
                                HashMap teamMap = (HashMap) team;



                                String teamname = world_infoMap.get("name").toString();
                                StringBuilder res = new StringBuilder();
                                res.append(ChatColor.GRAY +"");
                                res.append(teamname).append(""+ChatColor.WHITE + " , " + ChatColor.GRAY +"" );

                                res.toString();

                                player.sendMessage(res.toString());
                            }

                            player.sendMessage("World Names:");

//                                    for (Object world : (Object[]) world_infoMap.get("world_id")) {
//                                        HashMap worldMap = (HashMap) world;

                                        String worldname = world_infoMap.get("name").toString();
                                        StringBuilder res = new StringBuilder();
                                        res.append(ChatColor.GRAY + "");
                                        res.append(worldname).append("" + ChatColor.WHITE + " , " + ChatColor.GRAY + "");

                                        res.toString();

                                        player.sendMessage(res.toString());
//                                    }
                                }

                                player.sendMessage(ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "----------------------------------");


                            }
                            break;
                        }
                }
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


        List<String> teamlistofplayer = new ArrayList<>();
        for (Object o : builderSystem.getAllTeams()) {
            HashMap m = (HashMap) o;
            teamlistofplayer.add((String) m.get("name"));
        }
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);


        for (Player value : players) {
            playerNames.add(value.getName());
        }


        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("List");


            if (player.hasPermission("builderteam.admin")) {

                arguments.add("Create");
                arguments.add("Remove");

            }

            return arguments;

        } else

            switch (args[0].toLowerCase()) {
                case "create":
                    if (args.length == 2) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.task.create.tab_task_name", playerUUID) + ">");
                        return createname;
                    } else if (args.length == 3) {
                        List<String> createDescription = new ArrayList<>();
                        createDescription.add("<" + t.t("builder_team.task.create.tab_task_x_size", playerUUID) + ">");
                        return createDescription;
                    } else if (args.length == 4) {
                        List<String> createDescription = new ArrayList<>();
                        createDescription.add("<" + t.t("builder_team.task.create.tab_task_y_size", playerUUID) + ">");
                        return createDescription;
                    } else if (args.length == 5) {
                        List<String> createDescription = new ArrayList<>();
                        createDescription.add("<" + t.t("builder_team.create.tab_description", playerUUID) + ">");
                        return createDescription;
                    }
                    break;
                case "remove":
                    if (args.length == 2) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.tab_task_id", playerUUID) + ">");
                        return createname;


                    }
                   break;
            }
        return null;
    }

    private String getDescription(String[] args, int at) {
        FileConfiguration config = getConfig();
        StringBuilder res = new StringBuilder();

        int maxwords = config.getInt("Teams.create.MaxWorld");

        for (int i = at; i < args.length; i++) {
            if (i < maxwords + at) {
                res.append(args[i]).append(" ");
            } else
                break;
        }

        return res.toString();
    }

    private String getWorldIds(String[] args, int at) {
        FileConfiguration config = getConfig();
        StringBuilder res = new StringBuilder();
        BuilderSystem builderSystem = Main.getBuilderSystem();

        List<String> tasklistofplayer = new ArrayList<>();
        for (Object o : builderSystem.getAllWorlds()) {
            HashMap m = (HashMap) o;
            res.append(m.get("world_ids") + " , ");

            return res.toString();
        }


        return "false";
    }

    private static boolean isInt(String str)
    { try { Integer.parseInt(str); return true;
    } catch(NumberFormatException e){ return false; } }

}