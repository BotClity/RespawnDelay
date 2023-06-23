package bc.respawndelay;

import bc.respawndelay.Listeners.DeathEvent;
import bc.respawndelay.commands.AbstractCommands;
import bc.respawndelay.commands.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance=this;
        getServer().getPluginManager().registerEvents(new DeathEvent(),this);
        new Reload();
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getLogger().info("Creating config file...");
            getLogger().info("Please write config");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
