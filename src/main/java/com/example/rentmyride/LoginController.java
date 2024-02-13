package com.example.rentmyride;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernamefield;
    @FXML
    private TextField passwordfield;
    @FXML
    private Button loginButton;
    @FXML
    private Button backButton;
    private Database database;
   
    @FXML
    public void onLogin() throws IOException, SQLException, ClassNotFoundException {
        String username = usernamefield.getText();
        String password = passwordfield.getText();
        database = Database.getInstance();
        
        // Check if the username and password are correct
        ResultSet result = database.executeQuery("SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'");
        if (result.next()) {
            // Login successful
            int id = result.getInt("userid");
            ResultSet cardResult = database.executeQuery("SELECT * FROM cards WHERE userid=" + id);
            User user = new User(result.getString("firstname"), result.getString("lastname"), result.getString("email"), result.getString("username"), result.getString("password"));
            user.setUserId(id);

            // Create a new card object with the retrieved card info
            if (cardResult.next()) {
                Card card = new Card(cardResult.getInt("userid"), cardResult.getString("name"), cardResult.getString("number"), cardResult.getString("expirydate"), cardResult.getString("cvv"));
                int cardid = cardResult.getInt("cardid");
                card.setCardId(cardid);
                // load the homepage
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                Parent root = loader.load();
                HomeController controller = loader.getController();
                controller.setUser(user, card);
                Scene scene = new Scene(root);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } 
            else {
                // Alert the user that no card information was found
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Card Information Found");
                alert.setHeaderText(null);
                alert.setContentText("No card information was found for this user.");
                alert.showAndWait();

                // Load card register page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CardRegister.fxml"));
                Parent root = loader.load();
                CardRegisterController controller = loader.getController();
                controller.setUser(user);
                Scene scene = new Scene(root);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setTitle("Credit Card Registration");
                stage.setScene(scene);
                stage.show();
            }
        } 
        else {
            // Alert the user that the username and/or password was incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Login");
            alert.setHeaderText(null);
            alert.setContentText("The username and/or password entered is incorrect.");
            alert.showAndWait();
        }
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
}