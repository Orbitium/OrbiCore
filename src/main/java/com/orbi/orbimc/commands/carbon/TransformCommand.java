package com.orbi.orbimc.commands.carbon;

import com.orbi.orbimc.commands.root.ArgumentCompleter;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.systems.carbon.CarbonObserver;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TransformCommand extends Command {
    public TransformCommand(JavaPlugin m) {
        super(m);
        addAlias("ayrıştır");
        addAlias("ayristir");
        setUsage("/ayrıştır < /*/**>");
        setPermission(0);
        setTabCompleter(new ArgumentCompleter()
                .set(0, "*", "**", "<dönüştürme adedi>"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (args.length == 0)
            CarbonObserver.convertOneType((Player) sender);
    }
}
