package com.orbi.orbimc.commands.carbon;

import com.orbi.orbimc.commands.root.ArgumentCompleter;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.carbon.CarbonData;
import com.orbi.orbimc.util.StringParser;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CarbonCommand extends Command {
    public CarbonCommand(JavaPlugin m) {
        super(m);
        addAlias("karbon");
        addAlias("k");
        setUsage("/karbon");
        setPermission(0);
        setTabCompleter(new ArgumentCompleter()
                .set(0, "gönder", "miktar")
                .set(1, Bukkit.getOnlinePlayers())
                .set(2, "<gönderilecek miktar>"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        Player cSender = (Player) sender;
        if (args.length == 0)
            cSender.sendMessage(StringParser.parse(Repo.getText("cs-available-carbon"), CarbonData.getCarbon(cSender)));
        else if (args.length == 3 && args[0].equals("gönder") || args.length == 3 && args[0].equals("gonder")) {
            int carbonSent = Integer.parseInt(args[2]);
            if (carbonSent <= 0)
                return;
            if (CarbonData.getCarbon(cSender) > carbonSent) {
                Player sent = Bukkit.getPlayer(args[1]).getPlayer();
                /*
                if (sent.equals((Player) sender))
                    return;
                 */
                Cache.decreaseLongValue(cSender.getName(), "availableCarbon", Integer.parseInt(args[2]));
                Cache.increaseLongValue(sent.getName(), "availableCarbon", carbonSent);

                cSender.sendMessage(Repo.getMSG("cs-success-task"));
                sent.sendMessage(String.format(Repo.getMSG("cs-carbon-came"), cSender.getPlayerListName(), carbonSent));
            } else
                cSender.sendMessage(Repo.getMSG("cs-insufficient-carbon"));
        }

    }
}