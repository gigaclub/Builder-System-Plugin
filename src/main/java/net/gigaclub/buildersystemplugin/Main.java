package net.gigaclub.buildersystemplugin;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import net.gigaclub.buildersystemplugin.Commands.BuildServerCreate;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        BuildServerCreate bsc = new BuildServerCreate();

        getCommand("bsc").setExecutor(bsc);


        CloudNetDriver.getInstance().getEventManager() .registerListener(bsc);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
