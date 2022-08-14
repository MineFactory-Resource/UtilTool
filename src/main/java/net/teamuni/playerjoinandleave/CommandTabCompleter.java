package net.teamuni.playerjoinandleave;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("utiltool")) {
            List<String> tabCompleteList = new ArrayList<>();
            if (args.length == 1) {
                tabCompleteList.add("reload");
            }
            return tabCompleteList;
        }
        for (String commandList : CommandsManager.get().getConfigurationSection("Commands").getKeys(false)) {
            if (command.getName().equalsIgnoreCase(commandList)) {
                List<String> tabCompleteList = new ArrayList<>();
                if (args.length == 1) {
                    for (int i = 1; i <= CommandsManager.get().getConfigurationSection("Commands." + command.getName()).getKeys(false).size(); i++) {
                        tabCompleteList.add(String.valueOf(i));
                    }
                }
                return tabCompleteList;
            }
        }
        return null;
    }
}