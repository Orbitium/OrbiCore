package com.orbi.orbimc.villager;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.systems.customcraft.CustomShapedRecipe;
import com.orbi.orbimc.systems.season.SeasonCycle;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parchment {

    public static NamespacedKey lastOpenSeason = new NamespacedKey(OrbiCore.getInstance(), "lastOpenSeason");
    public static NamespacedKey trades = new NamespacedKey(OrbiCore.getInstance(), "tradeIDs");
    public static NamespacedKey parchmentItem = new NamespacedKey(OrbiCore.getInstance(), "parchmentItem");
    public static NamespacedKey raises = new NamespacedKey(OrbiCore.getInstance(), "raises");
    public static NamespacedKey lock = new NamespacedKey(OrbiCore.getInstance(), "lock");
    public static NamespacedKey jobPoint = new NamespacedKey(OrbiCore.getInstance(), "jobPoint");
    private static final NamespacedKey owners = new NamespacedKey(OrbiCore.getInstance(), "owners");

    /*
    Köylüler 59 yıl yaşar ve her yaptıkları zam onları 1 kez yaşlandırır, başlangıç yaşı 18'dir
     */

    public static void loadCrafts() {
        new CustomShapedRecipe("farmerParchment", "121 345 161", "1MAP 2WHEAT_SEEDS 3BEETROOT_SEEDS 4COMPOSTER 5PUMPKIN_SEEDS 6MELON_SEEDS", StaticItems.farmerParchment);
        new CustomShapedRecipe("butcherParchment", "121 345 161", "1MAP 2BEEF 3SALMON 4SMOKER 5TROPICAL_FISH 6MUTTON", StaticItems.butcherParchment);
        new CustomShapedRecipe("toolSmithParchment", "121 345 161", "1MAP 2GOLDEN_SWORD 3IRON_SWORD 4SMITHING_TABLE 5IRON_PICKAXE 6GOLDEN_SHOVEL", StaticItems.toolSmithParchment);
        new CustomShapedRecipe("librarianParchment", "121 232 121", "1MAP 2BOOKSHELF 3LECTERN", StaticItems.librarianParchment);
        new CustomShapedRecipe("armorerParchment", "121 345 161", "1MAP 2LEATHER_HELMET 3IRON_CHESTPLATE 4BLAST_FURNACE 5GOLDEN_LEGGINGS 6DIAMOND_BOOTS", StaticItems.armorerParchment);
        new CustomShapedRecipe("clericParchment", "121 345 161", "1MAP 2ROTTEN_FLESH 3NETHER_WART 4BREWING_STAND 5BONE 6RABBIT_FOOT", StaticItems.clericParchment);
        new CustomShapedRecipe("shepardParchment", "121 345 161", "1MAP 2SHEARS 3POPPY 4LOOM 5DANDELION 6WHITE_WOOL", StaticItems.shepardParchment);
        new CustomShapedRecipe("masonParchment", "121 345 161", "1MAP 2COBBLESTONE 3DIORITE 4STONECUTTER 5STONE 6GRANITE", StaticItems.masonParchment);
        new CustomShapedRecipe("fletcherParchment", "121 345 161", "1MAP 2BOW 3STICK 4FLETCHING_TABLE 5ARROW 6FEATHER", StaticItems.fletcherParchment);

        new CustomShapedRecipe("reloadVillagerTradesParchment", "121 232 121 ", "1MAP 2PAPER 3FLINT_AND_STEEL", StaticItems.reloadTradesParchment);
        new CustomShapedRecipe("removeJobParchment", "101 000 101 ", "1MAP 0AIR", StaticItems.removeJobParchment);
        new CustomShapedRecipe("packetVillagerParchment", "121 343 121 ", "1MAP 2EGG 3TURTLE_EGG 4EMERALD", StaticItems.packetVillagerParchment);

        new CustomShapedRecipe("tier2JobBook", "111 222 111", "1RABBIT_HIDE 2PAPER", StaticItems.tier2JobBook);
        new CustomShapedRecipe("tier3JobBook", "121 232 121", "1IRON_INGOT 2RABBIT_HIDE 3BOOK", StaticItems.tier3JobBook);
        new CustomShapedRecipe("tier4JobBook", "121 232 121", "1DIAMOND 2RABBIT_HIDE 3BOOK", StaticItems.tier4JobBook);
        new CustomShapedRecipe("tier5JobBook", "121 232 121", "1NETHERITE_INGOT 2RABBIT_HIDE 3BOOK", StaticItems.tier5JobBook);
    }

    public static void cancelMapConvert(PlayerInteractEvent e) {
        if (e.getItem().getItemMeta().hasDisplayName())
            e.setCancelled(true);
    }

    public static void handleParchmentGive(EntityDamageByEntityEvent e) {
        Player player = (Player) e.getDamager();
        ItemStack playerItem = player.getInventory().getItemInMainHand();
        ItemMeta im = playerItem.getItemMeta();
        if (im != null) {
            PersistentDataContainer itemPDC = im.getPersistentDataContainer();
            if (itemPDC.has(parchmentItem, PersistentDataType.BYTE)) {
                e.setCancelled(true);
                Villager villager = (Villager) e.getEntity();
                PersistentDataContainer pdc = villager.getPersistentDataContainer();
                List<String> ownerNames = new ArrayList<>(List.of(pdc.get(owners, PersistentDataType.STRING).split("\\|")));
                if (!ownerNames.contains(player.getName())) {
                    player.sendMessage("mal senin köylün mü");
                    return;
                }
                String name = im.getDisplayName().split(" ")[1];
                switch (name) {
                    case "Yenileme":
                        if (villager.getProfession() != Villager.Profession.NONE)
                            generateRandomRecipes(playerItem, villager, villager.getProfession(), pdc, villager.getVillagerLevel());
                        else
                            player.sendMessage("Bu işlem için köylünüzün bir mesleği olmalıdır!");
                        break;
                    case "Saklama":
                        if (pdc.has(lock, PersistentDataType.BYTE))
                            return;
                        ItemStack itemStack = new ItemStack(Material.VILLAGER_SPAWN_EGG);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        PersistentDataContainer ipdc = itemMeta.getPersistentDataContainer();

                        List<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GOLD + "→ Köylü bilgileri: ");
                        lore.add(ChatColor.GREEN + "● Zeka seviyesi: " + pdc.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE));
                        lore.add(ChatColor.GREEN + "● Öğrenim çarpanı: " + pdc.get(VillagerIQ.villagerIQMultiplier, PersistentDataType.DOUBLE));
                        lore.add(ChatColor.GREEN + "● Yaşı: " + pdc.get(VillagerIQ.villagerAge, PersistentDataType.INTEGER));
                        lore.add(ChatColor.GOLD + "→ Mesleki bilgiler: ");
                        if (villager.getVillagerExperience() >= 1) {
                            int raised = 0;
                            int[] raisedArr = pdc.get(raises, PersistentDataType.INTEGER_ARRAY);
                            for (int i : raisedArr)
                                raised += i;
                            int[] ids = pdc.get(trades, PersistentDataType.INTEGER_ARRAY);
                            ipdc.set(trades, PersistentDataType.INTEGER_ARRAY, ids);
                            ipdc.set(raises, PersistentDataType.INTEGER_ARRAY, raisedArr);
                            ipdc.set(lastOpenSeason, PersistentDataType.INTEGER, SeasonCycle.season);
                            lore.add(ChatColor.GRAY + "● Mesleği: " + villager.getProfession().name());
                            lore.add(ChatColor.GRAY + "● Seviyesi: " + villager.getVillagerLevel());
                            lore.add(ChatColor.GRAY + "● İşbilirlik puanı: " + pdc.get(jobPoint, PersistentDataType.INTEGER));
                            lore.add(ChatColor.GRAY + "● Takas ID'leri: " + Arrays.toString(ids));
                            lore.add(ChatColor.GRAY + "● Yapılan zam sayısı(kez): " + raised);
                        } else
                            lore.add(ChatColor.GRAY + "● Mesleği: İşsiz");
                        lore.add(ChatColor.AQUA + "ⓘ Bilgi almak için: /bilgi köylüler");

                        itemMeta.setLore(lore);
                        itemStack.setItemMeta(itemMeta);
                        player.getInventory().addItem(itemStack);
                        villager.setHealth(0);
                        playerItem.setAmount(playerItem.getAmount() - 1);
                        pdc.set(lock, PersistentDataType.BYTE, (byte) 1);
                        break;
                    case "Meslek":
                        if (villager.getProfession() != Villager.Profession.NONE) {
                            villager.setProfession(Villager.Profession.NONE);
                            villager.setVillagerExperience(0);
                            pdc.remove(trades);
                            playerItem.setAmount(playerItem.getAmount() - 1);
                        } else
                            player.sendMessage("köylünün mesleği yok");
                        break;
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                        if (villager.getProfession() != Villager.Profession.NONE) {
                            int villagerLevel = villager.getVillagerLevel();
                            int bookLevel = Integer.parseInt(name);
                            if (bookLevel - 1 == villagerLevel) {
                                int nowJobPoint = pdc.get(jobPoint, PersistentDataType.INTEGER);
                                if (nowJobPoint >= VillagerIQ.JobIQ.getWithName(villager.getProfession().name()).getIQList()[villagerLevel - 1]) {
                                    addNewRecipes(playerItem, villager, villager.getProfession(), pdc, villagerLevel);
                                    villager.setVillagerLevel(villagerLevel + 1);
                                } else
                                    player.sendMessage("Köylünüz bu işlem için yeterli işbilirliğe sahip değil!!");
                            } else
                                player.sendMessage("Köylünün seviye atlayabilmesi için, ona şu anki seviyesinden bir seviye yüksek bir kitap " +
                                        "vermelisin! Örneğin: köylünüz 2 seviye ise ve onu 3. seviye yapmak istiyorsanız; köylünüze, Seviye 3 işçi kitabı " +
                                        "vermelisiniz");
                        } else
                            player.sendMessage("köylü meslek sahibi olmalı");
                        break;
                    default: //Meslek verme parşomeni
                        if (villager.getProfession() == Villager.Profession.NONE) {
                            Villager.Profession p = TradeItem.getProfession(name);
                            double villagerIQ = pdc.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE);
                            if (villagerIQ >= VillagerIQ.JobIQ.getWithName(p.name()).getIQList()[0]) {
                                villager.setProfession(p);
                                generateRandomRecipes(playerItem, villager, p, pdc, 1);
                                villager.setVillagerExperience(1);
                                pdc.set(lastOpenSeason, PersistentDataType.INTEGER, SeasonCycle.season);
                            } else {
                                player.sendMessage("Köylünüz bu işlem için yeterli IQ seviyesine sahip değil!");
                            }
                        } else
                            player.sendMessage("Köylüye yeni bir meslek vermek istiyorsanız, önce var olan mesleği kaldırmalısınız!");
                        break;
                }
            }
        }
    }

    public static void checkVillager(PlayerInteractAtEntityEvent e) {
        Villager villager = (Villager) e.getRightClicked();
        if (villager.getEquipment().getItemInMainHand().getType() == Material.BOOK)
            return;
        PersistentDataContainer villagerPDC = villager.getPersistentDataContainer();
        Player player = e.getPlayer();
        ItemStack playerItem = player.getInventory().getItemInMainHand();
        if (playerItem.getType() == Material.BOOK) {
            villager.getEquipment().setItemInMainHand(new ItemStack(Material.BOOK));
            Location l = villager.getLocation();
            l.setPitch(20);
            villager.teleport(l);
            villager.setAI(false);

            if (villager.getProfession() != Villager.Profession.NONE) {
                List<MerchantRecipe> recipes = villager.getRecipes();
                villager.setRecipes(new ArrayList<>());
                Runnable task = () -> {
                    villager.setAI(true);
                    villager.setRecipes(recipes);
                    double newIQ = villagerPDC.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE) + (0.1 * villagerPDC.get(VillagerIQ.villagerIQMultiplier, PersistentDataType.DOUBLE));
                    villagerPDC.set(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE, newIQ);
                };
                Bukkit.getScheduler().runTaskLater(OrbiCore.getInstance(), task, 1200);
                playerItem.setAmount(playerItem.getAmount() - 1);
            } else
                Bukkit.getScheduler().runTaskLater(OrbiCore.getInstance(), () -> villager.setAI(true), 1200);
        } else if (playerItem.getType() == Material.VILLAGER_SPAWN_EGG) {
            Parchment.handleVillagerSpawnEgg(player, playerItem);
        } else if (villager.getProfession() != Villager.Profession.NONE) { //Köylülerin satış fiyatları sabit olur, stok yenilemesi başına yaptıkları zam mevsime bağlı olur
            List<MerchantRecipe> recipes = villager.getRecipes();
            int[] ids = villagerPDC.get(trades, PersistentDataType.INTEGER_ARRAY);
            int index = 0;
            if (villagerPDC.get(lastOpenSeason, PersistentDataType.INTEGER) == SeasonCycle.season) {
                for (MerchantRecipe mr : recipes) {
                    if (mr.getUses() == mr.getMaxUses()) {
                        double iq = villagerPDC.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE);
                        double newIQ = iq + 0.2 * villagerPDC.get(VillagerIQ.villagerIQMultiplier, PersistentDataType.DOUBLE);
                        villagerPDC.set(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE, newIQ);
                        mr.setMaxUses((int) (newIQ * 10 / 100));
                        List<ItemStack> needs = mr.getIngredients();
                        TradeItem ti = TradeItem.getWithId(ids[index]);
                        int newAmount = needs.size() == 1 ? needs.get(0).getAmount() : needs.get(0).getAmount() + needs.get(1).getAmount();
                        newAmount += ti.getIncreaseAmount();

                        if (newAmount >= 64) {
                            needs.set(0, StaticItems.setAmount(ti.getGiven(), 64));
                            if (needs.size() == 2)
                                needs.set(1, StaticItems.setAmount(ti.getGiven(), Math.min(newAmount - 64, 64)));
                            else
                                needs.add(StaticItems.setAmount(ti.getGiven(), Math.min(newAmount - 64, 64)));
                        } else
                            needs.get(0).setAmount(newAmount);
                        mr.setIngredients(needs);
                    }
                    index++;
                }
            } else {
                int i = 0;
                for (MerchantRecipe mr : recipes) {
                    TradeItem tradeItem = TradeItem.getWithId(ids[i]);
                    List<ItemStack> needs = mr.getIngredients();
                    needs.set(0, tradeItem.getGiven());
                    needs.set(1, new ItemStack(Material.AIR));
                    mr.setIngredients(needs);
                    i++;
                }
                villagerPDC.set(lastOpenSeason, PersistentDataType.INTEGER, SeasonCycle.season);
            }
            villager.setRecipes(recipes);
        }
    }

    public static void generateRandomRecipes(ItemStack playerItem, Villager villager, Villager.Profession
            profession, PersistentDataContainer pdc, int level) {
        int[] tradeIDs = new int[level * 2];
        List<MerchantRecipe> recipes = new ArrayList<>();

        int i = 0;
        for (TradeItem tradeItem : TradeItem.getRandomTrade(profession.name(), level, level * 2)) {
            int maxUses = (int) (pdc.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE) * 10 / 100);
            MerchantRecipe recipe = new MerchantRecipe(tradeItem.getReceived(), maxUses);
            recipe.addIngredient(tradeItem.getGiven());
            recipes.add(recipe);
            tradeIDs[i++] = tradeItem.getID();
        }

        // TODO: 6.05.2022 Buraları TradeItemE'ye göre düzenle

        villager.setRecipes(recipes);
        pdc.set(trades, PersistentDataType.INTEGER_ARRAY, tradeIDs);
        playerItem.setAmount(playerItem.getAmount() - 1);
    }

    public static void addNewRecipes(ItemStack playerItem, Villager villager, Villager.Profession
            profession, PersistentDataContainer pdc, int level) {
        List<MerchantRecipe> recipes = new ArrayList<>(villager.getRecipes());
        int[] tradeIDs = new int[recipes.size() + 2];

        int i = recipes.size();
        for (TradeItem tradeItem : TradeItem.getRandomTrade(profession.name(), level + 1, 2)) {
            int maxUses = (int) (pdc.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE) * 10 / 100);
            MerchantRecipe recipe = new MerchantRecipe(tradeItem.getReceived(), maxUses);
            recipe.addIngredient(tradeItem.getGiven());
            recipes.add(recipe);
            tradeIDs[i++] = tradeItem.getID();
        }

        villager.setRecipes(recipes);
        pdc.set(trades, PersistentDataType.INTEGER_ARRAY, tradeIDs);
        playerItem.setAmount(playerItem.getAmount() - 1);
    }

    public static void handleVillagerSpawnEgg(Player player, ItemStack itemstack) {
        Bukkit.getScheduler().runTaskLater(OrbiCore.getInstance(), () -> {
            for (Entity entity : player.getNearbyEntities(7, 7, 7)) {
                if (entity.getType() == EntityType.VILLAGER) {
                    PersistentDataContainer pdc = entity.getPersistentDataContainer();
                    if (!pdc.has(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE)) {
                        Villager villager = (Villager) entity;
                        if (!villager.isAdult())
                            villager.setAdult();
                        ItemMeta im = itemstack.getItemMeta();
                        PersistentDataContainer itempdc = im.getPersistentDataContainer();
                        List<String> lore = im.getLore();

                        pdc.set(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE, Double.parseDouble(lore.get(1).split(" ")[3]));
                        pdc.set(VillagerIQ.villagerIQMultiplier, PersistentDataType.DOUBLE, Double.parseDouble(lore.get(2).split(" ")[3]));
                        pdc.set(VillagerIQ.villagerAge, PersistentDataType.INTEGER, Integer.parseInt(lore.get(3).split(" ")[2]));
                        pdc.set(lastOpenSeason, PersistentDataType.INTEGER, SeasonCycle.season);

                        /*
                        lore.add(ChatColor.GOLD + "→ Köylü bilgileri: ");
                        lore.add(ChatColor.GREEN + "● Zeka seviyesi: " + pdc.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE));
                        lore.add(ChatColor.GREEN + "● Öğrenim çarpanı: " + pdc.get(VillagerIQ.villagerIQMultiplier, PersistentDataType.DOUBLE));
                        lore.add(ChatColor.GREEN + "● Yaşı: " + pdc.get(VillagerIQ.villagerAge, PersistentDataType.INTEGER));
                        lore.add(ChatColor.GOLD + "→ Mesleki bilgiler: ");

                            lore.add(ChatColor.GRAY + "● Mesleği: " + villager.getProfession().name());
                            lore.add(ChatColor.GRAY + "● Seviyesi: " + villager.getVillagerLevel());
                            lore.add(ChatColor.GRAY + "● İşbilirlik puanı: " + pdc.get(jobPoint, PersistentDataType.INTEGER));
                            lore.add(ChatColor.GRAY + "● Takas ID'leri: " + Arrays.toString(ids));
                            lore.add(ChatColor.GRAY + "● Yapılan zam sayısı(kez): " + raised);
                         */

                        if (itempdc.has(trades, PersistentDataType.INTEGER_ARRAY)) {
                            villager.setProfession(Villager.Profession.valueOf(lore.get(5).split(" ")[2]));
                            villager.setVillagerExperience(1);
                            double iq = pdc.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE);
                            int age = pdc.get(VillagerIQ.villagerAge, PersistentDataType.INTEGER);
                            List<MerchantRecipe> recipes = new ArrayList<>();

                            if (itempdc.get(lastOpenSeason, PersistentDataType.INTEGER) != SeasonCycle.season) {
                                for (int id : itempdc.get(trades, PersistentDataType.INTEGER_ARRAY)) {
                                    TradeItem tradeItem = TradeItem.getWithId(id);
                                    int maxUses = 15 - ((age - 18) / 2);
                                    MerchantRecipe recipe = new MerchantRecipe(tradeItem.getReceived(), maxUses);
                                    recipe.addIngredient(tradeItem.getGiven());
                                    recipes.add(recipe);
                                }
                            } else {
                                int[] raised = itempdc.get(raises, PersistentDataType.INTEGER_ARRAY);
                                int[] ids = itempdc.get(trades, PersistentDataType.INTEGER_ARRAY);
                                for (int i = 0; i < ids.length; i++) {
                                    TradeItem tradeItem = TradeItem.getWithId(ids[i]);
                                    int maxUses = (int) (iq * 10 / 100);
                                    MerchantRecipe recipe = new MerchantRecipe(tradeItem.getReceived(), maxUses);
                                    ItemStack given = tradeItem.getGiven();
                                    int newAmount = tradeItem.getIncreaseAmount() * raised[i] + given.getAmount();
                                    if (newAmount >= 64) {
                                        List<ItemStack> needs = new ArrayList<>();
                                        needs.add(StaticItems.setAmount(given, 64));
                                        needs.add(StaticItems.setAmount(given, Math.min(newAmount - 64, 64)));
                                        recipe.setIngredients(needs);
                                    } else
                                        recipe.addIngredient(StaticItems.setAmount(given, newAmount));
                                    recipes.add(recipe);
                                }
                                pdc.set(trades, PersistentDataType.INTEGER_ARRAY, ids);
                            }
                            villager.setRecipes(recipes);
                            villager.setVillagerLevel(recipes.size() / 2);
                        }
                    }
                }
            }
        }, 2L);
    }


}

/*
     if (im != null) {
            PersistentDataContainer itemPDC = im.getPersistentDataContainer();
            if (itemPDC.has(parchmentItem, PersistentDataType.BYTE)) {
                String name = im.getDisplayName().split(" ")[1];
                switch (name) {
                    case "Yenileme":
                        if (villager.getProfession() != Villager.Profession.NONE)
                            generateRandomRecipes(playerItem, villager, villager.getProfession(), villagerPDC, villager.getVillagerLevel());
                        else
                            player.sendMessage("Bu işlem için köylünüzün bir mesleği olmalıdır!");
                        break;
                    case "Saklama":
                        if (villagerPDC.has(lock, PersistentDataType.BYTE))
                            return;
                        ItemStack itemStack = new ItemStack(Material.VILLAGER_SPAWN_EGG);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        PersistentDataContainer ipdc = itemMeta.getPersistentDataContainer();

                        List<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GOLD + "→ Köylü bilgileri: ");
                        lore.add(ChatColor.GREEN + "● Zeka seviyesi: " + villagerPDC.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE));
                        lore.add(ChatColor.GREEN + "● Öğrenim çarpanı: " + villagerPDC.get(VillagerIQ.villagerIQMultiplier, PersistentDataType.DOUBLE));
                        lore.add(ChatColor.GREEN + "● Yaşı: " + villagerPDC.get(VillagerIQ.villagerAge, PersistentDataType.INTEGER));
                        lore.add(ChatColor.GOLD + "→ Mesleki bilgiler: ");
                        if (villager.getVillagerExperience() >= 1) {
                            int raised = 0;
                            int[] raisedArr = new int[villager.getVillagerLevel() * 2];
                            int[] ids = villagerPDC.get(trades, PersistentDataType.INTEGER_ARRAY);
                            int index = 0;
                            for (MerchantRecipe mr : villager.getRecipes()) {
                                TradeItem ti = TradeItem.getWithId(ids[index]);
                                List<ItemStack> needs = mr.getIngredients();
                                int newAmount = needs.size() == 1 ? needs.get(0).getAmount() : needs.get(0).getAmount() + needs.get(1).getAmount();
                                newAmount -= ti.getGiven().getAmount();
                                raised += newAmount / ti.getIncreaseAmount();
                                if (mr.getUses() == mr.getMaxUses())
                                    raised++;
                                raisedArr[index] = newAmount / ti.getIncreaseAmount();
                                index++;
                            }
                            ipdc.set(trades, PersistentDataType.INTEGER_ARRAY, ids);
                            ipdc.set(raises, PersistentDataType.INTEGER_ARRAY, raisedArr);
                            ipdc.set(lastOpenSeason, PersistentDataType.INTEGER, SeasonCycle.season);
                            lore.add(ChatColor.GRAY + "● Mesleği: " + villager.getProfession().name());
                            lore.add(ChatColor.GRAY + "● Seviyesi: " + villager.getVillagerLevel());
                            lore.add(ChatColor.GRAY + "● Takas ID'leri: " + Arrays.toString(ids));
                            lore.add(ChatColor.GRAY + "● Yapılan zam sayısı(kez): " + raised);
                        } else
                            lore.add(ChatColor.GRAY + "● Mesleği: İşsiz");
                        lore.add(ChatColor.AQUA + "ⓘ Bilgi almak için: /bilgi köylüler");

                        itemMeta.setLore(lore);
                        itemStack.setItemMeta(itemMeta);
                        player.getInventory().addItem(itemStack);
                        villager.setHealth(0);
                        playerItem.setAmount(playerItem.getAmount() - 1);
                        villagerPDC.set(lock, PersistentDataType.BYTE, (byte) 1);
                        break;
                    case "Meslek":
                        villager.setProfession(Villager.Profession.NONE);
                        villager.setVillagerExperience(0);
                        villagerPDC.remove(trades);
                        playerItem.setAmount(playerItem.getAmount() - 1);
                        break;
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                        if (villager.getProfession() != Villager.Profession.NONE) {
                            int villagerLevel = villager.getVillagerLevel();
                            if (Integer.parseInt(name) - 1 == villagerLevel) {
                                double villagerIQ = villagerPDC.get(VillagerIQ.villagerIQ, PersistentDataType.DOUBLE);
                                if (villagerIQ >= VillagerIQ.JobIQ.getWithName(villager.getProfession().name()).getIQList()[villagerLevel - 1]) {
                                    addNewRecipes(playerItem, villager, villager.getProfession(), villagerPDC, villagerLevel);
                                    villager.setVillagerLevel(villagerLevel + 1);
                                } else
                                    player.sendMessage("Köylünüz bu işlem için yeterli zeka düzeyine sahip değil!");
                            } else
                                player.sendMessage("Köylünün seviye atlayabilmesi için, ona şu anki seviyesinden bir seviye yüksek bir kitap " +
                                        "vermelisin! Örneğin: köylünüz 2 seviye ise ve onu 3. seviye yapmak istiyorsanız; köylünüze, Seviye 3 işçi kitabı " +
                                        "vermelisiniz");
                        } else
                            player.sendMessage("köylü meslek sahibi olmalı");
                        break;
                    default: //Meslek verme parşomeni
                        if (villager.getProfession() == Villager.Profession.NONE) {
                            Villager.Profession p = TradeItem.getProfession(name);
                            villager.setProfession(p);
                            generateRandomRecipes(playerItem, villager, p, villagerPDC, 1);
                            villager.setVillagerExperience(1);
                            villagerPDC.set(lastOpenSeason, PersistentDataType.INTEGER, SeasonCycle.season);
                        } else
                            player.sendMessage("Köylüye yeni bir meslek vermek istiyorsanız, önce var olan mesleği kaldırmalısınız!");
                        break;
                }
            }



                        List<MerchantRecipe> recipes2 = villager.getRecipes();
            int[] ids = villagerPDC.get(trades, PersistentDataType.INTEGER_ARRAY);
            //id:uses:needsAmount-id:uses:needsAmount
            for (int i = 0; i < recipes2.size(); i++) {
                MerchantRecipe recipe = recipes2.get(i);
                List<ItemStack> needs = recipe.getIngredients();
                int needAmount = needs.size() == 1 ? needs.get(0).getAmount() : needs.get(0).getAmount() + needs.get(1).getAmount();
                sb.append(ids[i]).append("[").append(recipe.getUses()).append(":").append(needAmount).append("]-");
            }
            System.out.println(sb.deleteCharAt(sb.length() - 1));
 */