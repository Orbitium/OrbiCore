package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.systems.fly.FlyGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyCommands extends Command {
    public FlyCommands(JavaPlugin m) {
        super(m);
        addAlias("uçuş");
        addAlias("ucus");
        addAlias("u");
        setUsage("/uçuş");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        FlyGUI.createPanel((Player) sender);
    }
}
