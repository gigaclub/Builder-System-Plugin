package net.gigaclub.buildersystemplugin.Andere;

import de.dytanic.cloudnet.driver.service.ServiceConfiguration;
import de.dytanic.cloudnet.driver.service.ServiceEnvironmentType;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.driver.service.ServiceTemplate;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Main;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;

import static net.gigaclub.buildersystemplugin.Config.Config.getConfig;

public class CreateServer {
    BuilderSystem builderSystem = Main.getBuilderSystem();



    private void startServer1(int world_id, String team_name, Player player) {
        Translation t = Main.getTranslation();
        String playerUUID = player.getUniqueId().toString();
        JSONObject world = builderSystem.getWorld(world_id);



        String world_name = world.getString("name");
        int task_id = world.getInt("task_id");
        JSONObject task = builderSystem.getTask(task_id);

        String task_name = task.getString("name");

        String worlds_typ = world.getString("world_type");
        //  world_name, task_name, task_id, worlds_typ, word_id, team_name
        player.sendMessage(t.t("bsc.Command.CreateServer", playerUUID));
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
        }
    }



