/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author Ivan
 */
public class CheckBJ {

    // returns true if hand is blackjack (i.e handvalue ==true)
    public boolean isBlackJack(User user) {
        if (user.calcHandValue() != 21) {
            return false;
        } else {
            return true;
        }
    }

    public void checkBlackJack(Player player, Dealer dealer) {
        // BLACKJACK CHECK
        if (this.isBlackJack(player) && this.isBlackJack(dealer)) { // if both have blackjack

            System.out.println("Tie - Game Over");
            dealer.setDealerFinished(true);
            player.setPlayerFinished(true);

        } else if (this.isBlackJack(player)) { // if player has blackjack

            System.out.println("BlackJack, You Won!");
            player.setPlayerFinished(true);

        } else if (this.isBlackJack(dealer)) {

            System.out.println("Whoops, house got blackjack");
            dealer.setDealerFinished(true);

        }

    }
}