package com.orbi.orbimc.systems.autopickup;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

public class Pickup {

    public static void checkPlayerInventory(BlockDropItemEvent e) {
        Material blockType = e.getBlock().getType();
        if (blockType.isInteractable())
            return;
        Player p = e.getPlayer();
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null && i.getType().equals(Material.BUNDLE)) {
                BundleMeta bm = (BundleMeta) i.getItemMeta();
                int size = 0;
                for (ItemStack tm : bm.getItems())
                    size += tm.getAmount();
                if (size + e.getItems().size() <= 64) {
                    for (Item it : e.getItems()) {
                        for (ItemStack bmt : bm.getItems()) {
                            ItemStack itms = it.getItemStack();
                            if (bmt.getType().equals(itms.getType())) {
                                bmt.setAmount(bmt.getAmount() + itms.getAmount());
                                itms.setAmount(0);
                            }
                        }
                        if (it.getItemStack().getAmount() > 0)
                            bm.addItem(it.getItemStack());
                    }
                    i.setItemMeta(bm);
                    e.getItems().clear();
                    return;
                }
            }
        }
    }
}


