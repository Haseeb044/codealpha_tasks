package codealpah_stocktradingplatform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PortfolioWindow extends JFrame {
    public PortfolioWindow(int userId, DatabaseHandler db) {
        setTitle("My Portfolio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Portfolio Overview", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        String[] cols = {"Symbol","Company","Qty","Price","Total Value"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);

        double overall = 0;
        try {
            ResultSet rs = db.getUserPortfolio(userId);
            while(rs.next()) {
                String sym = rs.getString("symbol");
                String comp = rs.getString("company_name");
                int qty = rs.getInt("quantity");
                double price = rs.getDouble("current_price");
                double total = qty * price;
                overall += total;
                model.addRow(new Object[]{sym, comp, qty, price, total});
            }
        } catch (SQLException ignored) {}

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JLabel totalLabel = new JLabel("Portfolio Value: $" + String.format("%.2f", overall));
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(totalLabel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
