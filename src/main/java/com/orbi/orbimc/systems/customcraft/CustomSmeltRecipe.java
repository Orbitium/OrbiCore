package com.orbi.orbimc.systems.customcraft;

import com.orbi.orbimc.OrbiCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CustomSmeltRecipe {

    NamespacedKey namespacedKey;
    ItemStack need;
    Material result;
    float xp;
    int smeltTime;

    public CustomSmeltRecipe(String namespacedKey, ItemStack need, Material result, float xp, int smeltTime) {
        this.namespacedKey = new NamespacedKey(OrbiCore.getInstance(), namespacedKey);
        this.need = need;
        this.result = result;
        this.xp = xp;
        this.smeltTime = smeltTime;
        buildCraft();
    }

    void buildCraft() {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(namespacedKey, need, result, xp, smeltTime);
        Bukkit.addRecipe(furnaceRecipe);
        RecipeController.recipes.add(namespacedKey);
    }
}
