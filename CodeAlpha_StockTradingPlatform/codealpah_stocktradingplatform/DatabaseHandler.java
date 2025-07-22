/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codealpah_stocktradingplatform;

import java.sql.*;

public class DatabaseHandler {
    private Connection conn;

    public DatabaseHandler() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocktradingplatform?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "Haseeb044@");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet loginUser(String username, String password) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean registerUser(String username, String password) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, password, balance) VALUES (?, ?, 10000.00)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResultSet getAllStocks() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM stocks");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean buyStock(int userId, int stockId, int quantity, double price) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO transactions (user_id, stock_id, type, quantity, price) VALUES (?, ?, 'BUY', ?, ?)");
            ps.setInt(1, userId);
            ps.setInt(2, stockId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            ps.executeUpdate();

            PreparedStatement updateBalance = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE user_id = ?");
            updateBalance.setDouble(1, price * quantity);
            updateBalance.setInt(2, userId);
            updateBalance.executeUpdate();

            PreparedStatement check = conn.prepareStatement("SELECT * FROM portfolio WHERE user_id=? AND stock_id=?");
            check.setInt(1, userId);
            check.setInt(2, stockId);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                PreparedStatement update = conn.prepareStatement("UPDATE portfolio SET quantity = quantity + ? WHERE user_id=? AND stock_id=?");
                update.setInt(1, quantity);
                update.setInt(2, userId);
                update.setInt(3, stockId);
                update.executeUpdate();
            } else {
                PreparedStatement insert = conn.prepareStatement("INSERT INTO portfolio (user_id, stock_id, quantity) VALUES (?, ?, ?)");
                insert.setInt(1, userId);
                insert.setInt(2, stockId);
                insert.setInt(3, quantity);
                insert.executeUpdate();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean sellStock(int userId, int stockId, int quantity, double price) {
    try {
        PreparedStatement check = conn.prepareStatement("SELECT quantity FROM portfolio WHERE user_id = ? AND stock_id = ?");
        check.setInt(1, userId);
        check.setInt(2, stockId);
        ResultSet rs = check.executeQuery();

        if (rs.next()) {
            int ownedQty = rs.getInt("quantity");
            if (ownedQty < quantity) {
                return false;
            }

            PreparedStatement ps = conn.prepareStatement("INSERT INTO transactions (user_id, stock_id, type, quantity, price) VALUES (?, ?, 'SELL', ?, ?)");
            ps.setInt(1, userId);
            ps.setInt(2, stockId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            ps.executeUpdate();

            PreparedStatement update = conn.prepareStatement("UPDATE portfolio SET quantity = quantity - ? WHERE user_id = ? AND stock_id = ?");
            update.setInt(1, quantity);
            update.setInt(2, userId);
            update.setInt(3, stockId);
            update.executeUpdate();

            PreparedStatement delete = conn.prepareStatement("DELETE FROM portfolio WHERE user_id = ? AND stock_id = ? AND quantity <= 0");
            delete.setInt(1, userId);
            delete.setInt(2, stockId);
            delete.executeUpdate();

            PreparedStatement updateBalance = conn.prepareStatement("UPDATE users SET balance = balance + ? WHERE user_id = ?");
            updateBalance.setDouble(1, price * quantity);
            updateBalance.setInt(2, userId);
            updateBalance.executeUpdate();

            return true;
        } else {
            return false;
        }
    } catch (Exception e) {
        return false;
    }
}

    public ResultSet getUserPortfolio(int userId) {
        try {
            String query = "SELECT s.symbol, s.company_name, s.current_price, p.quantity FROM portfolio p JOIN stocks s ON p.stock_id = s.stock_id WHERE p.user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }

    public double getUserBalance(int userId) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT balance FROM users WHERE user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (Exception e) {
        }
        return 0.0;
    }
}
