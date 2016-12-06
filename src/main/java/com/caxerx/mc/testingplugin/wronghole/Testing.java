package com.caxerx.mc.testingplugin.wronghole;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by caxerx on 2016/8/23.
 */
public class Testing extends JavaPlugin {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        getServer().dispatchCommand(getServer().getConsoleSender(), "Fuck you la");
        return false;
    }
}
