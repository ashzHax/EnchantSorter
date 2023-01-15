package io.ashz.command;

import io.ashz.utility.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;



public class enchantsort implements CommandExecutor {
    private List<String> configurationList;
    private int configurationListSize;

    public enchantsort(List<String> e) {
        this.configurationList = e;
        this.configurationListSize = e.size();
    }

    private int getPriorityLevel(String key) {
        for(int i=0; i<this.configurationListSize; i++) {
            if(Objects.equals(this.configurationList.get(i), key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player commandPlayer;
        ItemStack playerHeldItem;
        Map<Enchantment, Integer> enchantmentMap;
        Set<Enchantment> new_enchantmentList = new LinkedHashSet<>();
        Set<Enchantment> old_enchantmentList;
        int size;
        Enchantment priority;

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(Message.error+"player only command");
            return false;
        }

        commandPlayer = (Player)commandSender;
        playerHeldItem = commandPlayer.getInventory().getItemInMainHand();
        enchantmentMap = playerHeldItem.getEnchantments();

        if(enchantmentMap.isEmpty()) {
            commandPlayer.sendMessage(Message.error+"you are not holding a enchanted item");
            return false;
        }

        old_enchantmentList = new LinkedHashSet<>(enchantmentMap.keySet());
        size = old_enchantmentList.size();

        for(int i=0; i<size; i++) {
            priority = null;
            for(Enchantment e : old_enchantmentList) {
                // first index handle
                if(priority == null) {
                    priority = e;
                    continue;
                }

                //if(hasHigherPriority(priority.getKey().getKey(), e.getKey().getKey())) {
                if(getPriorityLevel(priority.getKey().getKey()) > getPriorityLevel(e.getKey().getKey())) {
                    priority = e;
                }
            }

            if(priority != null) {
                old_enchantmentList.remove(priority);
                new_enchantmentList.add(priority);

                // clear all enchant from item
                playerHeldItem.removeEnchantment(priority);
            }
        }

        // set enchant on item, get value from old list
        for(Enchantment e : new_enchantmentList) {
            playerHeldItem.addUnsafeEnchantment(e, enchantmentMap.get(e));
        }

        commandSender.sendMessage(Message.notification+"enchantment order has been re-organized");

        return true;
    }
}