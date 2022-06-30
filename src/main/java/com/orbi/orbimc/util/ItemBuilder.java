package com.orbi.orbimc.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ItemBuilder implements Serializable {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(String value, String name, List<String> lore) {
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        itemStack = new ItemStack(Material.PLAYER_HEAD);
        itemStack = Bukkit.getUnsafe().modifyItemStack(this.itemStack,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
    }

    public ItemBuilder(String value, String name, String lore) {
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        itemStack = new ItemStack(Material.PLAYER_HEAD);
        itemStack = Bukkit.getUnsafe().modifyItemStack(this.itemStack,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Collections.singletonList(lore));
    }

    public ItemBuilder(String value, String name) {
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        itemStack = new ItemStack(Material.PLAYER_HEAD);
        itemStack = Bukkit.getUnsafe().modifyItemStack(this.itemStack,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
    }

    public ItemBuilder(String value, String name, int amount) {
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        itemStack = new ItemStack(Material.PLAYER_HEAD, amount);
        itemStack = Bukkit.getUnsafe().modifyItemStack(this.itemStack,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
    }

    public ItemBuilder(Material material, String name) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
    }


    public ItemBuilder(Material material, String lore, boolean isLore, boolean custom) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Collections.singletonList(lore));
    }

    public ItemBuilder(Material material, String name, List<String> lore) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(name);
        this.itemMeta.setLore(lore);
    }

    public ItemBuilder(Material material, String name, String lore) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(name);
        this.itemMeta.setLore(Collections.singletonList(lore));
    }

    public ItemBuilder(Material material, String name, List<String> lore, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(name);
        this.itemMeta.setLore(lore);
    }

    public ItemBuilder(Material material, String name, boolean glow) {
        itemStack = new ItemStack(material);
        if (glow)
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 0);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        if (glow)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder(Material material, String name, List<String> lore, boolean glow) {
        this.itemStack = new ItemStack(material);
        if (glow)
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 0);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(name);
        this.itemMeta.setLore(lore);
        if (glow)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder(Material material, boolean glow) {
        this.itemStack = new ItemStack(material);
        if (glow)
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 0);
        this.itemMeta = itemStack.getItemMeta();
        if (glow)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder(ItemStack itemstack, boolean glow) {
        this.itemStack = new ItemStack(itemstack);
        if (glow)
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 0);
        this.itemMeta = itemStack.getItemMeta();
        if (glow)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder(Material material, String name, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public String getName() {
        return itemMeta.getDisplayName();
    }

    public List<String> getLore() {
        return itemMeta.getLore();
    }


    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public void addLore(String... lore) {
        itemMeta.getLore().addAll(List.of(lore));
    }

    public boolean hasLore() {
        return itemMeta.hasLore();
    }

    public int getDamage() {
        Damageable d = (Damageable) itemStack.getItemMeta();
        return d.getDamage();
    }

    public void setDamage(int damage) {
        Damageable d = (Damageable) itemStack.getItemMeta();
        d.setDamage(damage);
        itemMeta = d;
    }

    public ItemBuilder updatePDC(NamespacedKey key, PersistentDataType persistentDataType, Object value) {
        itemMeta.getPersistentDataContainer().set(key, persistentDataType, value);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
