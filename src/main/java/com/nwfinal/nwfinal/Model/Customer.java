package com.nwfinal.nwfinal.Model;

import helper.customersQuery;
import helper.usersQuery;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * UNUSED CLASS! DO NOT USE!
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private Date dateTime;
    private String createdBy;
    private String updatedBy;
    private int divisionID;
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public Customer(int customerID, String customerName, String address, String zipCode, String phoneNumber, Date dateTime, String createdBy, String updatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.divisionID = divisionID;
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ResultSet rs = customersQuery.select();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String zipCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            Date dateTime = rs.getDate("Create_Date");
            String createdBy = rs.getString("Created_By");
            String updatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");

            Customer cus = new Customer(customerID, customerName, address, zipCode, phoneNumber, dateTime, createdBy, updatedBy, divisionID);
            allCustomers.add(cus);
        }



        return allCustomers;
    }


    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
