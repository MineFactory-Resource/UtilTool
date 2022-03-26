package net.teamuni.playerjoinandleave;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PlayerJoinAndLeave extends JavaPlugin implements Listener {

    String join_message = "";
    String leave_message = "";
    String first_time_join_message = "";
    String message = "";

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
        this.join_message = getConfig().getString("join_message");
        this.leave_message = getConfig().getString("leave_message");
        this.first_time_join_message = getConfig().getString("first_time_join_message");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("utiltoolreload") && player.hasPermission("utiltool.reload")) {
            Bukkit.getPluginManager().disablePlugin(this);
            Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("UtilTool")).reloadConfig();
            Bukkit.getPluginManager().enablePlugin(this);
            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UtilTool has been reloaded!");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("setspawn") && player.hasPermission("utiltool.setspawn")) {
            getConfig().set("spawnpoint.x", player.getLocation().getX());
            getConfig().set("spawnpoint.y", player.getLocation().getY());
            getConfig().set("spawnpoint.z", player.getLocation().getZ());
            getConfig().set("spawnpoint.yaw", player.getLocation().getYaw());
            getConfig().set("spawnpoint.pitch", player.getLocation().getPitch());
            saveConfig();
            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Respawn point has been set!");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("spawn") && player.hasPermission("utiltool.spawn")) {
            World world = player.getWorld();
            double x = getConfig().getDouble("spawnpoint.x");
            double y = getConfig().getDouble("spawnpoint.y");
            double z = getConfig().getDouble("spawnpoint.z");
            float yaw = (float) getConfig().getDouble("spawnpoint.yaw");
            float pitch = (float) getConfig().getDouble("spawnpoint.pitch");
            sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "이동 중...");
            player.teleport(new Location(world, x, y, z, yaw, pitch));
            return false;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            if (join_message.contains("[NAME]")) {
                message = join_message.replace("[NAME]", player.getDisplayName());
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', message));
            } else {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', join_message));
            }
        } else {
            if (first_time_join_message.contains("[NAME]")) {
                message = first_time_join_message.replace("[NAME]", player.getDisplayName());
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', message));
            } else {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', first_time_join_message));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (leave_message.contains("[NAME]")) {
            message = leave_message.replace("[NAME]", player.getDisplayName());
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else {
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', leave_message));
        }
    }
}