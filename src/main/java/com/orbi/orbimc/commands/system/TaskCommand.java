package com.orbi.orbimc.commands.system;


import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.systems.tasks.PlayerTaskGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TaskCommand extends Command {

    public TaskCommand(JavaPlugin m) {
        super(m);
        addAlias("görevler");
        setUsage("/görevler");
        setUsage("/gorevler");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        PlayerTaskGUI.createPanel((Player) sender);
    }
}