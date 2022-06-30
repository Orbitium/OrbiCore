package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.playeritem.PlayerItemGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerItemCommands extends Command {

    public PlayerItemCommands(JavaPlugin m) {
        super(m);
        addAlias("depo");
        setUsage("/depo");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        PlayerItemGUI.createPanel((Player) sender);
    }
}
