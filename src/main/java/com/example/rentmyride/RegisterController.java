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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField firstnamefield;
    @FXML
    private TextField lastnamefield;
    @FXML
    private TextField emailfield;
    @FXML
    private TextField usernamefield;
    @FXML
    private TextField passwordfield;
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;
    private User user;
    
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = backButton.getScene();
        scene.setRoot(root);
    }
    
    @FXML
    private void registerUser() throws ClassNotFoundException, IOException {
        String firstName = firstnamefield.getText();
        String lastName = lastnamefield.getText();
        String email = emailfield.getText();
        String username = usernamefield.getText();
        String password = passwordfield.getText();
        
        // Validate input fields
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            // Display error message in a dialog box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText("Please fill out all fields.");
            alert.showAndWait();
        } 
        else {
            try {
                // Check if the username is pre-used
                Database db = Database.getInstance();
                String selectUsernameSql = String.format("SELECT COUNT(*) FROM users WHERE username='%s'", username);
                ResultSet usernameResultSet = db.executeQuery(selectUsernameSql);
                if (usernameResultSet.next()) {
                    int count = usernameResultSet.getInt(1);
                    if (count > 0) {

                        // Display error message
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Registration Error");
                        alert.setHeaderText("Username is already used.");
                        alert.showAndWait();
                        return;
                    }
                }
                // Check if the email is pre-used
                String selectEmailSql = String.format("SELECT COUNT(*) FROM users WHERE email='%s'", email);
                ResultSet emailResultSet = db.executeQuery(selectEmailSql);
                if (emailResultSet.next()) {
                    int count = emailResultSet.getInt(1);
                    if (count > 0) {
                        // Display error message
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Registration Error");
                        alert.setHeaderText("Email is already used.");
                        alert.showAndWait();
                        return;
                    }
                }
                // Create a new user
                user = new User(firstName, lastName, email, username, password);
                // Insert user data into database
                String sql = String.format("INSERT INTO users (firstname, lastname, email, username, password) VALUES ('%s', '%s', '%s', '%s', '%s')", firstName, lastName, email, username, password);
                int rowsInserted = db.executeUpdate(sql);
                int userid = 0;
                if (rowsInserted > 0) {
                    // Get the ID of the registered user
                    String selectSql = String.format("SELECT userid FROM users WHERE email='%s'", email);
                    ResultSet resultSet = db.executeQuery(selectSql);
                    if (resultSet.next()) {
                        userid = resultSet.getInt("userid");
                    }
                    // Display success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration Successful");
                    alert.setHeaderText("Registration successful!");
                    alert.showAndWait();
                }
                // Save the userid in user info
                user.setUserId(userid);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CardRegister.fxml"));
                Parent root = loader.load();
                CardRegisterController controller = loader.getController();
                controller.setUser(user);
                Scene scene = new Scene(root);
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.setTitle("Credit Card Registration");
                stage.setScene(scene);
                stage.show();
            } 
            catch (SQLException e) {
                e.printStackTrace();
                // Display error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                alert.setHeaderText("Unable to register user.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}