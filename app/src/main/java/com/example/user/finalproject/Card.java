package com.example.user.finalproject;

class Card {
    private String rank;
    private String suit;
    public boolean ifUnusedAce = false;
    public boolean isUp = false;

    public Card(String setRank, String setSuit) {
        rank = setRank;
        suit = setSuit;
        if (rank.equals("a")) {
            ifUnusedAce = true;
        }
    }

    public String getID() {
        return (suit + rank);
    }

    public void setCardDown() {
        isUp = false;
    }

    public int getValue() {
        if (rank.equals("a")) {
            return 11;
        } else if (rank.equals("2")) {
            return 2;
        } else if (rank.equals("3")) {
            return 3;
        } else if (rank.equals("4")) {
            return 4;
        } else if (rank.equals("5")) {
            return 5;
        } else if (rank.equals("6")) {
            return 6;
        } else if (rank.equals("7")) {
            return 7;
        } else if (rank.equals("8")) {
            return 8;
        } else if (rank.equals("9")) {
            return 9;
        } else if (rank.equals("10") || rank.equals("j") || rank.equals("q") || rank.equals("k")) {
            return 10;
        }
        return 0;
    }
}
