package net.gigaclub.buildersystemplugin.listener;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceConnectNetworkEvent;
import de.dytanic.cloudnet.driver.service.*;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Andere.InterfaceAPI.ItemBuilder;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import static net.gigaclub.buildersystemplugin.Config.Config.getConfig;

public class joinlistener implements Listener {
    private int serviceId;
    private Player player;
    private @NotNull BukkitTask taskID;

    @EventListener
    public void handleServiceConnected(CloudServiceConnectNetworkEvent event) {
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();

        ServiceInfoSnapshot serviceInfoSnapshot = event.getServiceInfo(); //The serviceInfoSnapshot with all important information from a service

        ServiceLifeCycle serviceLifeCycle = serviceInfoSnapshot.getLifeCycle();
        ServiceId serviceId = serviceInfoSnapshot.getServiceId();

        if (this.serviceId == serviceId.getTaskServiceId()) {

            if (serviceLifeCycle == ServiceLifeCycle.RUNNING) {

                if (player != null) {
                    System.out.println(4);
                    List<? extends ICloudPlayer> cloudPlayers = this.playerManager.getOnlinePlayers(this.player.getName());
                    if (!cloudPlayers.isEmpty()) {
                        ICloudPlayer entry = cloudPlayers.get(0);
                        IPlayerManager playerManager = this.playerManager;
                        int serviceId1 = this.serviceId;
                        Player player2 = player;
                        @NotNull BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                        taskID = scheduler.runTaskTimer(Main.getPlugin(), new Runnable() {
                            int countdown = 10;

                            public void run() {
                                player2.sendMessage(t.t("BuilderSystem.countdown_begin", player));
                                if (countdown > 0) {
                                    player2.sendMessage(String.valueOf(countdown));
                                } else {
                                    playerManager.getPlayerExecutor(entry).connect(event.getServiceInfo().getServiceId().getTaskName() + "-" + serviceId1);
                                    scheduler.cancelTask(taskID.getTaskId());
                                    return;
                                }
                                countdown--;
                            }
                        }, 0, 20);
                    }
                }
            }
        }
    }

    private final IPlayerManager playerManager = CloudNetDriver.getInstance().getServicesRegistry()
            .getFirstService(IPlayerManager.class);

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();
        FileConfiguration config = getConfig();


        if (config.getBoolean("server.server_autostart")) {
            String playerName = player.getName();
            JSONArray worlds = builderSystem.getAllWorlds();
            if (worlds.length() == 0) return;

            for (int i = 0; i < worlds.length(); i++) {
                JSONObject world = worlds.getJSONObject(i);
                JSONArray users = world.getJSONArray("user_ids");
                for (int j = 0; j < users.length(); j++) {
                    JSONObject user = users.getJSONObject(j);
                    String userUUID = user.getString("mc_uuid");
                    if (Objects.equals(userUUID, playerUUID)) {
                        startServer(worlds, i, builderSystem, playerUUID, playerName, t, player);
                    }
                }
            }


            try {
                JSONArray team = builderSystem.getTeamsByMember(playerUUID);

            } catch (Exception e) {
                return;
            }
            JSONArray teams = builderSystem.getTeamsByMember(playerUUID);

/*            for (int j = 0; j < teams.length(); j++) {
                JSONArray teamworlds = teams.getJSONArray(j);
                JSONArray team = teamworlds.getJSONArray("")

                JSONArray teamWorlds = worldid.getJSONObject("world_ids");
                JSONArray teamWorldManagers = team.getJSONArray("world_manager_ids");
                for (int i = 0; i < teamWorlds.length(); i++) {
                    startServer(teamWorlds, i, builderSystem, playerUUID, j, t, player);
                }*/

            //           }
        }
    }


    private void startServer(JSONArray teamWorlds, int i, BuilderSystem builderSystem, String playerUUID, String team_name, Translation t, Player player) {
        
        JSONObject world_data = teamWorlds.getJSONObject(i);
        int world_id = 0;
        try {
            world_id = world_data.getInt("id");
        } catch (Exception e) {
            world_id = world_data.getInt("world_id");
        }
        JSONObject world = builderSystem.getWorld(world_id);

        String world_name = world.getString("name");
        int task_id = world.getInt("task_id");
        JSONObject task = builderSystem.getTask(task_id);

        String task_name = task.getString("name");

        String worlds_typ = world.getString("world_type");
        //  world_name, task_name, task_id, worlds_typ, word_id, team_name
        player.sendMessage(t.t("bsc.Command.CreateServer", player));
        player.sendMessage(t.t("bsc.Command.Teleport", player));
        ServiceInfoSnapshot serviceInfoSnapshot = ServiceConfiguration.builder()
                .task(team_name + "_" + task_name + "_" + task_id + "_" + world_id)
                .node("Node-1")
                .autoDeleteOnStop(true)
                .staticService(false)
                .templates(new ServiceTemplate("Builder", worlds_typ, "local"),new ServiceTemplate("Builder","Plugins","local"))
                .groups("Builder")
                .maxHeapMemory(1525)
                .environment(ServiceEnvironmentType.MINECRAFT_SERVER)
                .build()
                .createNewService();

        if (serviceInfoSnapshot != null) {
            serviceInfoSnapshot.provider().start();
            serviceId = serviceInfoSnapshot.getServiceId().getTaskServiceId();
        }
    }

    ItemStack GuiOpener = new ItemBuilder(Material.NETHER_STAR).setDisplayName((ChatColor.BLUE + "BuilderGui")).setLore((ChatColor.AQUA + "Open The BuilderGui")).setGui(true).addIdentifier("Gui_Opener").build();

    @EventHandler
    public void joinListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.getInventory().setItem(0, GuiOpener);

        }










}

