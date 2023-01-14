package io.ashz.utility;

import io.ashz.EnchantSorter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Message {
    public static String notification = ChatColor.GRAY+"["+
            ChatColor.GREEN+"!"+
            ChatColor.GRAY+"] ";
    public static String warning = ChatColor.GRAY+"["+
            ChatColor.YELLOW+"!"+
            ChatColor.GRAY+"] "+
            ChatColor.YELLOW;
    public static String error = ChatColor.GRAY+"["+
            ChatColor.RED+"!"+
            ChatColor.GRAY+"] "+
            ChatColor.RED;

    public static void sendConsoleMessage(EnchantSorter plugin, String msg) {
        plugin.getServer().getConsoleSender().sendMessage(msg);
    }

    public static void sendBroadcast(EnchantSorter plugin, String msg) {
        plugin.getServer().broadcastMessage(msg);
    }

    public static void sendAdminBroadcast(EnchantSorter plugin, String msg) {
        Collection<? extends Player> playerCollection = plugin.getServer().getOnlinePlayers();
        for(Player p : playerCollection) {
            if(p.isOp()) { // TODO: need a better permission checking code
                p.sendMessage(msg);
            }
        }
    }

    public static void sendNonAdminBroadcast(EnchantSorter plugin, String msg) {
        Collection<? extends Player> playerCollection = plugin.getServer().getOnlinePlayers();
        for(Player p : playerCollection) {
            if(!p.isOp()) { // TODO: need a better permission checking code
                p.sendMessage(msg);
            }
        }
    }
}
