package net.gigaclub.buildersystemplugin.Commands;


import de.dytanic.cloudnet.common.document.gson.JsonDocument;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.Event;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceConnectNetworkEvent;
import de.dytanic.cloudnet.driver.service.*;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BuildServerCreate implements CommandExecutor {
    private int serviceId;
    private Player player;

    @EventListener
    public void handleServiceConnected(CloudServiceConnectNetworkEvent event) {
        ServiceInfoSnapshot serviceInfoSnapshot = event.getServiceInfo(); //The serviceInfoSnapshot with all important information from a service

        ServiceLifeCycle serviceLifeCycle = serviceInfoSnapshot.getLifeCycle();
        ServiceId serviceId = serviceInfoSnapshot.getServiceId();

        if (this.serviceId == event.getServiceInfo().getServiceId().getTaskServiceId()) {

            if (serviceLifeCycle == ServiceLifeCycle.RUNNING) {

                if (player != null) {

                    List<? extends ICloudPlayer> cloudPlayers = this.playerManager.getOnlinePlayers(this.player.getName());
                    if (!cloudPlayers.isEmpty()) { ICloudPlayer entry = cloudPlayers.get(0);
                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.playerManager.getPlayerExecutor(entry).connect(event.getServiceInfo().getServiceId().getTaskName()+"-"+this.serviceId);


                        }
                    }
                }
            }
        }
    }

    private final IPlayerManager playerManager = CloudNetDriver.getInstance().getServicesRegistry()
            .getFirstService(IPlayerManager.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        Translation t = Main.getTranslation();

        if (sender instanceof Player) {
            this.player = (Player) sender;
            if (this.player.hasPermission("BuilderSystem.CreateServer")) {
                if (args.length == 0) {
                    this.player.sendMessage(t.t("bsc.Command.noArgument", playerUUID));
                } else if (args.length == 1) {
                    this.player.sendMessage(t.t("bsc.Command.CreateServer", playerUUID));
                    this.player.sendMessage(t.t("bsc.Command.Teleport", playerUUID));
                    ServiceInfoSnapshot serviceInfoSnapshot = ServiceConfiguration.builder()
                            .task(args[0])
                            .node("Node-1")
                            .autoDeleteOnStop(false)
                            .staticService(true)
                            .templates(new ServiceTemplate("Builder", "Normal", "local"))
                            .maxHeapMemory(1025)
                            .environment(ServiceEnvironmentType.MINECRAFT_SERVER)
                            .build()
                            .createNewService();

                    if (serviceInfoSnapshot != null) {
                        serviceInfoSnapshot.provider().start();
                        this.serviceId = serviceInfoSnapshot.getServiceId().getTaskServiceId();
                    }

                }
                if (args.length == 2) {
/*                    if (!args[1].equals("Normal") || !args[1].equals("Void") || !args[1].equals("Flat")) {
                        this.player.sendMessage(t.t("bsc.Command.WrongWorldtyp", playerUUID));
                        return false;
                    } else
                    {*/
                    this.player.sendMessage(t.t("bsc.Command.CreateServer", playerUUID));
                    this.player.sendMessage(t.t("bsc.Command.Teleport", playerUUID));
                    ServiceInfoSnapshot serviceInfoSnapshot = ServiceConfiguration.builder()
                            .task(args[0])
                            .node("Node-1")
                            .autoDeleteOnStop(false)
                            .staticService(true)
                            .templates(new ServiceTemplate("Builder", args[1], "local"))
                            .groups("Builder")
                            .maxHeapMemory(1025)
                            .environment(ServiceEnvironmentType.MINECRAFT_SERVER)
                            .build()
                            .createNewService();


                    if (serviceInfoSnapshot != null) {
                        serviceInfoSnapshot.provider().start();
                        this.serviceId = serviceInfoSnapshot.getServiceId().getTaskServiceId();
                    }
                }


            }
            if (args.length >= 3) {
                this.player.sendMessage(t.t("bsc.Command.toomanyArguments", playerUUID));
                return false;

            }


        }
    //}

        return true;

    }
}
