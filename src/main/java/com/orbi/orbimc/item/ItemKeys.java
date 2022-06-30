package com.orbi.orbimc.item;

import com.orbi.orbimc.OrbiCore;
import org.bukkit.NamespacedKey;

public class ItemKeys {
    public static NamespacedKey progressID = new NamespacedKey(OrbiCore.getInstance(), "progressID");
    public static NamespacedKey isMainProgress = new NamespacedKey(OrbiCore.getInstance(), "isMainProgress");

    public static NamespacedKey progressXPNeed = new NamespacedKey(OrbiCore.getInstance(), "progressXPNeed");
    public static NamespacedKey progressXPAdvance = new NamespacedKey(OrbiCore.getInstance(), "progressXPAdvance");
    public static NamespacedKey progressMaxLevel = new NamespacedKey(OrbiCore.getInstance(), "progressMaxLevel");

    //Arraylistler
    public static NamespacedKey cXPs = new NamespacedKey(OrbiCore.getInstance(), "cXPs");
    public static NamespacedKey nXPs = new NamespacedKey(OrbiCore.getInstance(), "nXPs");
    public static NamespacedKey mXPs = new NamespacedKey(OrbiCore.getInstance(), "mXPs");
    public static NamespacedKey cLevels = new NamespacedKey(OrbiCore.getInstance(), "cLevels");
    public static NamespacedKey maxLevels = new NamespacedKey(OrbiCore.getInstance(), "maxLevels");

    public static NamespacedKey noDrop = new NamespacedKey(OrbiCore.getInstance(), "noDrop");

    public static NamespacedKey buffPotionRemainingCounter = new NamespacedKey(OrbiCore.getInstance(), "buffPotionRemainingCounter");
    public static NamespacedKey buffLastPotion = new NamespacedKey(OrbiCore.getInstance(), "buffLastPotion");

    public static NamespacedKey nerfPotionRemainingCounter = new NamespacedKey(OrbiCore.getInstance(), "nerfPotionRemainingCounter");
    public static NamespacedKey nerfLastPotion = new NamespacedKey(OrbiCore.getInstance(), "nerfLastPotion");


}
