package net.gigaclub.buildersystemplugin;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import net.gigaclub.buildersystem.BuilderSystem;
import net.gigaclub.buildersystemplugin.Commands.*;
import net.gigaclub.buildersystemplugin.Config.Config;
import net.gigaclub.buildersystemplugin.Config.ConfigTeams;
import net.gigaclub.buildersystemplugin.Config.OdooConfig;
import net.gigaclub.buildersystemplugin.listener.joinlistener;
import net.gigaclub.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {
    private static Main plugin;
    private static Translation translation;
    final public static String PREFIX = "[GC-BSP]: ";
    private static BuilderSystem builderSystem;


    @Override
    public void onEnable() {
		plugin = this;
        setPlugin(this);
        TapBSC tbsc = new TapBSC();
        BuildServerCreate bsc = new BuildServerCreate();
        setConfig();
        getCommand("bsc").setExecutor( bsc);
        getCommand("bsc").setTabCompleter( tbsc);

        getCommand("gcteams").setExecutor(new Team(plugin));
        getCommand("gcteams").setTabCompleter(new Team(plugin));

        getCommand("gctask").setExecutor(new Tasks());
        getCommand("gctask").setTabCompleter(new Tasks());

        Worlds projeckt = new Worlds();
        getCommand("gcprojekt").setExecutor(projeckt);
        getCommand("gcprojekt").setTabCompleter(projeckt);

        setConfig();
        FileConfiguration config = getConfig();

        CloudNetDriver.getInstance().getEventManager() .registerListener(projeckt);
        setTranslation(new Translation(
                config.getString("Base.Odoo.Host"),
                config.getString("Base.Odoo.Database"),
                config.getString("Base.Odoo.Username"),
                config.getString("Base.Odoo.Password")
        ));

        setBuilderSystem(new BuilderSystem(
                config.getString("Base.Odoo.Host"),
                config.getString("Base.Odoo.Database"),
                config.getString("Base.Odoo.Username"),
                config.getString("Base.Odoo.Password")
        ));

        registerTranslations();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new joinlistener(), this);

        CloudNetDriver.getInstance().getEventManager() .registerListener(bsc);
        setTranslation(new Translation(
                config.getString("Base.Odoo.Host"),
                config.getString("Base.Odoo.Database"),
                config.getString("Base.Odoo.Username"),
                config.getString("Base.Odoo.Password")
        ));

    }






    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Translation getTranslation() {
        return translation;
    }

    public static void setTranslation(Translation translation) {
        Main.translation = translation;
    }

    private void setConfig() {
        Config.createConfig();

        OdooConfig.setOdooConfig();
        ConfigTeams.setConfigTeams();

        Config.save();

        System.out.println(PREFIX + "Config files set.");
    }
    public static Main getPlugin() {
        return plugin;
    }

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;

    }
	
    public static BuilderSystem getBuilderSystem() {
        return Main.builderSystem;
    }

    public static void setBuilderSystem(BuilderSystem builderSystem) {
        Main.builderSystem = builderSystem;
    }

    public static void registerTranslations() {
        Main.translation.registerTranslations(Arrays.asList(
                "builder_team.to_less_arguments",
                "builder_team.wrong_arguments",
                "BuilderSystem.toomany_Arguments",

                "builder_team.create.only_name",
                "builder_team.Create.name_desc",

                "builder_team.edit.name",
                "builder_team.edit.description",

                "builder_team.leave",
                "builder_team.kick",
                "builder_team.add_manager",
                "builder_team.add",
                "builder_team.no_permission",

                "builder_team.create.tab_teamname",
                "builder_team.create.tab_description",

                "builder_team.edit.tab_teamname",
                "builder_team.edit.tab_description",

                "builder_team.task.remove_succses",
                "builder_team.task.create.tab_task_name",
                "builder_team.task.create.tab_task_x_size",
                "builder_team.task.create.tab_task_y_size",
                "builder_team.task.create.task_name_succses",
                "builder_team.task.create.task_name_desc_succses",
                "builder_team.task_id",

                "builder_team.tab_task_id",

                "builder_team.world.tab_world_name",
                "BuilderSystem.world.id_list",
                "BuilderSystem.world.name_list",
                "BuilderSystem.world.world_typ_list",
                "BuilderSystem.world.team_list",
                "BuilderSystem.world.user_list",
                "BuilderSystem.world.create_team_succses",
                "BuilderSystem.world.create_user_succses",
                "BuilderSystem.world.remove_succses",
                "BuilderSystem.world.addteam_succses",
                "BuilderSystem.world.adduser_succses",

                "BuilderSystem.countdown_begin"
//      status msg´s


        ));
    }

}
