package com.orbi.orbimc.systems.customcraft;

import com.orbi.orbimc.OrbiCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class CustomShapelessRecipe {

    NamespacedKey namespacedKey;
    Material[] ingredients;
    ItemStack result;
    int amount;

    public CustomShapelessRecipe(String namespacedKey, Material[] ingredients, ItemStack result) {
        this.namespacedKey = new NamespacedKey(OrbiCore.getInstance(), namespacedKey);
        this.ingredients = ingredients;
        this.result = result;
        buildCraft();
    }

    public CustomShapelessRecipe(String namespacedKey, Material[] ingredients, ItemStack result, int amount) {
        this.namespacedKey = new NamespacedKey(OrbiCore.getInstance(), namespacedKey);
        this.ingredients = ingredients;
        this.result = result;
        this.amount = amount;
        buildCraftAmount();
    }

    private void buildCraft() {
        ShapelessRecipe recipe = new ShapelessRecipe(namespacedKey, result);
        for (Material m : ingredients)
            recipe.addIngredient(m);
        Bukkit.addRecipe(recipe);
        RecipeController.recipes.add(namespacedKey);
    }

    private void buildCraftAmount() {
        ShapelessRecipe recipe = new ShapelessRecipe(namespacedKey, result);
        recipe.addIngredient(amount, ingredients[0]);
        Bukkit.addRecipe(recipe);
        RecipeController.recipes.add(namespacedKey);
    }
}
