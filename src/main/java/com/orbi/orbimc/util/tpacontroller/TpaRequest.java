package com.orbi.orbimc.util.tpacontroller;

import org.bukkit.entity.Player;

public class TpaRequest {

    private Player sent;
    private int sentTime;

    public Player getSent() {
        return sent;
    }

    public void setSent(Player sent) {
        this.sent = sent;
    }

    public int getSentTime() {
        return sentTime;
    }

    public void setSentTime(int sentTime) {
        this.sentTime = sentTime;
    }

    public TpaRequest(Player sent, int sentTime) {
        this.sent = sent;
        this.sentTime = sentTime;
    }
}
