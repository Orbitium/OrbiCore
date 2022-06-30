package com.orbi.orbimc.commands.system;


import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.energy.EnergyGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class EnergyCommands extends Command {

    public EnergyCommands(JavaPlugin m) {
        super(m);
        addAlias("enerji");
        setUsage("/enerji");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        EnergyGUI.createPanel((Player) sender);
    }
}
