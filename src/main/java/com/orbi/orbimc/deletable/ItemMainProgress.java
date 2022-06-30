package com.orbi.orbimc.deletable;

public enum ItemMainProgress {
    HAYVAN(0, "Deşici", "Hayvanlara karşı kullanım sayısı: ", 1, 5, ItemSubProgress.KRITIK),
    YARATIK(1, "Yaratık ruhu sömürüsü", "Yaratıklara karşı kullanım sayısı: ", 0, 10, ItemSubProgress.RUHSOMURUSU);


    private final int progressID;
    private final String name;
    private final int antiProgressID;
    private final int needXP;
    private final String conditionText;
    private final ItemSubProgress[] subProgresses;

    ItemMainProgress(int progressID, String name, String conditionText, int antiProgressID, int needXP, ItemSubProgress... subProgresses) {
        this.progressID = progressID;
        this.name = name;
        this.conditionText = conditionText;
        this.antiProgressID = antiProgressID;
        this.needXP = needXP;
        this.subProgresses = subProgresses;
    }

    public int getProgressID() {
        return progressID;
    }

    public String getName() {
        return name;
    }

    public int getAntiProgressID() {
        return antiProgressID;
    }

    public int getNeedXP() {
        return needXP;
    }

    public ItemSubProgress[] getSubProgresses() {
        return subProgresses;
    }

    public String getConditionText() {
        return conditionText;
    }

    public static ItemMainProgress getByID(int id) {
        for (ItemMainProgress imp : values()) {
            if (imp.progressID == id)
                return imp;
        }
        throw new NullPointerException(id + " ID'li bir ItemMainProgress üyesi bulunmamadı!");
    }

    public void runSubProgresses(Object p) {
        for (ItemSubProgress ibs : subProgresses) {
            ibs.getEvent().accept(p);
        }
    }
}
