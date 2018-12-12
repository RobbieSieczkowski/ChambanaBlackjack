package com.example.user.finalproject;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DisplayNewGame extends AppCompatActivity {

    private RequestQueue mQueue;
    boolean loaded = false;
    private boolean isGameStarted = false;
    private boolean userTurn = true;
    private Integer potMoney = 0;
    private Player user = new Player(500);
    private Player dealer = new Player(500);

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_new_game);
        mQueue = Volley.newRequestQueue(this);
    }

    public void startNewGame() {
        isGameStarted = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String card1ID = user.getCardID(1);
                ImageView image = findViewById(R.id.userL);
                image.setImageResource(getImageId(DisplayNewGame.this, card1ID));
                user.playerHit();
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String card2ID = user.getCardID(2);
                        ImageView image2 = findViewById(R.id.userR);
                        image2.setImageResource(getImageId(DisplayNewGame.this, card2ID));
                        user.playerHit();
                        Handler handler3 = new Handler();
                        handler3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String dealerCardID = dealer.getCardID(1);
                                ImageView image3 = findViewById(R.id.dealerL);
                                image3.setImageResource(getImageId(DisplayNewGame.this, dealerCardID));
                                dealer.playerHit();
                            }
                        }, 500);
                    }
                }, 500);
            }
        }, 500);
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    /** Called when the user inputs bet/Inputs user's bet */
    public void setBet(View view) {
        if (!isGameStarted) {
            EditText betText = findViewById(R.id.bet);
            String betResult = betText.getText().toString();
            if (betResult.length() == 0) {
                return;
            }
            Integer number1 = Integer.valueOf(betResult);
            Integer number2 = user.getMoney();
            if (number1 > number2) {
                number1 = number2;
            }
            user.changeMoney(-number1);
            potMoney += number1 * 2;
            TextView potText = findViewById(R.id.pot);
            potText.setText(potMoney.toString());
            Integer userTotal = number2 - number1;
            String totalString = userTotal.toString();
            TextView userText = findViewById(R.id.user);
            userText.setText(totalString);
            Integer number3 = dealer.getMoney();
            if (number3 < number1) {
                number1 = number3;
            }
            dealer.changeMoney(-number1);
            Integer dealerTotal = number3 - number1;
            TextView dealerText = findViewById(R.id.dealer);
            dealerText.setText(dealerTotal.toString());
            Log.v("Bet", "You bet");
            startNewGame();
        }
    }

    /** Called when the user taps the Hit button/Gives player another card */
    public void hit(View view) {
        if (isGameStarted && userTurn) {
            if (user.getCardCount() == 3) {
                ImageView image = findViewById(R.id.userL1);
                image.setImageResource(getImageId(this, user.getCardID(3)));
                image.setVisibility(View.VISIBLE);
            } else if (user.getCardCount() == 4) {
                ImageView image = findViewById(R.id.userR1);
                image.setImageResource(getImageId(this, user.getCardID(4)));
                image.setVisibility(View.VISIBLE);
            } else if (user.getCardCount() == 5) {
                ImageView image = findViewById(R.id.userL2);
                image.setImageResource(getImageId(this, user.getCardID(5)));
                image.setVisibility(View.VISIBLE);
            } else if (user.getCardCount() == 6) {
                ImageView image = findViewById(R.id.userR2);
                image.setImageResource(getImageId(this, user.getCardID(6)));
                image.setVisibility(View.VISIBLE);
            } else if (user.getCardCount() == 7) {
                ImageView image = findViewById(R.id.userL3);
                image.setImageResource(getImageId(this, user.getCardID(7)));
                image.setVisibility(View.VISIBLE);
            } else if (user.getCardCount() == 8) {
                ImageView image = findViewById(R.id.userR3);
                image.setImageResource(getImageId(this, user.getCardID(8)));
                image.setVisibility(View.VISIBLE);
            }
            user.playerHit();
            Button doubleBetButton = findViewById(R.id.doubleBet);
            doubleBetButton.setVisibility(View.GONE);
            Log.v("Hit", "You hit");
            Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (user.getCardCount() == 9) {
                        callDealer();
                    } else {
                        postMove();
                    }
                }
            }, 1000);
        }
    }

    /** Called when the user taps the Double button/Doubles player's bet */
    public void doubleBet(View view) {
        if (isGameStarted && userTurn && potMoney != 0) {
            Log.v("Double", "You doubled");
            if ((int) (potMoney / 2) > user.getMoney()) {
                return;
            }
            user.changeMoney((int) (-potMoney / 2));
            TextView userText = findViewById(R.id.user);
            Integer userMoney = user.getMoney();
            userText.setText(userMoney.toString());
            TextView dealerText = findViewById(R.id.dealer);
            if (potMoney > dealer.getMoney()) {
                dealer.changeMoney(-dealer.getMoney());
                Integer dealerMoney = dealer.getMoney();
                dealerText.setText(dealerMoney.toString());
            } else {
                dealer.changeMoney((int) -(potMoney / 2));
                Integer dealerMoney = dealer.getMoney();
                dealerText.setText(dealerMoney.toString());
            }
            potMoney = potMoney * 2;
            TextView potText = findViewById(R.id.pot);
            potText.setText(potMoney.toString());
            Button doubleBetButton = findViewById(R.id.doubleBet);
            doubleBetButton.setVisibility(View.GONE);
            ImageView image = findViewById(R.id.userL1);
            image.setImageResource(getImageId(this, user.getCardID(3)));
            image.setVisibility(View.VISIBLE);
            user.playerHit();
            Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (postMove()) {
                        callDealer();
                    }
                }
            }, 1000);
        }
    }

    /** Called when the user taps the Stand button/Ends player's turn */
    public void stand(View view) {
        if (isGameStarted && userTurn) {
            Log.v("Stand", "You stood");
            if ((user.card1.getValue() + user.card2.getValue()) == 21) {
                Log.v("CheckBlackjack", "You win!");
                final TextView blackjackText = findViewById(R.id.blackjack);
                blackjackText.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blackjackText.setVisibility(View.INVISIBLE);
                    }
                }, 4000);
                userWins();
            } else {
                callDealer();
            }
        }
    }

    /** Called after the user makes a move/Checks if round is over and takes action if so */
    public boolean postMove() {
        Log.v("PostMove", "Post-move check");
        if (user.getTotalCardValue() > 21) {
            if (user.card1.ifUnusedAce && user.card1.isUp) {
                user.changeTotalCardValue(-10);
                user.card1.ifUnusedAce = false;
                return true;
            } else if (user.card2.ifUnusedAce && user.card2.isUp) {
                user.changeTotalCardValue(-10);
                user.card2.ifUnusedAce = false;
                return true;
            } else if (user.card3.ifUnusedAce && user.card3.isUp) {
                user.changeTotalCardValue(-10);
                user.card3.ifUnusedAce = false;
                return true;
            } else if (user.card4.ifUnusedAce && user.card4.isUp) {
                user.changeTotalCardValue(-10);
                user.card4.ifUnusedAce = false;
                return true;
            } else if (user.card5.ifUnusedAce && user.card5.isUp) {
                user.changeTotalCardValue(-10);
                user.card5.ifUnusedAce = false;
                return true;
            } else if (user.card6.ifUnusedAce && user.card6.isUp) {
                user.changeTotalCardValue(-10);
                user.card6.ifUnusedAce = false;
                return true;
            } else if (user.card7.ifUnusedAce && user.card7.isUp) {
                user.changeTotalCardValue(-10);
                user.card7.ifUnusedAce = false;
                return true;
            } else if (user.card8.ifUnusedAce && user.card8.isUp) {
                user.changeTotalCardValue(-10);
                user.card8.ifUnusedAce = false;
                return true;
            } else {
                dealerWins();
            }
        } else if (user.getTotalCardValue() == 21) {
            potMoney = (int) (potMoney * 1.5);
            callDealer();
            return false;
        }
        return false;
    }

    /** Called when it's the dealer's turn/Runs through dealer's turn */
    public void callDealer() {
        Log.v("CallDealer", "Dealer is called");
        userTurn = false;
        for (int i = 2; i < 9; i++) {
            if (dealer.getTotalCardValue() > 16) {
                break;
            }
            dealer.playerHit();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dealer.getCardCount() >= 3) {
                    ImageView image = findViewById(R.id.dealerR);
                    image.setImageResource(getImageId(DisplayNewGame.this, dealer.getCardID(2)));
                    image.setVisibility(View.VISIBLE);
                }
                if (dealer.getCardCount() >= 4) {
                    ImageView image = findViewById(R.id.dealerL1);
                    image.setImageResource(getImageId(DisplayNewGame.this, dealer.getCardID(3)));
                    image.setVisibility(View.VISIBLE);
                }
                if (dealer.getCardCount() >= 5) {
                    ImageView image = findViewById(R.id.dealerR1);
                    image.setImageResource(getImageId(DisplayNewGame.this,
                            dealer.getCardID(4)));
                    image.setVisibility(View.VISIBLE);
                }
                if (dealer.getCardCount() >= 6) {
                    ImageView image = findViewById(R.id.dealerL2);
                    image.setImageResource(getImageId(DisplayNewGame.this,
                            dealer.getCardID(5)));
                    image.setVisibility(View.VISIBLE);
                }
                if (dealer.getCardCount() >= 7) {
                    ImageView image = findViewById(R.id.dealerR2);
                    image.setImageResource(getImageId(DisplayNewGame.this,
                            dealer.getCardID(6)));
                    image.setVisibility(View.VISIBLE);
                }
                if (dealer.getCardCount() >= 8) {
                    ImageView image = findViewById(R.id.dealerL3);
                    image.setImageResource(getImageId(DisplayNewGame.this,
                            dealer.getCardID(7)));
                    image.setVisibility(View.VISIBLE);
                }
                if (dealer.getCardCount() >= 9) {
                    ImageView image = findViewById(R.id.dealerR3);
                    image.setImageResource(getImageId(DisplayNewGame.this,
                            dealer.getCardID(8)));
                    image.setVisibility(View.VISIBLE);
                }
                if (checkIfWon()) {
                    userWins();
                } else {
                    dealerWins();
                }
            }
        }, 1000);
    }

    /** Checks if user has won */
    public boolean checkIfWon() {
        if (user.getTotalCardValue() > 21) {
            Log.v("CheckWinner", "You lose (You bust)");
            return false;
        } else if (dealer.getTotalCardValue() > 21) {
            Log.v("CheckWinner", "You win (Dealer bust)");
            return true;
        } else if ((user.card1.getValue() + user.card2.getValue()) == 21) {
            Log.v("CheckWinner", "You win!");
            final TextView blackjackText = findViewById(R.id.blackjack);
            blackjackText.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    blackjackText.setVisibility(View.INVISIBLE);
                }
            }, 4000);
            return true;
        } else if (user.getTotalCardValue() > dealer.getTotalCardValue()){
            Log.v("CheckWinner", "You win!");
            return true;
        } else {
            Log.v("CheckWinner", "You lose");
            return false;
        }
    }

    /** Runs when user wins/With a win sound */
    public void userWins() {
        Log.v("UserWins", "You get money");
        user.changeMoney(potMoney);
        TextView userText = findViewById(R.id.user);
        Integer userMoney = user.getMoney();
        userText.setText(userMoney.toString());
        final TextView userWinsText = findViewById(R.id.userWins);
        userWinsText.setVisibility(View.VISIBLE);

        final SoundPool soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .build();
        final int soundID = soundPool.load(this, R.raw.tada, 1);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPool.play(soundID, 1, 1, 1, 0, 1);
                loaded = false;
            }
        }, 500);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                userWinsText.setVisibility(View.INVISIBLE);
                setNewRound();
            }
        }, 4000);
    }

    /** Runs when dealer wins */
    public void dealerWins() {
        Log.v("DealerWins", "Dealer gets money");
        dealer.changeMoney(potMoney);
        TextView userText = findViewById(R.id.dealer);
        Integer dealerMoney = dealer.getMoney();
        userText.setText(dealerMoney.toString());
        final TextView dealerWinsText = findViewById(R.id.dealerWins);
        dealerWinsText.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dealerWinsText.setVisibility(View.INVISIBLE);
                setNewRound();
            }
        }, 4000);
    }

    /** Sets up next round */
    public void setNewRound() {
        ImageView image1 = findViewById(R.id.userL);
        image1.setImageResource(R.drawable.red_back);
        ImageView image2 = findViewById(R.id.userR);
        image2.setImageResource(R.drawable.red_back);
        ImageView image3 = findViewById(R.id.userL1);
        image3.setVisibility(View.INVISIBLE);
        ImageView image4 = findViewById(R.id.userL2);
        image4.setVisibility(View.INVISIBLE);
        ImageView image5 = findViewById(R.id.userL3);
        image5.setVisibility(View.INVISIBLE);
        ImageView image6 = findViewById(R.id.userR1);
        image6.setVisibility(View.INVISIBLE);
        ImageView image7 = findViewById(R.id.userR2);
        image7.setVisibility(View.INVISIBLE);
        ImageView image8 = findViewById(R.id.userR3);
        image8.setVisibility(View.INVISIBLE);
        ImageView image9 = findViewById(R.id.dealerL);
        image9.setImageResource(R.drawable.red_back);
        ImageView image10 = findViewById(R.id.dealerR);
        image10.setImageResource(R.drawable.red_back);
        ImageView image11 = findViewById(R.id.dealerL1);
        image11.setVisibility(View.INVISIBLE);
        ImageView image12 = findViewById(R.id.dealerL2);
        image12.setVisibility(View.INVISIBLE);
        ImageView image13 = findViewById(R.id.dealerL3);
        image13.setVisibility(View.INVISIBLE);
        ImageView image14 = findViewById(R.id.dealerR1);
        image14.setVisibility(View.INVISIBLE);
        ImageView image15 = findViewById(R.id.dealerR2);
        image15.setVisibility(View.INVISIBLE);
        ImageView image16 = findViewById(R.id.dealerR3);
        image16.setVisibility(View.INVISIBLE);
        Button doubleBetButton = findViewById(R.id.doubleBet);
        doubleBetButton.setVisibility(View.VISIBLE);
        userTurn = true;
        isGameStarted = false;
        potMoney = 0;
        user.resetCards();
        dealer.resetCards();
        dealer.card2.setCardDown();
        TextView potText = findViewById(R.id.pot);
        potText.setText(potMoney.toString());
        Log.v("SetNewRound", "New round is set up");
    }

    /** Called when the user taps the Start New Game button/Restarts DisplayNewGame Activity */
    public void restartGame(View view) {
        Intent intent = new Intent(this, DisplayNewGame.class);
        startActivity(intent);
        Log.v("RestartGame", "Game is restarted");
    }

    /** Called when the user taps the Quit Game button/Returns to MainActivity */
    public void quit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.v("Quit", "You quit");
    }
}
