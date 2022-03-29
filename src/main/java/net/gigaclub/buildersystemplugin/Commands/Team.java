package net.gigaclub.buildersystemplugin.Commands;

import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.CooldownManager;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.Navigator;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static net.gigaclub.buildersystemplugin.Config.Config.getConfig;
import static org.bukkit.Bukkit.getLogger;

public class Team implements CommandExecutor, TabCompleter {

    private final CooldownManager cooldownManager = new CooldownManager();

    private final Plugin plugin;

    public Team(Plugin plugin) {
        this.plugin = plugin;
    }




    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();




        if (sender instanceof Player) {

            if (args.length == 0){
                player.sendMessage("test");
                Navigator navigator = new Navigator();
                navigator.openGui(player);
            }
            if (args.length >= 1) {
                if (args.length == 1) {
                    player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                    return false;
                }
                switch (args[0].toLowerCase()) {



                    case "create":
                        if (args.length == 2) {

                            int status = builderSystem.createTeam(playerUUID, args[1]);
                            switch (status) {
                                case 0 ->
                                        //                  z.b  Du hast die gruppe args[1] erstellt
                                        player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.create.only_name", playerUUID));
                                case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.create.team_could_not_be_created", playerUUID));
                                case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_with_name_already_exists", playerUUID));
                                case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.player_can_only_create_one_team", playerUUID));
                                case 4 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                            }


                        } else if (args.length >= 3) {

                            //                     z.b du hast die gruppe args[1] mit der beschreibung ...
                            int status = builderSystem.createTeam(playerUUID, args[1], getDescription(args, 2));
                            switch (status) {
                                case 0 ->
                                        //                      z.b du hast die gruppe args[1] mit der beschreibung ...
                                        player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.create.name_desc", playerUUID, Arrays.asList(getDescription(args, 2))));
                                case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.create.team_could_not_be_created", playerUUID));
                                case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_with_name_already_exists", playerUUID));
                                case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.player_can_only_create_one_team", playerUUID));
                                case 4 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                            }

                        }
                        break;

                    case "edit":
                        if (args.length == 2) {
                            player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.to_less_arguments", playerUUID));
                            return false;
                        } else {
                            switch (args[0].toLowerCase()) {
                                case "name" -> {
                                    if (args.length == 3) {
                                        player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.to_less_arguments", playerUUID));
                                        return false;
                                    } else if (args.length == 4) {
                                        int status = builderSystem.editTeam(playerUUID, args[2], args[3]);
                                        switch (status) {
                                            case 0 -> player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.edit.name", playerUUID));
                                            case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_manager_of_this_team", playerUUID));
                                            case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_member_of_this_team", playerUUID));
                                            case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_does_not_exist", playerUUID));
                                            case 4 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_has_no_team", playerUUID));
                                            case 5 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                                        }

                                    }
                                }
                                case "description" -> {
                                    String team = builderSystem.getTeam(args[2]).getString("name");
                                    int status = builderSystem.editTeam(playerUUID, args[2], team, getDescription(args, 2));
                                    switch (status) {
                                        case 0 -> player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.edit.description", playerUUID));
                                        case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_manager_of_this_team", playerUUID));
                                        case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_member_of_this_team", playerUUID));
                                        case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_does_not_exist", playerUUID));
                                        case 4 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_has_no_team", playerUUID));
                                        case 5 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                                    }

                                }
                            }
                            break;
                        }
                    case "leave":
                        int status = builderSystem.leaveTeam(playerUUID);
                        switch (status) {
                            case 0 -> player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.leave_Team_Success", playerUUID));
                            case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.User_has_no_team", playerUUID));
                            case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                        }
                        break;
                    case "kick":
                        if (args.length == 2) {
                            String p = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                            int status2 = builderSystem.kickMember(playerUUID, p);
                            switch (status2) {
                                case 0 -> player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.kick_user_success", playerUUID));
                                case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_user_of_this_team", playerUUID));
                                case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_manager", playerUUID));
                                case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_does_not_exist", playerUUID));
                                case 4 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                            }
                        }
                        break;

                    case "addmanager":
                        String p = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        int status2 = builderSystem.promoteMember(playerUUID, p);
                        switch (status2) {
                            case 0 -> player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.promoteMember_success", playerUUID));
                            case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_already_manager_of_this_team", playerUUID));
                            case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_to_promote_is_not_in_this_team", playerUUID));
                            case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_to_promote_is_not_a_team", playerUUID));
                            case 4 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_manager", playerUUID));
                            case 5 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_does_not_exist", playerUUID));
                            case 6 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                        }
                        break;

                    case "removemanager":
                        String p5 = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        int status4 = builderSystem.demoteMember(playerUUID, p5);
                        switch (status4) {
                            case 0 -> player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.demoteMember_success", playerUUID));
                            case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_a_manager_of_this_team", playerUUID));
                            case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_to_demoteMember_is_not_in_this_team", playerUUID));
                            case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_to_demoteMember_is_not_a_team", playerUUID));
                            case 4 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_manager", playerUUID));
                            case 5 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_does_not_exist", playerUUID));
                            case 6 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                        }

                        break;

                    case "add":
                        String p2 = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        if (player.hasPermission("builderteam.admin")) {
                            int status3 = builderSystem.addMember(playerUUID, p2);
                            switch (status3) {
                                case 0 -> player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.add_success", playerUUID));
                                case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_user_of_this_team", playerUUID));
                                case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_manager", playerUUID));
                                case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_does_not_exist", playerUUID));
                                case 4 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.other_error", playerUUID));
                            }
                            break;
                        }
                        break;
                    case "invite":


                        //msg after invite success
                        String empengeruuid = Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId().toString();
                        int timeLeft = cooldownManager.getCooldown(player.getUniqueId());
                        if (timeLeft == 0) {

                            if (Bukkit.getOfflinePlayer(empengeruuid) == null) {
                                player.sendMessage(ChatColor.RED.toString() + args[2] + t.t("builder_team.is_not_a_player", playerUUID));
                            } else {
                                int status3 = builderSystem.inviteMember(playerUUID, empengeruuid);
                                switch (status3) {
                                    //  case 0 -> player.sendMessage(ChatColor.GREEN.toString() + t.t("builder_team.invite_success", playerUUID));
                                    case 1 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_already_manager_of_this_team", playerUUID));
                                    case 2 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.user_is_not_manager", playerUUID));
                                    case 3 -> player.sendMessage(ChatColor.RED.toString() + t.t("builder_team.team_does_not_exist", playerUUID));
                                }
                                Player empfängername = Bukkit.getPlayer(args[1]);
                                List<String> teamn = new ArrayList<>();

                                JSONObject getTeam = builderSystem.getTeamNameByMember(playerUUID);


                                String teamname = getTeam.getString("name");

                                Object o = builderSystem.getTeamNameByMember(playerUUID);


                                player.sendMessage(ChatColor.AQUA + teamname + " " + "builder_team.invite.sender" + " " + ChatColor.GREEN + empfängername.getName());
                                empfängername.sendMessage(ChatColor.AQUA + "builder_team.invite.receiver" + " " + ChatColor.GREEN + teamname);

                                cooldownManager.setCooldown(player.getUniqueId(), getConfig().getInt("Teams.invite.Timersek"));
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        int timeLeft = cooldownManager.getCooldown(player.getUniqueId());
                                        cooldownManager.setCooldown(player.getUniqueId(), --timeLeft);
                                        if (timeLeft == 0) {
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(this.plugin, 20, 20);

                            }

                        } else {
                            //Hasn't expired yet, shows how many seconds left until it does
                            player.sendMessage(ChatColor.RED.toString() + timeLeft + " " + t.t("builder_team.TimerTimeLeft", playerUUID));
                        }
                        break;
                    case "accept":

                        builderSystem.acceptRequest(playerUUID, args[1]);
                        break;
                    case "deny":
                        builderSystem.denyRequest(playerUUID, args[1]);
                }
            }


        } else
            getLogger().info("You´r not a Player  XD");

        return false;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();



        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player value : players) {
            playerNames.add(value.getName());
        }

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("Create");
            arguments.add("Edit");
            arguments.add("Invite");
            arguments.add("Leave");
            arguments.add("Kick");
            arguments.add("addManager");
            arguments.add("removeManager");
            arguments.add("accept");
            arguments.add("deny");

            if (player.hasPermission("builderteam.admin")) {

                arguments.add("add");

            }
            return arguments;

        } else
            switch (args[0].toLowerCase()) {
                case "create":
                        if (args.length == 2) {
                        List<String> createname = new ArrayList<>();
                        createname.add("<" + t.t("builder_team.create.tab_teamname", playerUUID) + ">");
                        return createname;
                    } else if (args.length == 3) {
                        List<String> createDescription = new ArrayList<>();
                        createDescription.add("<" + t.t("builder_team.create.tab_description", playerUUID) + ">");
                        return createDescription;
                    }
                    break;


                case "edit":
                     if (args.length == 2) {
                        List<String> arguments = new ArrayList<>();
                        arguments.add("Name");
                        arguments.add("Description");
                        return arguments;
                    }
                    if (args.length >= 3) {


                        switch (args[1].toLowerCase()) {
                            case "name":
                                if (args.length == 3) {

                                    return Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).getString("name"));
                                } else if (args.length == 4) {
                                    List<String> createnewname = new ArrayList<>();
                                    createnewname.add("<" + t.t("builder_team.edit.tab_teamname", playerUUID) + ">");
                                    return createnewname;
                                }
                                break;
                            case "description":
                                if (args.length == 3) {
                                    return Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).getString("name"));
                                } else if (args.length == 4) {
                                    List<String> description = new ArrayList<>();
                                    description.add("<" + t.t("builder_team.edit.tab_description", playerUUID) + ">");
                                    return description;
                                }
                                break;

                        }
                    }


                case "leave":
                    if (args.length == 1) {
                        player.sendMessage(t.t("builder_team.to_less_arguments", playerUUID));
                        return null;
                    } else if (args.length == 2) {
                        return Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).getString("name"));
                    }
                    break;


                case "kick","addManager":

                    List<String> team = Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).getString("name"));
                    JSONObject teamObject = builderSystem.getTeam(team.get(0));
                    JSONArray userIds = teamObject.getJSONArray("user_ids");
                    ArrayList<String> names = new ArrayList<>();
                    for (int i = 0; i < userIds.length(); i++) {
                        JSONObject user = userIds.getJSONObject(i);
                        String uuid = user.getString("mc_uuid");
                        String playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                        names.add(playerName);
                    }
                    return names;

                case "removemanager":
                    List<String> team2 = Collections.singletonList(builderSystem.getTeamNameByMember(playerUUID).getString("name"));
                    JSONObject teamObject2 = builderSystem.getTeam(team2.get(0));
                    JSONArray userIds2 = teamObject2.getJSONArray("manager_ids");
                    ArrayList<String> names2 = new ArrayList<>();
                    for (int i = 0; i < userIds2.length(); i++) {
                        JSONObject user = userIds2.getJSONObject(i);
                        String uuid = user.getString("mc_uuid");
                        String playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                        names2.add(playerName);
                    }
                    return names2;


                case "add":
                    if (args.length == 2) {
                        return playerNames;
                    } else if (args.length == 3) {
                        List<String> teamlistofplayer = new ArrayList<>();
                        JSONArray teamlist = builderSystem.getAllTeams();
                        for (int i = 0; i < teamlist.length(); i++) {
                            JSONObject team3 = teamlist.getJSONObject(i);
                            teamlistofplayer.add(team3.getString("name"));
                        }

                        return teamlistofplayer;
                    }
                    break;


                case "invite":
                    if (args.length == 2) {
                        return playerNames;
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

}



