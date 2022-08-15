package net.teamuni.playerjoinandleave;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class BroadCasterCooldown {

    public static HashMap<UUID, Double> cooldowns;

    public static void setupCooldown(){
        cooldowns = new HashMap<>();
    }

    //쿨타임 설정
    public static void setCooldown(Player player, int seconds){
        double delay = System.currentTimeMillis() + (seconds * 1000L);
        cooldowns.put(player.getUniqueId(), delay);
    }
    //쿨타임 계산
    public static int getCooldown(Player player){
        return Math.toIntExact(Math.round((cooldowns.get(player.getUniqueId()) - System.currentTimeMillis())/1000));
    }

    //쿨타임 체크
    public static boolean checkCooldown(Player player){
        if(!cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) <= System.currentTimeMillis()){
            return true;
        }
        return false;
    }
}