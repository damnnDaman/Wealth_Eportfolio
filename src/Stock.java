package src;

/**
 * The Stock class represents a stock investment.
 * It includes details such as the symbol, name, quantity, price, book value,
 * payment received, and gain on investment. It also provides methods to
 * update these details.
 *
 * <p>This class is designed to manage stock investments by providing
 * methods to buy and sell shares, and to calculate the book value and gain
 * on investment.</p>
 *
 * <p>Note: The commission fee for buying or selling stocks is defined as a constant.</p>
 *
 * @see Investment
 * @see #getBookValue()
 * @see #getPaymentReceived()
 * @see #getGainOnInvestment()
 * // * @see #updateBookValue(double, int, double)
 * // * @see #setBookValue(double, int, int)
 * @see #setBookValuefromFile(double)
 * @see #setGainOnInvestment(double, double)
 * @see #setPaymentReceived(int, double)
 */
public class Stock extends Investment {

    /**
     * Represents the commission fee for buying or selling stocks.
     * The value is fixed at 9.99.
     */
    private final double COMMISSION = 9.99;
    //private double bookValue;
    private double paymentReceived = 0;
    private double gainOnInvestment = 0;

    /**
     * Creates a new Stock with the specified symbol, name, quantity, and price.
     * Initializes the book value, payment received, and gain on investment.
     *
     * @param symbol   the stock symbol
     * @param name     the stock name
     * @param quantity the quantity of the stock
     * @param price    the price of the stock
     */
    public Stock(String symbol, String name, int quantity, double price) {

        super(symbol, name, quantity, price);
        this.paymentReceived = 0.0;
        this.gainOnInvestment = 0.0;

    }

    /**
     * Creates a new Stock with default values.
     */
    public Stock() {
        super("", "", 0, 0.0);
    }


    public void setDefaultBookValue() {
        this.bookValue = this.quantity * this.price + COMMISSION;
    }

    /**
     * Updates the book value when buying more stock.
     *
     * @param oldBookValue the old book value
     * @param quantity     the quantity of stock bought
     * @param price        the price of the stock bought
     */
    public void AdditionalBookValue(double oldBookValue, int quantity, double price) {
        this.bookValue = oldBookValue + (quantity * price + COMMISSION);

    }

    /**
     * Returns the payment received for the stock.
     *
     * @return the payment received for the stock
     */
//    public double getPaymentReceived() {
//        return paymentReceived;
//    }

    /**
     * Returns the gain on investment for the stock.
     *
     * @return the gain on investment for the stock
     */


    /**
     * Sets the book value from a file.
     *
     * @param bookvalue the book value of the stock
     */
    public void setBookValuefromFile(double bookvalue) {
        this.bookValue = bookvalue;
    }


    /**
     * Sets the gain on investment.
     *
     * @param payment   the payment received from selling the stock
     * @param bookValue the book value of the stock
     */
    public void setGainOnInvestment(double payment, double bookValue) {
        this.gainOnInvestment = payment - bookValue;
    }

    /**
     * Sets the payment received when selling stock.
     *
     * @param quantity the quantity of stock sold
     * @param price    the price of the stock sold
     */
    public void setPaymentReceived(int quantity, double price) {
        this.paymentReceived = quantity * price - COMMISSION;
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
}

