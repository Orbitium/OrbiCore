package com.orbi.orbimc.math;

public enum ConvertType {

    DEFAULT("", "bin", "milyon", "milyar"),
    ENERGY("watt", "kilowatt", "megawatt", "gigawatt");

    String[] chars;

    ConvertType(String... chars) {
        this.chars = chars;
    }

    public String[] getChars() {
        return chars;
    }
}
