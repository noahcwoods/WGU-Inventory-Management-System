module com.nwfinal.nwfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.nwfinal.nwfinal to javafx.fxml;
    opens com.nwfinal.nwfinal.Controller to javafx.fxml;
    exports com.nwfinal.nwfinal;
    exports com.nwfinal.nwfinal.Controller;
}