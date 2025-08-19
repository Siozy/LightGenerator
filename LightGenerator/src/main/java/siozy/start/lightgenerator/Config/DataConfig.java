package siozy.start.lightgenerator.Config;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.configuration.Configuration;
import org.novasparkle.lunaspring.API.configuration.IConfig;
import siozy.start.lightgenerator.LightGenerator;

import java.util.UUID;

public class DataConfig {
    private static Configuration config;

    public static void init() {
        config = new Configuration(LightGenerator.getInstance().getDataFolder(), "generatorsLocations");
    }

    public static void reload() {
        config.reload();
    }

    public static void put(Location location, String id) {
        config.setLocation("locations." + id, location, true, true);
        config.save();
    }

    public static void remove(String id) {
        config.set("locations." + id, null);
        config.save();
    }

    public static Location getLocation(String id) {
        return config.getLocation("locations." + id);
    }

    public static ConfigurationSection getSection() {
        return config.getSection("locations");
    }

    public static void clear() {
        config.set("locations", null);
        config.save();
    }
}
