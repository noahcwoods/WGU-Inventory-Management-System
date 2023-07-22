package com.nwfinal.nwfinal.Controller;

import com.nwfinal.nwfinal.Main;
import helper.JDBC;
import helper.usersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static javafx.fxml.FXMLLoader.*;

public class LoginPageController implements Initializable {

    ResourceBundle rb = ResourceBundle.getBundle("rb");

    @FXML
    private Button loginBtn;

    @FXML
    private Label passwordLbl;

    @FXML
    private Label zoneIDTextLbl;

    @FXML
    private Label usernameLbl;

    Stage stage;
    Parent scene;


    @FXML
    private TextField userLoginTxt;

    @FXML
    private TextField userPasswordTxt;

    @FXML
    private Label zoneIDLbl;


    @FXML
    void onActionClickLoginBtn(ActionEvent event) throws SQLException, IOException, ParseException {

        String userName = userLoginTxt.getText();
        String password = userPasswordTxt.getText();
        ZoneId z = ZoneId.systemDefault();
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(z);
        ZonedDateTime loginTime = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-mm-dd HH:mm:ss");

        JDBC.openConnection();
        boolean authenticationCheck = usersQuery.select(userName, password);
        System.out.println(authenticationCheck);
        JDBC.closeConnection();

        if (authenticationCheck) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/nwfinal/nwfinal/dashboard.fxml"));
            loader.load();

            DashboardController controller = loader.getController();

            controller.sendLoggedInUser(userName, loginTime);

            stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("alert"));
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //ZoneId.getAvailableZoneIds().stream().sorted().forEach(System.out::println);


        ZoneId z = ZoneId.systemDefault();


        zoneIDLbl.setText(z.toString());


        usernameLbl.setText(rb.getString("username"));
        passwordLbl.setText(rb.getString("password"));
        zoneIDTextLbl.setText(rb.getString("zoneid"));
        loginBtn.setText(rb.getString("login"));

    }
}
