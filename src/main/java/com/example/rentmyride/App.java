package com.example.rentmyride;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        // create a Scene with full-screen dimensions
        Scene scene = new Scene(loader.load(), Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());

        // set the Scene on the primary stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("RentMyRide");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}