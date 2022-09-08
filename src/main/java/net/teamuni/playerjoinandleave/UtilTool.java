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
    String setSpawnMessage = "";
    String teleportMessage = "";
    String unknownCommandMessage = "";
    String createModeMessage = "";

    String survivalModeMessage = "";

    String adventureModeMessage = "";

    String specterModeMessage = "";

    String targetCreateModeMessage = "";

    String targetSurvivalModeMessage = "";
    String myChatClearMessage = "";
    String allChatClearMessage = "";
    String broadcastCommandMessage = "";
    String broadcastCooldownMessage = "";
    String messageGm0 = "";
    String messageGm1 = "";
    String messageGm2 = "";
    String messageGm3 = "";
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
        MessagesManager.createMessagesYml();
        PlayerUuidManager.createPlayersYml();
        CommandsManager.createCommandsYml();
        registerCommands();
        BroadCasterCooldown.setupCooldown();
        getCommand("utiltool").setTabCompleter(new CommandTabCompleter());
        getCommand("gm").setTabCompleter(new CommandTabCompleter());
        getSpawnInfo();
        getConfigMessages();
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
                    CommandsManager.save();
                    CommandsManager.reload();
                    PlayerUuidManager.save();
                    PlayerUuidManager.reload();
                    MessagesManager.save();
                    MessagesManager.reload();
                    getSpawnInfo();
                    registerCommands();
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UtilTool has been reloaded!");
                    return false;
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', unknownCommandMessage));
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
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', setSpawnMessage));
            return false;
        }
        if (Arrays.asList(spawn).contains(cmd.getName()) && player.hasPermission("utiltool.spawn")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', teleportMessage));
            player.teleport(new Location(world, x, y, z, yaw, pitch));
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("채팅청소") && player.hasPermission("utiltool.mychatclear")) {
            for (int myChatClearCount = 0; myChatClearCount < 100; myChatClearCount++) {
                player.sendMessage("");
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', myChatClearMessage));
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("전체채팅청소") && player.hasPermission("utiltool.allchatclear")) {
            for (int allChatClearCount = 0; allChatClearCount < 100; allChatClearCount++) {
                getServer().broadcastMessage("");
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', allChatClearMessage));
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("gmc") && player.hasPermission("utiltool.gamemode")) {
            if (args.length > 0){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null){
                    target.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', createModeMessage));
                    String targetCreateMessage = ChatColor.translateAlternateColorCodes('&', targetCreateModeMessage);
                    player.sendMessage(targetCreateMessage.replace("%target%", args[0]));
                }
            } else {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', createModeMessage));
                return false;
            }
        }
        if (cmd.getName().equalsIgnoreCase("gms") && player.hasPermission("utiltool.gamemode")) {
            if (args.length > 0){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null){
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', survivalModeMessage));
                    String targetSurvivalMessage = ChatColor.translateAlternateColorCodes('&', targetSurvivalModeMessage);
                    player.sendMessage(targetSurvivalMessage.replace("%target%", args[0]));
                }
            } else {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', survivalModeMessage));
                return false;
            }
        }
        if (cmd.getName().equalsIgnoreCase("gm") && player.hasPermission("utiltool.gamemode")) {
            if (args.length == 1) {
                switch (args[0]) {
                    case "0":
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', survivalModeMessage));
                        break;
                    case "1":
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', createModeMessage));
                        break;
                    case "2":
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', adventureModeMessage));
                        break;
                    case "3":
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', specterModeMessage));
                        break;
                    default:
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageGm0));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageGm1));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageGm2));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageGm3));
                        break;
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageGm0));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageGm1));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageGm2));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageGm3));
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
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', broadcastCommandMessage));
                }
            } else {
                String broadcastMessage = ChatColor.translateAlternateColorCodes('&', broadcastCooldownMessage);
                player.sendMessage(broadcastMessage.replace("%time%", Integer.toString(BroadCasterCooldown.getCooldown(player))));
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
            joinMessage = MessagesManager.get().getString("join_message");
            leaveMessage = MessagesManager.get().getString("leave_message");
            firstTimeJoinMessage = MessagesManager.get().getString("first_time_join_message");
            setSpawnMessage = MessagesManager.get().getString("set_spawn_message");
            teleportMessage = MessagesManager.get().getString("teleport_message");
            unknownCommandMessage = MessagesManager.get().getString("unknown_command_message");
            createModeMessage = MessagesManager.get().getString("create_mode_message");
            survivalModeMessage = MessagesManager.get().getString("survival_mode_message");
            adventureModeMessage = MessagesManager.get().getString("adventure_mode_message");
            specterModeMessage = MessagesManager.get().getString("specter_mode_message");
            targetCreateModeMessage = MessagesManager.get().getString("target_create_mode_message");
            targetSurvivalModeMessage = MessagesManager.get().getString("target_survival_mode_message");
            myChatClearMessage = MessagesManager.get().getString("my_chat_clear_message");
            allChatClearMessage = MessagesManager.get().getString("all_chat_clear_message");
            broadcastCommandMessage = MessagesManager.get().getString("broadcast_command_message");
            broadcastCooldownMessage = MessagesManager.get().getString("broadcast_cooldown_message");
            messageGm0 = MessagesManager.get().getString("message_gm_0");
            messageGm1 = MessagesManager.get().getString("message_gm_1");
            messageGm2 = MessagesManager.get().getString("message_gm_2");
            messageGm3 = MessagesManager.get().getString("message_gm_3");
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