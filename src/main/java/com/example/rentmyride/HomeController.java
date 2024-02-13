package com.example.rentmyride;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    private Button backButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button cardButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView logoImage;
    private User user;
    private Card card;
    
    public void setUser(User user, Card card) {
        this.user = user;
        this.card = card;
        // Set the welcome message to display the user's full name
        welcomeLabel.setText("Welcome " + user.getFullname() + "!");
    }
    public void initialize() {
        Image logo = new Image(getClass().getResourceAsStream("/car_images/logo.png"));
        logoImage.setImage(logo);
    }
    @FXML
    public void handleBackButton() throws IOException {
        // Return to the login screen
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loginRoot);
        stage.setTitle("Login Page");
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
    void handleCardButton(ActionEvent event) throws IOException {
        // Load the credit card screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreditCard.fxml"));
        Parent root = loader.load();
        CreditCardController controller = loader.getController();
        controller.setUser(user, card);
        Scene scene = new Scene(root);
        Stage stage = (Stage) cardButton.getScene().getWindow();
        stage.setTitle("Your CreditCard");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showCars(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("car-list.fxml"));
        Parent root = loader.load();
        CarListController controller = loader.getController();
        controller.setUser(user, card);
        Scene scene = new Scene(root);
        Stage stage = (Stage) cardButton.getScene().getWindow();
        stage.setTitle("Cars Page");
        stage.setScene(scene);
        stage.show();
    }
}