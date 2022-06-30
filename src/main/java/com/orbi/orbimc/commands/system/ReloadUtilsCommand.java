package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.YAMLBase;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadUtilsCommand extends Command {
    public ReloadUtilsCommand(JavaPlugin m) {
        super(m);
        addAlias("orbload");
        setUsage("/orbload");
        setPermission(9999);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        YAMLBase.loadValues();
        //CarbonData.loadItemValues();
        sender.sendMessage(ChatColor.GREEN + "İşlem tamamlandı!");
    }
}
