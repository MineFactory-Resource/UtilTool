package net.teamuni.playerjoinandleave;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class IgnorePlayerManager {
    private static final UtilTool main = UtilTool.getPlugin(UtilTool.class);
    private static File file;
    private static FileConfiguration ignoreplayersFile;

    public static void createCommandsYml() {
        file = new File(main.getDataFolder(), "ignoreplayer.yml");

        if (!file.exists()) {
            main.saveResource("ignoreplayer.yml", false);
        }
        ignoreplayersFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return ignoreplayersFile;
    }

    public static void save() {
        try {
            ignoreplayersFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        ignoreplayersFile = YamlConfiguration.loadConfiguration(file);
    }
}

