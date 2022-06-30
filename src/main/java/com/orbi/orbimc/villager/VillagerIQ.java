package com.orbi.orbimc.villager;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.database.Repo;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class VillagerIQ {

    static List<Runnable> tasks = new ArrayList<>();

    public static NamespacedKey villagerIQ = new NamespacedKey(OrbiCore.getInstance(), "villagerIQ");
    public static NamespacedKey villagerIQMultiplier = new NamespacedKey(OrbiCore.getInstance(), "villagerIQMultiplier");
    public static NamespacedKey villagerAge = new NamespacedKey(OrbiCore.getInstance(), "villagerAge");

    enum JobIQ {
        FARMER(14, 15, 28, 30),
        BUTCHER(22, 24, 28, 30),
        SHEPARD(25, 24, 28, 30),
        MASON(29, 24, 28, 30),
        TOOLSMITH(30, 24, 28, 30),
        ARMORER(30, 24, 28, 30),
        FLETCHER(33, 24, 28, 30),
        CLERIC(33, 24, 28, 30),
        LIBRARIAN(40, 24, 28, 30);

        private final int[] IQList;

        JobIQ(int... IQList) {
            this.IQList = IQList;
        }

        public int[] getIQList() {
            return IQList;
        }

        public static JobIQ getWithName(String name) {
            for (JobIQ j : values())
                if (j.name().equals(name))
                    return j;
            throw new NullPointerException();
        }

    }

    /*
    IQ Köylülerin satış fiyatlarına eti eder, düşük IQ'lu köylüler daha ucuza çalışır (20 havuç -> para) fakat hızlı zam yapar & stokları yavaş yeniler
                                              yüksek IQ'lu köylüler biraz daha pahalı çalışır(25 havuç -> para) daha az zam yapar

    Zombi köylüden, köylüye döşen köylülerin IQ seviyeleri 15'tir
    Köylüler tarafından doğurulan çocuklarda ise evrim mekanizması uygulanır
        Çocukların IQ'ları evebeynlerin ortalama IQ'larına göre hesaplanır:
        Köylüler tarafından yapılan çocuklar %55 ihtimal ile +%8 IQ'ya sahip olur(evebeynlerine göre) +0.3 IQ çarpanı
        Köylüler tarafından yapılan çocuklar %35 ihtimal ile -%8 IQ'ya sahip olur(evebeynlerine göre) -0.1 IQ çarpanı

    Köylülere kitap verilerek(en az 1 dakika bekleme süresi) IQ'ları arttırılabilir +0.01

    Köylülerin mesleklerinde seviye atlaması için IQ'ya ihtiyaçları vardır, yeterli IQ'ta sahip olduklarında seviye kitabı
    aldıklarında seviye atlarlar

    köylülerin stok yenilemeleri onlara (1.5 dakika sonra) 0.1 * mutiplier IQ kazandırır

    Meslekler için gerekli IQ'lar:
    Çiftçi 22 - 24 - 28 - 36 gibi seviye başına IQ gerekli olur
    Yüncü 22 - 22 - 22 - 22
    Kasap 25,
    Mason 29
    Aletçi 30
    Zırhçı 30
    Okçu 33
    Rahip 33
    Kütüphaneci 40
     */

    public static void bornHandler(EntityBreedEvent e) {
        if (e.getEntity().getType() != EntityType.VILLAGER)
            return;

        PersistentDataContainer pdc = e.getEntity().getPersistentDataContainer();

        double fatherIQ = e.getFather().getPersistentDataContainer().get(villagerIQ, PersistentDataType.DOUBLE);
        double motherIQ = e.getMother().getPersistentDataContainer().get(villagerIQ, PersistentDataType.DOUBLE);
        double fatherIQMultiplier = e.getFather().getPersistentDataContainer().get(villagerIQMultiplier, PersistentDataType.DOUBLE);
        double motherIQMultiplier = e.getMother().getPersistentDataContainer().get(villagerIQMultiplier, PersistentDataType.DOUBLE);
        double childIQ;
        double childIQMultiplier;
        //İşbilirlik seviyesi ekle, köylü bununla level atlasın; yaşına bağlı olarak stok yenileme hızı ve sayısı olsun
        //IQ seviyesi sadece meslek alımında geçerli olsun ve 150 ile limitle

        if (55 >= OrbiCore.getRandom().nextInt(100)) {
            childIQ = (fatherIQ + motherIQ) / 2 + ((fatherIQ + motherIQ) / 2) * 8 / 100;
            childIQMultiplier = (fatherIQMultiplier + motherIQMultiplier) / 2 + (0.30 - (OrbiCore.getRandom().nextInt(60) * 0.01));
        } else {
            childIQ = (fatherIQ + motherIQ) / 2 - ((fatherIQ + motherIQ) / 2) * 8 / 100;
            childIQMultiplier = (fatherIQMultiplier + motherIQMultiplier) / 2 + (0.10 - (OrbiCore.getRandom().nextInt(60) * 0.01));
        }

        pdc.set(villagerIQ, PersistentDataType.DOUBLE, childIQ);
        pdc.set(villagerIQMultiplier, PersistentDataType.DOUBLE, childIQMultiplier);
        pdc.set(villagerAge, PersistentDataType.INTEGER, 18);
    }

    public static void zombieToVillager(EntityTransformEvent e) {
        PersistentDataContainer pdc = e.getEntity().getPersistentDataContainer();
        pdc.set(villagerIQ, PersistentDataType.DOUBLE, 15.0);
        pdc.set(villagerIQMultiplier, PersistentDataType.DOUBLE, 1.0);
        pdc.set(villagerAge, PersistentDataType.INTEGER, 20 + OrbiCore.getRandom().nextInt(36));
    }

    public static void villagerSpawnHandle(EntitySpawnEvent e) {
        PersistentDataContainer pdc = e.getEntity().getPersistentDataContainer();
        if (pdc.isEmpty()) {
            pdc.set(villagerIQ, PersistentDataType.DOUBLE, 15.0);
            pdc.set(villagerIQMultiplier, PersistentDataType.DOUBLE, 1.0);
            pdc.set(villagerAge, PersistentDataType.INTEGER, 18);
        }
    }

    public static void printVillagerInfo(PlayerInteractAtEntityEvent e) {
        Villager villager = (Villager) e.getRightClicked();
        if (villager.isAdult()) {
            ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
            bookMeta.setTitle("Bilgi");
            bookMeta.setAuthor("OrbiCore");
            Entity en = e.getRightClicked();
            PersistentDataContainer pdc = en.getPersistentDataContainer();
            String name = en.getCustomName() == null ? "Bilinmiyor" : en.getCustomName();
            bookMeta.setPages(ChatColor.GOLD + "   Köylüye ait bilgiler" + ChatColor.DARK_AQUA
                    + "\n-------------------"
                    + ChatColor.RESET + "\n\n→ Adı: " + ChatColor.LIGHT_PURPLE + name
                    + ChatColor.RESET + "\n\n→ Yaşı: " + ChatColor.LIGHT_PURPLE + pdc.get(villagerAge, PersistentDataType.INTEGER)
                    + ChatColor.RESET + "\n\n→ Zeka seviyesi: " + ChatColor.LIGHT_PURPLE + pdc.get(villagerIQ, PersistentDataType.DOUBLE) + " IQ"
                    + ChatColor.RESET + "\n\n→ Öğrenim hızı: " + ChatColor.LIGHT_PURPLE + OrbiCore.getDecimalFormat().format(
                    pdc.get(villagerIQMultiplier, PersistentDataType.DOUBLE)) + "x"
                    + ChatColor.RESET + " \n\n→ İş bilgisi: " + ChatColor.LIGHT_PURPLE + (villager.getProfession() != Villager.Profession.NONE ? pdc.get(Parchment.jobPoint, PersistentDataType.INTEGER) :
                    "mevcut bir işe sahip değil")
            );
            bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);
            itemStack.setItemMeta(bookMeta);
            e.getPlayer().openBook(itemStack);
        }
    }

}
