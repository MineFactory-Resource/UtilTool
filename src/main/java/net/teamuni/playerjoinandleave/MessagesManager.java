package net.teamuni.playerjoinandleave;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;

public class MessagesManager {
    private static final UtilTool main = UtilTool.getPlugin(UtilTool.class);
    private static File file;
    private static FileConfiguration messagesFile;



    public static void createMessagesYml() {
        file = new File(main.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            main.saveResource("messages.yml", false);
        }
        messagesFile = YamlConfiguration.loadConfiguration(file);
    }


    public static String get(String path) {
        String value = messagesFile.getString(path);
        return ChatColor.translateAlternateColorCodes('&',value);
    }
    public static void save() {
        try {
            messagesFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        messagesFile = YamlConfiguration.loadConfiguration(file);
    }
}
