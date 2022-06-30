package com.orbi.orbimc.bone.logger;

public enum DiscordChannel {

    INOUT(937849020200661082L),
    BREAKBLOCK(937849682686771330L),
    PLACEBLOCK(937850921604493382L),
    CHAT(937849949188665405L),
    COMMAND(937850805279686696L),
    ITEMSHARE(938019111097761802L);

    private long channelID;

    public long getChannelID() {
        return channelID;
    }

    DiscordChannel(long channelID) {
        this.channelID = channelID;
    }
}
