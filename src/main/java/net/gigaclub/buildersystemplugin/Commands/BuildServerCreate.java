package net.gigaclub.buildersystemplugin.Commands;


import de.dytanic.cloudnet.common.document.gson.JsonDocument;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.Event;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceConnectNetworkEvent;
import de.dytanic.cloudnet.driver.service.*;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

                if(player !=null){

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

    private final IPlayerManager playerManager = CloudNetDriver.getInstance().getServicesRegistry()
            .getFirstService(IPlayerManager.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,String[] args) {

        if(sender instanceof Player) {
             this.player = (Player) sender;
            if(this.player.hasPermission("BuilderSystem.CreateServer")) {
                if(args.length ==0){
                    this.player.sendMessage("...");
                }else if (args.length == 1){
                    this.player.sendMessage("server wirt erstellt");
                    this.player.sendMessage("du wirst geich teleportirt");
                    ServiceInfoSnapshot serviceInfoSnapshot = ServiceConfiguration.builder()
                            .task(args[0])
                            .node("Node-1")
                            .autoDeleteOnStop(false)
                            .staticService(true)
                            .templates(new ServiceTemplate("Builder", "default", "local"))
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
                }





        return true;
    }
}
