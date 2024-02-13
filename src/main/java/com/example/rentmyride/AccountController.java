package com.example.rentmyride;

import java.io.IOException;
import java.sql.PreparedStatement;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AccountController {
    @FXML
    private Button backButton;
    @FXML
    private Button cardButton;
    @FXML
    private Button carsButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private Button logoutButton;
    @FXML
    private TextField passwordField;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button updateButton;
    @FXML
    private TextField usernameField;
    @FXML
    private ImageView logoImage;
    private int id;
    public User user;
    private Card card;

    public void setUser(User user, Card card) {
        this.user = user;
        this.card = card;
        // Update the labels with the user's information
        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        emailField.setText(user.getEmail());
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        this.id = user.getUserId();
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

    @FXML
    void handleUpdateButton(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Get the updated user information from the text fields
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate that all fields are filled in
        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        // Check if the updated username is already used by other users
        if (!username.equals(user.getUsername())) {
            Database connection = Database.getInstance();
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT COUNT(*) FROM users WHERE username=?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next() && result.getInt(1) > 0) {
                // Username is already used by another user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists, please choose another one");
                alert.showAndWait();
                return;
            }
        }

        // Check if the updated email is already used by other users
        if (!email.equals(user.getEmail())) {
            Database connection = Database.getInstance();
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT COUNT(*) FROM users WHERE email=?");
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next() && result.getInt(1) > 0) {
                // Email is already used by another user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Email already exists, please choose another one");
                alert.showAndWait();
                return;
            }                
        }
        // Update the user in the database
        try {
            // Get the database connection
            Database connection = Database.getInstance();
            PreparedStatement statement = connection.getConnection().prepareStatement("UPDATE users SET firstname=?, lastname=?, email=?, username=?, password=? WHERE userid=?");
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, email);
            statement.setString(4, username);
            statement.setString(5, password);
            statement.setInt(6, id);
            statement.executeUpdate();

            // Update the user object
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("User information updated successfully");
            alert.showAndWait();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update user information");
            alert.showAndWait();
        }
    }
}