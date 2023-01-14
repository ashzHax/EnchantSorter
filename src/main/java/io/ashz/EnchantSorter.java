package io.ashz;

import io.ashz.command.enchantsort;
import io.ashz.utility.Message;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EnchantSorter extends JavaPlugin {
    public String pluginName = "PlayerConnection";

    private void handleCommand(String command, CommandExecutor handleFunc) {
        PluginCommand p = this.getCommand(command);

        if(p != null) {
            Message.sendConsoleMessage(this, "no handler found for command \""+command+"\", creating new handler...");
        } else {
            Message.sendConsoleMessage(this, "handler found for command \""+command+"\", trying to overwrite it...");
        }

        p.setExecutor(handleFunc);
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        List<String> list = this.getConfig().getStringList("enchants");
        for(int i=0; i<list.size(); i++) {
            Message.sendConsoleMessage(this, "-> ["+i+"] "+list.get(i));
        }
        handleCommand("enchantsort", new enchantsort(list));

        Message.sendConsoleMessage(this, Message.notification + "starting plugin: "+this.pluginName);
    }
    @Override
    public void onDisable() {
        Message.sendConsoleMessage(this, Message.warning + "ending plugin: "+this.pluginName);
    }
}
