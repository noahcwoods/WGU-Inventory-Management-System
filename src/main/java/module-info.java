module com.nwfinal.nwfinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nwfinal.nwfinal to javafx.fxml;
    exports com.nwfinal.nwfinal;
}