/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Ivan
 */
public class Controller implements ActionListener {

    public View view;
    public Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);

    }

    /**
     * Listener for buttons
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Log in":
//                System.out.println("Login Pressed");
                String username = this.view.loginPanel.unInput.getText();
                String password = this.view.loginPanel.pwInput.getText();

                this.model.checkName(username, password);
//                System.out.println("CHECKNAME CALLED IN CONTROLLEr");

                break;
            case "Place bet":
//                System.out.println("pressed place bet");
                double bet = 0;
                try { // checks that input is numerical
                    bet = Double.parseDouble(this.view.betPanel.betField.getText());

                } catch (NumberFormatException ex) {
//                   
                    view.betPanel.betField.setText("");

                }
                if (!(bet <= 0) && bet <= model.data.player.getPlayerBalance()) { // check that input is within player balance range
                    this.model.placeBet(this.model.data.player, bet);
                    model.initialDeal();
                } else {
                    view.invalidBet();
                }
//                System.out.println(this.model.bet.getBetAmount());

                break;
            case "HIT":
                this.model.playerHit();

                break;

            case "STAND":
                this.model.playerStand();
                break;

            case "End Game":
                this.model.quitGame();
                break;
            case "Reset":
//                System.out.println("Reset pressed");
                this.model.resetGame();

                break;
            case "Help":
//                System.out.println("help clicked");
                String rules = model.data.rules;
                this.view.help(rules);
                break;
            case "Logout":
                model.logout();
                view.logout();
                break;
            case "About":
                view.displayAbout(model.data);
            case "Exit":
                if (model.data.player != null && model.db.conn != null) {
                    model.db.quitGame(model.data.player.getPlayerName(), model.data.player.getPlayerBalance(), model.data.player.getPlayerWins(), model.data.player.getPlayerLoss());
                }
                System.exit(0);
        }

    }

}
