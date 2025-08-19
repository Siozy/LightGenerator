package siozy.start.lightgenerator.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;

public class Particles {
    private final Particle type;
    private final int count;
    private final double additiveRadius;
    private final double additiveHeight;
    private final int rgb;
    private Location center;


    public Particles(ConfigurationSection particleSection) {
        this.type = Particle.valueOf(particleSection.getString("type"));
        this.count = particleSection.getInt("count");
        this.additiveHeight = particleSection.getDouble("additiveHeight");
        this.additiveRadius = particleSection.getDouble("additiveRadius");
        this.rgb = particleSection.getInt("rgb");
    }

    public void create(Location center, double additiveRadius, Particle particle) {
    }
}
