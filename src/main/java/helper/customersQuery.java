package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.sql.Date;

/**
 * Contains all queries for the customers table
 */
public abstract class customersQuery {

    /**
     * Inserts customer into the table
     * @param customerName
     * @param address
     * @param zipCode
     * @param phoneNumber
     * @param dateTime
     * @param createdBy
     * @param lastUpdate
     * @param updatedBy
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static int insert(String customerName, String address, String zipCode, String phoneNumber, String dateTime, String createdBy, String lastUpdate, String updatedBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, zipCode);
        ps.setString(4, phoneNumber);
        ps.setString(5, dateTime);
        ps.setString(6, createdBy);
        ps.setString(7, lastUpdate);
        ps.setString(8, updatedBy);
        ps.setInt(9, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * updates specified customer
     * @param customerName
     * @param address
     * @param zipCode
     * @param phoneNumber
     * @param lastUpdateDate
     * @param updatedBy
     * @param divisionID
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static int update(String customerName, String address, String zipCode, String phoneNumber, String lastUpdateDate, String updatedBy, int divisionID, int customerID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, zipCode);
        ps.setString(4, phoneNumber);
        ps.setString(5, lastUpdateDate);
        ps.setString(6, updatedBy);
        ps.setInt(7, divisionID);
        ps.setInt(8, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * deletes specified customer
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static int delete(int customerID) throws SQLException{
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * selects all customers from table
     * @return
     * @throws SQLException
     */
    public static ResultSet select() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * selects specific customer given the ID
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static ResultSet select(int customerID) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }


    /*public static boolean select(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ?";
        String db_password = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            db_password = rs.getString("Password");
        }
        return Objects.equals(db_password, password);
    }*/

}
