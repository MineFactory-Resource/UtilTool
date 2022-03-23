package net.teamuni.playerjoinandleave;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("utiltoolreload")) {
            if (sender.hasPermission("utiltool.reload")) {
                Bukkit.getPluginManager().disablePlugin(this);
                Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("UtilTool")).reloadConfig();
                Bukkit.getPluginManager().enablePlugin(this);
                sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UtilTool has been reloaded!");
                return false;
            }
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