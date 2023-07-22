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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static javafx.fxml.FXMLLoader.*;

public class LoginPageController implements Initializable {

    ResourceBundle rb = ResourceBundle.getBundle("rb");
    Stage stage;
    Parent scene;

    /** GUI interface item */
    @FXML
    private Button loginBtn;

    /** GUI interface item */
    @FXML
    private Label passwordLbl;

    /** GUI interface item */
    @FXML
    private Label zoneIDTextLbl;

    /** GUI interface item */
    @FXML
    private Label usernameLbl;

    /** GUI interface item */
    @FXML
    private TextField userLoginTxt;

    /** GUI interface item */
    @FXML
    private TextField userPasswordTxt;

    /** GUI interface item */
    @FXML
    private Label zoneIDLbl;


    /**
     * Checks users input against database and makes sure the user entered correct information
     * @param event
     * @throws SQLException
     * @throws IOException
     * @throws ParseException
     */
    @FXML
    void onActionClickLoginBtn(ActionEvent event) throws SQLException, IOException, ParseException {

        String userName = userLoginTxt.getText();
        String password = userPasswordTxt.getText();
        ZoneId z = ZoneId.systemDefault();
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(z);
        ZonedDateTime loginTime = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        long loginTimeMilli = loginTime.toInstant().toEpochMilli();
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-mm-dd HH:mm:ss");
        Logger log = Logger.getLogger("login_activity.txt");

        try{
            FileHandler handle = new FileHandler("login_activity.txt", true);
            SimpleFormatter simple = new SimpleFormatter();
            handle.setFormatter(simple);
            log.addHandler(handle);
        }catch (IOException ex){
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }


        Date test = new Date(loginTimeMilli);

        //System.out.println(test + "THIS IS THE DATE AFTER CONVERSION FROM MILLI");

        JDBC.openConnection();
        boolean authenticationCheck = usersQuery.select(userName, password);
        JDBC.closeConnection();

        if (authenticationCheck) {

            log.severe("NEW SUCCESSFUL LOGIN FROM USER: " + userName);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/nwfinal/nwfinal/dashboard.fxml"));
            loader.load();

            DashboardController controller = loader.getController();

            controller.sendLoggedInUser(userName, loginTimeMilli);

            stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }else {
            log.severe("NEW LOGIN ATTEMPT FAILED FROM USER: " + userName);
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("alert"));
            alert.show();
        }
    }

    /**
     * French or English conversion based on user's computer settings
     * @param url
     * @param resourceBundle
     */
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
