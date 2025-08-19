package siozy.start.lightgenerator.GeneratorRunnable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaspring.API.util.utilities.LunaMath;
import org.novasparkle.lunaspring.API.util.utilities.LunaTask;
import org.satellite.progiple.satejewels.SateJewels;
import siozy.start.lightgenerator.Config.Config;
import siozy.start.lightgenerator.Config.DataConfig;
import siozy.start.lightgenerator.LightGenerator;
import siozy.start.lightgenerator.utils.HologramManager;

import java.util.Collection;

public class GeneratorRunnable extends LunaTask {
    private final int lifeTime;

    private int leftTime;
    private final int delay;
    private final int amount;
    private final int radius;

    private final Block block;
    private final boolean divideJewels;
    private final HologramManager hologramManager;

    private final String id;

    public GeneratorRunnable(ConfigurationSection settingsSection, Block block, String id) {
        super(0);
        this.lifeTime = settingsSection.getInt("lifeTime");
        this.delay = settingsSection.getInt("executeCooldown");
        this.radius = settingsSection.getInt("executeRadius");
        this.block = block;
        this.amount = settingsSection.getInt("giveJewels");
        this.divideJewels = settingsSection.getBoolean("divideJewels");
        this.hologramManager = new HologramManager(block.getLocation());
        this.id = id;
    }

    @Override
    @SuppressWarnings("all")
    public void start() {
        this.leftTime = this.lifeTime;

        RunnableManager.load(this);
        while (leftTime > 0 && !block.getType().isAir()) {
            if (!this.isActive()) {
                System.out.println("[debug] not active");
                return;
            }

            leftTime--;
            this.hologramManager.updateHologram(leftTime);

            try {
                Thread.sleep(this.delay * 1000L);

                Bukkit.getScheduler().runTask(LightGenerator.getInstance(), () -> {
                    Collection<Player> nearbyPlayers = block.getLocation().getNearbyPlayers(this.radius);
                    int finalAmount = this.divideJewels ? Math.max(1, (int) LunaMath.round(this.amount / nearbyPlayers.size(), 0)) : this.amount;
                    for (Player nearbyPlayer : nearbyPlayers) {
                        SateJewels.getINSTANCE().getSjapi().giveJewels(nearbyPlayer, finalAmount);
                        Config.sendMessage(nearbyPlayer, "onGetJewels", "amount-%-" + finalAmount, "playersCount-%-" + nearbyPlayers.size(), "leftTime-%-" + leftTime);
                    }

                });
            } catch (InterruptedException e) {
                System.out.println("[debug] catch");
                throw new RuntimeException(e);
            }
        }
        block.setType(Material.AIR);
        RunnableManager.unload(this);
        hologramManager.removeHologram();
        DataConfig.remove(id);
    }

    public Block getBlock() {
        return block;
    }

    public String getId() {
        return id;
    }

}
