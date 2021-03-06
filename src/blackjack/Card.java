package blackjack;

/**
 *
 * @author ivanc
 */
public class Card {

    // member variables
    private Suit suit;
    private Value value;
    private String URL = "";

    // constructor
    public Card(Suit suit, Value value) {
        setSuit(suit);
        setValue(value);
        setURL("./resources/images/cards/" + value.toString().toLowerCase() + "_of_" + suit.toString().toLowerCase() + ".png");

    }

    // getters and setters
    public Suit getSuit() {
        return this.suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String toString() {
        return getValue() + " of " + getSuit();
    }

}
