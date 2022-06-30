package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.cloudfarm.CloudFarmGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CloudFarmCommands extends Command {

    public CloudFarmCommands(JavaPlugin m) {
        super(m);
        addAlias("buluttarla");
        setUsage("/buluttarla");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        CloudFarmGUI.createPanel((Player) sender);
    }
}
