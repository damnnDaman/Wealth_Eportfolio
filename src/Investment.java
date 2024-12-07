package src;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The Investment class represents a financial investment with a symbol, name, quantity, price, and book value.
 * This is an abstract class that provides basic functionality for managing an investment.
 * Subclasses should implement the abstract method getGainOnInvestment to calculate the gain on the investment.
 */
public abstract class Investment {
    /**
     * The symbol of the investment.
     */
    protected String symbol;

    /**
     * The name of the investment.
     */
    protected String name;

    /**
     * The quantity of the investment.
     */
    protected int quantity;

    /**
     * The price of the investment.
     */
    protected double price;

    /**
     * The book value of the investment.
     */
    protected double bookValue;

    /**
     * The count of investments.
     */
    protected int investmentCount = 0;

    /**
     * The gain on the investment.
     */
    protected double gainOnInvestment;

    /**
     * The payment received for the investment.
     */
    protected double paymentReceived;
    DecimalFormat formatDecimal = new DecimalFormat("#.00");

    /**
     * Constructs an Investment with the specified symbol, name, quantity, and price.
     *
     * @param symbol   the symbol of the investment
     * @param name     the name of the investment
     * @param quantity the quantity of the investment
     * @param price    the price of the investment
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    public Investment(String symbol, String name, int quantity, double price) {
        if (symbol == null || symbol.isEmpty() || name == null || name.isEmpty() || quantity <= 0 || price < 0) {
            throw new IllegalArgumentException("Invalid input for Investment constructor.");
        }
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = Double.parseDouble(formatDecimal.format(price));
        this.bookValue = 0;
        ++this.investmentCount;
    }

    /**
     * Returns the previous investment in the list.
     *
     * @param symbol     the symbol of the current investment
     * @param investList the list of investments
     * @return the previous investment in the list, or null if not found
     */
    public static Investment getPrevious(String symbol, ArrayList<Investment> investList) {
        if (symbol == null) {
            return null;
        } else {
            for (int i = 0; i < investList.size(); i++) {
                if (symbol.equals(investList.get(i).getSymbol())) {
                    return investList.get(i - 1);
                }
            }
            return null;
        }
    }

    /**
     * Returns the next investment in the list.
     *
     * @param symbol     the symbol of the current investment
     * @param investList the list of investments
     * @return the next investment in the list, or null if not found
     */
    public static Investment getNext(String symbol, ArrayList<Investment> investList) {
        if (symbol == null) {
            return null;
        } else {
            for (int i = 0; i < investList.size(); i++) {
                if (symbol.equals(investList.get(i).getSymbol())) {
                    return investList.get(i + 1);
                }
            }
            return null;
        }
    }

    /**
     * Returns the symbol of the investment.
     *
     * @return the symbol of the investment
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of the investment.
     *
     * @param symbol the new symbol of the investment
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the name of the investment.
     *
     * @return the name of the investment
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the investment.
     *
     * @param name the new name of the investment
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the quantity of the investment.
     *
     * @return the quantity of the investment
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the investment.
     *
     * @param quantity the new quantity of the investment
     * @throws IllegalArgumentException if the quantity is less than or equal to zero
     */
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
    }

    /**
     * Returns the price of the investment.
     *
     * @return the price of the investment
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the investment.
     *
     * @param price the new price of the investment
     * @throws IllegalArgumentException if the price is negative
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = Double.parseDouble(formatDecimal.format(price));
    }

    /**
     * Returns the book value of the investment.
     *
     * @return the book value of the investment
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * Sets the book value when selling shares.
     *
     * @param oldBookValue      the previous book value
     * @param remainingQuantity the remaining quantity of shares after selling
     * @param quantity          the quantity of shares sold
     */
    public void BookValueAfterSell(double oldBookValue, int remainingQuantity, int quantity) {
        this.bookValue = oldBookValue * ((double) remainingQuantity / quantity);
    }

    /**
     * Sets the default book value.
     */
    public abstract void setDefaultBookValue();

    /**
     * Sets the book value from the file.
     *
     * @param bookvalue the book value of the investment
     */
    public void setBookValuefromFile(double bookvalue) {
        this.bookValue = bookvalue;
    }

    /**
     * Updates the book value when buying shares.
     *
     * @param bookValue the previous book value
     * @param quantity  the quantity of shares bought
     * @param price     the price per share
     */
    public abstract void AdditionalBookValue(double bookValue, int quantity, double price);

    /**
     * Sets the payment received for the investment.
     *
     * @param quantity the quantity of shares
     * @param price    the price per share
     */
    public abstract void setPaymentReceived(int quantity, double price);

    /**
     * Returns the payment received for the investment.
     *
     * @return the payment received for the investment
     */
    public abstract double getPaymentReceived();

    /**
     * Sets the gain on the investment.
     *
     * @param payment   the payment received
     * @param bookValue the book value of the investment
     */
    public abstract void setGainOnInvestment(double payment, double bookValue);

    /**
     * Returns the gain on the investment.
     *
     * @return the gain on the investment
     */
    public double getGainOnInvestment() {
        return Double.parseDouble(formatDecimal.format(gainOnInvestment));
    }

    /**
     * Returns a string representation of the investment.
     *
     * @return a string representation of the investment
     */
    @Override
    public String toString() {
        return "Investment #" + this.investmentCount + "\nSymbol=" + symbol +
                "\nName=" + name +
                "\nQuantity=" + quantity +
                "\nPrice=$" + formatDecimal.format(price) +
                "\nBookValue=" + bookValue;
    }

    /**
     * Compares this investment to the specified object.
     *
     * @param otherObject the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null)
            return false;
        else if (getClass() != otherObject.getClass())
            return false;
        else {
            Investment otherInvestment = (Investment) otherObject;
            return (symbol.equals(otherInvestment.symbol));
        }
    }
}


