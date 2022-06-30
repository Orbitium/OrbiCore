package com.orbi.orbimc.bone.security.authority;

public enum Permission {

    //Root
    ADMIN("#de0f00Yönetici", 9999, "1"),
    MODERATOR("#b88102Mod", 9998, "b"),
    GUILDER("#afddffRehber", 9997, "c"),
    //Ranks
    HYDROGEN("#03A9F4Hidrojen", 0, "p"),
    HELIUM("#d9ecfcHelyum", 1, "o"),
    LITHIUM("#1b1e21Lityum", 2, "n"),
    BERYLLIUM("Berilyum", 3, "m"),
    BORON("Bor", 4, "l"),
    CARBON("Karbon", 5, "h"),
    NITROGEN("Nitrojen", 6, "g"),
    OXYGEN("Oksijen", 7, "f"),
    FLUORINE("Flor", 8, "e"),
    NEON("Neon", 9, "d");


    private final String permissionName;
    private final int permissionLevel;
    private final String scoreboardValue;

    Permission(String permissionName, int permissionLevel, String scoreboardValue) {
        this.permissionName = permissionName;
        this.permissionLevel = permissionLevel;
        this.scoreboardValue = scoreboardValue;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public String getScoreboardValue() {
        return scoreboardValue;
    }

}


