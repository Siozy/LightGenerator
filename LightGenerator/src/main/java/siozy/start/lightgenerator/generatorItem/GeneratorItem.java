package siozy.start.lightgenerator.generatorItem;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.menus.items.NonMenuItem;
import org.novasparkle.lunaspring.API.util.service.managers.NBTManager;
import siozy.start.lightgenerator.Config.Config;

public class GeneratorItem extends NonMenuItem {
    private final String id;
    public GeneratorItem(ConfigurationSection section, int amount, String id) {
        super(section);
        this.setAmount(Math.max(amount, 1));
        this.id = id;
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack itemStack = super.getDefaultStack();
        NBTManager.setString(itemStack, "generatorItem", id);
        return itemStack;
    }

    public static GeneratorItem createItem(int amount, String id) {
        return new GeneratorItem(Config.getSection("items." + id), amount, id);
    }

    public static boolean isCustomItem(ItemStack itemStack) {
        return itemStack != null && !itemStack.getType().isAir() && NBTManager.hasTag(itemStack, "generatorItem");
    }
}
