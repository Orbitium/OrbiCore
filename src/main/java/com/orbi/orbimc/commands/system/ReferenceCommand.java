package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.carbon.CarbonReference;
import com.orbi.orbimc.systems.cloudfarm.CloudFarmGUI;
import com.orbi.orbimc.systems.playerreference.PlayerReferenceData;
import com.orbi.orbimc.util.StringParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ReferenceCommand extends Command {

    public ReferenceCommand(JavaPlugin m) {
        super(m);
        addAlias("referans");
        setUsage("/referans <oyuncu/liste>");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (args.length == 1) {
            if (args[0].equals("liste") || args[0].equals("list")) {
                List<String> playerReferencers = new ArrayList<>();
                Player player;
                for (String s : PlayerReferenceData.getPlayerReferencers((Player) sender)) {
                    player = Bukkit.getPlayer(UUID.fromString(s));
                    if (player == null)
                        playerReferencers.add(Bukkit.getOfflinePlayer(UUID.fromString(s)).getName());
                    else
                        playerReferencers.add(player.getName());
                }
                sender.sendMessage(StringParser.parse(Repo.getMSG("rs-reference-list"), playerReferencers));
                return;
            }
            Player cSender = (Player) sender;
            Player sent = Bukkit.getPlayer(args[0]);
            if (sent != null && cSender != sent) {
                if (PlayerReferenceData.getPlayerReferenced(cSender).equals("null")) {
                    PlayerReferenceData.addReferencer(cSender, sent);
                } else {
                    try {
                        Player referencingPlayer = Bukkit.getPlayer(UUID.fromString(PlayerReferenceData.getPlayerReferenced(cSender)));
                        cSender.sendMessage(StringParser.parse(Repo.getMSG("rs-reference-filled"), referencingPlayer.getName()));
                    } catch (NullPointerException e) {
                        PlayerReferenceData.addReferencer(cSender, sent);
                    }
                }
            } else
                cSender.sendMessage(Repo.getMSG("rs-reference-error"));
        }
    }
}
