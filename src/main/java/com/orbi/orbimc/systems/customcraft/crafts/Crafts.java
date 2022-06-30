package com.orbi.orbimc.systems.customcraft.crafts;

import com.orbi.orbimc.systems.customcraft.CustomShapedRecipe;
import com.orbi.orbimc.systems.customcraft.CustomShapelessRecipe;
import com.orbi.orbimc.systems.customcraft.CustomSmeltRecipe;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class Crafts {

    public static void load() {
        new CustomShapedRecipe("carbonBoxCraft", "111 121 111", "1BAMBOO 2CHARCOAL", StaticItems.carbonBox);
        ItemStack bambooShear = new ItemStack(Material.SHEARS);
        bambooShear.setDurability((short) 234);
        ItemStack bambooShear2 = new ItemStack(Material.SHEARS);
        bambooShear2.setDurability((short) 230);
        new CustomShapedRecipe("bambooShearCraft", "011 111 210", "1BAMBOO 2CLAY_BALL", bambooShear);
        new CustomShapedRecipe("craftBlackDyeCraft", "010 121 010", "1BAMBOO 2CHARCOAL", Material.BLACK_DYE);
        new CustomShapedRecipe("craftBlueDyeCraft", "010 121 010", "1BAMBOO 2ICE", Material.BLUE_DYE);
        new CustomShapedRecipe("craftBrownDyeCraft", "010 121 010", "1BAMBOO 2COCOA_BEANS", Material.BROWN_DYE);
        new CustomShapedRecipe("craftGreenDyeCraft", "010 121 010", "1BAMBOO 2CACTUS", Material.GREEN_DYE);
        new CustomShapedRecipe("craftRedDyeCraft", "010 121 010", "1BAMBOO 2SWEET_BERRIES", Material.RED_DYE);
        new CustomShapedRecipe("craftYellowDyeCraft", "010 121 010", "1BAMBOO 2PUMPKIN", Material.YELLOW_DYE);
        new CustomShapedRecipe("oakSaplingCraft", "000 010 111", "1BAMBOO", Material.OAK_SAPLING);
        new CustomShapedRecipe("birchSaplingCraft", "100 000 000", "1OAK_SAPLING", Material.BIRCH_SAPLING);
        new CustomShapedRecipe("spruceSaplingCraft", "110 000 000", "1OAK_SAPLING", Material.SPRUCE_SAPLING);
        new CustomShapedRecipe("acaciaSaplingCraft", "111 000 000", "1OAK_SAPLING", Material.ACACIA_SAPLING);
        new CustomShapedRecipe("jungleSaplingCraft", "000 100 000", "1OAK_SAPLING", Material.JUNGLE_SAPLING);
        new CustomShapedRecipe("darkOakSaplingCraft", "000 110 000", "1OAK_SAPLING", Material.DARK_OAK_SAPLING);
        new CustomShapedRecipe("crimsonSaplingCraft", "000 111 000", "1OAK_SAPLING", Material.CRIMSON_FUNGUS);
        new CustomShapedRecipe("warpedSaplingCraft", "000 000 100", "1OAK_SAPLING", Material.WARPED_FUNGUS);
        new CustomShapedRecipe("chorusFlowerCraft", "111 111 111", "1CHORUS_FRUIT", Material.CHORUS_FLOWER);
        new CustomShapedRecipe("graniteCraft", "131 324 141", "1COBBLESTONE 2STONE 3RED_DYE 4RED_DYE", Material.GRANITE);
        new CustomShapedRecipe("dioriteCraft", "131 324 141", "1COBBLESTONE 2STONE 3WHITE_DYE 4BLACK_DYE", Material.DIORITE);
        new CustomShapedRecipe("andesiteCraft", "131 324 141", "1COBBLESTONE 2STONE 3BLACK_DYE 4WHITE_DYE", Material.ANDESITE);
        new CustomShapedRecipe("cobbledDeepslateCraft", "131 324 141", "1COBBLESTONE 2STONE 3BLACK_DYE 4BLACK_DYE", Material.COBBLED_DEEPSLATE);
        new CustomShapedRecipe("calciteCraft", "131 324 141", "1COBBLESTONE 2STONE 3WHITE_DYE 4WHITE_DYE", Material.CALCITE);
        new CustomShapedRecipe("tuffCraft", "131 324 141", "1COBBLESTONE 2STONE 3BLACK_DYE 4GREEN_DYE", Material.TUFF);
        new CustomShapedRecipe("dirtCraft1", "123 242 321", "1BAMBOO 2OAK_LEAVES 3OAK_SAPLING 4CHARCOAL", Material.DIRT);
        new CustomShapedRecipe("dirtCraft2", "123 242 321", "1BAMBOO 2BIRCH_LEAVES 3BIRCH_SAPLING 4CHARCOAL", Material.DIRT);
        new CustomShapedRecipe("dirtCraft3", "123 242 321", "1BAMBOO 2SPRUCE_LEAVES 3SPRUCE_SAPLING 4CHARCOAL", Material.DIRT);
        new CustomShapedRecipe("dirtCraft4", "123 242 321", "1BAMBOO 2JUNGLE_LEAVES 3JUNGLE_SAPLING 4CHARCOAL", Material.DIRT);
        new CustomShapedRecipe("dirtCraft5", "123 242 321", "1BAMBOO 2DARK_OAK_LEAVES 3DARK_OAK_SAPLING 4CHARCOAL", Material.DIRT);
        new CustomShapedRecipe("dirtCraft6", "123 242 321", "1BAMBOO 2ACACIA_LEAVES 3ACACIA_SAPLING 4CHARCOAL", Material.DIRT);
        new CustomShapedRecipe("bundleCraft", "121 313 333", "1FEATHER 2SUGAR_CANE 3LEATHER", Material.BUNDLE);

        new CustomShapedRecipe("gravelCraft", "121 232 121", "1SAND 2STONE 3SLIME_BALL", new ItemStack(Material.GRAVEL, 8));

        new CustomShapelessRecipe("boneMealCraft", new Material[]{Material.BONE}, new ItemStack(Material.BONE_MEAL, 2));
        new CustomShapelessRecipe("whiteWoolCraft", new Material[]{Material.FEATHER}, new ItemStack(Material.WHITE_WOOL), 4);
        new CustomShapelessRecipe("shearCraft2", new Material[]{Material.SHEARS, Material.SHEARS}, bambooShear2);

        new CustomSmeltRecipe("sandSmelt", new ItemStack(Material.SAND), Material.DIRT, 1f, 400);
    }

}
