package net.gigaclub.buildersystemplugin.listener;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceConnectNetworkEvent;
import de.dytanic.cloudnet.driver.service.*;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
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
                        Player player2 = (Player)player ;
                        @NotNull BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                        taskID = scheduler.runTaskTimer(Main.getPlugin(), new Runnable() {
                            int countdown = 10;

                            public void run() {
                                player2.sendMessage(t.t("BuilderSystem.countdown_begin", playerUUID));
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
                    }}}}}

    private final IPlayerManager playerManager = CloudNetDriver.getInstance().getServicesRegistry()
            .getFirstService(IPlayerManager.class);

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();
        BuilderSystem builderSystem = Main.getBuilderSystem();
        FileConfiguration config = getConfig();

        if (config.getBoolean("server.server_autostart")) {

            Object team = builderSystem.getTeamNameByMember(playerUUID);

            HashMap teamMap = (HashMap) team;
            if (Objects.equals(teamMap.get("name").toString(), "false")){ System.out.println("no name"); return;}

            String team_name = teamMap.get("name").toString();
            System.out.println(1+" "+team_name);

                for (Object world_id : (Object[]) teamMap.get("world_ids")) {
                    HashMap world_idMap = (HashMap) world_id;
                    System.out.println(1.1);
                    int word_id = Integer.parseInt(world_idMap.get("id").toString());
                    System.out.println("ID: "+word_id);

                    for (Object world_info : (Object[]) builderSystem.getWorld(word_id)) {
                        System.out.println(1.2);
                        HashMap world_infoMap = (HashMap) world_info;
                        String world_name = world_infoMap.get("name").toString();
                        Integer task_id = Integer.parseInt(world_infoMap.get("task_id").toString());

                        for (Object task_o : (Object[]) builderSystem.getTask(task_id)) {
                            System.out.println(1.3);
                            HashMap task_m = (HashMap) task_o;
                            String task_name = task_m.get("name").toString();

                            String worlds_typ = world_idMap.get("world_type").toString();
                            //  world_name, task_name, task_id, worlds_typ, word_id, team_name
                            System.out.println(world_name+" "+task_name+" "+task_id+" "+worlds_typ+" "+word_id+" "+team_name);
                            System.out.println(2);
                            player.sendMessage(t.t("bsc.Command.CreateServer", playerUUID));
                            player.sendMessage(t.t("bsc.Command.Teleport", playerUUID));
                            ServiceInfoSnapshot serviceInfoSnapshot = ServiceConfiguration.builder()
                                    .task(team_name + "_" + task_name+"_"+task_id +"_" + word_id)
                                    .node("Node-1")
                                    .autoDeleteOnStop(true)
                                    .staticService(false)
                                    .templates(new ServiceTemplate("Builder", worlds_typ, "local"))
                                    .groups("Builder")
                                    .maxHeapMemory(1525)
                                    .environment(ServiceEnvironmentType.MINECRAFT_SERVER)
                                    .build()
                                    .createNewService();

                            System.out.println("nach server daten");
                            if (serviceInfoSnapshot != null) {
                                System.out.println(3);
                                serviceInfoSnapshot.provider().start();
                                serviceId = serviceInfoSnapshot.getServiceId().getTaskServiceId();
                            }

                        }

                    }
                }

        }
        }
    }

