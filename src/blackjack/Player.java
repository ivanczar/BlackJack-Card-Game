package blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ivanc
 */
public class Player implements User {

    // memeabr variables
    private String playerName;
    private double playerBalance;
    private Hand playerHand;
    private int playerWins;
    private int playerLoss;
    private double playerBet;

    public Player(String userName, double balance, int wins, int losses) {
        setPlayerHand(new Hand());
        setPlayerName(userName);
        setPlayerBalance(balance);
        setPlayerWins(wins);
        setPlayerLoss(losses);
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String userName) {
        this.playerName = userName;
    }

    public double getPlayerBalance() {
        return playerBalance;
    }

    public void setPlayerBalance(double balance) {
        this.playerBalance = balance;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public void setPlayerWins(int playerWins) {
        this.playerWins = playerWins;
    }

    public int getPlayerLoss() {
        return playerLoss;
    }

    public void setPlayerLoss(int playerLoss) {
        this.playerLoss = playerLoss;
    }

    @Override
    public int calcHandValue() {
        return this.getPlayerHand().getHandValue();
    }

    public double getPlayerBet() {
        return playerBet;
    }

    public void setPlayerBet(double playerBet) {
        this.playerBet = playerBet;
    }

    /**
     * returns and prompts user for bet amount, deducts amount from balance and
     * handles invalid input
     *
     * @return
     * @throws InputMismatchException
     */
    public void placeBet() throws InputMismatchException {
        Scanner scan = new Scanner(System.in);
        double bet = 0;

        do {

            try {
                System.out.print("Please enter a bet amount which is below or equal to your current balance: ");
                bet = scan.nextDouble();

            } catch (InputMismatchException e) {

                System.out.println("Input must be numerical");

            }
            scan.nextLine(); //clears buffer

        } while (bet > this.getPlayerBalance());

        this.setPlayerBalance(this.getPlayerBalance() - bet); // removes bet amount from players balance
        setPlayerBet(bet);
    }

    /**
     * If allowed, adds card to player hand. Also corrects for ACE being 11 or 1
     *
     * @param card
     */
    @Override
    public void hit(Card card) {

        if (this.calcHandValue() < 21) {
            if (this.calcHandValue() >= 11 && card.getValue().equals(Value.ACE)) {

                playerHand.setHandValue(playerHand.getHandValue() - 10); // Corrects for ACE being 11 or 1
                playerHand.add(card);
            } else {
                playerHand.add(card);
            }

        }
    }

    /**
     * game logic for player's turn
     *
     * @param dealer
     * @param player
     * @param myDeck
     * @return
     * @throws InputMismatchException
     */
    @Override
    public boolean play(Dealer dealer, Player player, Deck myDeck) throws InputMismatchException {
        Scanner scan = new Scanner(System.in);

        int choice = 0;
        
        boolean isBust = false;

        // prompts user for action and catches invalid input
        while (!(choice == 1 || choice == 2)) {
            try {

                System.out.println("Hit(1) or Stand(2)?: ");
                choice = scan.nextInt();
                


            } catch (InputMismatchException e) {
   
                System.out.println("Input must be numerical");
            }
            scan.nextLine();
        }

        while (choice != 2) {

            if (player.calcHandValue() < 21) {

                if (choice == 1) { // i.e if player hits

                    player.hit(myDeck.deal()); //deal a card

                    System.out.println("========================================");
                    System.out.println(player);
                    System.out.println("Dealer's hand: [" + dealer.getDealerHand().getHand().get(0) + ", HIDDEN]  (value: UNKNOWN)");
                    System.out.println("=======================================");

                    if (player.calcHandValue() > 21) { // if player has bust
                        player.setPlayerLoss(player.getPlayerLoss() + 1); // increase player loss
                        System.out.println(player.getPlayerName() + " Bust!");
                        isBust = true;
                        return isBust;
                    } else {

                        choice = 0;
                        while (!(choice == 1 || choice == 2)) {
                            try {
                                System.out.println("Type 1 to Hit or 2 to Stand: ");
                                choice = scan.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Input must be numerical");
                            }
                            scan.nextLine();
                        }

                    }
                }
            }

        }

        System.out.println(player.getPlayerName() + " stands!");
        return isBust;
    }

    @Override
    public String toString() {

        return getPlayerName() + "'s hand: " + getPlayerHand();

    }

}
