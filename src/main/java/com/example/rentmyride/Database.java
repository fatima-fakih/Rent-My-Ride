package com.example.rentmyride;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public  class Database {
    private static Connection conn;
    private static Database instance;
    private String url = "jdbc:mysql://localhost:3306/carrentdb";
    private String user = "root";
    private String password = "";

    private Database() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
    }

    public static Database getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql);
    }

    public void close() throws SQLException {
        conn.close();
    }

    public static List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try (
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM cars");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                CarFactory carFactory = type.equalsIgnoreCase("luxury") ? new LuxuryCarFactory() : new RegularCarFactory();
                Car car = carFactory.createCar(resultSet.getInt("carid"), resultSet.getString("carname"), resultSet.getString("model"),
                        resultSet.getDouble("baseprice"), resultSet.getBoolean("available"), resultSet.getString("imagePath"));
                cars.add(car);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void insertRental(int userId, int carId, int cardId, double rentPrice, LocalDate dateTaken, int duration) throws SQLException {
        String sql = "INSERT INTO reservations (userid, cardid, carid, rent_price, date_taken, duration) VALUES " +
                "(" + userId + ", '" + cardId + "', " + carId + ", " + rentPrice + ", '" + dateTaken.toString() + "', " + duration + ")";
        Statement stmt = conn.createStatement();
        try {
            stmt.executeUpdate(sql);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void changeAvailable(int carId) throws SQLException {
        String sql = "UPDATE cars SET available = 0 WHERE carid = ?";
        try (
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carId);
            stmt.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}