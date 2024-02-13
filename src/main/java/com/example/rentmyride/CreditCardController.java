package com.example.rentmyride;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreditCardController {
    @FXML
    private Button accountButton;
    @FXML
    private Button backButton;
    @FXML
    private Button carsButton;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField cvvField;
    @FXML
    private TextField expirationDateField;
    @FXML
    private Button logoutButton;
    @FXML
    private TextField nameOnCardField;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button updateButton;
    @FXML
    private Button cardButton;
    @FXML
    private ImageView logoImage;
    private int id;
    public User user;
    public Card card;


    public void setUser(User user, Card card) {
        this.user = user;
        this.id = user.getUserId();
        this.card = card;

        // Set the card information in the text fields
        nameOnCardField.setText(card.getName());
        cardNumberField.setText(card.getNumber());
        expirationDateField.setText(card.getExpiryDate());
        cvvField.setText(card.getCvv());
    }
    public void initialize() {
        Image logo = new Image(getClass().getResourceAsStream("/car_images/logo.png"));
        logoImage.setImage(logo);
    }
    @FXML
    public void handleBackButton() throws IOException {
        // Go back to the previous screen 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setUser(user, card);
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleAccountButton(ActionEvent event) throws IOException {
        // Load the account screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));
        Parent root = loader.load();
        AccountController controller = loader.getController();
        controller.setUser(user, card);
        Scene scene = new Scene(root);
        Stage stage = (Stage) accountButton.getScene().getWindow();
        stage.setTitle("Your Account");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleLogoutButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        // Return to the login screen
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loginRoot);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();



    }

    @FXML
    void handleUpdateButton(ActionEvent event) throws ClassNotFoundException {
        String nameOnCard = nameOnCardField.getText();
        String cardNumber = cardNumberField.getText();
        String expirationDate = expirationDateField.getText();
        String cvv = cvvField.getText();
        
        // Validate input fields
        if (nameOnCard.isEmpty() || !nameOnCard.matches("[a-zA-Z\\s]+")) {
            showAlert("Please enter a valid name on card.");
            return;
        }
        if (cardNumber.isEmpty() || !cardNumber.matches("[0-9]{16}")) {
            showAlert("Please enter a valid 16-digit card number.");
            return;
        }
        if (expirationDate.isEmpty() || !expirationDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            showAlert("Please enter a valid expiration date (MM/YY).");
            return;
        }
        if (cvv.isEmpty() || !cvv.matches("[0-9]{3}")) {
            showAlert("Please enter a valid 3-digit CVV code.");
            return;
        }

        // Update the credit card information in the database
        try {
            Database db = Database.getInstance();
            Connection con = db.getConnection();
            String query = "UPDATE cards SET name=?, number=?, expirydate=?, cvv=? WHERE userid=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nameOnCard);
            statement.setString(2, cardNumber);
            statement.setString(3, expirationDate);
            statement.setString(4, cvv);
            statement.setInt(5, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Credit card information updated.");
            } 
            else {
                showAlert("Credit card information could not be updated.");
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            showAlert("An error occurred while updating credit card information.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void showCars(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("car-list.fxml"));
        Parent root = loader.load();
        CarListController controller = loader.getController();
        controller.setUser(user, card);
        Scene scene = new Scene(root);
        Stage stage = (Stage) carsButton.getScene().getWindow();
        stage.setTitle("Cars Page");
        stage.setScene(scene);
        stage.show();
    }
}