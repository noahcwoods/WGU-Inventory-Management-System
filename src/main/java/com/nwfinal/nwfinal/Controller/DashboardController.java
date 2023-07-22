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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.scene.control.TableColumn.CellDataFeatures;
import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    private ZonedDateTime loginTime;

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
    private TextField existingApptEndDate;

    @FXML
    private TextField existingApptIDTxt;

    @FXML
    private TextField existingApptLocationTxt;

    @FXML
    private TextField existingApptStartDate;

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
    void allApptSelectedClick(MouseEvent event) throws SQLException, ParseException {

        try {
            ObservableList<Object> obj = FXCollections.observableArrayList(allAppointmentsTableView.getSelectionModel().getSelectedItem());
            String selectedItem = obj.toString();
            int index = new Scanner(selectedItem).useDelimiter("\\D+").nextInt();
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

            String convertedStartDate = convertToLocal(startDate);
            String convertedEndDate = convertToLocal(endDate);

            String[] start = convertedStartDate.split("\\s+");
            String[] end = convertedEndDate.split("\\s+");

            existingApptEndDate.setText(start[0]);
            existingApptStartDate.setText(end[0]);

            existingApptStartTimeTxt.setText(start[1]);
            existingApptEndTimeTxt.setText(end[1]);
        }catch (Exception e){

        }

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
    void customerSelectedFromViewClick(MouseEvent event) throws Exception {



        try {
            ObservableList<Object> obj = FXCollections.observableArrayList(allCustomersTableView.getSelectionModel().getSelectedItem());
            String selectedItem = obj.toString();
            int index = new Scanner(selectedItem).useDelimiter("\\D+").nextInt();
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
            if (existingCustomerAssociatedAppointmentsTableView != null){
                existingCustomerAssociatedAppointmentsTableView.getColumns().clear();
            }
            buildDataAppt(associatedAppointments, existingCustomerAssociatedAppointmentsTableView, filteredAppointments);

        }catch (Exception e){

        }



    }

    @FXML
    void deleteExistingCustomerBtn(ActionEvent event) throws SQLException {

        int customerID = Integer.parseInt(existingCustomerIDTxt.getText());

        ResultSet rs = appointmentsQuery.select(customerID);
        if (rs.next() == false){
            customersQuery.delete(customerID);
            String deleteMessage = existingCustomerNameTxt.getText() + " has been deleted!";
            alertSuccessful(deleteMessage);
            updateAllTables();
            toggleEdit();

        }else {
            alertError("Cannot Delete Customers with Appointments Associated");
        }



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
    void existingApptDeleteBtn(ActionEvent event) throws SQLException {
        int apptID = Integer.parseInt(existingApptIDTxt.getText());

        appointmentsQuery.delete(apptID);
        String message = "Appointment ID: " + existingApptIDTxt.getText() + "\n"
                + "Appointment Type: " + existingApptTypeTxt.getText() + "\n" +
                "This appointment has been cancelled successfully ";
        alertSuccessful(message);

        updateAllTables();
        toggleApptEdit();
    }

    @FXML
    void existingApptEditBtn(ActionEvent event) {
        if (Objects.equals(existingApptEditBtn.getText(), "Cancel")){
            toggleApptEdit();
        }else {
            existingApptTitleTxt.setEditable(true);
            existingApptDescTxt.setEditable(true);
            existingApptLocationTxt.setEditable(true);
            existingApptContactCombo.setDisable(false);
            existingApptTypeTxt.setEditable(true);
            existingApptStartDate.setEditable(true);
            existingApptStartTimeTxt.setEditable(true);
            existingApptEndDate.setEditable(true);
            existingApptEndTimeTxt.setEditable(true);
            existingApptCustomerIDTxt.setEditable(true);
            ExistingApptUserIDTxt.setEditable(true);

            existingApptEditBtn.setText("Cancel");
            existingApptDeleteBtn.setDisable(false);
            existingApptSaveBtn.setDisable(false);
            allAppointmentsTableView.setDisable(true);

        }
    }

    @FXML
    void existingApptSaveBtn(ActionEvent event) throws SQLException, ParseException {
        int apptID = Integer.parseInt(existingApptIDTxt.getText());
        String apptTitle = existingApptTitleTxt.getText();
        String apptDesc = existingApptDescTxt.getText();
        String apptLocation = existingApptLocationTxt.getText();
        String apptContact = existingApptContactCombo.getSelectionModel().getSelectedItem();
        int contactID = -1;
        ResultSet getContactID = contactsQuery.select(apptContact);
        while(getContactID.next()){
            contactID = getContactID.getInt("Contact_ID");
        }
        String apptType = existingApptTypeTxt.getText();
        String startDateTime;
        String endDateTime;
        String lastUpdateDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC")).format(Instant.now());
        String lastUpdatedBy = this.loggedInUser;
        int customerID = Integer.parseInt(existingApptCustomerIDTxt.getText());
        int userID = Integer.parseInt(ExistingApptUserIDTxt.getText());

        String localStartDateTime = existingApptStartDate.getText() + " " + existingApptStartTimeTxt.getText();
        String localEndDateTime = existingApptEndDate.getText() + " " + existingApptEndTimeTxt.getText();

        startDateTime = convertToUTC(localStartDateTime);
        endDateTime = convertToUTC(localEndDateTime);

        String start = startDateTime.toString();
        String end = endDateTime.toString();
        ResultSet overlap = appointmentsQuery.select(customerID);

        if (appointmentTimeVerification(startDateTime, endDateTime) && !overlappingAppointmentsCheck(overlap, start, end, apptID)){
            appointmentsQuery.update(apptID, apptTitle, apptDesc, apptLocation, apptType, startDateTime, endDateTime, lastUpdateDate, lastUpdatedBy, customerID, userID, contactID);
            updateAllTables();
            toggleApptEdit();
        }
    }

    @FXML
    void newApptCreateBtn(ActionEvent event) throws SQLException, ParseException {
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

        String localStartDateTime = newApptStartDate.getValue() + " " + newApptStartTimeTxt.getText();
        String localEndDateTime = newApptEndDate.getValue() + " " + newApptEndTimeTxt.getText();

        startDateTime = convertToUTC(localStartDateTime);
        endDateTime = convertToUTC(localEndDateTime);

        String start = startDateTime.toString();
        String end = endDateTime.toString();

        ResultSet verifyApptOverlap = appointmentsQuery.select(customerID);
        //boolean test = overlappingAppointmentsCheck(verifyApptOverlap, start, end);
        //System.out.println(test);


        if (appointmentTimeVerification(startDateTime, endDateTime) && !overlappingAppointmentsCheck(verifyApptOverlap, start, end, -1)){
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


            //
            //System.out.println(allAppointmentsTableView.getItems().size());

            if (allAppointmentsTableView.getColumns() != null){
                allAppointmentsTableView.getColumns().clear();
            }
            if (allCustomersTableView.getColumns() != null){
                allCustomersTableView.getColumns().clear();
            }
            if (existingCustomerAssociatedAppointmentsTableView.getColumns() != null){
                existingCustomerAssociatedAppointmentsTableView.getColumns().clear();
            }

            if (allCustomers != null){
                allCustomers.clear();
            }
            if (allAppointments != null){
                allAppointments.clear();
            }
            if (countryOptions != null){
                countryOptions.clear();
            }
            if (contactOptions != null){
                contactOptions.clear();
            }
            //System.out.println(allAppointments);

            buildData(populateCustomersTable, allCustomersTableView, allCustomers);
            buildDataAppt(populateAppointmentsTable, allAppointmentsTableView, allAppointments);


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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void buildData(ResultSet rs, TableView tb, ObservableList<ObservableList> list)throws Exception{

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

                    if (i == 6 || i == 8){
                        String date = rs.getString(i);
                        String[] actualDate = date.split("\\s+");
                        String[] actualTime = actualDate[1].split(":");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(actualDate[0]);



                        LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        LocalDateTime ldt = LocalDateTime.of(date2.getYear(), date2.getMonth(), date2.getDayOfMonth(), Integer.parseInt(actualTime[0]), Integer.parseInt(actualTime[1]), Integer.parseInt(actualTime[2]));
                        OffsetDateTime odt = ldt.atOffset(ZoneOffset.UTC);
                        ZoneId z = ZoneId.systemDefault();
                        ZonedDateTime zdt = odt.atZoneSameInstant(z);
                        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        row.add(customFormat.format(zdt));
                    }else {
                        row.add(rs.getString(i));
                    }


                }
                list.add(row);

            }
            tb.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildDataAppt(ResultSet rs, TableView tb, ObservableList<ObservableList> list)throws Exception{

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

                    if (i == 6 || i == 7 || i == 8 || i == 10){
                        String date = rs.getString(i);
                        String[] actualDate = date.split("\\s+");
                        String[] actualTime = actualDate[1].split(":");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(actualDate[0]);
                        LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDateTime ldt = LocalDateTime.of(date2.getYear(), date2.getMonth(), date2.getDayOfMonth(), Integer.parseInt(actualTime[0]), Integer.parseInt(actualTime[1]), Integer.parseInt(actualTime[2]));
                        OffsetDateTime odt = ldt.atOffset(ZoneOffset.UTC);
                        ZoneId z = ZoneId.systemDefault();
                        ZonedDateTime zdt = odt.atZoneSameInstant(z);
                        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        row.add(customFormat.format(zdt));
                    }else {
                        row.add(rs.getString(i));
                    }


                }
                list.add(row);

            }
            tb.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLoggedInUser(String username, ZonedDateTime loginTime){
        this.loggedInUser = username;
        this.loginTime = loginTime;

        try {
            ResultSet rs = appointmentsQuery.select();
            checkForUpcomingAppointments(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

    public void toggleApptEdit(){
        allAppointmentsTableView.setDisable(false);
        existingApptTitleTxt.setEditable(false);
        existingApptDescTxt.setEditable(false);
        existingApptLocationTxt.setEditable(false);
        existingApptContactCombo.setDisable(true);
        existingApptTypeTxt.setEditable(false);
        existingApptStartDate.setEditable(false);
        existingApptStartTimeTxt.setEditable(false);
        existingApptEndDate.setEditable(false);
        existingApptEndTimeTxt.setEditable(false);
        existingApptCustomerIDTxt.setEditable(false);
        ExistingApptUserIDTxt.setEditable(false);

        existingApptTitleTxt.setText("");
        existingApptDescTxt.setText("");
        existingApptLocationTxt.setText("");
        existingApptContactCombo.getSelectionModel().select("");
        existingApptTypeTxt.setText("");
        existingApptStartDate.setText("");
        existingApptStartTimeTxt.setText("");
        existingApptEndDate.setText("");
        existingApptEndTimeTxt.setText("");
        existingApptCustomerIDTxt.setText("");
        ExistingApptUserIDTxt.setText("");
        existingApptIDTxt.setText("");

        existingApptEditBtn.setText("Edit");
        existingApptDeleteBtn.setDisable(true);
        existingApptSaveBtn.setDisable(true);
        allAppointmentsTableView.getSelectionModel().clearSelection();


    }

    public String convertToUTC(String date) throws ParseException {

        String[] actualDate = date.split("\\s+");
        String[] actualTime = actualDate[1].split(":");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(actualDate[0]);
        ZoneId z = ZoneId.systemDefault();
        LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime ldt = LocalDateTime.of(date2.getYear(), date2.getMonth(), date2.getDayOfMonth(), Integer.parseInt(actualTime[0]), Integer.parseInt(actualTime[1]), Integer.parseInt(actualTime[2]));
        ZonedDateTime zdt = ldt.atZone(z);
        ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //System.out.println("Now printing the integer: " + Integer.parseInt(actualTime[0]) + " Complete.");
        return customFormat.format(utc);

    }
    public String convertToLocal(String date) throws ParseException {


        String[] actualDate = date.split("\\s+");
        String[] actualTime = actualDate[1].split(":");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(actualDate[0]);
        LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime ldt = LocalDateTime.of(date2.getYear(), date2.getMonth(), date2.getDayOfMonth(), Integer.parseInt(actualTime[0]), Integer.parseInt(actualTime[1]), Integer.parseInt(actualTime[2]));
        OffsetDateTime odt = ldt.atOffset(ZoneOffset.UTC);
        ZoneId z = ZoneId.systemDefault();
        ZonedDateTime zdt = odt.atZoneSameInstant(z);
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return customFormat.format(zdt);
    }
    public String convertToET(String date) throws ParseException {
        String[] actualDate = date.split("\\s+");
        String[] actualTime = actualDate[1].split(":");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(actualDate[0]);
        LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime ldt = LocalDateTime.of(date2.getYear(), date2.getMonth(), date2.getDayOfMonth(), Integer.parseInt(actualTime[0]), Integer.parseInt(actualTime[1]), Integer.parseInt(actualTime[2]));
        OffsetDateTime odt = ldt.atOffset(ZoneOffset.UTC);
        ZoneId z = ZoneId.of("America/New_York");
        ZonedDateTime zdt = odt.atZoneSameInstant(z);
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return customFormat.format(zdt);
    }

    public void alertError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }

    public void alertSuccessful(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.show();
    }

    public boolean appointmentTimeVerification(String startDateTimeUTC, String endDateTimeUTC) throws ParseException {

        String startDateTime = convertToET(startDateTimeUTC);
        String endDateTime = convertToET(endDateTimeUTC);

        String[] actualStartDate = startDateTime.split("\\s+");
        String[] actualStartTime = actualStartDate[1].split(":");

        String[] actualEndDate = endDateTime.split("\\s+");
        String[] actualEndTime = actualEndDate[1].split(":");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = sdf.parse(actualStartDate[0]);
        Date endDate = sdf.parse(actualEndDate[0]);
        
        if (startDate.compareTo(endDate) == 0){
        } else if (startDate.compareTo(endDate) > 0) {
            alertError("Error, the start date can not occur after the end date");
            return false;
        }else {
            alertError("Error, the end date can not occur after the start date. They must be on the same date");
            return false;
        }

        //System.out.println(actualStartTime[0]);
        //System.out.println(actualEndTime[0]);

        if (Integer.parseInt(actualStartTime[0]) < 8 || (Integer.parseInt(actualStartTime[0]) == 22 && Integer.parseInt(actualStartTime[1]) > 0 || Integer.parseInt(actualStartTime[2]) > 0) || Integer.parseInt(actualStartTime[0]) > 22){
            alertError("Error, you cannot start an appointment outside of business hours");
            return false;
        } else if (Integer.parseInt(actualEndTime[0]) < 8 || (Integer.parseInt(actualEndTime[0]) == 22 && Integer.parseInt(actualEndTime[1]) > 0 || Integer.parseInt(actualEndTime[2]) > 0) || Integer.parseInt(actualEndTime[0]) > 22) {
            alertError("Error, you cannot end an appointment outside of business hours");
            return false;
        } else if (Integer.parseInt(actualStartTime[0]) > Integer.parseInt(actualEndTime[0])) {
            alertError("Error, you cannot end an appointment prior to its start time");
            return false;
        }
        return true;
    }

    public boolean overlappingAppointmentsCheck(ResultSet rs, String start, String end, int apptID) throws SQLException, ParseException {

        //String startDateTimes[] = null;
        ArrayList<String> startDateTimes = new ArrayList<String>();
        //String endDateTimes[] = null;
        ArrayList<String> endDateTimes = new ArrayList<String>();
        int j = 0;
        boolean check1 = false;
        boolean check2 = false;

        while (rs.next()){
            if (apptID != rs.getInt("Appointment_ID")){
                startDateTimes.add(rs.getString("Start"));
                endDateTimes.add(rs.getString("End"));
            }
        }

        if (startDateTimes.size() > 0){

            /*ArrayList<String> actualStartDate = new ArrayList<String>();
            ArrayList<String> actualStartTime = new ArrayList<String>();
            ArrayList<String> actualEndDate = new ArrayList<String>();
            ArrayList<String> actualEndTime = new ArrayList<String>();

            for (String s: startDateTimes){
                String[] temp = s.split("\\s+");
                actualStartDate.add(temp[0]);
                actualStartTime.add(temp[1]);

            }

            for (String s: endDateTimes){
                String[] temp = s.split("\\s+");
                actualEndDate.add(temp[0]);
                actualEndTime.add(temp[1]);
            }*/

            for (String s: startDateTimes){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //System.out.println(s);
                Date startTimeLocated = sdf.parse(s);
                Date providedEndTime = sdf.parse(end);
                //System.out.println("START TIME    " + startTimeLocated + "     END");
                //System.out.println("END TIME    " + providedEndTime + "     END");

                if (startTimeLocated.before(providedEndTime)){
                    check1 = true;
                }
            }
            for (String s: endDateTimes){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date endTimeLocated = sdf.parse(s);
                Date providedStartTime = sdf.parse(start);
                //System.out.println("END TIME    " + endTimeLocated + "     END");

                if (providedStartTime.before(endTimeLocated)){
                    check2 = true;
                }
            }
        }

        if (check1 && check2){
            alertError("Error, you cannot overlap appointment times for a single customer.");
            return true;
        }
        return false;
    }

    public void checkForUpcomingAppointments(ResultSet allAppointments) throws SQLException, ParseException {

        ArrayList<Date> allAppointmentDates = new ArrayList<>();

        while (allAppointments.next()){
            String tempdate = allAppointments.getString("Start");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date tempDateConverted = sdf.parse(tempdate);
            allAppointmentDates.add(tempDateConverted);
        }

        String loginTimeUTCString = convertToUTC(loginTime.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date loginTimeUTC = sdf.parse(loginTimeUTCString);

        System.out.println("checked date login = " + loginTimeUTC);
        System.out.println("Checked date list = " + allAppointmentDates);

        for (Date d: allAppointmentDates){
            long dateNow = 012312; //loginTime.ofInstant(Instant.ofEpochMilli(m), ZoneId.systemDefault());
            long dateCompare = d.getTime();

            if (Math.abs(dateCompare - dateNow) < TimeUnit.MINUTES.toMillis(15)){
                Alert alert = new Alert(Alert.AlertType.WARNING, "You have an upcoming appointment within 15 minutes of now!");
                alert.show();
            }
        }
    }
}
