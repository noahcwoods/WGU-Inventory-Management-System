package com.nwfinal.nwfinal.Controller;

import com.nwfinal.nwfinal.Model.Customer;
import helper.*;
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
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
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

import javax.xml.stream.events.EndDocument;

public class DashboardController implements Initializable {

    private ObservableList<ObservableList> allCustomers;
    private ObservableList<ObservableList> allAppointments;
    private ObservableList<ObservableList> filteredAppointments;
    private ObservableList<String> countryOptions = FXCollections.observableArrayList();
    private ObservableList<String> stateOptions = FXCollections.observableArrayList();
    private ObservableList<String> contactOptions = FXCollections.observableArrayList();
    private String loggedInUser;



    @FXML
    private TextField existingApptEndTimeTxt;
    @FXML
    private TextField existingApptStartTimeTxt;
    @FXML
    private TextField newApptEndTimeTxt;
    @FXML
    private TextField newApptStartTimeTxt;
    @FXML
    private Button saveExistingCustomersBtn;
    @FXML
    private Button existingApptSaveBtn;
    @FXML
    private Button existingApptEditBtn;
    @FXML
    private Button existingApptDeleteBtn;
    @FXML
    private Button deleteExistingCustomerBtn;

    @FXML
    private Button editExistingCustomerBtn;

    @FXML
    private TextField ExistingApptUserIDTxt;

    @FXML
    private TableView allAppointmentsTableView;

    @FXML
    private TableView allCustomersTableView;

    @FXML
    private ComboBox<String> existingApptContactCombo;

    @FXML
    private TextField existingApptCustomerIDTxt;

    @FXML
    private TextArea existingApptDescTxt;

    @FXML
    private DatePicker existingApptEndDate;

    @FXML
    private TextField existingApptIDTxt;

    @FXML
    private TextField existingApptLocationTxt;

    @FXML
    private DatePicker existingApptStartDate;

    @FXML
    private TextField existingApptTitleTxt;

    @FXML
    private TextField existingApptTypeTxt;

    @FXML
    private TextField existingCustomerAddressTxt;

    @FXML
    private TableView<?> existingCustomerAssociatedAppointmentsTableView;

    @FXML
    private ComboBox<String> existingCustomerCountryCombo;

    @FXML
    private TextField existingCustomerIDTxt;

    @FXML
    private TextField existingCustomerNameTxt;

    @FXML
    private TextField existingCustomerPhoneTxt;

    @FXML
    private ComboBox<String> existingCustomerStateCombo;

    @FXML
    private TextField existingCustomerZipTxt;

    @FXML
    private ComboBox<String> newApptContactCombo;

    @FXML
    private TextField newApptCustomerIDTxt;

    @FXML
    private TextArea newApptDescTxt;

    @FXML
    private DatePicker newApptEndDate;

    @FXML
    private TextField newApptID;

    @FXML
    private TextField newApptLocationTxt;

    @FXML
    private DatePicker newApptStartDate;

    @FXML
    private TextField newApptTitleTxt;

    @FXML
    private TextField newApptTypeTxt;

    @FXML
    private TextField newApptUserIDTxt;

    @FXML
    private TextField newCustomerAddressTxt;

    @FXML
    private ComboBox<String> newCustomerCountryCombo;

    @FXML
    private TextField newCustomerNameTxt;

    @FXML
    private TextField newCustomerPhoneTxt;

    @FXML
    private ComboBox<String> newCustomerStateCombo;

    @FXML
    private TextField newCustomerZipTxt;

    @FXML
    void newCustomerStateCombo(ActionEvent event) {

    }

    @FXML
    void allApptSelectedClick(MouseEvent event) throws SQLException {

        ObservableList<Object> obj = FXCollections.observableArrayList(allAppointmentsTableView.getSelectionModel().getSelectedItem());
        String selectedItem = obj.toString();
        char itemIndex = selectedItem.charAt(2);
        int index = Character.getNumericValue(itemIndex);
        int customerID = -1;
        String startDate = null;
        String endDate = null;

        ResultSet selectedAppointment = appointmentsQuery.selectChosen(index);
        while (selectedAppointment.next()){
            existingApptIDTxt.setText(selectedAppointment.getString("Appointment_ID"));
            existingApptTitleTxt.setText(selectedAppointment.getString("Title"));
            existingApptDescTxt.setText(selectedAppointment.getString("Description"));
            existingApptLocationTxt.setText(selectedAppointment.getString("Location"));
            int contactID = Integer.parseInt(selectedAppointment.getString("Contact_ID"));
            ResultSet contact = contactsQuery.select(contactID);
            while (contact.next()){
                String contactName = contact.getString("Contact_Name");
                existingApptContactCombo.getSelectionModel().select(contactName);
            }
            existingApptTypeTxt.setText(selectedAppointment.getString("Type"));
            existingApptCustomerIDTxt.setText(selectedAppointment.getString("Customer_ID"));
            ExistingApptUserIDTxt.setText(selectedAppointment.getString("User_ID"));
            endDate = selectedAppointment.getString("End");
            startDate = selectedAppointment.getString("Start");

        }
        String[] start = startDate.split("\\s+");
        String[] end = endDate.split("\\s+");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        existingApptStartDate.setValue(LocalDate.parse(start[0], format));
        existingApptEndDate.setValue(LocalDate.parse(end[0], format));

        existingApptStartTimeTxt.setText(start[1]);
        existingApptEndTimeTxt.setText(end[1]);





    }
    @FXML
    void newCustomerCountryCombo(ActionEvent event) throws SQLException {
        String countryName = newCustomerCountryCombo.getSelectionModel().getSelectedItem();
        newCustomerStateCombo.setItems(getStateOptions(countryName));
    }
    @FXML
    void existingCustomerStateCombo(ActionEvent event) {
    }
    @FXML
    void existingCustomerCountryCombo(ActionEvent event) throws SQLException {
        String countryName = existingCustomerCountryCombo.getSelectionModel().getSelectedItem();
        existingCustomerStateCombo.setItems(getStateOptions(countryName));
    }
    @FXML
    void customerSelectedFromViewClick(MouseEvent event) throws SQLException {

        ObservableList<Object> obj = FXCollections.observableArrayList(allCustomersTableView.getSelectionModel().getSelectedItem());

        String selectedItem = obj.toString();
        char itemIndex = selectedItem.charAt(2);
        int index = Character.getNumericValue(itemIndex);
        int customerID = -1;

        ResultSet selectedCustomer = customersQuery.select(index);

        while (selectedCustomer.next()){
            existingCustomerNameTxt.setText(selectedCustomer.getString("Customer_Name"));
            existingCustomerIDTxt.setText(String.valueOf(selectedCustomer.getInt("Customer_ID")));
            existingCustomerAddressTxt.setText(selectedCustomer.getString("Address"));
            existingCustomerZipTxt.setText(selectedCustomer.getString("Postal_Code"));
            existingCustomerPhoneTxt.setText(selectedCustomer.getString("Phone"));
            customerID = selectedCustomer.getInt("Customer_ID");

            int divisionID = Integer.parseInt(selectedCustomer.getString("Division_ID"));
            ResultSet customerCountryCode = firstLevelDivisionsQuery.select(divisionID);
            while (customerCountryCode.next()){
                int countryCode = Integer.parseInt(customerCountryCode.getString("country_ID"));
                String divisionName = customerCountryCode.getString("Division");
                existingCustomerStateCombo.getSelectionModel().select(divisionName);
                ResultSet customerCountryName = countriesQuery.select(countryCode);
                while (customerCountryName.next()){
                    String countryName = customerCountryName.getString("Country");
                    existingCustomerCountryCombo.getSelectionModel().select(countryName);
                }
            }
        }

        ResultSet associatedAppointments = appointmentsQuery.select(customerID);
        buildData(associatedAppointments, existingCustomerAssociatedAppointmentsTableView, filteredAppointments);

    }

    @FXML
    void deleteExistingCustomerBtn(ActionEvent event) throws SQLException {

        int customerID = Integer.parseInt(existingCustomerIDTxt.getText());

        customersQuery.delete(customerID);
        updateAllTables();
        toggleEdit();



    }

    @FXML
    void editExistingCustomerBtn(ActionEvent event) throws SQLException {

        if (Objects.equals(editExistingCustomerBtn.getText(), "Cancel")){
            toggleEdit();
        }else {
            allCustomersTableView.setDisable(true);
            saveExistingCustomersBtn.setDisable(false);
            deleteExistingCustomerBtn.setDisable(false);
            existingCustomerNameTxt.setEditable(true);
            existingCustomerAddressTxt.setEditable(true);
            existingCustomerZipTxt.setEditable(true);
            existingCustomerPhoneTxt.setEditable(true);
            existingCustomerCountryCombo.setDisable(false);
            existingCustomerStateCombo.setDisable(false);
            editExistingCustomerBtn.setText("Cancel");
        }

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
    void newApptCreateBtn(ActionEvent event) throws SQLException {
        String apptTitle = newApptTitleTxt.getText();
        String apptDesc = newApptDescTxt.getText();
        String apptLocation = newApptLocationTxt.getText();
        String apptContact = newApptContactCombo.getSelectionModel().getSelectedItem();
        int contactID = -1;
        ResultSet getContactID = contactsQuery.select(apptContact);
        while (getContactID.next()){
            contactID = getContactID.getInt("Contact_ID");
        }
        String apptType = newApptTypeTxt.getText();
        String startDateTime;
        String endDateTime;
        String createDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC")).format(Instant.now());
        String createdBy = loggedInUser;
        String lastUpdateDate = createDate;
        String lastUpdatedBy = createdBy;
        int customerID = Integer.parseInt(newApptCustomerIDTxt.getText());
        int userID = Integer.parseInt(newApptUserIDTxt.getText());

        startDateTime = String.valueOf(newApptStartDate.getValue()) + " " + newApptStartTimeTxt.getText();
        System.out.println(startDateTime);
        endDateTime = String.valueOf(newApptEndDate.getValue()) + " " + newApptEndTimeTxt.getText();

        appointmentsQuery.insert(apptTitle, apptDesc, apptLocation, apptType, startDateTime, endDateTime, createDate, createdBy, lastUpdateDate, lastUpdatedBy, customerID, userID, contactID);
        updateAllTables();

        newApptTitleTxt.setText("");
        newApptDescTxt.setText("");
        newApptLocationTxt.setText("");
        newApptContactCombo.getSelectionModel().select("");
        newApptTypeTxt.setText("");
        newApptCustomerIDTxt.setText("");
        newApptUserIDTxt.setText("");
        newApptStartTimeTxt.setText("");
        newApptEndTimeTxt.setText("");
        newApptStartDate.getEditor().clear();
        newApptEndDate.getEditor().clear();

    }

    @FXML
    void newCustomerCreateBtn(ActionEvent event) throws SQLException {

        String customerName = newCustomerNameTxt.getText();
        String address = newCustomerAddressTxt.getText();
        String zipCode = newCustomerZipTxt.getText();
        String phoneNumber = newCustomerPhoneTxt.getText();
        String createdBy = this.loggedInUser;
        String lastUpdatedBy = this.loggedInUser;
        String createDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC")).format(Instant.now());
        String lastUpdate = createDate;
        String divisionName = newCustomerStateCombo.getSelectionModel().getSelectedItem();
        int divisionID = getDivisionID(divisionName);


        customersQuery.insert(customerName, address, zipCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
        updateAllTables();

        newCustomerNameTxt.setText("");
        newCustomerAddressTxt.setText("");
        newCustomerZipTxt.setText("");
        newCustomerPhoneTxt.setText("");
        newCustomerCountryCombo.getSelectionModel().select("");
        newCustomerStateCombo.getSelectionModel().select("");

    }

    @FXML
    void saveExistingCustomerBtn(ActionEvent event) throws SQLException {

        String customerName = existingCustomerNameTxt.getText();
        String address = existingCustomerAddressTxt.getText();
        String zipCode = existingCustomerZipTxt.getText();
        String phoneNumber = existingCustomerPhoneTxt.getText();
        String lastUpdateDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC")).format(Instant.now());
        String updatedBy = loggedInUser;
        String divisionName = existingCustomerStateCombo.getSelectionModel().getSelectedItem();
        int divisionID = getDivisionID(divisionName);
        int customerID = Integer.parseInt(existingCustomerIDTxt.getText());

        customersQuery.update(customerName, address, zipCode, phoneNumber, lastUpdateDate, updatedBy, divisionID, customerID);

        toggleEdit();
        updateAllTables();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JDBC.openConnection();
        updateAllTables();

    }

    public void updateAllTables(){
        try {
            ResultSet populateCustomersTable = customersQuery.select();
            ResultSet populateAppointmentsTable = appointmentsQuery.select();
            buildData(populateCustomersTable, allCustomersTableView, allCustomers);
            buildData(populateAppointmentsTable, allAppointmentsTableView, allAppointments);


            ResultSet populateCountriesCombo = countriesQuery.select();
            ResultSet populateContactsCombo = contactsQuery.select();

            while (populateCountriesCombo.next()){

                String temp = populateCountriesCombo.getString("Country");
                countryOptions.add(temp);
            }

            while (populateContactsCombo.next()){
                String temp = populateContactsCombo.getString("Contact_Name");
                contactOptions.add(temp);
            }

            newCustomerCountryCombo.setItems(countryOptions);
            existingCustomerCountryCombo.setItems(countryOptions);
            newApptContactCombo.setItems(contactOptions);
            existingApptContactCombo.setItems(contactOptions);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildData(ResultSet rs, TableView tb, ObservableList<ObservableList> list){

        list = FXCollections.observableArrayList();


        try {
            //rs = usersQuery.select();

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tb.getColumns().addAll(col);
            }
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                list.add(row);

            }
            tb.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLoggedInUser(String username){
        this.loggedInUser = username;
    }

    public int getDivisionID(String divisionName) throws SQLException {
        int divisionID = -1;
        ResultSet getDivisionID = firstLevelDivisionsQuery.select(divisionName);
        while (getDivisionID.next()){
            divisionID = getDivisionID.getInt("Division_ID");
        }
        return divisionID;
    }

    public ObservableList<String> getStateOptions(String countryName) throws SQLException {
        int countryID = -1;
        ResultSet getCountryID = countriesQuery.select(countryName);
        stateOptions.clear();
        while (getCountryID.next()){
            countryID = getCountryID.getInt("Country_ID");
        }
        ResultSet populateDivisions = firstLevelDivisionsQuery.selectStates(countryID);
        while (populateDivisions.next()){
            stateOptions.add(populateDivisions.getString("Division"));
        }
        return stateOptions;
    }

    public void toggleEdit(){
        allCustomersTableView.setDisable(false);
        saveExistingCustomersBtn.setDisable(true);
        deleteExistingCustomerBtn.setDisable(true);
        existingCustomerNameTxt.setEditable(false);
        existingCustomerNameTxt.setText("");
        existingCustomerAddressTxt.setEditable(false);
        existingCustomerAddressTxt.setText("");
        existingCustomerZipTxt.setEditable(false);
        existingCustomerZipTxt.setText("");
        existingCustomerPhoneTxt.setEditable(false);
        existingCustomerPhoneTxt.setText("");
        existingCustomerCountryCombo.setDisable(true);
        existingCustomerCountryCombo.getSelectionModel().select("");
        existingCustomerStateCombo.setDisable(true);
        existingCustomerStateCombo.getSelectionModel().select("");
        existingCustomerIDTxt.setText("");
        editExistingCustomerBtn.setText("Edit");
    }
}
