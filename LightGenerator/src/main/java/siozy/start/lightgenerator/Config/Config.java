package siozy.start.lightgenerator.Config;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.configuration.IConfig;
import siozy.start.lightgenerator.LightGenerator;

import java.util.List;

public class Config {
    private static IConfig config;

    public static void init() {
        LightGenerator.getInstance().saveDefaultConfig();
        config = new IConfig(LightGenerator.getInstance());
    }

    public static void reload() {
        config.reload(LightGenerator.getInstance());
    }

    public static void sendMessage(CommandSender sender, String id, String...rpl) {
        config.sendMessage(sender, id, rpl);
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static String getString(String path) {
        return config.getString(path);
    }

    public static boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public static double getDouble(String path) {
        return config.getDouble(path);
    }

    public static List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public static ConfigurationSection getSection(String path) {
        return config.getSection(path);
    }
}
