package net.gigaclub.buildersystemplugin.Config;

import org.bukkit.configuration.file.FileConfiguration;

public class OdooConfig {

    public static void setOdooConfig() {

        FileConfiguration config = Config.getConfig();

        config.addDefault("Base.Odoo.Host", "http://localhost:14069");
        config.addDefault("Base.Odoo.Database", "devel");
        config.addDefault("Base.Odoo.Username", "admin");
        config.addDefault("Base.Odoo.Password", "admin");

        Config.save();

    }

}