package net.gigaclub.buildersystemplugin;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import net.gigaclub.buildersystemplugin.Commands.BuildServerCreate;
import net.gigaclub.buildersystemplugin.Commands.TapBSC;
import net.gigaclub.buildersystemplugin.Config.Config;
import net.gigaclub.buildersystemplugin.Config.OdooConfig;
import net.gigaclub.translation.Translation;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;

public final class Main extends JavaPlugin {
    private static Main plugin;
    private static Translation translation;
    final public static String PREFIX = "[GC]: ";


    @Override
    public void onEnable() {
        setPlugin(this);
        TapBSC tbsc = new TapBSC();
        BuildServerCreate bsc = new BuildServerCreate();
        setConfig();
        FileConfiguration config = getConfig();
        getCommand("bsc").setExecutor( bsc);
        getCommand("bsc").setTabCompleter( tbsc);

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

        Config.save();

        System.out.println(PREFIX + "Config files set.");
    }
    public static Main getPlugin() {
        return plugin;
    }

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;

    }
}
