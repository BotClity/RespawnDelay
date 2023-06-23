package bc.respawndelay.Listeners;

import bc.respawndelay.Main;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class DeathEvent implements Listener {
    private final Main main = Main.getInstance();
    private final ArrayList<UUID> respawning = new ArrayList<>();
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        if (!event.getPlayer().isOp()){
            if (event.getPlayer().getGameMode() != GameMode.SURVIVAL)event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }
    @EventHandler
    public void Spawn(PlayerRespawnEvent event){
        FileConfiguration cfg = main.getConfig();
        Location respawn = event.getRespawnLocation();
        Player player =  event.getPlayer();
        GameMode gameMode = player.getGameMode();
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,400,3));
        Location temp = new Location(respawn.getWorld(),cfg.getInt("x"),cfg.getInt("y"), cfg.getInt("z"));
        new BukkitRunnable(){

            @Override
            public void run() {
                temp.getChunk().load();
                player.teleport(temp);
                player.setGameMode(GameMode.SPECTATOR);
                respawning.add(player.getUniqueId());
                player.sendTitle(cfg.getString("title.name"),cfg.getString("title.subtitle"),10,cfg.getInt("title.stay"),20);
            }
        }.runTaskLater(main,5);
        new BukkitRunnable(){
            @Override
            public void run() {
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.setGameMode(gameMode);
                player.teleport(respawn);
                respawning.remove(player.getUniqueId());
            }
        }.runTaskLater(main,cfg.getInt("delay")+20);
    }
    @EventHandler
    public void DontMove(PlayerMoveEvent event){
        if (respawning.contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }
}
