package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;


/**
 * The Portfolio class is the main class that interacts with the user
 * to perform various operations like buying, selling, updating, getting gain, and searching investments.
 */

public class Portfolio extends JFrame {

    /**
     * /**
     * The main method serves as the entry point for the Portfolio application.
     * It initializes the necessary data structures, loads investments from a file,
     * and provides a menu-driven interface for the user to interact with their portfolio.
     *
     * @param args Command line arguments. The first argument should be the filename to load investments from.
     */


    public static void main(String[] args) {


        // Create an ArrayList to store investments
        ArrayList<Investment> InvestList = new ArrayList<Investment>();




        /*
         *  frame is a JFrame object that represents the main window of the application.
         */
        JFrame frame = new JFrame("ePortfolio");
        frame.setSize(1000, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String userInput;
//        frame.setBackground(Color.PINK);
        frame.setVisible(true);


        /*
         * panelScreen is a JPanel object that will be used to handle the layout for different commands.
         * */
        JPanel panelScreen = new JPanel();
        JPanel panelScreen2 = new JPanel();
        frame.add(panelScreen);
        frame.add(panelScreen2);

        /* Create an object of GUI_for_menus class */
        GUI_for_menus gui = new GUI_for_menus(InvestList);

        /*
         * commandPanel is a JPanel object that contains the buttons for the user to select a command.
         * */
        JPanel commandPanel = new JPanel();
        commandPanel.setVisible(true);
        frame.add(commandPanel, BorderLayout.NORTH);
        JPanel BuyPanel = new JPanel();
        BuyPanel.setVisible(false);
        JPanel SellPanel = new JPanel();
        SellPanel.setVisible(false);
        JPanel UpdatePanel = new JPanel();
        UpdatePanel.setVisible(false);
        JPanel GetGainPanel = new JPanel();
        GetGainPanel.setVisible(false);
        JPanel SearchPanel = new JPanel();
        SearchPanel.setVisible(false);
        JPanel QuitPanel = new JPanel();
        QuitPanel.setVisible(false);
        JMenuBar menuBar = new JMenuBar();
        JMenu commandsMenu = new JMenu("Commands");

        JMenuItem buyMenu = new JMenuItem("Buy");
        JMenuItem sellMenu = new JMenuItem("Sell");
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem getGainMenu = new JMenuItem("Get Gain");
        JMenuItem searchMenu = new JMenuItem("Search");
        JMenuItem quitMenu = new JMenuItem("Quit");

        commandsMenu.add(buyMenu);
        commandsMenu.add(sellMenu);
        commandsMenu.add(updateMenu);
        commandsMenu.add(getGainMenu);
        commandsMenu.add(searchMenu);
        commandsMenu.add(quitMenu);

        menuBar.add(commandsMenu);
        frame.setJMenuBar(menuBar);
        // Set custom font
        Font menuFont = new Font("Arial", Font.PLAIN, 22);

        menuBar.setFont(menuFont);
        commandsMenu.setFont(menuFont);
        buyMenu.setFont(menuFont);
        sellMenu.setFont(menuFont);
        updateMenu.setFont(menuFont);
        getGainMenu.setFont(menuFont);
        searchMenu.setFont(menuFont);
        quitMenu.setFont(menuFont);

        JPanel WelcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeMessage = new JLabel("Welcome to ePortfolio. Choose a command from the “Commands” menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.", JLabel.CENTER);
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeMessage.setVerticalAlignment(SwingConstants.CENTER);

        WelcomePanel.add(welcomeMessage);

        frame.add(commandPanel, BorderLayout.NORTH);
        panelScreen2.add(WelcomePanel, BorderLayout.CENTER);


        buyMenu.addActionListener(event -> {
            System.out.println("lets BUY!!!");
            panelScreen2.setVisible(false);
            UpdatePanel.setVisible(false);
            GetGainPanel.setVisible(false);
            SearchPanel.setVisible(false);
            QuitPanel.setVisible(false);

            SellPanel.setVisible(false);
            frame.add(BuyPanel);
            BuyPanel.setVisible(true);
            gui.new BuyAction(BuyPanel).actionPerformed(event);
        });

        sellMenu.addActionListener(event -> {
            panelScreen2.setVisible(false);
            UpdatePanel.setVisible(false);
            GetGainPanel.setVisible(false);
            SearchPanel.setVisible(false);
            QuitPanel.setVisible(false);
            BuyPanel.setVisible(false);
            SellPanel.setVisible(true);
            frame.add(SellPanel);

            System.out.println("lets SELL!!!");

            gui.new SellAction(SellPanel).actionPerformed(event);
        });

        updateMenu.addActionListener(event -> {
            System.out.println("lets UPDATE!!!");
            panelScreen2.setVisible(false);

            GetGainPanel.setVisible(false);
            SearchPanel.setVisible(false);
            QuitPanel.setVisible(false);
            BuyPanel.setVisible(false);
            SellPanel.setVisible(false);
            UpdatePanel.setVisible(true);
            frame.add(UpdatePanel);

            gui.new UpdateAction(UpdatePanel).actionPerformed(event);
        });
//
        getGainMenu.addActionListener(event -> {
            System.out.println("lets GET GAIN!!!");
            panelScreen2.setVisible(false);

            GetGainPanel.setVisible(true);
            SearchPanel.setVisible(false);
            QuitPanel.setVisible(false);
            BuyPanel.setVisible(false);
            SellPanel.setVisible(false);
            UpdatePanel.setVisible(false);
            frame.add(GetGainPanel);
            gui.new GetGainAction(GetGainPanel).actionPerformed(event);
        });
//
        searchMenu.addActionListener(event -> {
            System.out.println("lets SEARCH!!!");
            panelScreen2.setVisible(false);

            GetGainPanel.setVisible(false);
            SearchPanel.setVisible(true);
            QuitPanel.setVisible(false);
            BuyPanel.setVisible(false);
            SellPanel.setVisible(false);
            UpdatePanel.setVisible(false);
            frame.add(SearchPanel);
            gui.new SearchAction(SearchPanel).actionPerformed(event);
        });

        quitMenu.addActionListener(event -> {
            System.out.println("lets QUIT!!!");
            panelScreen2.setVisible(false);

            GetGainPanel.setVisible(false);
            SearchPanel.setVisible(false);
            QuitPanel.setVisible(true);
            BuyPanel.setVisible(false);
            SellPanel.setVisible(false);
            UpdatePanel.setVisible(false);
            frame.add(SearchPanel);
            gui.new QuitAction(QuitPanel).actionPerformed(event);
        });


    }
}