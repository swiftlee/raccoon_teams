package com.phaseos.team;

public enum Rank {
    OWNER(2), CAPTAIN(1), NORMAL(0);

    private final int ranking;

    Rank(int ranking) {
        this.ranking = ranking;
    }

    public boolean isGreaterThan(Rank rank) {
        return this.ranking > rank.getRanking();
    }

    public boolean isLessThan(Rank rank) {
        return this.ranking < rank.getRanking();
    }

    public int getRanking() {
        return this.ranking;
    }

    public static Rank fromString(String str) {
        if (str.equalsIgnoreCase("owner"))
            return OWNER;
        else if (str.equalsIgnoreCase("captain"))
            return CAPTAIN;
        else if (str.equalsIgnoreCase("normal"))
            return NORMAL;

        return null;
    }
}
