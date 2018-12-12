package com.example.user.finalproject;

public class Player {
    private int money;
    private int totalCardValue = 0;
    private int cardCount = 1;
    public Card card1;
    public Card card2;
    public Card card3;
    public Card card4;
    public Card card5;
    public Card card6;
    public Card card7;
    public Card card8;

    public Player(int setMoney) {
        money = setMoney;
        newDeck();
    }

    public void resetCards() {
        newDeck();
        totalCardValue = 0;
        cardCount = 1;
        card3.setCardDown();
        card4.setCardDown();
        card5.setCardDown();
        card6.setCardDown();
        card7.setCardDown();
        card8.setCardDown();
    }

    public void newDeck() {
        Deck playerDeck = new Deck();
        playerDeck.shuffle();
        card1 = playerDeck.draw(0);
        card2 = playerDeck.draw(1);
        card3 = playerDeck.draw(2);
        card4 = playerDeck.draw(3);
        card5 = playerDeck.draw(4);
        card6 = playerDeck.draw(5);
        card7 = playerDeck.draw(6);
        card8 = playerDeck.draw(7);
    }

    public int getMoney() {
        return money;
    }
    public void changeMoney(int changeMoney) {
        money += changeMoney;
    }
    public int getTotalCardValue() {
        return totalCardValue;
    }
    public void changeTotalCardValue(int changeTCV) {
        totalCardValue += changeTCV;
    }

    public String getCardID(int cardNum) {
        if (cardNum == 1) {
            return card1.getID();
        } else if (cardNum == 2) {
            return card2.getID();
        } else if (cardNum == 3) {
            return card3.getID();
        } else if (cardNum == 4) {
            return card4.getID();
        } else if (cardNum == 5) {
            return card5.getID();
        } else if (cardNum == 6) {
            return card6.getID();
        } else if (cardNum == 7) {
            return card7.getID();
        } else {
            return card8.getID();
        }
    }

    public void playerHit() {
        if (cardCount == 1) {
            totalCardValue += card1.getValue();
            card1.isUp = true;
        } else if (cardCount == 2) {
            totalCardValue += card2.getValue();
            card2.isUp = true;
        } else if (cardCount == 3) {
            totalCardValue += card3.getValue();
            card3.isUp = true;
        } else if (cardCount == 4) {
            totalCardValue += card4.getValue();
            card4.isUp = true;
        } else if (cardCount == 5) {
            totalCardValue += card5.getValue();
            card5.isUp = true;
        } else if (cardCount == 6) {
            totalCardValue += card6.getValue();
            card6.isUp = true;
        } else if (cardCount == 7) {
            totalCardValue += card7.getValue();
            card7.isUp = true;
        } else if (cardCount == 8) {
            totalCardValue += card8.getValue();
            card8.isUp = true;
        }
        cardCount++;
    }

    public int getCardCount() {
        return cardCount;
    }
}
