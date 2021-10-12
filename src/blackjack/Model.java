package blackjack;

import java.util.Observable;

/**
 *
 * @author ivanc
 */
public class Model extends Observable {

    final int DECKCAPACITY = 52;

    Deck myDeck; // instantiates a deck object  
//    Player player;
//    Dealer dealer;
    Data data = new Data();
    Bet bet;
    CheckBJ checkBJ;
    CheckWinner checkWinner;

    Database db = new Database();

    private int winner = 0;

    ;
    public Model() {
        checkWinner = new CheckWinner();
        myDeck = new Deck(DECKCAPACITY);
//        dealer = new Dealer();
        checkBJ = new CheckBJ();
        bet = new Bet();
        db.dbsetup();
        data.dealer = new Dealer();

    }

    public void checkName(String playerName, String password) {

        this.data.player = db.checkName(playerName, password);

        System.out.println(data.player.getPlayerBalance());
        this.setChanged();
        this.notifyObservers(this.data);

    }

    public void placeBet(Player player, Double betAmount) {
//        if (player.isLoginFlag() && betAmount <= player.getPlayerBalance()) {
        System.out.println("Bet placed");
        this.bet.placeBet(player, betAmount);

        this.setChanged();
        this.notifyObservers(this.data);
//        }

    }

    public void initialDeal() {
        System.out.println("Dealing initial caRds...");
        System.out.println("INTIAL WINS : " + data.player.getPlayerWins());
        System.out.println("INTIAL Loss : " + data.player.getPlayerLoss());

        if (!(data.player.getPlayerFinished() || data.dealer.getDealerFinished())) {

            // INITIAL DEAL of 2 cards p/player (dealer's second card ealth face down)
            for (int i = 0; i < 2; i++) {
                data.player.hit(myDeck.deal());
                data.dealer.hit(myDeck.deal());
            }
        }
        System.out.println(data.dealer);
        System.out.println(data.player);
        checkBJ.checkBlackJack(data.player, data.dealer);
        if (data.player.isHasWon() || data.dealer.isHasWon() == true) { //if anyone has blackjack 
            checkWin();
        }
        this.setChanged();
        this.notifyObservers(this.data);
    }

    public void playerHit() {
        Card c = null;
        if (data.player.calcHandValue() <= 21) {
            c = myDeck.deal();
            data.player.hit(c);

        }
        if (data.player.calcHandValue() > 21) {
            data.player.setPlayerFinished(true);
            dealerPlay();
        }
        System.out.println(data.dealer);
        System.out.println(data.player);
        this.setChanged();
        this.notifyObservers(this.data);
    }

    public void playerStand() {
        data.player.setPlayerFinished(true);
        dealerPlay();
        this.setChanged();
        this.notifyObservers(this.data);
    }

    public void dealerPlay() {
        if ((data.player.calcHandValue() < 21) && !data.dealer.getDealerFinished()) {
            // DEALER TURN - CAN HIT IF HANDVALUE < 17   

            // dealer only hits if allowed and is beneficial (eg player has a higher handvalue than them)
            while ((data.dealer.calcHandValue() < 17)
                    && (data.player.calcHandValue() > data.dealer.calcHandValue())) {

                System.out.println("*DEALER HITS*");
                data.dealer.hit(myDeck.deal());
                System.out.println(data.dealer);

            }
            if (data.dealer.calcHandValue() > 21) {  //checks dealer bust          
                data.dealer.setDealerFinished(true);

            }
        }

        checkWin();

        this.setChanged();
        this.notifyObservers(this.data);

    }

    public void checkWin() {
        checkWinner.winCondition(data.player, data.dealer);
        this.winner = checkWinner.getWinner();

        switch (winner) {
            case (1):
                System.out.println("1");
                break;
            case (2):
                System.out.println("2");
                break;
            case (3):
                System.out.println("3");
                break;

        }
        setBet();
//        quitGame();
        this.setChanged();
        this.notifyObservers(this.data);
    }

    public void setBet() {

        this.bet.settleBet(data.player, winner);
        this.setChanged();
        this.notifyObservers(this.data); // update balance label
    }

    public void quitGame() {
        /**
         * Update data in the database. Go to quitGame() of Database.java for a
         * fast check.
         */
        this.db.quitGame(data.player.getPlayerName(), data.player.getPlayerBalance(), data.player.getPlayerWins(), data.player.getPlayerLoss());
        this.data.quitFlag = true; // Mark quitFlag as false.
        this.setChanged();
        this.notifyObservers(this.data);
        System.out.println("FINAL WINS : " + data.player.getPlayerWins());
        System.out.println("FINAL Loss : " + data.player.getPlayerLoss());
    }

    /**
     * Match logic
     *
     * @param player
     */
    public void begin(String playerName, String password) {

        //  Prompt prompt = new Prompt();
        // Prints state of player and prompts user for a bet amount
//        System.out.println("Welcome " + player.getPlayerName() + ", You have " + player.getPlayerWins() + " wins, " + player.getPlayerLoss()
//                + " losses and currently have a balance of $" + player.getPlayerBalance() + "\n");
        // USER'S TURN   
//        bet.settleBet(player);
    }

}
