package com.orbi.orbimc.util;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.villager.Parchment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class StaticItems {
    public static ItemStack spaceItem;
    public static ItemStack spaceItem2;
    public static ItemStack carbonBox;
    public static ItemStack mobSoul;
    public static ItemStack infoItem;
    public static ItemStack negative;
    public static ItemStack positive;

    public static ItemStack villagerCoin;

    //Parchment
    public static ItemStack reloadTradesParchment;
    public static ItemStack removeJobParchment;
    public static ItemStack packetVillagerParchment;

    public static ItemStack farmerParchment;
    public static ItemStack butcherParchment;
    public static ItemStack toolSmithParchment;
    public static ItemStack librarianParchment;
    public static ItemStack armorerParchment;
    public static ItemStack clericParchment;
    public static ItemStack shepardParchment;
    public static ItemStack masonParchment;
    public static ItemStack fletcherParchment;

    //tier1 -> like farmerParchment
    public static ItemStack tier2JobBook;
    public static ItemStack tier3JobBook;
    public static ItemStack tier4JobBook;
    public static ItemStack tier5JobBook;


    public static void loadItems() {
        String carbonBoxHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJmOGJhN2U1NTZjYmJhZTk2NTg5ZWIwY2EyMGM2OWMxOWM3Y2U0Njg0NmM0NTVhMDE2ZDQ3Y2E5MmVkNWE1NSJ9fX0=";
        String mobSoulHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWFlOGI5ZThlZTUwNWEwODRlMTk1ODg2NjhhOGVjN2ZkODM3YjhiNDk0MjA5ODc0N2I0YTAyZWE2MDkwNTNjIn19fQ==";
        spaceItem = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, " ").build();
        spaceItem2 = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ").build();
        carbonBox = new ItemBuilder(carbonBoxHead, Repo.getText("carbon-box-name"), Repo.getText("carbon-box-lore")).build();
        mobSoul = new ItemBuilder(mobSoulHead, ChatColor.DARK_PURPLE + "Yarat??k ruhu").build();
        infoItem = new ItemBuilder(Material.BOOK, ChatColor.GOLD + "Bilgi almak i??in t??klay??n").build();
        negative = new ItemBuilder(Material.GREEN_WOOL, ChatColor.GREEN + "????lemi tamamla!").build();
        positive = new ItemBuilder(Material.RED_WOOL, ChatColor.RED + "????lemi iptal et!").build();

        villagerCoin = new ItemBuilder(Material.GOLD_NUGGET, ChatColor.GOLD + "K??yl?? sikkesi").build();

        List<String> parchmentLore = new ArrayList<>(Arrays.asList(ChatColor.AQUA + "??? K??yl??ye meslek verir.",
                ChatColor.AQUA + "??? Bilgi almak i??in /bilgi k??yl??ler"));
        List<String> reloadParchmentLore = new ArrayList<>(Arrays.asList(ChatColor.AQUA + "??? K??yl??lerin t??m tekliflerinin tekrar olu??turur.",
                ChatColor.AQUA + "??? Bilgi almak i??in /bilgi k??yl??ler"));
        List<String> removeJobParchmentLore = new ArrayList<>(Arrays.asList(ChatColor.AQUA + "??? K??yl??n??n mesle??ini siler.",
                ChatColor.AQUA + "??? Bilgi almak i??in /bilgi k??yl??ler"));
        List<String> packageVillagerParchmentLore = new ArrayList<>(Arrays.asList(ChatColor.AQUA + "??? K??yl??y?? ??a????rma yumurtas?? haline getirir.",
                ChatColor.AQUA + "??? Bilgi almak i??in /bilgi k??yl??ler"));

        reloadTradesParchment = new ItemBuilder(Material.MAP, ChatColor.LIGHT_PURPLE + "??? Yenileme par??omeni", reloadParchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        removeJobParchment = new ItemBuilder(Material.MAP, ChatColor.LIGHT_PURPLE + "??? Meslek kald??rma par??omeni", removeJobParchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        packetVillagerParchment = new ItemBuilder(Material.MAP, ChatColor.LIGHT_PURPLE + "??? Saklama par??omeni", packageVillagerParchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();

        farmerParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? ??ift??i par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        butcherParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? Kasap par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        toolSmithParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? Alet??i par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        librarianParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? K??t??phaneci par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        armorerParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? Z??rh???? par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        clericParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? Rahip par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        shepardParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? ??oban par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        masonParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? Mason par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        fletcherParchment = new ItemBuilder(Material.MAP, ChatColor.GOLD + "??? Ok??u par??omeni", parchmentLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();

        List<String> tierBookLore = new ArrayList<>(Arrays.asList(ChatColor.AQUA + "??? Yeterli bilgiye sahip k??yl??y?? seviye atlat??r.",
                ChatColor.AQUA + "??? Bilgi almak i??in /bilgi k??yl??ler"));
        tier2JobBook = new ItemBuilder(Material.WRITTEN_BOOK, ChatColor.BLUE + "Seviye 2 i????i kitab??", tierBookLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        tier3JobBook = new ItemBuilder(Material.WRITTEN_BOOK, ChatColor.BLUE + "Seviye 3 i????i kitab??", tierBookLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        tier4JobBook = new ItemBuilder(Material.WRITTEN_BOOK, ChatColor.BLUE + "Seviye 4 i????i kitab??", tierBookLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
        tier5JobBook = new ItemBuilder(Material.WRITTEN_BOOK, ChatColor.BLUE + "Seviye 5 i????i kitab??", tierBookLore, true).
                updatePDC(Parchment.parchmentItem, PersistentDataType.BYTE, (byte) 1).build();
    }

    public static ItemStack setAmount(ItemStack i, int amount) {
        i = new ItemStack(i);
        i.setAmount(amount);
        return i;
    }

    private static final List<Material> swords = new ArrayList<>(Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD,
            Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_AXE));

    public static boolean isSword(Material type) {
        for (Material m : swords) {
            if (m.equals(type))
                return true;
        }
        return false;
    }

}
