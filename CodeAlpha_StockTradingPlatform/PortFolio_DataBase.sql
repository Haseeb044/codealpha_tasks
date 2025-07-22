create database StockTradingPlatform;
use StockTradingPlatform;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0.00
);
CREATE TABLE stocks (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    symbol VARCHAR(10) UNIQUE NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    current_price DECIMAL(10, 2) NOT NULL
); 
INSERT INTO stocks (symbol, company_name, current_price) VALUES
('AAPL', 'Apple Inc.', 195.30),
('GOOGL', 'Alphabet Inc.', 2805.45),
('AMZN', 'Amazon.com Inc.', 3722.10),
('MSFT', 'Microsoft Corporation', 299.87),
('TSLA', 'Tesla Inc.', 740.90),
('META', 'Meta Platforms Inc.', 310.45),
('NFLX', 'Netflix Inc.', 420.15),
('NVDA', 'NVIDIA Corporation', 900.75),
('BABA', 'Alibaba Group', 178.65),
('DIS', 'Walt Disney Co.', 145.30);
CREATE TABLE portfolio (
    portfolio_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    stock_id INT,
    quantity INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (stock_id) REFERENCES stocks(stock_id)
); 
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    stock_id INT,
    type ENUM('BUY', 'SELL') NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (stock_id) REFERENCES stocks(stock_id)
);



