package siozy.start.lightgenerator.handlers;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.util.service.managers.NBTManager;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import siozy.start.lightgenerator.Config.Config;
import siozy.start.lightgenerator.Config.DataConfig;
import siozy.start.lightgenerator.GeneratorRunnable.GeneratorRunnable;
import siozy.start.lightgenerator.GeneratorRunnable.RunnableManager;
import siozy.start.lightgenerator.LightGenerator;
import siozy.start.lightgenerator.generatorItem.GeneratorItem;
import siozy.start.lightgenerator.utils.HologramManager;

public class GeneratorHandlers implements Listener {

    @EventHandler
    @SuppressWarnings("all")
    private void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItemInHand();

        if (!GeneratorItem.isCustomItem(item)) {
            return;
        }

        ConfigurationSection settingsSection = Config.getSection("items." + NBTManager.getString(item, "generatorItem") + ".settings");
        int requiredPlayer = settingsSection.getInt("minOnlinePlayers");
        if (Bukkit.getOnlinePlayers().size() < requiredPlayer) {
            System.out.println("[debug] ok1");
            Config.sendMessage(player, "notEnoughPlayers", "required-%-" + requiredPlayer);
            e.setCancelled(true);
            return;
        }

        String id = Utils.getRKey((byte) 18);
        Utils.playersAction(p -> Config.sendMessage(p, "playerPlacedGenerator", "whoPlaced-%-" + player.getName(), "display-%-" + item.getItemMeta().getDisplayName(), "x-%-" + e.getBlock().getX(), "y-%-" + e.getBlock().getY(), "z-%-" + e.getBlock().getZ()));
        System.out.println("[start] ok");
        GeneratorRunnable runnable = new GeneratorRunnable(settingsSection, e.getBlockPlaced(), id);
        runnable.runTaskAsynchronously(LightGenerator.getInstance());
        DataConfig.put(e.getBlockPlaced().getLocation(), id);

        NBTManager.setString(e.getBlockPlaced(), "light", "generator");
    }

    @EventHandler
    private void onBreak(BlockBreakEvent e) {

        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (!NBTManager.hasTag(block, "light")) {
            return;
        }

        if (player.hasPermission("lightgenerator.admin")) {
            GeneratorRunnable runnable = RunnableManager.get(block);
            if (runnable != null) {
                RunnableManager.unload(runnable);
                DataConfig.remove(runnable.getId());
                return;
            }
        }
        e.setCancelled(true);
    }
}
