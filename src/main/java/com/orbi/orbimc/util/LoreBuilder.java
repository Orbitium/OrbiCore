package com.orbi.orbimc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoreBuilder {

    List<String> lore = new ArrayList<>();

    public LoreBuilder(String... args) {
        this.lore = Arrays.asList(args);
    }

    public LoreBuilder add(String... args) {
        lore.addAll(Arrays.asList(args));
        return this;
    }

    public List<String> build() {
        return this.lore;
    }
}
