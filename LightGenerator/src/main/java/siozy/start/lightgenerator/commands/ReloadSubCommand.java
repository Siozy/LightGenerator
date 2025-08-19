package siozy.start.lightgenerator.commands;

import org.bukkit.command.CommandSender;
import org.novasparkle.lunaspring.API.commands.Invocation;
import org.novasparkle.lunaspring.API.commands.annotations.Check;
import org.novasparkle.lunaspring.API.commands.annotations.SubCommand;
import siozy.start.lightgenerator.Config.Config;
import siozy.start.lightgenerator.GeneratorRunnable.RunnableManager;

@SubCommand(appliedCommand = "lightgenerator", commandIdentifiers = "reload")
@Check(permissions = "lightgenerator.reload", flags = {})
public class ReloadSubCommand implements Invocation {
    @Override
    public void invoke(CommandSender commandSender, String[] strings) {
        if (strings.length < 1) {
            Config.sendMessage(commandSender, "noArgs");
        }

        Config.sendMessage(commandSender, "reloaded");
        Config.reload();
        RunnableManager.clear();
    }
}
