package codealpah_stocktradingplatform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Dashboard extends JFrame {
    private int userId;
    private DatabaseHandler db;
    private JTable marketTable;
    private JLabel balanceLabel;
    private JTextField quantityField;

    public Dashboard(int userId, DatabaseHandler db) {
        this.userId = userId;
        this.db = db;

        setTitle("Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main layout panels
        JPanel header = new JPanel();
        header.setBackground(new Color(40, 53, 147));
        header.setPreferredSize(new Dimension(getWidth(), 80));
        header.setLayout(new BorderLayout());
        JLabel title = new JLabel("Market Overview", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(title, BorderLayout.CENTER);

        balanceLabel = new JLabel("Balance: $" + db.getUserBalance(userId));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.add(balanceLabel, BorderLayout.EAST);

        JPanel body = new JPanel(new GridLayout(1,2,10,10));
        body.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Left: Market table
        JPanel marketPanel = new JPanel(new BorderLayout());
        marketPanel.setBorder(BorderFactory.createTitledBorder("Available Stocks"));
        DefaultTableModel marketModel = new DefaultTableModel(new Object[]{"ID","Symbol","Company","Price"},0);
        marketTable = new JTable(marketModel);
        try {
            ResultSet rs = db.getAllStocks();
            while(rs.next()) {
                marketModel.addRow(new Object[]{
                    rs.getInt("stock_id"), rs.getString("symbol"),
                    rs.getString("company_name"), rs.getDouble("current_price")
                });
            }
        } catch (SQLException ignored) {}
        marketPanel.add(new JScrollPane(marketTable), BorderLayout.CENTER);

        // Right: Buy/Sell panel
        JPanel actionPanel = new JPanel(null);
        actionPanel.setBorder(BorderFactory.createTitledBorder("Trading"));
        quantityField = new JTextField();
        quantityField.setBorder(BorderFactory.createTitledBorder("Quantity"));
        quantityField.setBounds(50, 50, 200, 40);
        actionPanel.add(quantityField);

        JButton buyBtn = new JButton("Buy", new ImageIcon("icons/buy.png"));
        buyBtn.setBounds(50, 110, 200, 50);
        buyBtn.setBackground(new Color(56, 142, 60));
        buyBtn.setForeground(Color.WHITE);
        actionPanel.add(buyBtn);

        JButton sellBtn = new JButton("Sell", new ImageIcon("icons/sell.png"));
        sellBtn.setBounds(50, 180, 200, 50);
        sellBtn.setBackground(new Color(211, 47, 47));
        sellBtn.setForeground(Color.WHITE);
        actionPanel.add(sellBtn);

        JButton portfolioBtn = new JButton("View Portfolio", new ImageIcon("icons/portfolio.png"));
        portfolioBtn.setBounds(50, 260, 200, 50);
        actionPanel.add(portfolioBtn);

        // Button logic
        buyBtn.addActionListener(e -> trade("BUY"));
        sellBtn.addActionListener(e -> trade("SELL"));
        portfolioBtn.addActionListener(e -> new PortfolioWindow(userId, db));

        body.add(marketPanel);
        body.add(actionPanel);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        setVisible(true);
    }

    private void trade(String type) {
    int row = marketTable.getSelectedRow(); // Get selected row from table
    if (row < 0) return; // No row selected

    try {
        int stockId = (int) marketTable.getValueAt(row, 0);
        double price = (double) marketTable.getValueAt(row, 3);
        int quantity = Integer.parseInt(quantityField.getText()); // Get quantity from input

        boolean success;
        if (type.equals("BUY")) {
            success = db.buyStock(userId, stockId, quantity, price);
        } else {
            success = db.sellStock(userId, stockId, quantity, price);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, type + " successful!");
            balanceLabel.setText("Balance: $" + db.getUserBalance(userId));
        } else {
            JOptionPane.showMessageDialog(this, type + " failed!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
    }
}

}
