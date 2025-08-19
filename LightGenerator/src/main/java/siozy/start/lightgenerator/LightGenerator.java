package siozy.start.lightgenerator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.commands.LunaExecutor;
import org.novasparkle.lunaspring.LunaPlugin;
import siozy.start.lightgenerator.Config.Config;
import siozy.start.lightgenerator.Config.DataConfig;
import siozy.start.lightgenerator.GeneratorRunnable.RunnableManager;
import siozy.start.lightgenerator.handlers.GeneratorHandlers;

public final class LightGenerator extends LunaPlugin {
    private static LightGenerator instance;

    @Override
    public void onEnable() {
        instance = this;

        super.onEnable();
        LunaExecutor.initialize(this);
        Config.init();
        registerListeners(new GeneratorHandlers());
        this.loadFile("generatorsLocations.yml");
        DataConfig.init();

        Bukkit.getScheduler().runTask(this, () -> {
            ConfigurationSection locationSection = DataConfig.getSection();

            if (locationSection != null) {
                for (String key : locationSection.getKeys(false)) {
                    Location location = DataConfig.getLocation(key);

                    if (location == null) continue;
                    location.getBlock().setType(Material.AIR);
                }
                DataConfig.clear();
            }
        });
    }

    @Override
    public void onDisable() {
        RunnableManager.clear();
        super.onDisable();
    }

    public static LightGenerator getInstance() {
        return instance;
    }
}
