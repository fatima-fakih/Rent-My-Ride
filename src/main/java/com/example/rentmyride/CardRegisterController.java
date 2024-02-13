package com.example.rentmyride;

import java.io.IOException;
import java.sql.ResultSet;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CardRegisterController {
    @FXML
    private Button backButton;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField cvvField;
    @FXML
    private TextField expirationDateField;
    @FXML
    private TextField nameOnCardField;
    @FXML
    private Button registerButton;
    @FXML
    private AnchorPane rootPane;
    private User user;
    private int id;

    public void setUser(User user) {
        this.user = user;
        this.id = user.getUserId();
    }
    
    @FXML
    public void handleBackButton() throws IOException {
        // Go back to the previous screen 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setTitle("Main Page");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void handleRegisterButton(ActionEvent event) throws IOException {
        // Get the input values from the text fields
        String nameOnCard = nameOnCardField.getText().trim();
        String cardNumber = cardNumberField.getText().trim();
        String expirationDate = expirationDateField.getText().trim();
        String cvv = cvvField.getText().trim();
    
        // Validate the input values
        if (nameOnCard.isEmpty() || cardNumber.isEmpty() || expirationDate.isEmpty() || cvv.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("All fields are required");
            alert.showAndWait();
            return;
        }
        if (!cardNumber.matches("[0-9]+") || cardNumber.length() != 16) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid card number");
            alert.showAndWait();
            return;
        }
        if (!expirationDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid expiration date. Format should be MM/YY");
            alert.showAndWait();
            return;
        }
        if (!cvv.matches("[0-9]+") || cvv.length() != 3) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid CVV. Should be a 3-digit number");
            alert.showAndWait();
            return;
        }
    
        try {
            // Create a new card object with the input values
            Card card = new Card(id, nameOnCard, cardNumber, expirationDate, cvv);
    
            // Add the card to the database
            Database db = Database.getInstance();
            String sql = String.format("INSERT INTO cards (userid, name, number, expirydate, cvv) VALUES ('%s', '%s', '%s', '%s', '%s')",card.getUserId(), card.getName(), card.getNumber(), card.getExpiryDate(), card.getCvv());
            db.executeUpdate(sql);
            // Get the cardID
            ResultSet rs = db.executeQuery("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                int cardId = rs.getInt(1);
                card.setCardId(cardId);
            }


    
            // Clear the input fields
            nameOnCardField.clear();
            cardNumberField.clear();
            expirationDateField.clear();
            cvvField.clear();
    
            // Display a success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Card added successfully.");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            HomeController controller = loader.getController();
            controller.setUser(user, card);
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } 
        catch (SQLException | ClassNotFoundException ex) {
            // Display an error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add card.");
            alert.showAndWait();
        }
    }
}