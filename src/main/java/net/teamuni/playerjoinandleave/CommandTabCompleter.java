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
        if (command.getName().equalsIgnoreCase("gm")) {
            ArrayList<String> commandArguments = new ArrayList<>();
            if (args.length == 1) {
                commandArguments.add("0");
                commandArguments.add("1");
                commandArguments.add("2");
                commandArguments.add("3");
            }
            return commandArguments;
        }
        return null;
    }
}
