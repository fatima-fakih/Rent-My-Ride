package com.example.rentmyride;


import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class AdminController implements Initializable {

    @FXML
    private TextField Nametextfeild;

    @FXML
    private  Label home_totalIncome;

    @FXML
    private Label home_availableCars;

    @FXML
    private TableColumn<Car, String> availableCars_col_imgPath;
    @FXML
    private TextField Pricetextfeild;

    @FXML
    private Pane addcarform;

    @FXML
    private TableColumn<Car, String> availableCars_col_carId;



    @FXML
    private TableColumn<Car, String> availableCars_col_model;

    @FXML
    private TableColumn<Car, String> availableCars_col_price;

    @FXML
    private TableColumn<Car, String> availableCars_col_status;

    @FXML
    private TableColumn<Car, String> availableCars_col_brand;

    @FXML
    private TableView<Car> availableCars_tableView;

    @FXML
    private TableView<reservation> reservationtbl;



    @FXML
    private CheckBox availablecheckbox;

    @FXML
    private TextField caridtextfeild;

    @FXML
    private ImageView carimportimgview;

    @FXML
    private Button clearbtn;

    @FXML
    private TableColumn<?, ?> colfrstreservattion;

    @FXML
    private TableColumn<?, ?> colfrstreservattion1;

    @FXML
    private TableColumn<?, ?> colsavailableCars_col_brandcndname;

    @FXML
    private ImageView homeimgview;

    @FXML
    private Button importbtn;

    @FXML
    private Button insertbtn;





    @FXML
    private TextField modeltextfeild;



    @FXML
    private Pane reservationPane;

    @FXML
    private ImageView settingimgview;

    @FXML
    private CheckBox sunroofcheckbox;

    @FXML
    private TableColumn<?, ?> tblcardNumber;

    @FXML
    private TableColumn<?, ?> tblcarid;

    @FXML
    private TableColumn<?, ?> tbldatetaken;

    @FXML
    private TableColumn<?, ?> tblduration;

    @FXML
    private TableColumn<?, ?> tblrentprice;

    @FXML
    private TableColumn<?, ?> tblreservationId;

    @FXML
    private TableColumn<?, ?> tbluserid;

    @FXML
    private ComboBox<?> type;

    // database connection
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private Image image;

    public AdminController() {
    }

    public ObservableList<Car> availableCarListData() {

        ObservableList<Car> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM cars";


        connect = database2.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Car carD;

            while (result.next()) {
                carD = new Car(result.getInt("carid"),
                        result.getString("carname"),
                        result.getString("model"),
                        result.getDouble("baseprice"),
                        result.getBoolean("available"),
                        result.getString("imagePath"));




                listData.add(carD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Car> availableCarList;

    public void availableCarShowListData() {
        availableCarList = availableCarListData();


        availableCars_col_carId.setCellValueFactory(new PropertyValueFactory<>("carid"));
        availableCars_col_brand.setCellValueFactory(new PropertyValueFactory<>("carName"));
        availableCars_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        availableCars_col_price.setCellValueFactory(new PropertyValueFactory<>("baseprice"));
        availableCars_col_status.setCellValueFactory(new PropertyValueFactory<>("available"));

        availableCars_col_imgPath.setCellValueFactory(new PropertyValueFactory<>("imagePath"));

        availableCars_tableView.setItems(availableCarList);
    }

    public ObservableList<reservation> reservationListData() {

        ObservableList<reservation> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM reservations";

        try {
            Connection connect = database2.connectDb();
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
               result.getBigDecimal("rent_price").intValue();

                reservation res = new reservation(result.getInt("id"),
                        result.getInt("userid"),
                        result.getInt("cardid"),
                        result.getInt("carid"),
                        result.getBigDecimal("rent_price"),
                        result.getDate("date_taken"),
                        result.getInt("duration"));

                listData.add(res);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<reservation> reservationsList;

    public void reservationsShowListData() {
        reservationsList = reservationListData();


            tblreservationId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tbluserid.setCellValueFactory(new PropertyValueFactory<>("userid"));
            tblcardNumber.setCellValueFactory(new PropertyValueFactory<>("cardid"));
            tblcarid.setCellValueFactory(new PropertyValueFactory<>("carid"));
        tblrentprice.setCellValueFactory(new PropertyValueFactory<>("rent_price"));
        tbldatetaken.setCellValueFactory(new PropertyValueFactory<>("date_taken"));

        tblduration.setCellValueFactory(new PropertyValueFactory<>("duration"));


        reservationtbl.setItems(reservationsList);
    }

    public void availableCarImportImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png","*JPEG"));

        File file = open.showOpenDialog(addcarform.getScene().getWindow());

        if (file != null) {

            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 181, 180, false, true);
            carimportimgview.setImage(image);

        }

    }

    public void availableCarStatusList() {

        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        type.setItems(listData);
    }

    public void availableCarAdd() {


        String sql = "INSERT INTO cars (carid, carname, model, baseprice,type, available, imagePath,leatherseats,sunroof) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        connect = database2.connectDb();

        try {
            Alert alert;

            if (caridtextfeild.getText().isEmpty()
                    || Nametextfeild.getText().isEmpty()
                    || modeltextfeild.getText().isEmpty()
                    || type.getSelectionModel().getSelectedItem() == null
                    || Pricetextfeild.getText().isEmpty()
                    || getData.path == null || getData.path == "")
                     {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, caridtextfeild.getText());
                prepare.setString(2, Nametextfeild.getText());
                prepare.setString(3, modeltextfeild.getText());
                prepare.setString(4, Pricetextfeild.getText());
                prepare.setString(5, (String) type.getSelectionModel().getSelectedItem());
                prepare.setBoolean(6, availablecheckbox.isSelected());
                String uri = getData.path;
                uri = uri.replace("\\", "\\\\");

                prepare.setString(7, uri);
                boolean check;
                if(((String) type.getSelectionModel().getSelectedItem()).equals("Regular"))
                {
                     check = false;
                }
                else check = true;
                prepare.setBoolean(8,check);
                prepare.setBoolean(9,check);

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

                availableCarShowListData();
                availableCarClear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void availableCarDelete() {

        String sql = "DELETE FROM cars WHERE carid = '" + caridtextfeild.getText() + "'";

        connect = database2.connectDb();

        try {
            Alert alert;
            if (caridtextfeild.getText().isEmpty()
                    || Nametextfeild.getText().isEmpty()
                    || modeltextfeild.getText().isEmpty()

                    || Pricetextfeild.getText().isEmpty()
                    || !availablecheckbox.isSelected()
                    || getData.path == null || getData.path == "")
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Car ID: " + caridtextfeild.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    availableCarShowListData();
                    availableCarClear();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableCarClear() {
        caridtextfeild.setText("");
        Nametextfeild.setText("");
        modeltextfeild.setText("");
        type.getSelectionModel().clearSelection();
        Pricetextfeild.setText("");
        availablecheckbox.setSelected(false);
        getData.path = "";

        carimportimgview.setImage(null);

    }

    public void availableCarUpdate() {

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        int intValue = availablecheckbox.isSelected() ? 1 : 0;
        int check;
        if(((String) type.getSelectionModel().getSelectedItem()).equals("Regular"))
        {
            check = 0;
        }
        else check = 1;
        String sql = "UPDATE cars SET type = '" + type.getSelectionModel().getSelectedItem()+ "', model = '"
                + modeltextfeild.getText() + "', carname ='" + Nametextfeild.getText()  + "', baseprice = '"
                + Pricetextfeild.getText() + "',available = '" + intValue + "',leatherseats = '" + check + "',sunroof = '" + check + "', imagePath = '" + uri
                + "' WHERE carid = '" + caridtextfeild.getText() + "'";

        connect = database2.connectDb();

        try {
            Alert alert;

            if (caridtextfeild.getText().isEmpty()
                    || Nametextfeild.getText().isEmpty()
                    || modeltextfeild.getText().isEmpty()
                    || availableCars_tableView.getSelectionModel().getSelectedItem() == null
                    || type.getSelectionModel().getSelectedItem() == null
                    || Pricetextfeild.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Car ID: " + caridtextfeild.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    availableCarShowListData();
                    availableCarClear();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String[] listStatus = {"Regular", "Luxury"};




    public void availableCarSelect() {
        Car carD = (Car) availableCars_tableView.getSelectionModel().getSelectedItem();
        int num = availableCars_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }


        caridtextfeild.setText(String.valueOf(carD.getCarid()));
        Nametextfeild.setText(carD.getCarName());
        modeltextfeild.setText(carD.getModel());
        Pricetextfeild.setText(String.valueOf(carD.getBaseprice()));
        availablecheckbox.setSelected(carD.isAvailable());
        getData.path = carD.getImagePath();

        String uri = "file:" + carD.getImagePath();

        image = new Image(uri, 181, 180, false, true);
        carimportimgview.setImage(image);

    }

    public void homeAvailableCars(){

        String sql = "SELECT COUNT(carid) FROM cars WHERE available = 1";

        connect = database2.connectDb();
        int countAC = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countAC = result.getInt("COUNT(carid)");
            }


            home_availableCars.setText(String.valueOf(countAC));

        }catch(Exception e){e.printStackTrace();}

    }
    public void homeTotalIncome(){
        String sql = "SELECT SUM(rent_price) FROM reservations";

        double sumIncome = 0;

        connect = database2.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                sumIncome = result.getDouble("SUM(rent_price)");
            }

            home_totalIncome.setText("$" + String.valueOf(sumIncome));
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableCarShowListData();
        availableCarStatusList();
        reservationsShowListData();
        homeAvailableCars();
        homeTotalIncome();

    }
}
