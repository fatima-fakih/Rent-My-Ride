package com.example.rentmyride;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CarListController {
    @FXML
    private VBox carContainer;
    @FXML
    private Button backButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button cardButton;
    @FXML
    private ImageView logoImage;
    private User user;
    private Card card;
    
    public void setUser(User user, Card card) {
        this.user = user;
        this.card = card;
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
    void handleLogoutButton(ActionEvent event) throws IOException {
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
    public void initialize() {

        List<Car> cars = Database.getAllCars();

        Image logo = new Image(getClass().getResourceAsStream("/car_images/logo.png"));
        logoImage.setImage(logo);


        for (Car car : cars) {
            System.out.println("Car: " + car);
            HBox carBox = new HBox();

            ImageView carImage = new ImageView();
            Image image = new Image(new File(car.getImagePath()).toURI().toString());
            carImage.setImage(image);
            carImage.setFitWidth(550);
            carImage.setFitHeight(300);

            VBox carInfo = new VBox();
            carInfo.setPrefWidth(200); // set the preferred width to 200 pixels
            carInfo.setPrefHeight(150); // set the preferred height to 150 pixels
            carInfo.setSpacing(15);
            Label typeLabel;
            Label nameLabel = new Label("Name: " + car.getCarName());
            nameLabel.setStyle("-fx-font-size: 14pt;");
            Label modelLabel = new Label("Model: " + car.getModel());
            modelLabel.setStyle("-fx-font-size: 14pt;");
            Label availablelabel = new Label("Available: " + car.isAvailable());
            availablelabel.setStyle("-fx-font-size: 14pt;");
            Label priceLabel = new Label("Price: " + car.getBaseprice() + "$");
            priceLabel.setStyle("-fx-font-size: 14pt;");

            carInfo.getChildren().addAll(nameLabel, modelLabel, availablelabel, priceLabel);
            if(car instanceof RegularCar) {
                typeLabel = new Label("Type: Regular");
                typeLabel.setStyle("-fx-font-size: 14pt;");
            }
            else{
                typeLabel = new Label("Type: Luxury");
                Label sunrooflabel = new Label("Sunroof");
                Label leatherlabel = new Label("LeatherSeats");
                carInfo.getChildren().addAll(sunrooflabel, leatherlabel);
                typeLabel.setStyle("-fx-font-size: 14pt;");
                sunrooflabel.setStyle("-fx-font-size: 14pt;");
                leatherlabel.setStyle("-fx-font-size: 14pt;");
            }


            carInfo.getChildren().addAll(typeLabel);
            carInfo.setSpacing(18);

            if(car.isAvailable()){
                Button rentButton = new Button("Rent");
                rentButton.setStyle("-fx-background-color:  #D4AF37; -fx-text-fill: white; -fx-font-size: 14pt; -fx-font-weight: bold; -fx-layout-x: 1250.0; -fx-layout-y: 70.0; -fx-pref-width: 120.0; -fx-pref-height: 50.0; -fx-background-insets: 0;  -fx-padding: 0 10 0 10;");
                rentButton.setOnAction(event -> {
                    // Handle button click event here
                    try {
                        // Load the rent screen
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("rent.fxml"));
                        Parent root = loader.load();
                        RentalController controller = loader.getController();
                        controller.setUser(user, card, car);
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) rentButton.getScene().getWindow();
                        stage.setTitle("Rental Information");
                        stage.setScene(scene);
                        stage.show();
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });  
                VBox buttonBox = new VBox();
                buttonBox.getChildren().addAll(rentButton);
                buttonBox.setAlignment(Pos.CENTER);
                buttonBox.setPadding(new Insets(10));

                carBox.getChildren().addAll(carImage, carInfo, rentButton);
                carBox.setSpacing(20);

                carContainer.getChildren().add(carBox);
                carContainer.getChildren().add(buttonBox);


            }
        }
        carContainer.setSpacing(15);
    }
}