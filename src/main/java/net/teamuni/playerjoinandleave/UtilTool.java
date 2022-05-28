package net.teamuni.playerjoinandleave;

import java.util.List;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class UtilTool extends JavaPlugin implements Listener {

    String joinMessage = "";
    String leaveMessage = "";
    String firstTimeJoinMessage = "";
    String message = "";
    List<String> commandsList;
    String shiftRightClickCommand = "";

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
        this.joinMessage = getConfig().getString("join_message");
        this.leaveMessage = getConfig().getString("leave_message");
        this.firstTimeJoinMessage = getConfig().getString("first_time_join_message");
        this.shiftRightClickCommand = getConfig().getString("shift_right_click_command");
        CommandsManager.createCommandsYml();
        try {
            this.commandsList = new ArrayList<>(CommandsManager.get().getConfigurationSection("Commands").getKeys(false));
        } catch (NullPointerException e) {
            e.printStackTrace();
            getLogger().info("The command does not exist in commands.yml.");
        }
        registerCommands();
        getCommand("utiltool").setTabCompleter(new CommandTabCompleter());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("utiltool")) {
            if (args[0].equalsIgnoreCase("reload") && player.hasPermission("utiltool.reload")) {
                Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("UtilTool")).reloadConfig();
                CommandsManager.reload();
                CommandsManager.save();
                player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UtilTool has been reloaded!");
                return false;
            }
        }
        if (cmd.getName().equalsIgnoreCase("setspawn") && player.hasPermission("utiltool.setspawn")) {
            getConfig().set("spawnpoint.world", Objects.requireNonNull(player.getLocation().getWorld()).getName());
            getConfig().set("spawnpoint.x", player.getLocation().getX());
            getConfig().set("spawnpoint.y", player.getLocation().getY());
            getConfig().set("spawnpoint.z", player.getLocation().getZ());
            getConfig().set("spawnpoint.yaw", player.getLocation().getYaw());
            getConfig().set("spawnpoint.pitch", player.getLocation().getPitch());
            saveConfig();
            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Respawn point has been set!");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("spawn") && player.hasPermission("utiltool.spawn")) {
            World world = Bukkit.getServer().getWorld(Objects.requireNonNull(getConfig().getString("spawnpoint.world")));
            double x = getConfig().getDouble("spawnpoint.x");
            double y = getConfig().getDouble("spawnpoint.y");
            double z = getConfig().getDouble("spawnpoint.z");
            float yaw = (float) getConfig().getDouble("spawnpoint.yaw");
            float pitch = (float) getConfig().getDouble("spawnpoint.pitch");
            player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "이동 중...");
            player.teleport(new Location(world, x, y, z, yaw, pitch));
            return false;
        }
        if (commandsList != null && commandsList.contains(cmd.getName())) {
            for (String commandMessage : CommandsManager.get().getStringList("Commands." + cmd.getName())) {
                if (commandMessage != null) {
                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, commandMessage)));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', commandMessage));
                    }
                }
            }
            if (CommandsManager.get().getStringList("Commands." + cmd.getName()).isEmpty()) {
                getLogger().info("The message assigned to the Commands does not exist.");
            }
        }
        return false;
    }

    public void registerCommands() {
        try {
            if (commandsList != null) {
                Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                constructor.setAccessible(true);
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                CommandMap commandMap = (CommandMap) field.get(getServer().getPluginManager());
                for (String commandList : commandsList) {
                    PluginCommand pluginCommand = constructor.newInstance(commandList, this);
                    commandMap.register(getDescription().getName(), pluginCommand);
                }
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            if (joinMessage.contains("[NAME]")) {
                message = joinMessage.replace("[NAME]", player.getDisplayName());
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', message));
            } else {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
            }
        } else {
            if (firstTimeJoinMessage.contains("[NAME]")) {
                message = firstTimeJoinMessage.replace("[NAME]", player.getDisplayName());
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', message));
            } else {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', firstTimeJoinMessage));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (leaveMessage.contains("[NAME]")) {
            message = leaveMessage.replace("[NAME]", player.getDisplayName());
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else {
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage));
        }
    }

    @EventHandler
    public void onFall(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Objects.requireNonNull(player.getLocation().getWorld()).getName().equals(getConfig().getString("spawnpoint.world"))) {
            if (player.getLocation().getY() <= 0) {
                World world = Bukkit.getWorld(Objects.requireNonNull(getConfig().getString("spawnpoint.world")));
                double x = getConfig().getDouble("spawnpoint.x");
                double y = getConfig().getDouble("spawnpoint.y");
                double z = getConfig().getDouble("spawnpoint.z");
                float yaw = (float) getConfig().getDouble("spawnpoint.yaw");
                float pitch = (float) getConfig().getDouble("spawnpoint.pitch");
                player.teleport(new Location(world, x, y, z, yaw, pitch));
                player.setFallDistance(0);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        List<String> rightClickWorld = getConfig().getStringList("enable_world");
        if (event.getRightClicked().getType().equals(EntityType.PLAYER) && p.isSneaking()) {
            if (rightClickWorld.stream().anyMatch(current_world -> p.getWorld().equals(Bukkit.getWorld(current_world)))) {
                String clickPlayerName = (event.getRightClicked()).getName();
                String replacedShiftRightClick = (shiftRightClickCommand.replace("%player%", clickPlayerName));
                p.performCommand(replacedShiftRightClick);
            }
        }
    }
}