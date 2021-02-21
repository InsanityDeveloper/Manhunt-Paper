package de.insanitydev.manhunt.listeners;

import de.insanitydev.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.CompassMeta;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasItem() && event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getItem().getType() == Material.COMPASS && Manhunt.getInstance().getCompassItemBuilder().build().isSimilar(event.getItem())) {
                if (Manhunt.getInstance().getHunters().contains(event.getPlayer().getUniqueId().toString())) {
                    double distance = 0;
                    Location locationToTrack = null;
                    Player runnerToTarget = null;
                    for (String runnerUuid : Manhunt.getInstance().getRunners()) {
                        Player runner = Bukkit.getPlayer(runnerUuid);
                        if (runner != null) {
                            if (runnerToTarget == null) {
                                runnerToTarget = runner;
                                distance = runner.getLocation().distance(event.getPlayer().getLocation());
                                locationToTrack = runner.getLocation();
                            } else {
                                double newDistance = runner.getLocation().distance(event.getPlayer().getLocation());
                                if (newDistance < distance) {
                                    distance = newDistance;
                                    runnerToTarget = runner;
                                    locationToTrack = runner.getLocation();
                                }
                            }
                        }
                    }
                    if (runnerToTarget != null) {
                        CompassMeta compassMeta = (CompassMeta) event.getItem().getItemMeta();
                        compassMeta.setLodestoneTracked(false);
                        compassMeta.setLodestone(locationToTrack);
                        event.getItem().setItemMeta(compassMeta);
                        event.getPlayer().sendActionBar("§dTracking §b" + runnerToTarget.getName() + "!");
                    } else {
                        event.getPlayer().sendActionBar("§cNo target found!");
                    }
                }
            }
        }
    }

}
