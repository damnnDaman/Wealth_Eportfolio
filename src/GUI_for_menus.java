package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The GUI_for_menus class provides the graphical user interface for managing investments.
 */
public class GUI_for_menus implements ActionListener {
    private ArrayList<Investment> investList;

    private String filename;
    private double totalGain = 0;

    /**
     * Constructs a GUI_for_menus object with the specified list of investments.
     *
     * @param investList the list of investments
     */
    public GUI_for_menus(ArrayList<Investment> investList) {
        this.investList = investList;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // This method is left empty or used for a default action.
    }


    /**
     * The BuyAction class handles the action of buying an investment.
     */
    public class BuyAction implements ActionListener {
        private JPanel BuyPanel;

        /**
         * Constructs a BuyAction object with the specified panel.
         *
         * @param buyPanel the panel for buying an investment
         */
        public BuyAction(JPanel buyPanel) {
            this.BuyPanel = buyPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            BuyPanel.removeAll();
            BuyPanel.setLayout(new BorderLayout(400, 50));

            JLabel heading = new JLabel("Buying an Investment");
            heading.setFont(new Font("Arial", Font.BOLD, 28));

            JPanel entries = new JPanel(new GridLayout(6, 2, -100, 50));
            JComboBox<String> type = new JComboBox<>(new String[]{"Stock", "Mutual Fund"});

            JLabel symbolLabel = new JLabel("Symbol:");
            JTextField symbol = new JTextField();
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField();
            JLabel quantityLabel = new JLabel("Quantity:");
            JTextField quantityField = new JTextField();
            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField();

            entries.add(new JLabel("Type:"));
            entries.add(type);
            entries.add(symbolLabel);
            entries.add(symbol);
            entries.add(nameLabel);
            entries.add(nameField);
            entries.add(quantityLabel);
            entries.add(quantityField);
            entries.add(priceLabel);
            entries.add(priceField);

            Font font = new Font("Arial", Font.BOLD, 26);
            for (Component component : entries.getComponents()) {
                component.setFont(font);
            }

            JPanel MsgPanel = new JPanel(new BorderLayout(50, 5));
            JLabel message = new JLabel("Message: ");
            message.setFont(new Font("Arial", Font.BOLD, 28));

            JTextArea msgField = new JTextArea();
            msgField.setPreferredSize(new Dimension(100, 400));
            msgField.setLineWrap(true);
            msgField.setFont(new Font("Arial", Font.BOLD, 24));
            JScrollPane scrollPane = new JScrollPane(msgField);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            msgField.setEditable(false);

            MsgPanel.add(message, BorderLayout.NORTH);
            MsgPanel.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, -250, 100));
            JButton resetButton = new JButton("Reset");
            resetButton.setPreferredSize(new Dimension(100, -40));
            resetButton.setFont(new Font("Arial", Font.BOLD, 20));
            JButton buyButton = new JButton("Buy");
            buyButton.setPreferredSize(new Dimension(100, -40));
            buyButton.setFont(new Font("Arial", Font.BOLD, 20));

            buttonPanel.add(resetButton);
            buttonPanel.add(buyButton);

            BuyPanel.add(heading, BorderLayout.NORTH);
            BuyPanel.add(entries, BorderLayout.CENTER);
            BuyPanel.add(buttonPanel, BorderLayout.EAST);
            BuyPanel.add(MsgPanel, BorderLayout.SOUTH);

            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    symbol.setText("");
                    nameField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                }
            });

            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String investmentType = type.getSelectedItem().toString().trim().toLowerCase();
                        String symbolText = symbol.getText().trim().toLowerCase();
                        String nameText = nameField.getText().trim().toLowerCase();
                        int quantity = Integer.parseInt(quantityField.getText().trim());
                        double price = Double.parseDouble(priceField.getText().trim());

                        if (symbolText.isEmpty() || nameText.isEmpty() || quantity <= 0 || price < 0) {
                            resetButton.doClick();
                            msgField.setText("Please enter valid values for Symbol, Name, Quantity, and Price.");
                            return;
                        }

                        boolean investmentMade = false;

                        for (Investment currentInvest : investList) {
                            if (currentInvest.getSymbol().equalsIgnoreCase(symbolText)) {

                                if (((investmentType.equals("stock")) && (currentInvest.getClass() == MutualFunds.class) ||
                                        ((investmentType.equalsIgnoreCase("mutual fund") && (currentInvest.getClass() == Stock.class))))) {
                                    System.out.println("this if#2 condition works");
                                    resetButton.doClick();
                                    msgField.setText("You already have an investment with this symbol.");
                                    investmentMade = true;
                                    break;
                                } else {

                                    currentInvest.setQuantity(currentInvest.getQuantity() + quantity);
                                    currentInvest.setPrice(price);

                                    currentInvest.AdditionalBookValue(currentInvest.getBookValue(), quantity, price);

                                    investmentMade = true;
                                    resetButton.doClick();
                                    msgField.setText("Bought:\nAdditional " + quantity + " units of " + symbolText + " @ $" + price);
                                    System.out.println("Book value: " + currentInvest.getBookValue());
                                    break;
                                }
                            }
                        }

                        if (!investmentMade) {
                            Investment newInvestment;
                            if (investmentType.equals("stock")) {
                                newInvestment = new Stock(symbolText, nameText, quantity, price);

                            } else {
                                newInvestment = new MutualFunds(symbolText, nameText, quantity, price);

                            }
                            newInvestment.setDefaultBookValue();
                            investList.add(newInvestment);


                            resetButton.doClick();
                            msgField.setText("Bought:\n" + quantity + " units of " + symbolText + " @ $" + price);
                            System.out.println("Book value: " + newInvestment.getBookValue());
                        }
                    } catch (NumberFormatException ex) {
                        resetButton.doClick();
                        msgField.setText("Invalid input. Please enter valid numbers for quantity and price.");
                    } catch (Exception ex) {
                        resetButton.doClick();
                        msgField.setText("An error occurred: " + ex.getMessage());
                    }
                }
            });
        }
    }


    /**
     * Action listener for handling the "Sell" action.
     */
    public class SellAction implements ActionListener {
        private JPanel sellPanel;


        /**
         * Constructs a SellAction object with the specified panel.
         *
         * @param sellPanel the panel for selling an investment
         */
        public SellAction(JPanel sellPanel) {
            this.sellPanel = sellPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            sellPanel.removeAll();
            sellPanel.setLayout(new BorderLayout(500, 100));
            JLabel heading = new JLabel("Selling an investment");
            heading.setFont(new Font("Arial", Font.BOLD, 22));
            sellPanel.add(heading, BorderLayout.NORTH);


            JPanel Entries = new JPanel(new GridLayout(5, 2, -100, 50));
            Entries.add(new JLabel("Symbol: "));
            JTextField symbolField = new JTextField();
            Entries.add(symbolField);

            Entries.add(new JLabel("Quantity: "));
            JTextField quantityField = new JTextField();
            Entries.add(quantityField);

            Entries.add(new JLabel("Price: "));
            JTextField priceField = new JTextField();
            Entries.add(priceField);

            Font font = new Font("Arial", Font.BOLD, 20);
            for (Component component : Entries.getComponents()) {
                component.setFont(font);
            }

            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, -250, 100));
            JButton resetButton = new JButton("Reset");
            resetButton.setPreferredSize(new Dimension(100, -40));
            resetButton.setFont(new Font("Arial", Font.BOLD, 20));
            JButton sellButton = new JButton("Sell");
            sellButton.setPreferredSize(new Dimension(100, -40));
            sellButton.setFont(new Font("Arial", Font.BOLD, 20));

            buttonPanel.add(resetButton);
            buttonPanel.add(sellButton);

            sellPanel.add(Entries, BorderLayout.CENTER);
            sellPanel.add(buttonPanel, BorderLayout.EAST);

            JPanel MsgPanel = new JPanel(new BorderLayout());
            JLabel message = new JLabel("Message: ");
            message.setFont(new Font("Arial", Font.BOLD, 22));

            JTextArea msgField = new JTextArea();
            msgField.setPreferredSize(new Dimension(100, 400));
            msgField.setLineWrap(true);
            msgField.setFont(new Font("Arial", Font.BOLD, 24));
            JScrollPane scrollPane = new JScrollPane(msgField);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            msgField.setEditable(false);

            MsgPanel.add(message, BorderLayout.NORTH);
            MsgPanel.add(scrollPane, BorderLayout.CENTER);

            sellPanel.add(MsgPanel, BorderLayout.SOUTH);

            sellPanel.revalidate();
            sellPanel.repaint();

            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    symbolField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                    msgField.setText("");
                }
            });

            sellButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String symbol = symbolField.getText().trim().toLowerCase();
                        int quantity = Integer.parseInt(quantityField.getText().trim());
                        double price = Double.parseDouble(priceField.getText().trim());

                        if (symbol.isEmpty() || quantity <= 0 || price <= 0) {
                            resetButton.doClick();
                            msgField.setText("Please enter valid values.");
                            return;
                        }


                        boolean investmentSold = false;
                        for (int i = 0; i < investList.size(); i++) {
                            Investment currentInvest = investList.get(i);
                            if (currentInvest.getSymbol().equalsIgnoreCase(symbol)) {
                                if (currentInvest.getQuantity() >= quantity) {
                                    currentInvest.setQuantity(currentInvest.getQuantity() - quantity);
                                    currentInvest.setPrice(price);
                                    currentInvest.BookValueAfterSell(currentInvest.getBookValue(), currentInvest.getQuantity(), currentInvest.getQuantity() + quantity);
                                    investmentSold = true;
                                    msgField.setText("Sold:\n" + quantity + " units of " + symbol + " @ $" + price);


                                    break;
                                }
                            }
                        }
                        for (Investment investment : investList) {
                            if (investment.getQuantity() == 0) {
                                investList.remove(investment);
                            }
                        }

                        if (!investmentSold) {
                            resetButton.doClick();
                            msgField.setText("Investment not found or insufficient quantity to sell.");
                        }
                    } catch (NumberFormatException ex) {
                        resetButton.doClick();
                        msgField.setText("Invalid input. Please enter valid numbers for quantity or price.");
                    } catch (Exception ex) {
                        resetButton.doClick();
                        msgField.setText("An error occurred: " + ex.getMessage());
                    }
                }
            });
        }
    }


    /**
     * Action listener for handling the "Update" action.
     */
    public class UpdateAction implements ActionListener {
        private JPanel updatePanel;


        /**
         * Constructs an UpdateAction object with the specified panel.
         *
         * @param updatePanel the panel for updating an investment
         */
        public UpdateAction(JPanel updatePanel) {
            this.updatePanel = updatePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            updatePanel.removeAll();
            updatePanel.setLayout(new BorderLayout(500, 100));

            JLabel heading = new JLabel("Updating Investments");
            heading.setFont(new Font("Arial", Font.BOLD, 22));
            updatePanel.add(heading, BorderLayout.NORTH);

            JPanel Entries = new JPanel(new GridLayout(5, 2, -450, 100));

            JLabel symbolLabel = new JLabel("Symbol:");
            JTextField symbolField = new JTextField();
            symbolField.setEditable(false);
//            symbolField.setForeground(Color.WHITE);
            JLabel NameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField();
            nameField.setEditable(false);
//            nameField.setForeground();
            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField();


            Font font = new Font("Arial", Font.BOLD, 20);
            symbolLabel.setFont(font);
            symbolField.setFont(font);
            NameLabel.setFont(font);
            nameField.setFont(font);
            priceLabel.setFont(font);
            priceField.setFont(font);

            Entries.add(symbolLabel);
            Entries.add(symbolField);
            Entries.add(NameLabel);
            Entries.add(nameField);
            Entries.add(priceLabel);
            Entries.add(priceField);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(6, 1, 30, 30));

            JButton PrevButton = new JButton("Prev");
            JButton NextButton = new JButton("Next");
            JButton SaveButton = new JButton("Save");

            //setting the size of the buttons
            PrevButton.setPreferredSize(new Dimension(150, 10));
            PrevButton.setFont(new Font("Arial", Font.BOLD, 22));
            NextButton.setPreferredSize(new Dimension(150, 10));
            NextButton.setFont(new Font("Arial", Font.BOLD, 22));
            SaveButton.setPreferredSize(new Dimension(150, 10));
            SaveButton.setFont(new Font("Arial", Font.BOLD, 22));

            buttonPanel.add(PrevButton);
            buttonPanel.add(NextButton);
            buttonPanel.add(SaveButton);


            updatePanel.add(buttonPanel, BorderLayout.EAST);

            updatePanel.add(Entries, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel(new BorderLayout());
            JLabel messageLabel = new JLabel("Message:");
            messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
            JTextArea messageArea = new JTextArea();
            messageArea.setFont(new Font("Arial", Font.BOLD, 22));
            messageArea.setPreferredSize(new Dimension(350, 350));
            messageArea.setEditable(false);
            //adding scroll bar to the text area
            JScrollPane scrollPane = new JScrollPane(messageArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            bottomPanel.add(messageLabel, BorderLayout.NORTH);
            bottomPanel.add(scrollPane, BorderLayout.CENTER);
            updatePanel.add(bottomPanel, BorderLayout.SOUTH);


            updatePanel.revalidate();
            updatePanel.repaint();

            //to disable the next prev button---------
            if (investList.size() <= 1) {
                PrevButton.setEnabled(false);
                messageArea.setText("No previous investments to display.");
                //return;
            }
            //to disable the next prev button---------
            if ((investList.size() > 1 && investList.get(investList.size() - 1).getSymbol().equalsIgnoreCase(symbolField.getText())) || (investList.size() <= 1)) {
                symbolField.setText("NULL");
                nameField.setText("NULL");
                priceField.setText("$00.00");
                symbolField.setEditable(false);
                nameField.setEditable(false);
                priceField.setEditable(false);
                // messageArea.setText("No investments to update.");
                NextButton.setEnabled(false);
                messageArea.setText("No next investments to display.");
            }

            if (investList.isEmpty()) {
                symbolField.setText("NULL");
                nameField.setText("NULL");
                priceField.setText("$00.00");
                symbolField.setEditable(false);
                nameField.setEditable(false);
                priceField.setEditable(false);
                messageArea.setText("No investments to update.");
                return;
            } else {
                symbolField.setText(investList.get(0).getSymbol());
                nameField.setText(investList.get(0).getName());
                priceField.setText(String.valueOf(investList.get(0).getPrice()));
                symbolField.setEditable(false);
                nameField.setEditable(false);
                priceField.setEditable(true);
            }


            PrevButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (investList.isEmpty()) {
                        symbolField.setText("NULL");
                        nameField.setText("NULL");
                        priceField.setText("$00.00");
                        symbolField.setEditable(false);
                        nameField.setEditable(false);
                        priceField.setEditable(false);
                        //PrevButton.setEnabled(false);
                        messageArea.setText("No investments to update.");
                        return;
                    }

                    if (investList.get(0).getSymbol().equalsIgnoreCase(symbolField.getText())) {
                        messageArea.setText("No previous investments to display.");
                        return;
                    }
                    Investment currentInvest = Investment.getPrevious(symbolField.getText(), investList);
                    symbolField.setText(currentInvest.getSymbol());
                    nameField.setText(currentInvest.getName());
                    priceField.setText(String.valueOf(currentInvest.getPrice()));
                    symbolField.setEditable(false);
                    nameField.setEditable(false);
                    priceField.setEditable(true);
                    messageArea.setText("");
                }
            });

            NextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (investList.isEmpty()) {
                        symbolField.setText("");
                        nameField.setText("");
                        priceField.setText("");
                        symbolField.setEditable(false);
                        nameField.setEditable(false);
                        priceField.setEditable(false);
                        NextButton.setEnabled(false);
                        messageArea.setText("No investments to update.");
                        return;
                    }
                    if (investList.get(investList.size() - 1).getSymbol().equalsIgnoreCase(symbolField.getText())) {
                        messageArea.setText("No Next investments to display.");

                    } else {
                        Investment currentInvest = Investment.getNext(symbolField.getText(), investList);
                        symbolField.setText(currentInvest.getSymbol());
                        nameField.setText(currentInvest.getName());
                        priceField.setText(String.valueOf(currentInvest.getPrice()));
                        symbolField.setEditable(false);
                        nameField.setEditable(false);
                        priceField.setEditable(true);
                        messageArea.setText("");

                    }
                }
            });

            SaveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (investList.isEmpty() || priceField.getText().isEmpty()) {
                        priceField.setText("");
                        messageArea.setText("No investments to update.");
                        return;
                    }

                    for (Investment invest : investList) {
                        if (symbolField.getText().equalsIgnoreCase(invest.getSymbol())) {

                            invest.setPrice(Double.parseDouble(priceField.getText().trim()));
                            priceField.setText(String.valueOf(invest.getPrice()));
                            symbolField.setEditable(false);
                            nameField.setEditable(false);
                            priceField.setEditable(true);
                            messageArea.setText("Price saved successfully.");
                            //NextButton.doClick();

                        }
                    }
                }
            });

        }
    }

    /**
     * Action listener for handling the "Get Gain" action.
     */
    public class GetGainAction implements ActionListener {
        private JPanel getGain;

        /**
         * Constructs a GetGainAction object with the specified panel.
         *
         * @param getGain the panel for getting the total gain
         */
        public GetGainAction(JPanel getGain) {
            this.getGain = getGain;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            getGain.removeAll();
            getGain.setLayout(new BorderLayout(100, 100));
            JLabel heading = new JLabel("Getting total gain");
            heading.setFont(new Font("ARIAL", Font.BOLD, 28));
            getGain.add(heading, BorderLayout.NORTH);


            JPanel Entries = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 100));
            JLabel gainLabel = new JLabel("Total Gain: ");
            gainLabel.setFont(new Font("Arial", Font.BOLD, 26));
            TextField gainText = new TextField();
            gainText.setEditable(false);
            gainText.setFont(new Font("Arial", Font.BOLD, 24));
            gainText.setPreferredSize(new Dimension(300, 50));
            Entries.add(gainLabel);
            Entries.add(gainText);

            getGain.add(Entries, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel(new BorderLayout(100, 50));

            JLabel gainAreaLabel = new JLabel("Individual gains");
            gainAreaLabel.setFont(new Font("Arial", Font.BOLD, 24));
            TextArea gainArea = new TextArea();
            gainArea.setFont(new Font("Arial", Font.PLAIN, 20));
            gainArea.setPreferredSize(new Dimension(350, 550));
            gainArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(gainArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            bottomPanel.add(gainAreaLabel, BorderLayout.NORTH);
            bottomPanel.add(scrollPane, BorderLayout.CENTER);
            getGain.add(bottomPanel, BorderLayout.SOUTH);
            getGain.revalidate();
            getGain.repaint();

//                calculations for getting gain----
            //setting gain for each investment
            for (Investment currentInvestment : investList) {
                currentInvestment.setPaymentReceived(currentInvestment.getQuantity(), currentInvestment.getPrice());
                System.out.println("quantity: " + currentInvestment.getQuantity());
                System.out.println("paymeny: $" + currentInvestment.getPaymentReceived());
                currentInvestment.setGainOnInvestment(currentInvestment.getPaymentReceived(), currentInvestment.getBookValue());
                totalGain = totalGain + (currentInvestment.getGainOnInvestment());
                System.out.println(totalGain + "   $" + (currentInvestment.getGainOnInvestment()));
            }
            gainText.setText(String.valueOf(totalGain));

            for (Investment currentInvestment : investList) {
                gainArea.append(String.format("Symbol: %s\nName: %s\nQuantity: %d\nPrice: %s\nGain: $%s\n\n", currentInvestment.getSymbol(), currentInvestment.getName(), currentInvestment.getQuantity(), currentInvestment.getPrice(), currentInvestment.getGainOnInvestment()));
            }
        }

    }

    /**
     * Action listener for handling the "Search" action.
     */
    public class SearchAction implements ActionListener {
        private JPanel searchPanel;


        /**
         * Constructs a SearchAction object with the specified panel.
         *
         * @param SearchPanel the panel for searching investments
         */
        public SearchAction(JPanel SearchPanel) {
            this.searchPanel = SearchPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            searchPanel.removeAll();
            searchPanel.setLayout(new BorderLayout(600, 50));

            JLabel heading = new JLabel("Searching Investments");
            heading.setFont(new Font("Arial", Font.BOLD, 28));

            JPanel entries = new JPanel(new GridLayout(6, 2, -100, 50));


            JLabel symbolLabel = new JLabel("Symbol:");
            JTextField symbol = new JTextField();
            JLabel keywordsLabel = new JLabel("Name\nKeywords");
            JTextField keywordsField = new JTextField();
            JLabel LowpriceLabel = new JLabel("Low Price:");
            JTextField LowpriceField = new JTextField();
            JLabel HighpriceLabel = new JLabel("High Price:");
            JTextField HighpriceField = new JTextField();

            entries.add(symbolLabel);
            entries.add(symbol);
            entries.add(keywordsLabel);
            entries.add(keywordsField);
            entries.add(LowpriceLabel);
            entries.add(LowpriceField);
            entries.add(HighpriceLabel);
            entries.add(HighpriceField);

            Font font = new Font("Arial", Font.BOLD, 26);
            for (Component component : entries.getComponents()) {
                component.setFont(font);
            }

            JPanel bottomPanel = new JPanel(new BorderLayout(50, 5));
            JLabel searchLabel = new JLabel("Search Results: ");
            searchLabel.setFont(new Font("Arial", Font.BOLD, 28));

            JTextArea searchField = new JTextArea();
            searchField.setPreferredSize(new Dimension(100, 400));
            searchField.setLineWrap(true);
            searchField.setFont(new Font("Arial", Font.BOLD, 24));
            JScrollPane scrollPane = new JScrollPane(searchField);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            searchField.setEditable(false);

            bottomPanel.add(searchLabel, BorderLayout.NORTH);
            bottomPanel.add(scrollPane, BorderLayout.SOUTH);

            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, -50, 100));
            JButton resetButton = new JButton("Reset");
            resetButton.setPreferredSize(new Dimension(150, -40));
            resetButton.setFont(new Font("Arial", Font.BOLD, 20));
            JButton searchButton = new JButton("Search");
            searchButton.setPreferredSize(new Dimension(150, -40));
            searchButton.setFont(new Font("Arial", Font.BOLD, 20));

            buttonPanel.add(resetButton);
            buttonPanel.add(searchButton);

            searchPanel.add(heading, BorderLayout.NORTH);
            searchPanel.add(entries, BorderLayout.CENTER);
            searchPanel.add(buttonPanel, BorderLayout.EAST);
            searchPanel.add(bottomPanel, BorderLayout.SOUTH);

            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    symbol.setText("");
                    keywordsField.setText("");
                    LowpriceField.setText("");
                    HighpriceField.setText("");
                    searchField.setText("");
                }
            });

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String symbolText = symbol.getText().trim();
                    String keywordsText = keywordsField.getText().trim();
                    double lowPrice = 0;
                    double highPrice = Double.MAX_VALUE;

                    // Validate input fields
                    if (symbolText.isEmpty() || keywordsText.isEmpty() || LowpriceField.getText().isEmpty() || HighpriceField.getText().isEmpty()) {
                        resetButton.doClick();
                        searchField.setText("Please enter at least one search criterion.");
                        return;
                    }

                    try {
                        if (!LowpriceField.getText().isEmpty()) {
                            lowPrice = Double.parseDouble(LowpriceField.getText().trim());
                            if (lowPrice < 0) {
                                throw new NumberFormatException("Low price cannot be negative.");
                            }
                        }
                        if (!HighpriceField.getText().isEmpty()) {
                            highPrice = Double.parseDouble(HighpriceField.getText().trim());
                            if (highPrice < 0) {
                                throw new NumberFormatException("High price cannot be negative.");
                            }
                        }
                        if (lowPrice > highPrice) {
                            throw new IllegalArgumentException("Low price cannot be greater than high price.");
                        }
                    } catch (NumberFormatException ex) {
                        resetButton.doClick();
                        searchField.setText("Please enter valid numbers for low price and high price.");
                        return;
                    } catch (IllegalArgumentException ex) {
                        resetButton.doClick();
                        searchField.setText(ex.getMessage());
                        return;
                    }

                    searchField.setText("");
                    boolean foundInvestment = false;

                    for (int i = 0; i < investList.size(); i++) {
                        Investment currentInvestment = investList.get(i);
                        boolean matchesSymbol = symbolText.isEmpty() || currentInvestment.getSymbol().equalsIgnoreCase(symbolText);
                        boolean matchesKeywords = keywordsText.isEmpty() || currentInvestment.getName().toLowerCase().contains(keywordsText.toLowerCase());
                        boolean matchesPrice = currentInvestment.getPrice() >= lowPrice && currentInvestment.getPrice() <= highPrice;

                        if (matchesSymbol && matchesKeywords && matchesPrice) {
                            foundInvestment = true;
                            searchField.append(currentInvestment.toString() + "\n");
                        }
                    }

                    if (!foundInvestment) {
                        searchField.setText("No investment found!!!");
                    }
                }
            });

        }
    }


    /**
     * Action listener for handling the "Quit" action.
     */
    public class QuitAction implements ActionListener {
        private JPanel QuitPanel;

        /**
         * Constructs a QuitAction object with the specified panel.
         *
         * @param quitPanel the panel for quitting the application
         */
        public QuitAction(JPanel quitPanel) {
            this.QuitPanel = quitPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}




