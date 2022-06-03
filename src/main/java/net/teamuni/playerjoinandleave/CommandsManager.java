package net.teamuni.playerjoinandleave;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CommandsManager {

    private static final UtilTool main = UtilTool.getPlugin(UtilTool.class);
    private static File file;
    private static FileConfiguration commandsFile;

    public static void createCommandsYml() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("UtilTool").getDataFolder(), "commands.yml");

        if (!file.exists()) {
            main.saveResource("commands.yml", false);
        }
        commandsFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return commandsFile;
    }

    public static void save() {
        try {
            commandsFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        commandsFile = YamlConfiguration.loadConfiguration(file);
    }
}
