package com.example.rentmyride;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RentalController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField carnamelabel;
    @FXML
    private TextField basepricelabel;
    @FXML
    private TextField durationField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private ChoiceBox<String> durationTypeChoiceBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Button checkoutButton;
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
    private Car car;
    private Rental rental;
    private double price;
    private String per;
    private String carName;

    public void setUser(User user, Card card, Car car) {
        this.user = user;
        this.card = card;
        this.car = car;

        // check if car is null
        if (car == null) {
            System.err.println("Car is null in RentalController");
            return;
        }

        price = car.getBaseprice();

        // set car name and base price in the view
        carName = car.getCarName();
        carnamelabel.setText(carName);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        basepricelabel.setText(currencyFormat.format(price));

        // disable text fields for car name and base price
        carnamelabel.setDisable(true);
        basepricelabel.setDisable(true);
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
    void handleLogoutButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        // Return to the login screen
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loginRoot);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();

        Database db = Database.getInstance();

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

    @FXML
    private void handleCheckout() throws SQLException, ClassNotFoundException {
        // check if car is null
        if (car == null) {
            System.err.println("Car is null in RentalController");
            return;
        }

        // get rental details from user input
        LocalDate startDate = startDatePicker.getValue();
        per = durationTypeChoiceBox.getValue();
        int duration = Integer.parseInt(durationField.getText());
        int cardid = card.getcardid();
        int carId=car.getCarid();

        CarToRent cartorent=new CarToRent(carId,carName,price);

        // set rental details
        rental = new Rental(cartorent, startDate, duration, null);

        // set price strategy based on selected "per" value
        if (per.equals("per hour")) {
            rental.setPriceStrategy(new PerHourPriceStrategy());
        } 
        else if (per.equals("per day")) {
            rental.setPriceStrategy(new PerDayPriceStrategy());
        } 
        else if (per.equals("per month")) {
            rental.setPriceStrategy(new PerMonthPriceStrategy());
        }

        // calculate rental price and show final price in view
        double finalPrice = rental.calculateRentalPrice();
        String formattedPrice = String.format("%.2f", finalPrice);
        finalPrice = Double.parseDouble(formattedPrice);

        // insert the rental information into the database
        Database rentalDAO = Database.getInstance();
        int userId = user.getUserId();
        rentalDAO.insertRental(userId, carId, cardid, finalPrice, startDate, duration);

        // update the available part in the cars table of this database
        rentalDAO.changeAvailable(carId);
    
        // show alert message with welcome and final price
        String message = "Welcome, you have rented a " + carName + " for " + duration + " " + per + "s. "
                + "Your final price is " + finalPrice + "$.";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rent Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}