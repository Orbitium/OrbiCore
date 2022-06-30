package com.orbi.orbimc.systems.playerreference;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.StringParser;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerReferenceData {

    public static String getPlayerReferenced(Player player) {
        return MongoBase.getValue(player, "referencedPlayer", null) + "";
    }

    public static List<String> getPlayerReferencers(Player player) {
        return (List<String>) MongoBase.getValue(player, "referencers", new ArrayList<>());
    }

    public static void addReferencer(Player sender, Player sent) {
        changeReferenced(sender, sent);
        List<String> referencer = getPlayerReferencers(sent);
        referencer.add(sender.getUniqueId().toString());
        MongoBase.setValue(sent, "referencers", referencer);
    }

    public static void changeReferenced(Player sender, Player newReference) {
        MongoBase.setValue(sender, "referencedPlayer", newReference.getUniqueId().toString());
        sender.sendMessage(Repo.getMSG("rf-sent-success"));
        newReference.sendMessage(StringParser.parse(Repo.getMSG("rf-get-reference"), sender.getName()));
    }

    /*
                        cSender.sendMessage(Repo.getMSG("rf-sent-success"));
                    sent.sendMessage(StringParser.parse(Repo.getMSG("rf-get-reference"), cSender.getName()));
                } else {
                    Player referencingPlayer = Bukkit.getPlayer(UUID.fromString(PlayerReferenceData.getPlayerReferenced(cSender)));
                    cSender.sendMessage(StringParser.parse(Repo.getMSG("rs-reference-filled"), referencingPlayer.getName()));
                }
     */

}
