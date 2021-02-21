package de.insanitydev.manhunt.config;

import de.insanitydev.manhunt.Manhunt;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final File configFile = new File(Manhunt.getInstance().getDataFolder(), "config.yml");

    private YamlConfiguration yamlConfiguration;

    @Getter
    private boolean enabled = true;
    @Getter
    private boolean dropCompassOnDeath = false;
    @Getter
    private boolean displayPlayerNameOfTracked = true;
    @Getter
    private boolean setRunnerInSpectatorAfterDeath = true;
    @Getter
    private String prefix = "§dManhunt §8| §e";
    @Getter
    private String compassItemName = "§dTracker";

    public ConfigManager() {
        if (configFile.exists()) {
            yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
            if (yamlConfiguration.contains("manhunt.enabled")) {
                enabled = yamlConfiguration.getBoolean("manhunt.enabled");
            } else {
                yamlConfiguration.set("manhunt.enabled", true);
                save();
            }
            if (yamlConfiguration.contains("manhunt.death.hunter.dropCompass")) {
                dropCompassOnDeath = yamlConfiguration.getBoolean("manhunt.death.hunter.dropCompass");
            } else {
                yamlConfiguration.set("manhunt.death.hunter.dropCompass", false);
                save();
            }
            if (yamlConfiguration.contains("manhunt.tracker.displayRunnerName")) {
                displayPlayerNameOfTracked = yamlConfiguration.getBoolean("manhunt.tracker.displayRunnerName");
            } else {
                yamlConfiguration.set("manhunt.tracker.displayRunnerName", true);
                save();
            }
            if (yamlConfiguration.contains("manhunt.death.runner.setSpectator")) {
                setRunnerInSpectatorAfterDeath = yamlConfiguration.getBoolean("manhunt.death.runner.setSpectator");
            } else {
                yamlConfiguration.set("manhunt.death.runner.setSpectator", true);
                save();
            }
            if (yamlConfiguration.contains("manhunt.prefix")) {
                prefix = yamlConfiguration.getString("manhunt.prefix");
            } else {
                yamlConfiguration.set("manhunt.prefix", "§dManhunt §8| §e");
                save();
            }
            if (yamlConfiguration.contains("manhunt.compass.name")) {
                compassItemName = yamlConfiguration.getString("manhunt.compass.name");
            } else {
                yamlConfiguration.set("manhunt.compass.name", "§dTracker");
                save();
            }
        } else {
            try {
                if (configFile.createNewFile()) {
                    yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
                    yamlConfiguration.set("manhunt.enabled", true);
                    yamlConfiguration.set("manhunt.death.hunter.dropCompass", false);
                    yamlConfiguration.set("manhunt.tracker.displayRunnerName", true);
                    yamlConfiguration.set("manhunt.death.runner.setSpectator", true);
                    yamlConfiguration.set("manhunt.prefix", "§dManhunt §8| §e");
                    yamlConfiguration.set("manhunt.compass.name", "§dTracker");
                    yamlConfiguration.save(configFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setEnabled(boolean value) {
        this.enabled = value;
        yamlConfiguration.set("manhunt.enabled", value);
        save();
    }

    public void setDropCompassOnDeath(boolean value) {
        this.dropCompassOnDeath = value;
        yamlConfiguration.set("manhunt.death.hunter.dropCompass", value);
        save();
    }

    public void setDisplayPlayerNameOfTracked(boolean value) {
        this.displayPlayerNameOfTracked = value;
        yamlConfiguration.set("manhunt.tracker.displayRunnerName", value);
        save();
    }

    public void setSetRunnerInSpectatorAfterDeath(boolean value) {
        this.setRunnerInSpectatorAfterDeath = value;
        yamlConfiguration.set("manhunt.death.runner.setSpectator", value);
        save();
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        yamlConfiguration.set("manhunt.prefix", prefix);
        save();
    }

    public void setCompassItemName(String newName) {
        this.compassItemName = newName;
        yamlConfiguration.set("manhunt.compass.name", newName);
        save();
    }

    private void save() {
        try {
            yamlConfiguration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        if (configFile.exists()) {
            if (yamlConfiguration.contains("manhunt.enabled")) {
                enabled = yamlConfiguration.getBoolean("manhunt.enabled");
            } else {
                yamlConfiguration.set("manhunt.enabled", true);
                save();
            }
            if (yamlConfiguration.contains("manhunt.death.hunter.dropCompass")) {
                dropCompassOnDeath = yamlConfiguration.getBoolean("manhunt.death.hunter.dropCompass");
            } else {
                yamlConfiguration.set("manhunt.death.hunter.dropCompass", false);
                save();
            }
            if (yamlConfiguration.contains("manhunt.tracker.displayRunnerName")) {
                displayPlayerNameOfTracked = yamlConfiguration.getBoolean("manhunt.tracker.displayRunnerName");
            } else {
                yamlConfiguration.set("manhunt.tracker.displayRunnerName", true);
                save();
            }
            if (yamlConfiguration.contains("manhunt.death.runner.setSpectator")) {
                setRunnerInSpectatorAfterDeath = yamlConfiguration.getBoolean("manhunt.death.runner.setSpectator");
            } else {
                yamlConfiguration.set("manhunt.death.runner.setSpectator", true);
                save();
            }
            if (yamlConfiguration.contains("manhunt.prefix")) {
                prefix = yamlConfiguration.getString("resourcepack.url");
            } else {
                yamlConfiguration.set("resourcepack.url", "10");
                save();
            }
            if (yamlConfiguration.contains("manhunt.compass.name")) {
                compassItemName = yamlConfiguration.getString("manhunt.compass.name");
            } else {
                yamlConfiguration.set("manhunt.compass.name", "§dTracker");
                save();
            }
        } else {
            try {
                if (configFile.createNewFile()) {
                    yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
                    yamlConfiguration.set("resourcepack.url", "");
                    yamlConfiguration.set("resourcepack.hash", "");
                    yamlConfiguration.set("furnace.speed.medium", 10);
                    yamlConfiguration.set("furnace.speed.speed", 20);
                    yamlConfiguration.set("grapplingHook.multiplication", -2.25);
                    yamlConfiguration.set("fastsleep.percentage", 33);
                    yamlConfiguration.save(configFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
