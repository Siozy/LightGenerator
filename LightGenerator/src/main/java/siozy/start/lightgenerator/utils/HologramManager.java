package siozy.start.lightgenerator.utils;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.novasparkle.lunaspring.API.util.service.managers.ColorManager;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import siozy.start.lightgenerator.Config.Config;

import java.util.Objects;

public class HologramManager {
    private final String id = Utils.getRKey((byte) 18);
    private final Location location;
    private final Hologram hologram;

    public HologramManager(Location location) {
        this.location = location.clone().add(0.5, Config.getDouble("settings.hologram.height"), 0.5);
        this.hologram = DHAPI.createHologram(id, this.location);
    }

    public void updateHologram(int leftTime) {
        if (!hologram.getPages().isEmpty()) {
            hologram.removePage(0);
            hologram.addPage();
        }

        for (String string : Config.getStringList("settings.hologram.lines")) {
            string = string.replace("[leftTime]", String.valueOf(leftTime));
            if (string.startsWith("Material.")) DHAPI.addHologramLine(hologram,
                    Objects.requireNonNull(Material.getMaterial(string.replace("Material.", ""))));
            else DHAPI.addHologramLine(hologram, ColorManager.color(string));
        }
    }

    public void removeHologram() {
        if (DHAPI.getHologram(this.id) != null) DHAPI.removeHologram(this.id);
    }
}
