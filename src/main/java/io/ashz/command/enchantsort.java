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
    private List<String> list;
    public enchantsort(List<String> e) {
        this.list = e;
    }

    private boolean hasHigherPriority(Player p, String champ, String challanger) {
        int champ_pos=0;
        int challanger_pos=0;

        for(int i=0; i<this.list.size(); i++) {
            if(Objects.equals(this.list.get(i), champ)) {
                champ_pos = i;
            }
            if(Objects.equals(this.list.get(i), challanger)) {
                challanger_pos = i;
            }
        }

        if(challanger_pos < champ_pos) {
            return true;
        }

        return false;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player commandPlayer;
        ItemStack playerHeldItem;
        Map<Enchantment, Integer> enchantmentMap;
        Set<Enchantment> old_enchantmentList = new LinkedHashSet<Enchantment>();
        Set<Enchantment> new_enchantmentList = new LinkedHashSet<Enchantment>();

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

        old_enchantmentList.addAll(enchantmentMap.keySet());
        int size = old_enchantmentList.size();
        for(int i=0; i<size; i++) {
            Enchantment priority = null;
            for(Enchantment e : old_enchantmentList) {
                // first index handle
                if(priority == null) {
                    priority = e;
                    continue;
                }

                if(hasHigherPriority(commandPlayer, priority.getKey().getKey(), e.getKey().getKey())) {
                    priority = e;
                }
            }
            old_enchantmentList.remove(priority);
            new_enchantmentList.add(priority);

            // clear all enchant from item
            playerHeldItem.removeEnchantment(priority);
        }

        // set enchant on item, get value from old list
        for(Enchantment e : new_enchantmentList) {
            playerHeldItem.addUnsafeEnchantment(e, enchantmentMap.get(e));
        }

        commandPlayer.getInventory().setItemInMainHand(playerHeldItem);

        return true;
    }
}