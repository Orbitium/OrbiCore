package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.element.laboratory.LaboratoryGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ElementCommands extends Command {

    public ElementCommands(JavaPlugin m) {
        super(m);
        addAlias("element");
        setUsage("/element");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        LaboratoryGUI.createPanel((Player) sender);
    }
}
