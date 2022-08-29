package net.teamuni.playerjoinandleave;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerUuidManager {

    private static final UtilTool main = UtilTool.getPlugin(UtilTool.class);
    private static File file;
    private static FileConfiguration playersFile;

    public static void createPlayersYml() {
        file = new File(main.getDataFolder(), "players.yml");

        if (!file.exists()) {
            main.saveResource("players.yml", false);
        }
        playersFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return playersFile;
    }

    public static void save() {
        try {
            playersFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        playersFile = YamlConfiguration.loadConfiguration(file);
    }
}
