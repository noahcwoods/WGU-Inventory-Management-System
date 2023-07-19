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
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.*;

public class LoginPageController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TextField userLoginTxt;

    @FXML
    private TextField userPasswordTxt;

    @FXML
    private Label zoneIDLbl;


    @FXML
    void onActionClickLoginBtn(ActionEvent event) throws SQLException, IOException {

        String userName = userLoginTxt.getText();
        String password = userPasswordTxt.getText();

        JDBC.openConnection();
        boolean authenticationCheck = usersQuery.select(userName, password);
        System.out.println(authenticationCheck);
        JDBC.closeConnection();

        if (authenticationCheck) {

            stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
            scene = load(getClass().getResource("/com/nwfinal/nwfinal/dashboard.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The username or password is incorrect, please try again...");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
