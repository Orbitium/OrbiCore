package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.systems.generator.GeneratorGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GeneratorCommand extends Command {

    public GeneratorCommand(JavaPlugin m) {
        super(m);
        addAlias("jeneratör");
        addAlias("jenerator");
        setUsage("/jeneratör");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        GeneratorGUI.createPanel((Player) sender);
    }

}
