package com.nwfinal.nwfinal.Controller;

import helper.JDBC;
import helper.usersQuery;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.util.Callback;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn.CellDataFeatures;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DashboardController implements Initializable {

    private ObservableList<ObservableList> userDataTest;




    @FXML
    private TextField ExistingApptUserIDTxt;

    @FXML
    private TableView allAppointmentsTableView;

    @FXML
    private TableView<?> allCustomersTableView;

    @FXML
    private ComboBox<?> existingApptContactCombo;

    @FXML
    private TextField existingApptCustomerIDTxt;

    @FXML
    private TextArea existingApptDescTxt;

    @FXML
    private DatePicker existingApptEndDate;

    @FXML
    private ComboBox<?> existingApptEndTimeCombo;

    @FXML
    private TextField existingApptIDTxt;

    @FXML
    private TextField existingApptLocationTxt;

    @FXML
    private DatePicker existingApptStartDate;

    @FXML
    private ComboBox<?> existingApptStartTimeCombo;

    @FXML
    private TextField existingApptTitleTxt;

    @FXML
    private TextField existingApptTypeTxt;

    @FXML
    private TextField existingCustomerAddressTxt;

    @FXML
    private TableView<?> existingCustomerAssociatedAppointmentsTableView;

    @FXML
    private ComboBox<?> existingCustomerCountryCombo;

    @FXML
    private TextField existingCustomerIDTxt;

    @FXML
    private TextField existingCustomerNameTxt;

    @FXML
    private TextField existingCustomerPhoneTxt;

    @FXML
    private ComboBox<?> existingCustomerStateCombo;

    @FXML
    private TextField existingCustomerZipTxt;

    @FXML
    private ComboBox<?> newApptContactCombo;

    @FXML
    private TextField newApptCustomerIDTxt;

    @FXML
    private TextArea newApptDescTxt;

    @FXML
    private DatePicker newApptEndDate;

    @FXML
    private ComboBox<?> newApptEndTimeCombo;

    @FXML
    private TextField newApptID;

    @FXML
    private TextField newApptLocationTxt;

    @FXML
    private DatePicker newApptStartDate;

    @FXML
    private ComboBox<?> newApptStartTimeCombo;

    @FXML
    private TextField newApptTitleTxt;

    @FXML
    private TextField newApptTypeTxt;

    @FXML
    private TextField newApptUserIDTxt;

    @FXML
    private TextField newCustomerAddressTxt;

    @FXML
    private ComboBox<?> newCustomerCountryTxt;

    @FXML
    private TextField newCustomerNameTxt;

    @FXML
    private TextField newCustomerPhoneTxt;

    @FXML
    private ComboBox<?> newCustomerStateTxt;

    @FXML
    private TextField newCustomerZipTxt;

    @FXML
    void allApptSelectedClick(MouseEvent event) {

    }

    @FXML
    void customerSelectedFromViewClick(MouseEvent event) {

    }

    @FXML
    void deleteExistingCustomerBtn(ActionEvent event) {

    }

    @FXML
    void editExistingCustomerBtn(ActionEvent event) {

    }

    @FXML
    void existingApptDeleteBtn(ActionEvent event) {

    }

    @FXML
    void existingApptEditBtn(ActionEvent event) {

    }

    @FXML
    void existingApptSaveBtn(ActionEvent event) {

    }

    @FXML
    void newApptCreateBtn(ActionEvent event) {

    }

    @FXML
    void newCustomerCreateBtn(ActionEvent event) {

    }

    @FXML
    void saveExistingCustomerBtn(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildData();
    }

    public void buildData(){
        JDBC.openConnection();
        userDataTest = FXCollections.observableArrayList();


        try {
            ResultSet rs = usersQuery.select();

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                allAppointmentsTableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                userDataTest.add(row);

            }
            allAppointmentsTableView.setItems(userDataTest);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
