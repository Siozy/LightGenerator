package siozy.start.lightgenerator.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.novasparkle.lunaspring.API.commands.LunaCompleter;
import org.novasparkle.lunaspring.API.commands.annotations.Args;
import org.novasparkle.lunaspring.API.commands.annotations.Permissions;
import org.novasparkle.lunaspring.API.commands.annotations.SubCommand;
import org.novasparkle.lunaspring.API.util.utilities.LunaMath;
import org.novasparkle.lunaspring.API.util.utilities.Utils;
import siozy.start.lightgenerator.Config.Config;
import siozy.start.lightgenerator.generatorItem.GeneratorItem;

import java.util.List;

@SubCommand(appliedCommand = "lightgenerator", commandIdentifiers = "take")
@Permissions(permissionList = {"lightgenerator.take"})
@Args(min = 3, max = Integer.MAX_VALUE)
public class TakeSubCommand implements LunaCompleter {
    // /lightgenerator take [player]/all [amount]
    @Override
    @SuppressWarnings("all")
    public List<String> tabComplete(CommandSender commandSender, List<String> list) {
        if (list.size() == 1) return Utils.getPlayerNicks(list.get(0));
        if (list.size() == 1) return List.of("all");
        if (list.size() == 2) return List.of("<количество>");

        return null;
    }

    @Override
    public void invoke(CommandSender commandSender, String[] strings) {
        if (strings[1].equalsIgnoreCase("all")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerInventory inventory = player.getInventory();

                int amount = strings.length >= 3 ? LunaMath.toInt(strings[2]) : 1;

                takeGenerators(inventory, amount);
            }
            Config.sendMessage(commandSender, "successTakeByAll");
        } else {
            Player player = Bukkit.getPlayer(strings[1]);

            if (player == null || !player.isOnline()) {
                Config.sendMessage(commandSender, "playerNotFound" + strings[1]);
                return;
            }

            PlayerInventory inventory = player.getInventory();

            int amount = strings.length >= 3 ? LunaMath.toInt(strings[2]) : 1;

            takeGenerators(inventory, amount);
            Config.sendMessage(commandSender, "successTake", "amount-%-" + amount, "player-%-" + player.getName());
        }
    }

    public void takeGenerators(Inventory inventory, int amount) {
        for (ItemStack content : inventory.getStorageContents()) {
            if (amount <= 0) break;

            if (GeneratorItem.isCustomItem(content)) {
                int different = Math.min(amount, content.getAmount());
                amount -= different;
                content.setAmount(content.getAmount() - different);
            }
        }

    }
}
