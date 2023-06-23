package bc.respawndelay.commands;

import bc.respawndelay.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Reload extends AbstractCommands{
    public Reload() {
        super("delay");
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender)return;
        if (args.length == 0)return;
        if (args[0].equalsIgnoreCase("reload")){
            Main.getInstance().reloadConfig();
            sender.sendMessage("Plugin successfully reloaded");
        }
    }
}
