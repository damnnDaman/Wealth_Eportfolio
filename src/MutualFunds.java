package src;


//this project is a simple mutual fund tracker that allows the user to buy and sell mutual funds

/**
 * The MutualFunds class represents a mutual fund investment.
 * It includes details such as the symbol, name, quantity, price, book value,
 * payment received, and gain on investment. It also provides methods to
 * update these details.
 *
 * <p>This class is designed to manage mutual fund investments by providing
 * methods to buy and sell shares, and to calculate the book value and gain
 * on investment.</p>
 *
 * <p>Note: The fee for transactions is defined as a constant.</p>
 *
 * @see Investment
 * @see #getPaymentReceived()
 * @see #getGainOnInvestment()
 * @see #AdditionalBookValue(double, int, double) BookValue(double, int, double
 * @see #setBookValuefromFile(double)
 * @see #setGainOnInvestment(double, double)
 * @see #setPaymentReceived(int, double)
 */

public class MutualFunds extends Investment {
    /**
     * The fee for transactions.
     */
    private final int FEE = 45;
    //private double bookValue;
    private double paymentReceived = 0;
    private double gainOnInvestment = 0;

    /**
     * Creates a new MutualFunds with default values.
     */
    public MutualFunds() {
        super("", "", 0, 0.0);
        this.paymentReceived = 0;
        this.gainOnInvestment = 0;
    }


    /**
     * Creates a new MutualFunds with the specified symbol, name, quantity, and price.
     * Initializes the book value, payment received, and gain on investment.
     *
     * @param symbol   the mutual fund symbol
     * @param name     the mutual fund name
     * @param quantity the quantity of the mutual fund
     * @param price    the price of the mutual fund
     */
    public MutualFunds(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        this.paymentReceived = 0;
        this.gainOnInvestment = 0;
    }

    /**
     * Updates the book value when buying shares.
     *
     * @param oldBookValue the previous book value
     * @param quantity     the quantity of shares bought
     * @param price        the price per share
     */
    public void AdditionalBookValue(double oldBookValue, int quantity, double price) {
        this.bookValue = oldBookValue + (quantity * price);
    }

    public void setDefaultBookValue() {
        this.bookValue = this.quantity * this.price;
    }

    /**
     * Returns the gain on investment.
     *
     * @return the gain on investment
     */
    @Override
    public double getGainOnInvestment() {
        return gainOnInvestment;
    }

    /**
     * Returns the payment received for the stock.
     *
     * @return the payment received for the stock
     */
    @Override
    public double getPaymentReceived() {
        return paymentReceived;
    }


    /**
     * Sets the gain on investment.
     *
     * @param payment   the payment received from selling shares
     * @param bookValue the book value of the investment
     */

    public void setGainOnInvestment(double payment, double bookValue) {
        this.gainOnInvestment = payment - bookValue;
    }

    /**
     * Sets the payment received from selling shares.
     *
     * @param quantity the quantity of shares sold
     * @param price    the price per share
     */
    public void setPaymentReceived(int quantity, double price) {
        this.paymentReceived = (quantity * price) - FEE;
    }
}

