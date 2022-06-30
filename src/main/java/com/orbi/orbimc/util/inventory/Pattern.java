package com.orbi.orbimc.util.inventory;

import com.orbi.orbimc.util.StaticItems;
import org.bukkit.inventory.Inventory;

public enum Pattern {
    BORDER {
        @Override
        public Inventory loadContent(Inventory inv) {
            int size = inv.getSize();
            for (int i = 0; i < 9; i++)
                inv.setItem(i, StaticItems.spaceItem);
            for (int i = size - 9; i < size; i++)
                inv.setItem(i, StaticItems.spaceItem);
            for (int i = 9; i < size; i += 9)
                inv.setItem(i, StaticItems.spaceItem);
            for (int i = 17; i < size; i += 9)
                inv.setItem(i, StaticItems.spaceItem);
            return inv;
        }
    },
    FILL {
        @Override
        public Inventory loadContent(Inventory inv) {
            for (int i = 0; i < inv.getSize(); i++)
                inv.setItem(i, StaticItems.spaceItem);
            return inv;
        }
    },
    PARCHMENT {
        @Override
        public Inventory loadContent(Inventory inv) {
            return null;
        }
    },

    EMPTY {
        @Override
        public Inventory loadContent(Inventory inv) {
            return inv;
        }
    };

    public abstract Inventory loadContent(Inventory inv);
}
