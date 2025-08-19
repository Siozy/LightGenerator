package siozy.start.lightgenerator.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaspring.API.commands.LunaCompleter;
import org.novasparkle.lunaspring.API.commands.annotations.Check;
import org.novasparkle.lunaspring.API.commands.annotations.SubCommand;
import org.novasparkle.lunaspring.API.util.utilities.LunaMath;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import siozy.start.lightgenerator.Config.Config;
import siozy.start.lightgenerator.generatorItem.GeneratorItem;

import java.util.List;

@SubCommand(appliedCommand = "lightgenerator", commandIdentifiers = "give")
@Check(permissions = "lightgenerator.give", flags = {})
public class GiveSubCommand implements LunaCompleter {
    // /lightgenerator give [player] [id] [amount]
    @Override
    public List<String> tabComplete(CommandSender commandSender, List<String> list) {
        if (list.size() == 1) return Utils.getPlayerNicks(list.get(0));
        if (list.size() == 2) return Utils.tabCompleterFiltering(Config.getSection("items").getKeys(false), list.get(1));
        if (list.size() == 3) return List.of("<количество>");

        return null;
    }

    @Override
    public void invoke(CommandSender commandSender, String[] strings) {
        if (strings.length < 3) {
            Config.sendMessage(commandSender, "noArgs");
            return;
        }

        Player player = Bukkit.getPlayer(strings[1]);
        String id = strings[2];
        int amount = LunaMath.toInt(strings[3]);

        if (player == null || !player.isOnline()) {
            Config.sendMessage(commandSender, "playerNotFound" + strings[1]);
            return;
        }

        ConfigurationSection itemSection = Config.getSection("items.");

        if (!itemSection.contains(id)) {
            Config.sendMessage(commandSender, "itemNotFound" + id);
            return;
        }

        GeneratorItem generatorItem = GeneratorItem.createItem(amount, id);
        generatorItem.giveDefault(player);

        Config.sendMessage(commandSender, "successGive", "amount-%-" + amount, "player-%-" + player.getName(), "item-%-" + generatorItem.getDisplayName());
        Config.sendMessage(player, "onGet", "amount-%-" + amount, "item-%-" + generatorItem.getDisplayName());
    }
}
