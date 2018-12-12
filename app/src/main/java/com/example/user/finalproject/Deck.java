package com.example.user.finalproject;

public class Deck {
    private Card[] deck = new Card[52];

    public Deck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                String[] rank = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k"};
                if (i == 0) {
                    deck[j] = new Card(rank[j], "c");
                } else if (i == 1) {
                    deck[13 + j] = new Card(rank[j], "d");
                } else if (i == 2) {
                    deck[26 + j] = new Card(rank[j], "h");
                } else {
                    deck[39 + j] = new Card(rank[j], "s");
                }
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < deck.length; i++) {
            Card tmp = deck[i];
            int newPos = (int)(Math.random()*51);
            deck[i] = deck[newPos];
            deck[newPos] = tmp;
        }
    }

    public Card draw(int cardPos) {
        return deck[cardPos];
    }
}
