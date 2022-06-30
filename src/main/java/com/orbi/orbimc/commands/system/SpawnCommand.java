package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.bone.warps.Warp;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand extends Command {

    public SpawnCommand(JavaPlugin m) {
        super(m);
        addAlias("spawn");
        setUsage("/spawn");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Repo.getMSG("going-to-spawn"));
            player.teleport(Warp.warps.get("spawn"));
        }
    }
}
