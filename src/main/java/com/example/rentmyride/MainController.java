package com.example.rentmyride;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class MainController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private ImageView logoImage;

    @FXML
    private void handleLoginButtonAction() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loginRoot);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRegisterButtonAction() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Parent registerRoot = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene scene = new Scene(registerRoot);
        stage.setTitle("Register Page");
        stage.setScene(scene);
        stage.show();
    }
    public void initialize() {
        Image logo = new Image(getClass().getResourceAsStream("/car_images/logo.png"));
        logoImage.setImage(logo);
    }
}
