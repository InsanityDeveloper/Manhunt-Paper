package de.insanitydev.manhunt.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import de.insanitydev.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("manhunt|mh")
@CommandPermission("manhunt.command")
@Conditions("player")
public class ManhuntCommand extends BaseCommand {

    @Default
    public void onDefault(Player player) {
        player.sendMessage("/manhunt hunter (Player) - Makes the specified player a hunter.");
        player.sendMessage("/manhunt runner (Player) - Makes the specified player a runner.");
    }

    @Subcommand("hunter")
    @CommandCompletion("@players")
    @CommandPermission("manhunt.command.hunter")
    public void onHunterSubcommand(Player player, String playerName) {
        if (!Manhunt.getInstance().getConfigManager().isEnabled()) {
            player.sendMessage(Manhunt.getInstance().getPrefix() + "§cManhunt is disabled!");
            return;
        }
        if (playerName != null) {
            Player otherPlayer = Bukkit.getPlayer(playerName);
            if (otherPlayer != null) {
                if (Manhunt.getInstance().getHunters().contains(otherPlayer.getUniqueId().toString())) {
                    Manhunt.getInstance().getHunters().remove(otherPlayer.getUniqueId().toString());
                    player.sendMessage(Manhunt.getInstance().getPrefix() + "Player §a" + otherPlayer.getName() + "§e is no longer a hunter!");
                } else {
                    Manhunt.getInstance().getHunters().add(otherPlayer.getUniqueId().toString());
                    player.sendMessage(Manhunt.getInstance().getPrefix() + "Player §a" + otherPlayer.getName() + "§e is now a hunter!");
                }
            } else {
                player.sendMessage("§dManhunt §8| §cInvalid player!");
            }
        }
    }

    @Subcommand("runner")
    @CommandCompletion("@players")
    @CommandPermission("manhunt.command.runner")
    public void onRunnerSubcommand(Player player, String playerName) {
        if (!Manhunt.getInstance().getConfigManager().isEnabled()) {
            player.sendMessage(Manhunt.getInstance().getPrefix() + "§cManhunt is disabled!");
            return;
        }
        if (playerName != null) {
            Player otherPlayer = Bukkit.getPlayer(playerName);
            if (otherPlayer != null) {
                if (Manhunt.getInstance().getRunners().contains(otherPlayer.getUniqueId().toString())) {
                    Manhunt.getInstance().getRunners().remove(otherPlayer.getUniqueId().toString());
                    player.sendMessage(Manhunt.getInstance().getPrefix() + "Player §a" + otherPlayer.getName() + "§e is no longer a runner!");
                } else {
                    Manhunt.getInstance().getRunners().add(otherPlayer.getUniqueId().toString());
                    player.sendMessage(Manhunt.getInstance().getPrefix() + "Player §a" + otherPlayer.getName() + "§e is now a runner!");
                }
            } else {
                player.sendMessage("§dManhunt §8| §cInvalid player!");
            }
        }
    }


    @Subcommand("admin")
    @CommandAlias("manhuntadmin")
    @CommandPermission("manhunt.command.admin")
    public class onAdminSubcommand extends BaseCommand {

        @Default
        public void onDefault(Player player) {
            player.sendMessage("/manhunt admin reload - Reloads the config.");
            player.sendMessage("/manhunt admin config toggle - Enables or disables the plugin.");
            player.sendMessage("/manhunt admin config dropCompassOnDeath - Enables or disables compass drop.");
            player.sendMessage("/manhunt admin config displayTrackedName - Enables or disables the name from being displayed in the tracker.");
            player.sendMessage("/manhunt admin config setRunnerInSpectatorAfterDeath - Enables or disables the runner from being set in spectator after death.");
            player.sendMessage("/manhunt admin config prefix (Prefix) - Sets the prefix.");
            player.sendMessage("/manhunt admin config compassItemName (Compass Item Name) - Sets the compass item name.");
        }

        @Subcommand("reload")
        @Description("Reloads the config.")
        public void onReloadSubcommand(Player player) {
            Manhunt.getInstance().getConfigManager().reload();
            player.sendMessage(Manhunt.getInstance().getPrefix() + "Reloaded config!");
        }

        @Subcommand("config")
        public class onConfigSubcommand extends BaseCommand {

            @Subcommand("toggle")
            @Description("Enables or disables the plugin.")
            public void onToggleSubcommand(Player player) {
                Manhunt.getInstance().getConfigManager().setEnabled(!Manhunt.getInstance().getConfigManager().isEnabled());
                player.sendMessage(Manhunt.getInstance().getPrefix() + "Manhunt is now " +
                        (Manhunt.getInstance().getConfigManager().isEnabled() ? "§aenabled" : "§cdisabled") + " §e!");
                Manhunt.getInstance().reload();
            }

            @Subcommand("dropCompassOnDeath")
            @Description("Enables or disables compass drop.")
            public void onDropCompassOnDeathSubcommand(Player player) {
                Manhunt.getInstance().getConfigManager().setDropCompassOnDeath(!Manhunt.getInstance().getConfigManager().isDropCompassOnDeath());
                player.sendMessage(Manhunt.getInstance().getPrefix() + "Compass " +
                        (Manhunt.getInstance().getConfigManager().isDropCompassOnDeath() ? "now §adrops on death " : "§cno longer drops on death") + " §e!");
            }

            @Subcommand("displayTrackedName")
            @Description("Enables or disables the name from being displayed in the tracker.")
            public void onDisplayTrackedNameSubcommand(Player player) {
                Manhunt.getInstance().getConfigManager().setDisplayPlayerNameOfTracked(!Manhunt.getInstance().getConfigManager().isDisplayPlayerNameOfTracked());
                player.sendMessage(Manhunt.getInstance().getPrefix() + "Tracker " +
                        (Manhunt.getInstance().getConfigManager().isDisplayPlayerNameOfTracked() ? "§enow §ashows tracked name" : "§cno longer shows tracked name") + " §e!");
            }

            @Subcommand("setRunnerInSpectatorAfterDeath")
            @Description("Enables or disables the runner from being set in spectator after death.")
            public void onSetRunnerInSpectatorAfterDeathSubcommand(Player player) {
                Manhunt.getInstance().getConfigManager().setSetRunnerInSpectatorAfterDeath(!Manhunt.getInstance().getConfigManager().isSetRunnerInSpectatorAfterDeath());
                player.sendMessage(Manhunt.getInstance().getPrefix() + "Runner " +
                        (Manhunt.getInstance().getConfigManager().isSetRunnerInSpectatorAfterDeath() ?
                                "will now be §aset in spectator mode §eon death" : "will §cno longer be set in spectator mode §eon death") + " §e!");
            }

            @Subcommand("prefix")
            @Description("Sets the prefix.")
            @CommandCompletion("&dManhunt &8| &e")
            public void onPrefixSubcommand(Player player, String value) {
                if (value != null) {
                    Manhunt.getInstance().getConfigManager().setPrefix(ChatColor.translateAlternateColorCodes('&', value));
                    player.sendMessage(Manhunt.getInstance().getPrefix() + "Prefix has been changed!");
                } else {
                    player.sendMessage(Manhunt.getInstance().getPrefix() + "§eNo new prefix given!");
                }
            }

            @Subcommand("compassItemName")
            @Description("Sets the compass item name.")
            @CommandCompletion("&dTracker")
            public void onCompassItemNameSubcommand(Player player, String value) {
                if (value != null) {
                    Manhunt.getInstance().getConfigManager().setCompassItemName(ChatColor.translateAlternateColorCodes('&', value));
                    player.sendMessage(Manhunt.getInstance().getPrefix() + "Item name is now " +
                            Manhunt.getInstance().getConfigManager().getCompassItemName() + "!");
                } else {
                    player.sendMessage(Manhunt.getInstance().getPrefix() + "§eNo new item name given!");
                }
            }
        }
    }

}
