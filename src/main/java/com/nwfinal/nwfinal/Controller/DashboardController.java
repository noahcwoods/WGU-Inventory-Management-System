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
import java.text.DateFormatSymbols;
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

/**
 * Dashboard contains all aspects of the application except the initial login page. This handles all reporting, customer viewing/adding/deleting, appointment viewing/adding/deleting.
 */
public class DashboardController implements Initializable {

    private ObservableList<ObservableList> allCustomers;
    private ObservableList<ObservableList> allAppointments;
    private ObservableList<ObservableList> filteredAppointments;
    private ObservableList<ObservableList> contactAppointments;
    private ObservableList<String> countryOptions = FXCollections.observableArrayList();
    private ObservableList<String> stateOptions = FXCollections.observableArrayList();
    private ObservableList<String> contactOptions = FXCollections.observableArrayList();
    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
    private ObservableList<String> months = FXCollections.observableArrayList();
    private ObservableList<String> allUsers = FXCollections.observableArrayList();
    private String loggedInUser;
    private long loginTime;


    /** GUI interface item */
    @FXML
    private ComboBox<String> appointmentsByContactCombo;

    /** GUI interface item */
    @FXML
    private TableView appointmentsByContactTableView;

    /** GUI interface item */
    @FXML
    private ComboBox<String> appointmentsByMonthCombo;

    /** GUI interface item */
    @FXML
    private ComboBox<String> appointmentsByTypeCombo;

    /** GUI interface item */
    @FXML
    private TextField appointmentsByTypeOutputTxt;

    /** GUI interface item */
    @FXML
    private ComboBox<String> appointmentsByUserCombo;

    /** GUI interface item */
    @FXML
    private TextField appointmentsByUserOutput;

    /** GUI interface item */
    @FXML
    private RadioButton allAppointmentsRBtn;

    /** GUI interface item */
    @FXML
    private RadioButton weekAppointmentsRBtn;

    /** GUI interface item */
    @FXML
    private RadioButton monthAppointmentsRbtn;

    /** GUI interface item */
    @FXML
    private TextField existingApptEndTimeTxt;

    /** GUI interface item */
    @FXML
    private TextField existingApptStartTimeTxt;

    /** GUI interface item */
    @FXML
    private TextField newApptEndTimeTxt;

    /** GUI interface item */
    @FXML
    private TextField newApptStartTimeTxt;

    /** GUI interface item */
    @FXML
    private Button saveExistingCustomersBtn;

    /** GUI interface item */
    @FXML
    private Button existingApptSaveBtn;

    /** GUI interface item */
    @FXML
    private Button existingApptEditBtn;

    /** GUI interface item */
    @FXML
    private Button existingApptDeleteBtn;

    /** GUI interface item */
    @FXML
    private Button deleteExistingCustomerBtn;

    /** GUI interface item */
    @FXML
    private Button editExistingCustomerBtn;

    /** GUI interface item */
    @FXML
    private TextField ExistingApptUserIDTxt;

    /** GUI interface item */
    @FXML
    private TableView allAppointmentsTableView;

    /** GUI interface item */
    @FXML
    private TableView allCustomersTableView;

    /** GUI interface item */
    @FXML
    private ComboBox<String> existingApptContactCombo;

    /** GUI interface item */
    @FXML
    private TextField existingApptCustomerIDTxt;

    /** GUI interface item */
    @FXML
    private TextArea existingApptDescTxt;

    /** GUI interface item */
    @FXML
    private TextField existingApptEndDate;

    /** GUI interface item */
    @FXML
    private TextField existingApptIDTxt;

    /** GUI interface item */
    @FXML
    private TextField existingApptLocationTxt;

    /** GUI interface item */
    @FXML
    private TextField existingApptStartDate;

    /** GUI interface item */
    @FXML
    private TextField existingApptTitleTxt;

    /** GUI interface item */
    @FXML
    private TextField existingApptTypeTxt;

    /** GUI interface item */
    @FXML
    private TextField existingCustomerAddressTxt;

    /** GUI interface item */
    @FXML
    private TableView<?> existingCustomerAssociatedAppointmentsTableView;

    /** GUI interface item */
    @FXML
    private ComboBox<String> existingCustomerCountryCombo;

    /** GUI interface item */
    @FXML
    private TextField existingCustomerIDTxt;

    /** GUI interface item */
    @FXML
    private TextField existingCustomerNameTxt;

    /** GUI interface item */
    @FXML
    private TextField existingCustomerPhoneTxt;

    /** GUI interface item */
    @FXML
    private ComboBox<String> existingCustomerStateCombo;

    /** GUI interface item */
    @FXML
    private TextField existingCustomerZipTxt;

    /** GUI interface item */
    @FXML
    private ComboBox<String> newApptContactCombo;

    /** GUI interface item */
    @FXML
    private TextField newApptCustomerIDTxt;

    /** GUI interface item */
    @FXML
    private TextArea newApptDescTxt;

    /** GUI interface item */
    @FXML
    private DatePicker newApptEndDate;

    /** GUI interface item */
    @FXML
    private TextField newApptID;

    /** GUI interface item */
    @FXML
    private TextField newApptLocationTxt;

    /** GUI interface item */
    @FXML
    private DatePicker newApptStartDate;

    /** GUI interface item */
    @FXML
    private TextField newApptTitleTxt;

    /** GUI interface item */
    @FXML
    private TextField newApptTypeTxt;

    /** GUI interface item */
    @FXML
    private TextField newApptUserIDTxt;

    /** GUI interface item */
    @FXML
    private TextField newCustomerAddressTxt;

    /** GUI interface item */
    @FXML
    private ComboBox<String> newCustomerCountryCombo;

    /** GUI interface item */
    @FXML
    private TextField newCustomerNameTxt;

    /** GUI interface item */
    @FXML
    private TextField newCustomerPhoneTxt;

    /** GUI interface item */
    @FXML
    private ComboBox<String> newCustomerStateCombo;

    /** GUI interface item */
    @FXML
    private TextField newCustomerZipTxt;

    /**
     * Takes in a selected contact and displays all appointments associated to that contact
     * @param event
     * @throws Exception
     */
    @FXML
    void appointmentsByContactBtn(ActionEvent event) throws Exception {
        String selectedContact = appointmentsByContactCombo.getValue();
        ResultSet rs = contactsQuery.select(selectedContact);
        int contactID = -1;
        while (rs.next()){
            contactID = rs.getInt("Contact_ID");
        }

        ResultSet rs2 =  appointmentsQuery.selectByContact(contactID);

        if (contactAppointments != null){
            contactAppointments.clear();
        }
        buildDataAppt(rs2, appointmentsByContactTableView, contactAppointments);

    }

    /**
     * Takes in Type and Month inputs from user and displays the number of appointments in the given month with the given type
     * @param event
     * @throws SQLException
     */
    @FXML
    void appointmentsByTypeBtn(ActionEvent event) throws SQLException {
        String selectedType = appointmentsByTypeCombo.getValue();
        String selectedMonth = appointmentsByMonthCombo.getValue();
        int count = 0;

        ResultSet rs = appointmentsQuery.selectByTypeMonth(selectedMonth, selectedType);

        while (rs.next()){
            count++;
        }

        appointmentsByTypeOutputTxt.setText(String.valueOf(count));
    }

    /**
     * Takes in the requested user and displays the number of appointments that have been created by that user. NOTE: This does not display based on which user was assigned to that appointment. It only shows who created it.
     * @param event
     * @throws SQLException
     */
    @FXML
    void appointmentsByUserBtn(ActionEvent event) throws SQLException {
        String selectedUser = appointmentsByUserCombo.getValue();
        int count = 0;

        ResultSet rs = appointmentsQuery.selectByUser(selectedUser);
        while (rs.next()){
            count++;
        }
        appointmentsByUserOutput.setText(String.valueOf(count));
    }

    /**
     * Filters the main appointments view to show ALL appointments
     * */
    @FXML
    void allAppointmentsRBtnClick(MouseEvent event) {
        updateAllTables();
    }

    /**
     * Filters the main appointments view to show all appointments in a given WEEK
     * */
    @FXML
    void weekAppointmentsRBtnClick(MouseEvent event) {
        updateAllTables();
    }

    /**
     * Filters the main appointments view to show all appointments in a given MONTH
     * */
    @FXML
    void monthAppointmentsRbtnClick(MouseEvent event) {
        updateAllTables();
    }

    /**
     * Empty, may be needed for future functionality
     * */
    @FXML
    void newCustomerStateCombo(ActionEvent event) {

    }

    /**
     * Displays detailed appointment information on the left side of the user interface given the clicked on item.
     * @param event
     * @throws SQLException
     * @throws ParseException
     */
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

    /**
     * Once country is selected by user, this sets the STATE combo with the appropriate states / provinces
     * @param event
     * @throws SQLException
     */
    @FXML
    void newCustomerCountryCombo(ActionEvent event) throws SQLException {
        String countryName = newCustomerCountryCombo.getSelectionModel().getSelectedItem();
        newCustomerStateCombo.setItems(getStateOptions(countryName));
    }

    /**
     * Not used. May be needed for future functionality
     * @param event
     */
    @FXML
    void existingCustomerStateCombo(ActionEvent event) {
    }

    /**
     * Once country is selected by user, this sets the STATE combo with the appropriate states / provinces
     * @param event
     * @throws SQLException
     */
    @FXML
    void existingCustomerCountryCombo(ActionEvent event) throws SQLException {
        String countryName = existingCustomerCountryCombo.getSelectionModel().getSelectedItem();
        existingCustomerStateCombo.setItems(getStateOptions(countryName));
    }

    /**
     * Displays detailed customer information on the left side of the user interface given the clicked on item
     * @param event
     * @throws Exception
     */
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

    /**
     * Deletes the requested customer provided there are no associated appointments
     * @param event
     * @throws SQLException
     */
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

    /**
     * Enables fields to edit the existing customer. If this has already been selected, changes to 'Cancel' and upon second click, disables ability to edit fields and cancels any edits already made.
     * @param event
     * @throws SQLException
     */
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

    /**
     * Deletes the specified appointment
     * @param event
     * @throws SQLException
     */
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

    /**
     * Enables editable fields for the appointment selected. If this has already been clicked for editing, switches to 'Cancel' and upon second click disables ability to edit fields and cancels edits made already.w
     * @param event
     */
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

    /**
     * Saves edits made to an existing appointment provided those edits meet criteria for overlapping appointments
     * @param event
     * @throws SQLException
     * @throws ParseException
     */
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
        ResultSet overlap = appointmentsQuery.selectToday(customerID);

        if (appointmentTimeVerification(startDateTime, endDateTime) && !overlappingAppointmentsCheck(overlap, start, end, apptID)){
            appointmentsQuery.update(apptID, apptTitle, apptDesc, apptLocation, apptType, startDateTime, endDateTime, lastUpdateDate, lastUpdatedBy, customerID, userID, contactID);
            updateAllTables();
            toggleApptEdit();
        }
    }

    /**
     * Creates new appointment provided it meets the specified criteria for overlapping appointments
     * @param event
     * @throws SQLException
     * @throws ParseException
     */
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

        ResultSet verifyApptOverlap = appointmentsQuery.selectToday(customerID);
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
            newApptStartDate.setValue(LocalDate.now());
            newApptEndDate.setValue(LocalDate.now());

        }


    }

    /**
     * Creates a new customer
     * @param event
     * @throws SQLException
     */
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

    /**
     * Saves changes made to existing customer
     * @param event
     * @throws SQLException
     */
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

    /**
     * Starts database connection. Populates all tables in the view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JDBC.openConnection();
        updateAllTables();



    }

    /**
     * Clears tables if they are already populated, calls the methods to update the tables with new information. Used across the application to update changes made to appointments and customers.
     * LAMBDA expressions contained here!
     * Two are used, one for countries combos and one for contact combos. The expressions are used to grab each item from a List and add it to the combo box for the user.
     * The alternative would be a classic style for loop iterating through the list and adding one by one.
     */
    public void updateAllTables(){
        try {
            ResultSet populateCustomersTable = customersQuery.select();


            //ResultSet populateAppointmentsTable = appointmentsQuery.select();


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
            if (appointmentTypes != null){
                appointmentTypes.clear();
            }
            if (months != null){
                months.clear();
            }
            if (allUsers != null){
                allUsers.clear();
            }

            //System.out.println(allAppointments);

            buildData(populateCustomersTable, allCustomersTableView, allCustomers);


            //buildDataAppt(populateAppointmentsTable, allAppointmentsTableView, allAppointments);

            if (allAppointmentsRBtn.isSelected()){
                ResultSet populateAppointmentsTable = appointmentsQuery.select();
                buildDataAppt(populateAppointmentsTable, allAppointmentsTableView, allAppointments);
            }else if (monthAppointmentsRbtn.isSelected()){
                ResultSet populateAppointmentsTable = appointmentsQuery.selectMonth();
                buildDataAppt(populateAppointmentsTable, allAppointmentsTableView, allAppointments);
            }else {
                ResultSet populateAppointmentsTable = appointmentsQuery.selectWeek();
                buildDataAppt(populateAppointmentsTable, allAppointmentsTableView, allAppointments);
            }


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

            newApptContactCombo.getItems().clear();
            newCustomerCountryCombo.getItems().clear();
            appointmentsByContactCombo.getItems().clear();
            existingCustomerCountryCombo.getItems().clear();


            contactOptions.forEach((s) -> { newApptContactCombo.getItems().add(s); appointmentsByContactCombo.getItems().add(s); } );
            countryOptions.forEach((s) -> { newCustomerCountryCombo.getItems().add(s); existingCustomerCountryCombo.getItems().add(s); } );

            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] Months = dfs.getMonths();
            for (String m: Months){
                months.add(m);
            }
            appointmentsByMonthCombo.setItems(months);

            ResultSet appointmentTypes = appointmentsQuery.selectTypes();

            while (appointmentTypes.next()){
                this.appointmentTypes.add(appointmentTypes.getString("Type"));
            }

            appointmentsByTypeCombo.setItems(this.appointmentTypes);

            ResultSet allUsers = usersQuery.select();
            while (allUsers.next()){
                this.allUsers.add(allUsers.getString("User_Name"));
            }
            appointmentsByUserCombo.setItems(this.allUsers);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes an empty tableview, uses the database to grab and create table columns, then populates the rows. Takes the row data and parses out the date/time fields to convert to local time.
     * @param rs
     * @param tb
     * @param list
     * @throws Exception
     */
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

    /**
     * Takes and empty tableview for appointments, then populates the columns and rows. Parses out the date/time fields to convert them to local time before displaying
     * @param rs
     * @param tb
     * @param list
     * @throws Exception
     */
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

    /**
     * Gets the logged in user information from the login screen. Also grabs the loginTime field for later user in the application.
     * @param username
     * @param loginTime
     */
    public void sendLoggedInUser(String username, long loginTime){
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

    /**
     * Takes a division name (such as Pennsylvania) and queries the database for the ID to be used in other parts of the application
     * @param divisionName
     * @return
     * @throws SQLException
     */
    public int getDivisionID(String divisionName) throws SQLException {
        int divisionID = -1;
        ResultSet getDivisionID = firstLevelDivisionsQuery.select(divisionName);
        while (getDivisionID.next()){
            divisionID = getDivisionID.getInt("Division_ID");
        }
        return divisionID;
    }

    /**
     * Takes in the country name specified by the user and returns all the applicable state options
     * @param countryName
     * @return
     * @throws SQLException
     */
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

    /**
     * Toggles editable fields for the customers when the edit button is pressed.
     */
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

    /**
     * Toggles editable fields for the appointments when the edit button is pressed.
     */
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

    /**
     * Converts the given time to UTC. Used mainly for adding times to database from user-entered values
     * @param date
     * @return
     * @throws ParseException
     */
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

    /**
     * Takes a given time and converts it to the users local time.
     * @param date
     * @return
     * @throws ParseException
     */
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

    /**
     * takes a given time and converts it to Eastern Time
     * @param date
     * @return
     * @throws ParseException
     */
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

    /**
     * Takes a given message and displays the message to the user.
     * @param message
     */
    public void alertError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }

    /**
     * Successful custom message prompt. Takes in a successful action and displays those results to the user. This is used for things like successfully deleting an appointment.
     * @param message
     */
    public void alertSuccessful(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.show();
    }

    /**
     * Takes in the start and end time and verifies that it is within the business hours, and that the start time occurs before the end time
     * @param startDateTimeUTC
     * @param endDateTimeUTC
     * @return
     * @throws ParseException
     */
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

    /**
     * Takes in the requested start and end time for an appointment and makes sure it will not interfere with the start and end times of any other appointment on that day
     * @param rs
     * @param start
     * @param end
     * @param apptID
     * @return
     * @throws SQLException
     * @throws ParseException
     */
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

    /**
     * Checks on login for any appointments upcoming within 15 minutes. Displays a custom message to the user if so, displays a message to the user if there are no appointments upcoming
     * @param allAppointments
     * @throws SQLException
     * @throws ParseException
     */
    public void checkForUpcomingAppointments(ResultSet allAppointments) throws SQLException, ParseException {

        ArrayList<LocalDateTime> allAppointmentDates = new ArrayList<>();
        boolean apptExists = false;

        while (allAppointments.next()){
            String tempdate = allAppointments.getString("Start");
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String tempDateConverted = convertToLocal(tempdate);
            LocalDateTime tempDateFinal = LocalDateTime.parse(tempDateConverted, sdf);
            allAppointmentDates.add(tempDateFinal);

            for (LocalDateTime d: allAppointmentDates){
                long dateNow = loginTime;
                ZonedDateTime zdt = d.atZone(ZoneId.systemDefault());
                long dateCompare = zdt.toInstant().toEpochMilli();

                //System.out.println(dateCompare + " DATE COMPARE");
                //System.out.println(dateNow + "DATE NOW");
                //
                //System.out.println(TimeUnit.MINUTES.toMillis(15) + "MILLI FOR 15");

                String localTimeStart = convertToLocal(allAppointments.getString("Start"));
                String localTimeEnd = convertToLocal(allAppointments.getString("End"));


                if (Math.abs(dateCompare - dateNow) < TimeUnit.MINUTES.toMillis(15)){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "You have an upcoming appointment within 15 minutes of now! \n" + "Appointment Name: " + allAppointments.getString("Title") + "\n" + "Appointment Date/Time: " + localTimeStart + " TO " + localTimeEnd);
                    apptExists = true;
                    alert.show();
                }
            }
        }
        if (!apptExists){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No upcoming appointments!");
            alert.show();
        }


    }
}
