package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.rhodium.RhodiumGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RhodiumCommand extends Command {

    public RhodiumCommand(JavaPlugin m) {
        super(m);
        addAlias("rodyum");
        setUsage("/rodyum");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        RhodiumGUI.createPanel((Player) sender);
    }
}
