package com.orbi.orbimc.item;

import com.orbi.orbimc.util.Color;
import com.orbi.orbimc.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class P2 {

    /*
        Eşyalar ile yapılan işlem önemli olmayacak(yaratık ve hayvan gibi) ayrımlar yapılmayacak.
        Belirli bloklar/canlılar vs üzerinde yapılan işlemlere göre farklı özellikler XP kazanacak
        Uzun bir süre XP kazanmayan özellikler azalmaya başlayacaklar
     */

    public static List<PotionEffectType> buffPotionEffects = new ArrayList<>(Arrays.asList(
            PotionEffectType.SPEED,
            PotionEffectType.INCREASE_DAMAGE,
            PotionEffectType.DAMAGE_RESISTANCE,
            PotionEffectType.HEAL,
            PotionEffectType.HEALTH_BOOST
    ));

    public static List<PotionEffectType> nerfPotionEffects = new ArrayList<>(Arrays.asList(
            PotionEffectType.SLOW,
            PotionEffectType.WEAKNESS,
            PotionEffectType.POISON,
            PotionEffectType.HARM,
            PotionEffectType.BLINDNESS
    ));

    public static List<ItemStack> valuableItems = new ArrayList<>(Arrays.asList(
            new ItemStack(Material.SADDLE),
            new ItemStack(Material.NAME_TAG)
    ));

    public static List<ItemStack> junkItems = new ArrayList<>(Arrays.asList(
            new ItemStack(Material.STICK),
            new ItemStack(Material.BOWL),
            new ItemStack(Material.STRING),
            new ItemStack(Material.BONE),
            new ItemStack(Material.LEATHER),
            new ItemStack(Material.INK_SAC)
    ));

    public static List<ItemStack> fihes = new ArrayList<>(Arrays.asList(
            new ItemStack(Material.COD),
            new ItemStack(Material.SALMON),
            new ItemStack(Material.TROPICAL_FISH),
            new ItemStack(Material.PUFFERFISH)
    ));


    public static void t(Player player, Object sender, int xpEarn, int start, int size, Object event, int... triggerIndex) {
        ItemStack itemInUse = player.getInventory().getItemInMainHand();
        ItemMeta im = itemInUse.getItemMeta();
        PersistentDataContainer pdc = im.getPersistentDataContainer();

        //Eşyaya ait hiçbir veri yoksa
        if (pdc.isEmpty()) {
            register(pdc, itemInUse, im, start, size);
            return;
        }

        //Veriler çekiliyor
        int[] cXPs = pdc.get(ItemKeys.cXPs, PersistentDataType.INTEGER_ARRAY);
        int triggeredIndex;
        for (int index : triggerIndex) {
            triggeredIndex = index;
            int currentXP = cXPs[triggeredIndex] + xpEarn;
            int needXP = pdc.get(ItemKeys.nXPs, PersistentDataType.INTEGER_ARRAY)[triggeredIndex];

            //Eğer eşya son seviye değil ise
            if (currentXP != -1) {
                if (currentXP < needXP) { //Eşya seviye atlamayacak ise
                    if (ItemProgress.values()[triggeredIndex].canEarn(sender)) {
                        cXPs[triggeredIndex] += xpEarn;
                        pdc.set(ItemKeys.cXPs, PersistentDataType.INTEGER_ARRAY, cXPs);
                    }
                } else { //Eğer seviye atlayacak ise
                    player.sendMessage("Seviye atladı");
                    int[] nXPs = pdc.get(ItemKeys.nXPs, PersistentDataType.INTEGER_ARRAY);
                    int[] mXPs = pdc.get(ItemKeys.mXPs, PersistentDataType.INTEGER_ARRAY);

                    int[] maxLevels = pdc.get(ItemKeys.maxLevels, PersistentDataType.INTEGER_ARRAY);
                    int[] currentLevels = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY);

                    int currentLevel = currentLevels[index] += 1;
                    if (currentLevel == maxLevels[index])
                        cXPs[index] = -2;
                    else
                        cXPs[index] -= needXP;
                    nXPs[index] *= mXPs[index];

                    pdc.set(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY, currentLevels);
                    pdc.set(ItemKeys.nXPs, PersistentDataType.INTEGER_ARRAY, nXPs);
                    pdc.set(ItemKeys.cXPs, PersistentDataType.INTEGER_ARRAY, cXPs);

                    List<String> lore = im.getLore();
                    String[] s = lore.get(1 + index).split(":");

                    s[1] = Color.translateHex("#d6d6d6: #ffff55[#39cc4f" + "■".repeat(currentLevel) + "#AA0000" + "□".repeat(maxLevels[index] - currentLevel) + "#ffff55]");
                    lore.set(1 + index, s[0] + s[1]);
                    saveItem(itemInUse, im, lore);
                    for (int i : triggerIndex) {
                        if (pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[i] >= 1)
                            ItemProgress.values()[i].invoke(event);
                    }
                    return;
                }
            }
        }
        im.setLore(ItemProgress.updateStat(player.getInventory().getItemInMainHand()));
        itemInUse.setItemMeta(im);

        for (int i : triggerIndex) {
            if (pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[i] >= 1)
                ItemProgress.values()[i].invoke(event);
        }

    }

    public static void saveItem(ItemStack i, ItemMeta im, List<String> lore) {
        i.setItemMeta(im);
        ItemBuilder ib = new ItemBuilder(i);
        ib.setLore(lore);
        ib.build();
    }

    public static void register(PersistentDataContainer pdc, ItemStack itemInUse, ItemMeta im, int start, int size) {
        int c = 0;
        int[] needXPs = new int[size];
        int[] multiplierXPs = new int[size];
        int[] maxLevels = new int[size];

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GOLD + "➤ Özel eşya gelişimleri:");
        for (int i = start; i < start + size; i++) {
            lore.add(ItemProgress.values()[i].getDefaultLore());
            needXPs[c] = ItemProgress.values()[i].getMinXP();
            multiplierXPs[c] = ItemProgress.values()[i].getXpMultiplier();
            maxLevels[c] = ItemProgress.values()[i].getMaxLevel();
            c++;
        }
        pdc.set(ItemKeys.cXPs, PersistentDataType.INTEGER_ARRAY, new int[size]);
        pdc.set(ItemKeys.nXPs, PersistentDataType.INTEGER_ARRAY, needXPs);
        pdc.set(ItemKeys.mXPs, PersistentDataType.INTEGER_ARRAY, multiplierXPs);
        pdc.set(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY, new int[size]);
        pdc.set(ItemKeys.maxLevels, PersistentDataType.INTEGER_ARRAY, maxLevels);
        lore.add("");
        lore.add(ChatColor.GOLD + "➤ Eşya istatislikleri: ");
        lore.add(ChatColor.GRAY + "* 1 kez kullanıldı.");

        saveItem(itemInUse, im, lore);
    }

    public static void earnXP(Player player, Object sender, int start, int size, PersistentDataContainer pdc, int[] cXPs, int triggerIndex, int xpEarn) {
        for (int i = start; i < start + size; i++) {
            if (ItemProgress.values()[i].canEarn(sender)) {
                cXPs[triggerIndex] += xpEarn;
                pdc.set(ItemKeys.cXPs, PersistentDataType.INTEGER_ARRAY, cXPs);
                ItemProgress.updateStat(player.getInventory().getItemInMainHand());
            }
        }
    }

    public static List<String> levelUp(ItemStack it, PersistentDataContainer pdc, int triggerIndex, int[] cXPs, int needXP) {
        int[] nXPs = pdc.get(ItemKeys.nXPs, PersistentDataType.INTEGER_ARRAY);
        int[] mXPs = pdc.get(ItemKeys.mXPs, PersistentDataType.INTEGER_ARRAY);

        int[] maxLevels = pdc.get(ItemKeys.maxLevels, PersistentDataType.INTEGER_ARRAY);
        int[] currentLevels = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY);

        int currentLevel = currentLevels[triggerIndex] += 1;
        if (currentLevel == maxLevels[triggerIndex])
            cXPs[triggerIndex] = -1;
        else
            cXPs[triggerIndex] -= needXP;
        nXPs[triggerIndex] *= mXPs[triggerIndex];

        pdc.set(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY, currentLevels);
        pdc.set(ItemKeys.nXPs, PersistentDataType.INTEGER_ARRAY, nXPs);
        pdc.set(ItemKeys.cXPs, PersistentDataType.INTEGER_ARRAY, cXPs);

        List<String> lore = it.getItemMeta().getLore();
        String[] s = lore.get(1 + triggerIndex).split(":");

        s[1] = Color.translateHex("#d6d6d6: #ffff55[#39cc4f" + "■".repeat(currentLevel) + "#AA0000" + "□".repeat(maxLevels[triggerIndex] - currentLevel) + "#ffff55]");
        lore.set(1 + triggerIndex, s[0] + s[1]);
        return lore;
    }
}
