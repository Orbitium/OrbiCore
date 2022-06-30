package com.orbi.orbimc.bone.security.authority;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Cache;

public class PermissionController {

    public static void givePermission(String playerID, Permission permission) {
        MongoBase.setValue(playerID, "permission", permission.getPermissionLevel());
    }

    public static boolean checkPermission(String playerID, Permission permission) {
        return (int) MongoBase.getValue(playerID, "permission") >= permission.getPermissionLevel();
    }

    public static int getPermissionValue(String playerID) {
        return (int) Cache.getAsInt(playerID, "permission");
    }

    public static Permission getPermission(String playerID) {
        int permissionLevel = (int) Cache.getAsInt(playerID, "permission");
        for (Permission permission : Permission.values()) {
            if (permission.getPermissionLevel() == permissionLevel)
                return permission;
        }
        throw new NullPointerException(permissionLevel + " bulunamadı!");
    }

    public static Permission getPermission(int permLevel) {
        for (Permission permission : Permission.values()) {
            if (permission.getPermissionLevel() == permLevel)
                return permission;
        }
        throw new NullPointerException(permLevel + " bulunamadı!");
    }

    public static Permission getPermissionByName(String permName) {
        for (Permission permission : Permission.values()) {
            if (permission.getPermissionName().equals(permName))
                return permission;
        }
        throw new NullPointerException(permName + " bulunamadı!");
    }

}
