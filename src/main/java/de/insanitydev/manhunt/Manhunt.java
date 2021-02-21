package de.insanitydev.manhunt;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.ConditionFailedException;
import co.aikar.commands.PaperCommandManager;
import de.insanitydev.itembuilder.ItemBuilder;
import de.insanitydev.manhunt.commands.ManhuntCommand;
import de.insanitydev.manhunt.config.ConfigManager;
import de.insanitydev.manhunt.listeners.PlayerDeath;
import de.insanitydev.manhunt.listeners.PlayerInteract;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Manhunt extends JavaPlugin {

    @Getter
    private static Manhunt instance;
    @Getter
    private final ItemStack compassItem = new ItemBuilder(Material.COMPASS)
            .setName("§dTracker")
            .build();
    @Getter
    private final List<String> hunters = new ArrayList<>();
    @Getter
    private final List<String> runners = new ArrayList<>();
    private final PlayerDeath playerDeathListener = new PlayerDeath();
    private final PlayerInteract playerInteractListener = new PlayerInteract();
    private final ManhuntCommand manhuntCommand = new ManhuntCommand();
    @Getter
    private PaperCommandManager paperCommandManager;
    @Getter
    private ConfigManager configManager;
    @Getter
    private String prefix;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        prefix = configManager.getPrefix();
        paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("players", c -> {
            List<String> playerCompletion = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> playerCompletion.add(player.getName()));
            return playerCompletion;
        });
        paperCommandManager.getCommandConditions().addCondition("player", (context) -> {
            BukkitCommandIssuer issuer = context.getIssuer();
            if (!issuer.isPlayer()) {
                throw new ConditionFailedException("Issuer is not a type of player!");
            }
        });
        paperCommandManager.registerCommand(manhuntCommand);
        if (configManager.isEnabled()) {
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(playerDeathListener, this);
            pluginManager.registerEvents(playerInteractListener, this);
        }
    }

    public void reload() {
        if (configManager.isEnabled()) {
            HandlerList.unregisterAll(playerDeathListener);
            HandlerList.unregisterAll(playerInteractListener);
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(playerDeathListener, this);
            pluginManager.registerEvents(playerInteractListener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
