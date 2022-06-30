package com.orbi.orbimc.util.tpacontroller;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.StringParser;
import com.orbi.orbimc.util.TimeController;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TpaController {

    static Map<Player, TpaRequest> tpaRequestMap = new HashMap<>();

    public static void sendTpaRequest(Player sender, Player sentPlayer) {
        boolean isFilledSentPlayer = false;
        if (sender.equals(sentPlayer))
            return;
        else if (tpaRequestMap.containsKey(sender)) {
            if (TimeController.dateToTimeValue() - tpaRequestMap.get(sender).getSentTime() < 20) {//Sender yakın zamanda başkasına tpa gönderdiyse
                sender.sendMessage(Repo.getMSG("tpa-sent-filled"));
                return;
            }
        }
        for (TpaRequest tpaRequest : tpaRequestMap.values()) {
            if (tpaRequest.getSent().equals(sentPlayer)) {
                if (TimeController.dateToTimeValue() - tpaRequest.getSentTime() < 20) {
                    isFilledSentPlayer = true;
                    sender.sendMessage(Repo.getMSG("tpa-filled"));
                } else {
                    isFilledSentPlayer = false;
                    tpaRequestMap.remove(sender);
                    break;
                }
            }
        }

        if (!isFilledSentPlayer) {
            sender.sendMessage(StringParser.parse(Repo.getMSG("tpa-sent"), sentPlayer.getName()));
            tpaRequestMap.put(sender, new TpaRequest(sentPlayer, TimeController.dateToTimeValue()));
            sentPlayer.sendMessage(StringParser.parse(Repo.getMSG("tpa-came"), sender.getName()));
        }
    }

    public static void confirmRequest(Player accepter) {
        for (Map.Entry<Player, TpaRequest> entry : tpaRequestMap.entrySet()) {
            if (entry.getValue().getSent().equals(accepter)) {
                Player sender = entry.getKey();
                sender.teleport(accepter.getLocation());
                tpaRequestMap.remove(sender);
                accepter.sendMessage(Repo.getMSG("tpa-confirm"));
                sender.sendMessage(Repo.getMSG("tpa-confirm"));
                return;
            }
        }
        accepter.sendMessage(Repo.getMSG("tpa-not-came"));
    }
}
