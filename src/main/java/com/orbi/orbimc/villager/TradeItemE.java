package com.orbi.orbimc.villager;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.Map;

/*    T4(new ItemStack(Material.MELON), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T5(new ItemStack(Material.PUMPKIN), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T6(new ItemStack(Material.CACTUS), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4);*/


public enum TradeItemE {
    //Farmer
    T0(new ItemStack(Material.WHEAT), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T1(new ItemStack(Material.CARROT), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T2(new ItemStack(Material.POTATO), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T3(new ItemStack(Material.BEETROOT), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T4(StaticItems.villagerCoin, new ItemStack(Material.BREAD, 12), 6, 7, 1, 2, 3, 4),
    T5(StaticItems.villagerCoin, new ItemStack(Material.GOLDEN_CARROT, 12), 6, 7, 1, 2, 3, 4),
    T6(StaticItems.villagerCoin, new ItemStack(Material.GOLDEN_APPLE, 12), 6, 7, 1, 2, 3, 4),
    T7(StaticItems.villagerCoin, new ItemStack(Material.PUMPKIN_PIE, 12), 6, 7, 1, 2, 3, 4),

    T8(StaticItems.villagerCoin, new ItemStack(Material.WHEAT_SEEDS, 12), 6, 7, 1, 2, 3, 4),
    T9(StaticItems.villagerCoin, new ItemStack(Material.BEETROOT_SEEDS, 12), 6, 7, 1, 2, 3, 4),
    T10(StaticItems.villagerCoin, new ItemStack(Material.PUMPKIN_SEEDS, 12), 6, 7, 1, 2, 3, 4),
    T11(StaticItems.villagerCoin, new ItemStack(Material.MELON_SEEDS, 12), 6, 7, 1, 2, 3, 4),

    T12(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_HOE, 12), 6, 7, 1, 2, 3, 4),
    T13(StaticItems.villagerCoin, new ItemStack(Material.BONE_MEAL, 12), 6, 7, 1, 2, 3, 4),
    T15(new ItemStack(Material.MELON), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T14(new ItemStack(Material.PUMPKIN), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T16(StaticItems.villagerCoin, new ItemStack(Material.BEEHIVE, 12), 6, 7, 1, 2, 3, 4),
    T17(StaticItems.villagerCoin, new ItemStack(Material.BEE_NEST, 12), 6, 7, 1, 2, 3, 4),
    T18(new ItemStack(Material.HONEYCOMB, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T19(new ItemStack(Material.HONEY_BOTTLE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    //Butcher
    T20(new ItemStack(Material.PORKCHOP, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T21(new ItemStack(Material.BEEF, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T22(new ItemStack(Material.COD, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T23(new ItemStack(Material.SALMON, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T24(new ItemStack(Material.MUTTON, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T25(new ItemStack(Material.CHICKEN, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T26(new ItemStack(Material.RABBIT, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T27(new ItemStack(Material.PUFFERFISH, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T28(StaticItems.villagerCoin, new ItemStack(Material.COOKED_BEEF, 12), 6, 7, 1, 2, 3, 4),
    T29(StaticItems.villagerCoin, new ItemStack(Material.COOKED_PORKCHOP, 12), 6, 7, 1, 2, 3, 4),
    T30(StaticItems.villagerCoin, new ItemStack(Material.COOKED_COD, 12), 6, 7, 1, 2, 3, 4),
    T31(StaticItems.villagerCoin, new ItemStack(Material.COOKED_SALMON, 12), 6, 7, 1, 2, 3, 4),

    T32(StaticItems.villagerCoin, new ItemStack(Material.COOKED_MUTTON, 12), 6, 7, 1, 2, 3, 4),
    T33(StaticItems.villagerCoin, new ItemStack(Material.COOKED_CHICKEN, 12), 6, 7, 1, 2, 3, 4),
    T34(StaticItems.villagerCoin, new ItemStack(Material.COOKED_RABBIT, 12), 6, 7, 1, 2, 3, 4),
    T35(StaticItems.villagerCoin, new ItemStack(Material.RABBIT_STEW, 12), 6, 7, 1, 2, 3, 4),

    T36(StaticItems.villagerCoin, new ItemStack(Material.COAL, 12), 6, 7, 1, 2, 3, 4),
    T37(StaticItems.villagerCoin, new ItemStack(Material.ZOMBIE_HEAD, 12), 6, 7, 1, 2, 3, 4),
    T38(StaticItems.villagerCoin, new ItemStack(Material.SKELETON_SKULL, 12), 6, 7, 1, 2, 3, 4),
    T39(StaticItems.villagerCoin, new ItemStack(Material.CREEPER_HEAD, 12), 6, 7, 1, 2, 3, 4),
    //Toolsmith
    T40(new ItemStack(Material.STICK, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T41(new ItemStack(Material.RAW_COPPER, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T42(new ItemStack(Material.RAW_IRON, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T43(new ItemStack(Material.RAW_GOLD, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T44(new ItemStack(Material.SLIME_BALL, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T45(new ItemStack(Material.STRING, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T46(new ItemStack(Material.CHARCOAL, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T47(new ItemStack(Material.FIRE_CHARGE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T48(StaticItems.villagerCoin, new ItemStack(Material.IRON_SWORD, 12), 6, 7, 1, 2, 3, 4),
    T49(StaticItems.villagerCoin, new ItemStack(Material.IRON_AXE, 12), 6, 7, 1, 2, 3, 4),
    T50(StaticItems.villagerCoin, new ItemStack(Material.IRON_SHOVEL, 12), 6, 7, 1, 2, 3, 4),
    T51(StaticItems.villagerCoin, new ItemStack(Material.IRON_PICKAXE, 12), 6, 7, 1, 2, 3, 4),

    T52(StaticItems.villagerCoin, new ItemStack(Material.DIAMOND_SWORD, 12), 6, 7, 1, 2, 3, 4),
    T53(StaticItems.villagerCoin, new ItemStack(Material.DIAMOND_AXE, 12), 6, 7, 1, 2, 3, 4),
    T54(StaticItems.villagerCoin, new ItemStack(Material.DIAMOND_SHOVEL, 12), 6, 7, 1, 2, 3, 4),
    T55(StaticItems.villagerCoin, new ItemStack(Material.DIAMOND_PICKAXE, 12), 6, 7, 1, 2, 3, 4),

    T56(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_SWORD, 12), 6, 7, 1, 2, 3, 4),
    T57(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_AXE, 12), 6, 7, 1, 2, 3, 4),
    T58(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_SHOVEL, 12), 6, 7, 1, 2, 3, 4),
    T59(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_PICKAXE, 12), 6, 7, 1, 2, 3, 4),
    //Armorer
    T60(new ItemStack(Material.IRON_INGOT, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T61(new ItemStack(Material.GOLD_INGOT, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T62(new ItemStack(Material.DIAMOND, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T63(new ItemStack(Material.COPPER_INGOT, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T64(new ItemStack(Material.ICE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T65(new ItemStack(Material.NETHERITE_INGOT, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T66(new ItemStack(Material.FIRE_CHARGE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T67(new ItemStack(Material.COAL, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T68(StaticItems.villagerCoin, new ItemStack(Material.IRON_CHESTPLATE, 12), 6, 7, 1, 2, 3, 4),
    T69(StaticItems.villagerCoin, new ItemStack(Material.IRON_HELMET, 12), 6, 7, 1, 2, 3, 4),
    T70(StaticItems.villagerCoin, new ItemStack(Material.IRON_LEGGINGS, 12), 6, 7, 1, 2, 3, 4),
    T71(StaticItems.villagerCoin, new ItemStack(Material.IRON_BOOTS, 12), 6, 7, 1, 2, 3, 4),

    T72(StaticItems.villagerCoin, new ItemStack(Material.DIAMOND_CHESTPLATE, 12), 6, 7, 1, 2, 3, 4),
    T73(StaticItems.villagerCoin, new ItemStack(Material.DIAMOND_HELMET, 12), 6, 7, 1, 2, 3, 4),
    T74(StaticItems.villagerCoin, new ItemStack(Material.DIAMOND_LEGGINGS, 12), 6, 7, 1, 2, 3, 4),
    T75(StaticItems.villagerCoin, new ItemStack(Material.DIAMOND_BOOTS, 12), 6, 7, 1, 2, 3, 4),

    T76(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_CHESTPLATE, 12), 6, 7, 1, 2, 3, 4),
    T77(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_HELMET, 12), 6, 7, 1, 2, 3, 4),
    T78(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_LEGGINGS, 12), 6, 7, 1, 2, 3, 4),
    T79(StaticItems.villagerCoin, new ItemStack(Material.NETHERITE_BOOTS, 12), 6, 7, 1, 2, 3, 4),
    //Librarian
    T80(new ItemStack(Material.PAPER, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T81(new ItemStack(Material.INK_SAC, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T82(new ItemStack(Material.SUGAR_CANE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T83(new ItemStack(Material.LEATHER, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T84(StaticItems.villagerCoin, li(Enchantment.LOYALTY), 12, 6, 7, 1, 2, 3, 4),
    T85(StaticItems.villagerCoin, li(Enchantment.LUCK), 6, 7, 1, 2, 3, 4),
    T86(StaticItems.villagerCoin, li(Enchantment.LURE), 6, 7, 1, 2, 3, 4),
    T87(StaticItems.villagerCoin, li(Enchantment.DURABILITY), 6, 7, 1, 2, 3, 4),

    T88(StaticItems.villagerCoin, li(Enchantment.PROTECTION_FIRE), 12, 6, 7, 1, 2, 3, 4),
    T89(StaticItems.villagerCoin, li(Enchantment.ARROW_KNOCKBACK), 6, 7, 1, 2, 3, 4),
    T90(StaticItems.villagerCoin, li(Enchantment.LURE), 6, 7, 1, 2, 3, 4),
    T91(StaticItems.villagerCoin, li(Enchantment.DURABILITY), 6, 7, 1, 2, 3, 4),

    T92(StaticItems.villagerCoin, li(Enchantment.FIRE_ASPECT), 6, 7, 1, 2, 3, 4),
    T93(StaticItems.villagerCoin, li(Enchantment.ARROW_FIRE), 6, 7, 1, 2, 3, 4),
    T94(StaticItems.villagerCoin, li(Enchantment.ARROW_INFINITE), 6, 7, 1, 2, 3, 4),
    T95(StaticItems.villagerCoin, li(Enchantment.ARROW_DAMAGE), 6, 7, 1, 2, 3, 4),

    T96(StaticItems.villagerCoin, li(Enchantment.DIG_SPEED), 6, 7, 1, 2, 3, 4),
    T97(StaticItems.villagerCoin, li(Enchantment.LOOT_BONUS_BLOCKS), 6, 7, 1, 2, 3, 4),
    T98(StaticItems.villagerCoin, li(Enchantment.LOOT_BONUS_MOBS), 6, 7, 1, 2, 3, 4),
    T99(StaticItems.villagerCoin, li(Enchantment.MENDING), 6, 7, 1, 2, 3, 4),
    //Cleric
    T100(new ItemStack(Material.ROTTEN_FLESH, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T101(new ItemStack(Material.BONE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T102(new ItemStack(Material.GUNPOWDER, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T103(new ItemStack(Material.ARROW, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T104(new ItemStack(Material.STRING, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T105(new ItemStack(Material.PHANTOM_MEMBRANE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T106(new ItemStack(Material.RABBIT_FOOT, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T107(new ItemStack(Material.NETHER_WART, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T108(new ItemStack(Material.GLASS_BOTTLE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T109(new ItemStack(Material.GHAST_TEAR, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T110(new ItemStack(Material.SPIDER_EYE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T111(new ItemStack(Material.ENDER_PEARL, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T112(new ItemStack(Material.SLIME_BALL, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T113(new ItemStack(Material.INK_SAC, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T114(new ItemStack(Material.BLAZE_ROD, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T115(new ItemStack(Material.MAGMA_CREAM, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T116(StaticItems.villagerCoin, li(Enchantment.SOUL_SPEED), 6, 7, 1, 2, 3, 4),
    T117(StaticItems.villagerCoin, li(Enchantment.THORNS), 6, 7, 1, 2, 3, 4),
    T118(StaticItems.villagerCoin, li(Enchantment.BINDING_CURSE), 6, 7, 1, 2, 3, 4),
    T119(StaticItems.villagerCoin, li(Enchantment.VANISHING_CURSE), 6, 7, 1, 2, 3, 4),
    //Yüncü
    T120(new ItemStack(Material.WHITE_WOOL, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T121(new ItemStack(Material.GRAY_WOOL, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T122(new ItemStack(Material.WHEAT, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T123(new ItemStack(Material.COOKED_BEEF, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T124(StaticItems.villagerCoin, new ItemStack(Material.ORANGE_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T125(StaticItems.villagerCoin, new ItemStack(Material.MAGENTA_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T126(StaticItems.villagerCoin, new ItemStack(Material.LIGHT_BLUE_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T127(StaticItems.villagerCoin, new ItemStack(Material.YELLOW_WOOL, 12), 6, 7, 1, 2, 3, 4),

    T128(StaticItems.villagerCoin, new ItemStack(Material.LIME_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T129(StaticItems.villagerCoin, new ItemStack(Material.GRAY_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T130(StaticItems.villagerCoin, new ItemStack(Material.LIGHT_GRAY_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T131(StaticItems.villagerCoin, new ItemStack(Material.PINK_WOOL, 12), 6, 7, 1, 2, 3, 4),

    T132(StaticItems.villagerCoin, new ItemStack(Material.BROWN_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T133(StaticItems.villagerCoin, new ItemStack(Material.PURPLE_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T134(StaticItems.villagerCoin, new ItemStack(Material.CYAN_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T135(StaticItems.villagerCoin, new ItemStack(Material.GREEN_WOOL, 12), 6, 7, 1, 2, 3, 4),

    T136(StaticItems.villagerCoin, new ItemStack(Material.RED_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T137(StaticItems.villagerCoin, new ItemStack(Material.BLUE_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T138(StaticItems.villagerCoin, new ItemStack(Material.BLACK_WOOL, 12), 6, 7, 1, 2, 3, 4),
    T139(StaticItems.villagerCoin, new ItemStack(Material.WHITE_DYE, 12), 6, 7, 1, 2, 3, 4),
    //Taşçı
    T140(new ItemStack(Material.COBBLESTONE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T141(new ItemStack(Material.NETHERRACK, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T142(StaticItems.villagerCoin, new ItemStack(Material.GRANITE, 12), 6, 7, 1, 2, 3, 4),
    T143(StaticItems.villagerCoin, new ItemStack(Material.DIORITE, 12), 6, 7, 1, 2, 3, 4),

    T144(StaticItems.villagerCoin, new ItemStack(Material.GRAVEL, 12), 6, 7, 1, 2, 3, 4),
    T145(StaticItems.villagerCoin, new ItemStack(Material.SAND, 12), 6, 7, 1, 2, 3, 4),
    T146(new ItemStack(Material.STONE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T147(StaticItems.villagerCoin, new ItemStack(Material.BLACKSTONE, 12), 6, 7, 1, 2, 3, 4),

    T148(StaticItems.villagerCoin, new ItemStack(Material.NETHER_BRICK, 12), 6, 7, 1, 2, 3, 4),
    T149(StaticItems.villagerCoin, new ItemStack(Material.BASALT, 12), 6, 7, 1, 2, 3, 4),
    T150(StaticItems.villagerCoin, new ItemStack(Material.COBBLED_DEEPSLATE, 12), 6, 7, 1, 2, 3, 4),
    T151(StaticItems.villagerCoin, new ItemStack(Material.GLOWSTONE, 12), 6, 7, 1, 2, 3, 4),

    T152(StaticItems.villagerCoin, new ItemStack(Material.DRIPSTONE_BLOCK, 12), 6, 7, 1, 2, 3, 4),
    T153(StaticItems.villagerCoin, new ItemStack(Material.CRYING_OBSIDIAN, 12), 6, 7, 1, 2, 3, 4),
    T154(StaticItems.villagerCoin, new ItemStack(Material.SOUL_SOIL, 12), 6, 7, 1, 2, 3, 4),
    T155(StaticItems.villagerCoin, new ItemStack(Material.SOUL_SAND, 12), 6, 7, 1, 2, 3, 4),

    T156(StaticItems.villagerCoin, new ItemStack(Material.END_STONE, 12), 6, 7, 1, 2, 3, 4),
    T157(StaticItems.villagerCoin, new ItemStack(Material.PURPUR_PILLAR, 12), 6, 7, 1, 2, 3, 4),
    T158(StaticItems.villagerCoin, new ItemStack(Material.PURPUR_BLOCK, 12), 6, 7, 1, 2, 3, 4),
    T159(StaticItems.villagerCoin, new ItemStack(Material.AMETHYST_BLOCK, 12), 6, 7, 1, 2, 3, 4),
    //Fletcher
    T160(new ItemStack(Material.STICK, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T161(new ItemStack(Material.FEATHER, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T162(new ItemStack(Material.ARROW, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T163(new ItemStack(Material.FLINT, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T164(StaticItems.villagerCoin, new ItemStack(Material.SPECTRAL_ARROW, 12), 6, 7, 1, 2, 3, 4),
    T165(StaticItems.villagerCoin, a(PotionEffectType.HARM, 1, 1), 6, 7, 1, 2, 3, 4),
    T166(StaticItems.villagerCoin, a(PotionEffectType.POISON, 7, 1), 6, 7, 1, 2, 3, 4),
    T167(StaticItems.villagerCoin, a(PotionEffectType.WEAKNESS, 11, 1), 6, 7, 1, 2, 3, 4),

    T168(StaticItems.villagerCoin, new ItemStack(Material.TRIDENT, 12), 6, 7, 1, 2, 3, 4),
    T169(StaticItems.villagerCoin, new ItemStack(Material.CROSSBOW, 12), 6, 7, 1, 2, 3, 4),
    T170(StaticItems.villagerCoin, a(PotionEffectType.WEAKNESS, 30, 2), 6, 7, 1, 2, 3, 4),
    T171(StaticItems.villagerCoin, a(PotionEffectType.SLOW, 11, 1), 6, 7, 1, 2, 3, 4),

    T172(new ItemStack(Material.BLAZE_POWDER, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T173(new ItemStack(Material.NETHER_WART, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T174(new ItemStack(Material.FERMENTED_SPIDER_EYE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),
    T175(new ItemStack(Material.REDSTONE, 12), StaticItems.villagerCoin, 6, 7, 1, 2, 3, 4),

    T176(StaticItems.villagerCoin, new ItemStack(Material.TOTEM_OF_UNDYING, 12), 6, 7, 1, 2, 3, 4),
    T177(StaticItems.villagerCoin, a(PotionEffectType.HARM, 1, 2), 6, 7, 1, 2, 3, 4),
    T178(StaticItems.villagerCoin, a(PotionEffectType.POISON, 6, 2), 6, 7, 1, 2, 3, 4),
    T179(StaticItems.villagerCoin, a(PotionEffectType.SLOW, 4, 5), 6, 7, 1, 2, 3, 4);

    private int lastID;
    private final int id;
    private final ItemStack received;
    private final ItemStack given;
    private final int minAmount;
    private final int maxAmount;
    private final int[] raises;

    TradeItemE(ItemStack given, ItemStack received, int minAmount, int maxAmount, int... raises) {
        this.id = lastID++;
        this.received = received;
        this.given = given;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.raises = raises;
    }

    public int getId() {
        return id;
    }

    public static TradeItemE getWithID(int id) {
        return values()[id];
    }

    public static ItemStack li(Enchantment e) { //Kitaplar son seviye ve çok pahalı olabilirler emin değilim
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
        bookMeta.addStoredEnchant(e, 1, true);
        book.setItemMeta(bookMeta);
        return book;
    }

    public static ItemStack a(PotionEffectType pt, int dur, int level) {
        ItemStack item = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.addCustomEffect(new PotionEffect(pt, dur, level, true), true);
        item.setItemMeta(meta);
        return item;
    }

    public static TradeItemE[] getTradeItems(String professionString, int level) {
        TradeItemE[] trades = new TradeItemE[level * 2];
        int start = getProfessionStart(professionString);

        for (int i = 0; i < level; i++) {
            for (int j = 0; j < 2; j++) {
                trades[j] = getWithID(start + (i * 5) + OrbiCore.getRandom().nextInt(4));
            }
        }
        return trades;
    }

    public static int getProfessionStart(String s) {
        switch (s) {
            case "Çiftçi":
                return 0;
            case "Kasap":
                return 20;
            case "Aletçi":
                return 40;
            case "Kütüphaneci":
                return 60;
            case "Zırhçı":
                return 80;
            case "Çoban":
                return 100;
            case "Rahip":
                return 120;
            case "Okçu":
                return 140;
            case "Mason":
                return 160;
        }
        throw new NullPointerException();
    }

    public static class VillagerData {
        List<MerchantRecipe> recipes;

        public VillagerData(String s) {
          //  this = recipes;
        }
    }


}
