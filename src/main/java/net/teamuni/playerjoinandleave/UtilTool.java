package net.teamuni.playerjoinandleave;

import java.util.List;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class UtilTool extends JavaPlugin implements Listener {

    String joinMessage = "";
    String leaveMessage = "";
    String firstTimeJoinMessage = "";
    String shiftRightClickCommand = "";
    String title = "";
    String subtitle = "";
    List<String> commandsList;
    World world;
    double x;
    double y;
    double z;
    float yaw;
    float pitch;
    int fadeIn;
    int stay;
    int fadeOut;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
        getConfigMessages();
        CommandsManager.createCommandsYml();
        PlayerUuidManager.createPlayersYml();
        registerCommands();
        BroadCasterCooldown.setupCooldown();
        getCommand("utiltool").setTabCompleter(new CommandTabCompleter());
        getCommand("gm").setTabCompleter(new CommandTabCompleter());
        getSpawnInfo();
    }

    @Override
    public void onDisable() {
        PlayerUuidManager.save();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        String[] spawn = {"spawn", "tmvhs", "스폰", "넴주"};

        if (cmd.getName().equalsIgnoreCase("utiltool") && player.hasPermission("utiltool.reload")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    saveConfig();
                    getConfigMessages();
                    CommandsManager.reload();
                    CommandsManager.save();
                    PlayerUuidManager.save();
                    PlayerUuidManager.reload();
                    registerCommands();
                    getSpawnInfo();
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UtilTool has been reloaded!");
                    return false;
                }
            } else {
                player.sendMessage("§e[알림] §f올바르지 않은 명령어입니다.");
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
            getSpawnInfo();
            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Respawn point has been set!");
            return false;
        }
        if (Arrays.asList(spawn).contains(cmd.getName()) && player.hasPermission("utiltool.spawn")) {
            player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "이동 중...");
            player.teleport(new Location(world, x, y, z, yaw, pitch));
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("채팅청소") && player.hasPermission("utiltool.mychatclear")) {
            for (int myChatClearCount = 0; myChatClearCount < 100; myChatClearCount++) {
                player.sendMessage("");
            }
            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Your chat has been cleaned!");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("전체채팅청소") && player.hasPermission("utiltool.allchatclear")) {
            for (int allChatClearCount = 0; allChatClearCount < 100; allChatClearCount++) {
                getServer().broadcastMessage("");
            }
            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "All Chat has been cleaned!");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("gmc") && player.hasPermission("utiltool.gamemode")) {
            if (args.length > 0){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null){
                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage("§e[알림] §f현재 게임모드가 크리에이티브 모드로 변경되었습니다.");
                    player.sendMessage("§e[알림] §f현재" + args[0] + " 님의 게임모드가 크리에이티브 모드로 변경되었습니다.");

                }
            } else {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage("§e[알림] §f현재 게임모드가 크리에이티브 모드로 변경되었습니다.");
                return false;
            }
        }
        if (cmd.getName().equalsIgnoreCase("gms") && player.hasPermission("utiltool.gamemode")) {
            if (args.length > 0){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null){
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage("§e[알림] §f현재 게임모드가 크리에이티브 모드로 변경되었습니다.");
                    player.sendMessage("§e[알림] §f현재" + args[0] + " 님의 게임모드가 크리에이티브 모드로 변경되었습니다.");

                }
            } else {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage("§e[알림] §f현재 게임모드가 크리에이티브 모드로 변경되었습니다.");
                return false;
            }
        }
        if (cmd.getName().equalsIgnoreCase("gm") && player.hasPermission("utiltool.gamemode")) {
            if (args.length == 1) {
                switch (args[0]) {
                    case "0":
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage("§e[알림] §f현재 게임모드가 서바이벌 모드로 변경되었습니다.");
                        break;
                    case "1":
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage("§e[알림] §f현재 게임모드가 크리에이티브 모드로 변경되었습니다.");
                        break;
                    case "2":
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage("§e[알림] §f현재 게임모드가 모험 모드로 변경되었습니다.");
                        break;
                    case "3":
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage("§e[알림] §f현재 게임모드가 관전자 모드로 변경되었습니다.");
                        break;
                    default:
                        player.sendMessage("§6/gm 0 - 게임모드를 서바이벌 모드로 변경합니다.");
                        player.sendMessage("§6/gm 1 - 게임모드를 크리에이티브 모드로 변경합니다.");
                        player.sendMessage("§6/gm 2 - 게임모드를 모험 모드로 변경합니다.");
                        player.sendMessage("§6/gm 3 - 게임모드를 관전자 모드로 변경합니다.");
                        break;
                }
            } else {
                player.sendMessage("§6/gm 0 - 게임모드를 서바이벌 모드로 변경합니다.");
                player.sendMessage("§6/gm 1 - 게임모드를 크리에이티브 모드로 변경합니다.");
                player.sendMessage("§6/gm 2 - 게임모드를 모험 모드로 변경합니다.");
                player.sendMessage("§6/gm 3 - 게임모드를 관전자 모드로 변경합니다.");
            }
        }
        if (cmd.getName().equalsIgnoreCase("확성기") && player.hasPermission("utiltool.broadcaster")) {
            String lore = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
            if (BroadCasterCooldown.checkCooldown(player)) {
                if (args.length > 0) {
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("§6[§f " + player.getName() + " §6] §b" + lore);
                    Bukkit.broadcastMessage("");
                    BroadCasterCooldown.setCooldown(player, 300);
                } else {
                    player.sendMessage("§6/확성기 [메세지] - [메세지]를 전체채팅으로 전달합니다.");
                }
            } else {
                player.sendMessage("§e[알림] §f확성기 재사용까지 §a" + BroadCasterCooldown.getCooldown(player) + "§f초 남았습니다");
            }
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
            return false;
        }
        return false;
    }

    public void registerCommands() {
        try {
            commandsList = new ArrayList<>(CommandsManager.get().getConfigurationSection("Commands").getKeys(false));
        } catch (NullPointerException e) {
            e.printStackTrace();
            getLogger().info("The command does not exist in commands.yml.");
        }
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
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException |
                NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void getConfigMessages() {
        fadeIn = getConfig().getInt("fadeIn");
        stay = getConfig().getInt("stay");
        fadeOut = getConfig().getInt("fadeOut");
        try {
            joinMessage = getConfig().getString("join_message");
            leaveMessage = getConfig().getString("leave_message");
            firstTimeJoinMessage = getConfig().getString("first_time_join_message");
            shiftRightClickCommand = getConfig().getString("shift_right_click_command");
            title = getConfig().getString("title");
            subtitle = getConfig().getString("subtitle");
        } catch (NullPointerException e) {
            e.printStackTrace();
            getLogger().info("config.yml에서 정보를 불러오는데 문제가 발생하였습니다.");
        }
    }

    public void getSpawnInfo() {
        try {
            world = Bukkit.getServer().getWorld(Objects.requireNonNull(getConfig().getString("spawnpoint.world")));
            x = getConfig().getDouble("spawnpoint.x");
            y = getConfig().getDouble("spawnpoint.y");
            z = getConfig().getDouble("spawnpoint.z");
            yaw = (float) getConfig().getDouble("spawnpoint.yaw");
            pitch = (float) getConfig().getDouble("spawnpoint.pitch");
        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
            getLogger().info("스폰 정보를 불러오는데 문제가 발생하였습니다.");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (PlayerUuidManager.get().getStringList("UUIDs").contains(player.getUniqueId().toString())) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, joinMessage)));
            } else {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
            }
        } else {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, firstTimeJoinMessage)));
            } else {
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', firstTimeJoinMessage));
            }
            List<String> playerUuidList = PlayerUuidManager.get().getStringList("UUIDs");
            playerUuidList.add(player.getUniqueId().toString());
            PlayerUuidManager.get().set("UUIDs", playerUuidList);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, leaveMessage)));
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
}