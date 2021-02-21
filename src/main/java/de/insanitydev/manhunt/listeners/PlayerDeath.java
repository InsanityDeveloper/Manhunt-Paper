package de.insanitydev.manhunt.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import de.insanitydev.manhunt.Manhunt;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (Manhunt.getInstance().getRunners().contains(event.getEntity().getUniqueId().toString())) {
            event.getEntity().setGameMode(GameMode.SPECTATOR);
            Manhunt.getInstance().getRunners().remove(event.getEntity().getUniqueId().toString());
        }
        if (Manhunt.getInstance().getHunters().contains(event.getEntity().getUniqueId().toString())) {
            event.getDrops().removeIf(drop -> drop.isSimilar(Manhunt.getInstance().getCompassItemBuilder().build()));
        }
    }

    @EventHandler
    public void onPlayerPostRespawn(PlayerPostRespawnEvent event) {
        if (Manhunt.getInstance().getHunters().contains(event.getPlayer().getUniqueId().toString())) {
            event.getPlayer().getInventory().addItem(Manhunt.getInstance().getCompassItemBuilder().build());
        }
    }

}
