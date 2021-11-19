package me.dayofpay.opmodify;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.io.Console;
import java.util.Iterator;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OPModify extends JavaPlugin implements Listener {
    FileConfiguration config;
    File cfile;

    public void onEnable() {
        this.config = this.getConfig();
        this.config.options().copyDefaults(true);
        this.saveConfig();
        this.cfile = new File(this.getDataFolder(), "config.yml");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Съобщение] OPModify зареди успешно :)");
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[Съобщение] Съпорт към плъгина може да откриете в https://discord.magmacraft.club");
        Bukkit.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
    }
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Съобщение] OPModify се изключи успешно :)");
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[Съобщение] Съпорт към плъгина може да откриете в https://discord.magmacraft.club");
    }
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!sender.hasPermission("magma.seemessages")) {
            sender.sendMessage(ChatColor.RED + "Нямаш право на това действие!");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("magmamessage")) {
            sender.sendMessage(ChatColor.BLUE + "Резултат: " + this.getConfig().getString(ChatColor.translateAlternateColorCodes('&',"message")));
            return true;
        }
        if (!sender.hasPermission("magmaperms.setmessage")) {
            sender.sendMessage(ChatColor.RED + "Нямаш право да добавяш блокирани команди!");
            return true;
        }
        if (!cmd.getName().equalsIgnoreCase("magmaresetmessage")) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Моля, задайте съобщение!");
            return true;
        }
        final StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            str.append(String.valueOf(args[i]) + " ");
        }
        final String cmsg = str.toString();
        this.getConfig().set("message", (Object) cmsg);
        this.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "Съобщението беше зададено на: " + cmsg);
        return true;
    }

    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent e) {
        final String msg = e.getMessage().toLowerCase();
        for (final String blockedCommand : this.getConfig().getStringList("commands")) {
            if (msg.contains(blockedCommand.toLowerCase())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',this.config.getString("message")));
            }
        }
    }
}