package com.orbi.orbimc.systems.generator.generatorv2;

import org.bukkit.Material;
import org.bukkit.event.block.BlockFormEvent;

public class GeneratorListener {

    public void onGenerateCobblestone(BlockFormEvent e) {
        if (e.getNewState().getType() == Material.COBBLESTONE) {

        }
    }

}
