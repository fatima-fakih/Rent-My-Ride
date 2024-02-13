module com.example.rentmyride {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.rentmyride to javafx.fxml;
    exports com.example.rentmyride;
}