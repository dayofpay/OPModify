package me.dayofpay.opmodify;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class Events implements org.bukkit.event.Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        if(event.getPlayer().isOp()){
            Player magmaPlayer = event.getPlayer();
            magmaPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a&oOPModify беше прикрепен към вашият username"));
        }
    }
}
