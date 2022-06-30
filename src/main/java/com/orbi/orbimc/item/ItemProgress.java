package com.orbi.orbimc.item;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.systems.mob.MobSoul;
import com.orbi.orbimc.util.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.entity.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.function.Consumer;

public enum ItemProgress {

    SOUL_HARVEST("Ruh hasatı", 15, 3, 10, e -> {
        EntityDeathEvent event = (EntityDeathEvent) e;
        Player p = event.getEntity().getKiller();
        ItemStack i = p.getInventory().getItemInMainHand();
        PersistentDataContainer pdc = i.getItemMeta().getPersistentDataContainer();

        int mainLevel = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[0];
        int multiplier1 = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[1];
        int multiplier2 = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[2];

        double earn = mainLevel * 0.1;

        if (multiplier1 * 10 > OrbiCore.getRandom().nextInt(100)) {
            event.getEntity().getPersistentDataContainer().set(ItemKeys.noDrop, PersistentDataType.INTEGER, 1);
            earn += earn * (multiplier1 / 3);
        }

        earn += earn * (multiplier2 * 0.3);
        MobSoul.earnSoul(p, earn);
    }, CraftZombie.class),

    SOUL_HARVEST_EFFICIENCY("Ruh esiri", 15, 4, 10, null, CraftSkeleton.class, CraftSpider.class),

    SOUL_HARVEST_MULTIPLIER("Ruh avcısı", 7, 3, 10, null, CraftVillagerZombie.class),

    MIDAS("Midas", 25, 2, 10, e -> {
        EntityDeathEvent event = (EntityDeathEvent) e;
        ItemStack i = event.getEntity().getKiller().getInventory().getItemInMainHand();
        PersistentDataContainer pdc = i.getItemMeta().getPersistentDataContainer();

        int mainLevel = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[3];
        ItemStack drop = null;
        if (mainLevel * 8 >= OrbiCore.getRandom().nextInt(100)) {
            int r = OrbiCore.getRandom().nextInt(100);
            switch (mainLevel) {
                case 1:
                case 2:
                    drop = new ItemStack(Material.COAL);
                    break;
                case 3:
                case 4:
                    if (r > 85)
                        drop = new ItemStack(Material.COAL);
                    else
                        drop = new ItemStack(Material.IRON_INGOT);
                    break;
                case 5:
                case 6:
                    if (r > 40)
                        drop = new ItemStack(Material.IRON_INGOT);
                    else
                        drop = new ItemStack(Material.GOLD_INGOT);
                    break;
                case 7:
                case 8:
                    drop = new ItemStack(Material.IRON_INGOT);
                    break;
                case 9:
                case 10:
                    if (r > 60)
                        drop = new ItemStack(Material.GOLD_INGOT);
                    else
                        drop = new ItemStack(Material.DIAMOND);
                    break;


            }

            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), drop);
        }
    }, CraftTurtle.class),

    COPYCAT("Taklit", 14, 4, 10, e -> {
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;

        if (event.getEntity() instanceof Animals) {
            PersistentDataContainer pdc = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            int mainLevel = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[4];

            Entity entity = event.getEntity();

            if (mainLevel * 3 >= OrbiCore.getRandom().nextInt(100)) {
                LivingEntity ent = (LivingEntity) entity;
                (ent).setHealth(ent.getHealth() - 2);
                entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());
            }
        }

    }, CraftChicken.class, CraftRabbit.class),

    COPIER("Kopya", 5, 4, 10, e -> {
        EntityDeathEvent event = (EntityDeathEvent) e;

        Player en = event.getEntity().getKiller();
        PersistentDataContainer pdc = en.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        int mainLevel = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[5];

        if (mainLevel * 6.5 >= OrbiCore.getRandom().nextInt(100)) {
            en.getInventory().addItem(new ItemStack(Material.getMaterial(event.getEntity().getType() + "_SPAWN_EGG")));
        }

    }, CraftBat.class),

    HEARTH_BROKER("Bileme", 60, 3, 10, e -> {
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;

        PersistentDataContainer pdc = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        int mainLevel = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[6];

        if (mainLevel * 10 >= OrbiCore.getRandom().nextInt(100)) {
            event.setDamage(event.getDamage() + (1 + mainLevel / 3));
        }

    }, CraftIronGolem.class),

    WIZARD("Büyücü", 23, 5, 10, e -> {
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;

        PersistentDataContainer pdc = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        PersistentDataContainer pdc2 = event.getDamager().getPersistentDataContainer();

        int mainLevel = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[7];
        if (pdc2.get(ItemKeys.buffPotionRemainingCounter, PersistentDataType.INTEGER) == null)
            pdc2.set(ItemKeys.buffPotionRemainingCounter, PersistentDataType.INTEGER, 31 - mainLevel);

        int count = pdc2.get(ItemKeys.buffPotionRemainingCounter, PersistentDataType.INTEGER) - 1;

        if (count == 0) {
            int lastPotion = pdc2.get(ItemKeys.buffLastPotion, PersistentDataType.INTEGER);
            ((Player) event.getDamager()).addPotionEffect(new PotionEffect(P2.buffPotionEffects.get(lastPotion % 5), 3 + (mainLevel / 3), 1 + (mainLevel / 6)));
            pdc2.set(ItemKeys.buffPotionRemainingCounter, PersistentDataType.INTEGER, 31 - mainLevel);
            pdc2.set(ItemKeys.buffLastPotion, PersistentDataType.INTEGER, lastPotion + 1);
        } else
            pdc2.set(ItemKeys.buffPotionRemainingCounter, PersistentDataType.INTEGER, count);

    }, CraftWitch.class, CraftSlime.class),

    GAME_BUSTER("Oyunbozan", 14, 7, 10, e -> {
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;

        PersistentDataContainer pdc = ((Player) event.getDamager()).getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        PersistentDataContainer pdc2 = event.getDamager().getPersistentDataContainer();

        int mainLevel = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[8];
        if (pdc2.get(ItemKeys.nerfPotionRemainingCounter, PersistentDataType.INTEGER) == null)
            pdc2.set(ItemKeys.nerfPotionRemainingCounter, PersistentDataType.INTEGER, 31 - mainLevel);

        int count = pdc2.get(ItemKeys.nerfPotionRemainingCounter, PersistentDataType.INTEGER) - 1;

        if (count == 0) {
            int lastPotion = pdc2.get(ItemKeys.nerfLastPotion, PersistentDataType.INTEGER);
            ((Player) event.getDamager()).addPotionEffect(new PotionEffect(P2.buffPotionEffects.get(lastPotion % 5), 3 + (mainLevel / 3), 1 + (mainLevel / 6)));
            pdc2.set(ItemKeys.nerfPotionRemainingCounter, PersistentDataType.INTEGER, 31 - mainLevel);
            pdc2.set(ItemKeys.nerfLastPotion, PersistentDataType.INTEGER, lastPotion + 1);
        } else
            pdc2.set(ItemKeys.buffPotionRemainingCounter, PersistentDataType.INTEGER, count);
    }, CraftHusk.class, CraftStray.class),

    APPEALING("Çekici", 2, 4, 10, e -> {
        PlayerFishEvent event = (PlayerFishEvent) e;

        PersistentDataContainer pdc = ((Player) event.getCaught()).getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        int appealing = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[0];
        int bigFood = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[1];
        int spiritualWorth = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[2];

        int garbageman = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[3];
        int treasurer = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[4];
        int librarian = pdc.get(ItemKeys.cLevels, PersistentDataType.INTEGER_ARRAY)[5];

        PlayerFishEvent.State state = event.getState();

        FishHook fh = event.getHook();
        if (state == PlayerFishEvent.State.FISHING) {
            fh.setMinWaitTime(5 - (int) Math.floor(bigFood / 2.1));
            fh.setMaxWaitTime(30 - (int) Math.floor(bigFood / 1.5));
        } else if (state == PlayerFishEvent.State.BITE) {
            if (appealing * 10 >= OrbiCore.getRandom().nextInt(100)) {
                int random = OrbiCore.getRandom().nextInt(100);

                if (librarian * 4 >= random) {
                    int encType = OrbiCore.getRandom().nextInt(Enchantment.values().length);

                    ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
                    ItemMeta im = is.getItemMeta();
                    Enchantment enchantment = Enchantment.values()[encType];
                    int encLevel = 1 + librarian / 4;
                    if (encLevel <= enchantment.getMaxLevel())
                        im.addEnchant(enchantment, encLevel, false);
                    else
                        im.addEnchant(enchantment, enchantment.getMaxLevel(), false);
                    is.setItemMeta(im);

                    Item i = fh.getWorld().dropItem(fh.getLocation(), is);
                    fh.setHookedEntity(i);
                } //4 -> 40
                else if (treasurer * 1.5 >= random) {
                    int random2 = OrbiCore.getRandom().nextInt(P2.valuableItems.size());
                    Item i = fh.getWorld().dropItem(fh.getLocation(), P2.valuableItems.get(random2));
                    fh.setHookedEntity(i);
                }// 1.5 -> 15 //İhtimal dağılımı yanlış; bu dağılımı düzelt mantık açısından büyükler küçükleri eziyor else kısmındaki mantığa bak
                else if (30 - (garbageman * 2.7) >= random) {
                    int random2 = OrbiCore.getRandom().nextInt(P2.junkItems.size());
                    Item i = fh.getWorld().dropItem(fh.getLocation(), P2.junkItems.get(random2));
                    fh.setHookedEntity(i);
                } // 30 -> 3
                else {
                    int random2 = OrbiCore.getRandom().nextInt(100);
                    ItemStack it;
                    if (2 >= random2)
                        it = new ItemStack(Material.TROPICAL_FISH);
                    else if (13 >= random2)
                        it = new ItemStack(Material.PUFFERFISH);
                    else if (25 >= random2)
                        it = new ItemStack(Material.SALMON);
                    else
                        it = new ItemStack(Material.COD);

                    Item i = fh.getWorld().dropItem(fh.getLocation(), it);
                    fh.setHookedEntity(i);
                }
            }
        }

    }),

    BIG_FOOD("Büyük yem", 2, 2, 10, null),

    SPIRITUAL_WORTH("Ruhani kazanç", 2, 2, 10, null),

    GARBAGEMAN("Çöpçü", 2, 2, 10, null),

    TREASURER("Defineci", 2, 2, 10, null),

    LIBRARIAN("Kütüphaneci", 2, 2, 10, null);


    private final String name;
    private final int minXP;
    private final int xpMultiplier;
    private final int maxLevel;
    private final Class<?>[] xpCondition;
    private final Consumer<Object> event;

    ItemProgress(String name, int minXP, int xpMultiplier, int maxLevel, Consumer<Object> event, Class<?>...
            xpCondition) {
        this.name = name;
        this.minXP = minXP;
        this.xpMultiplier = xpMultiplier;
        this.maxLevel = maxLevel;
        this.xpCondition = xpCondition;
        this.event = event;
    }

    public String getDefaultLore() {
        StringBuilder l = new StringBuilder(Color.translateHex("#7426e0✦ #12e6e6" + name + "#d6d6d6: "));
        l.append("#ffff55[#AA0000").append("□".repeat(maxLevel)).append("#ffff55]");
        return Color.translateHex(l.toString());
    }

    public String getName() {
        return name;
    }

    public int getMinXP() {
        return minXP;
    }

    public int getXpMultiplier() {
        return xpMultiplier;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void invoke(Object object) {
        if (event == null)
            return;
        event.accept(object);
    }

    public boolean canEarn(Object o) {
        if (xpCondition == null)
            return true;
        for (Class<?> c : xpCondition) {
            if (o.getClass() == c)
                return true;
        }
        return false;
    }

    public static List<String> updateStat(ItemStack i) {// &a* x kez
        List<String> lore = i.getItemMeta().getLore();
        String[] sp = lore.get(lore.size() - 1).split(" ");
        sp[1] = Integer.parseInt(sp[1]) + 1 + "";
        StringBuilder sb = new StringBuilder();
        for (String s : sp) {
            sb.append(s).append(" ");
        }
        lore.set(lore.size() - 1, sb.toString());
        return lore;
    }
}
