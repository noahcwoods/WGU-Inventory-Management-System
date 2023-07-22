package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class contains all queries to the appointments table
 */
public abstract class appointmentsQuery {

    /**
     * selects all appointments
     * @return
     * @throws SQLException
     */
    public static ResultSet select() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, c.Contact_ID, Contact_Name, Email FROM appointments JOIN contacts c on appointments.Contact_ID = c.Contact_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * selects appointments in THIS month
     * @return
     * @throws SQLException
     */
    public static ResultSet selectMonth() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, c.Contact_ID, Contact_Name, Email FROM appointments JOIN contacts c on appointments.Contact_ID = c.Contact_ID WHERE MONTH(Start) = month(now()) AND YEAR(Start) = YEAR(now()) ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * selects appointments in this WEEK
     * @return
     * @throws SQLException
     */
    public static ResultSet selectWeek() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, c.Contact_ID, Contact_Name, Email FROM appointments JOIN contacts c on appointments.Contact_ID = c.Contact_ID WHERE WEEK(Start) = WEEK(now()) AND YEAR(Start) = YEAR(now())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects appointments based on the associated customer
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static ResultSet select(int customerID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects appointment that was selected by user CLICK
     * @param appointmentID
     * @return
     * @throws SQLException
     */
    public static ResultSet selectChosen(int appointmentID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Adds new appointments to the database
     * @param apptTitle
     * @param apptDesc
     * @param apptLocation
     * @param apptType
     * @param startDateTime
     * @param endDateTime
     * @param createDate
     * @param createdBy
     * @param lastUpdateDate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
    public static void insert(String apptTitle, String apptDesc, String apptLocation, String apptType, String startDateTime, String endDateTime, String createDate, String createdBy, String lastUpdateDate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, apptTitle);
        ps.setString(2, apptDesc);
        ps.setString(3, apptLocation);
        ps.setString(4, apptType);
        ps.setString(5, startDateTime);
        ps.setString(6, endDateTime);
        ps.setString(7, createDate);
        ps.setString(8, createdBy);
        ps.setString(9, lastUpdateDate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);
        ps.executeUpdate();
    }

    /**
     * Updates given appointment
     * @param apptID
     * @param apptTitle
     * @param apptDesc
     * @param apptLocation
     * @param apptType
     * @param startDateTime
     * @param endDateTime
     * @param lastUpdateDate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
    public static void update(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType, String startDateTime, String endDateTime, String lastUpdateDate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, apptTitle);
        ps.setString(2, apptDesc);
        ps.setString(3, apptLocation);
        ps.setString(4, apptType);
        ps.setString(5, startDateTime);
        ps.setString(6, endDateTime);
        ps.setString(7, lastUpdateDate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);
        ps.setInt(12, apptID);
        ps.executeUpdate();
    }

    /**
     * Deletes given appointment
     * @param apptID
     * @throws SQLException
     */
    public static void delete(int apptID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptID);
        ps.executeUpdate();
    }

    /**
     * Selects appointment types for use in a combobox
     * @return
     * @throws SQLException
     */
    public static ResultSet selectTypes() throws SQLException {
        String sql = "SELECT Type FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects appointments by contact
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static ResultSet selectByContact(int contactID) throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, c.Contact_ID, Contact_Name, Email FROM appointments JOIN contacts c on appointments.Contact_ID = c.Contact_ID WHERE c.Contact_ID = ? ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects appointments of a given type within a given month
     * @param monthName
     * @param type
     * @return
     * @throws SQLException
     */
    public static ResultSet selectByTypeMonth(String monthName, String type) throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, c.Contact_ID, Contact_Name, Email FROM appointments JOIN contacts c on appointments.Contact_ID = c.Contact_ID WHERE MONTHNAME(Start) = ? AND Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, monthName);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects appointments that were CREATED by a specific user
     * @param selectedUser
     * @return
     * @throws SQLException
     */
    public static ResultSet selectByUser(String selectedUser) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Created_By = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, selectedUser);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects appointments from TODAY
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static ResultSet selectToday(int customerID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND DAY(Start) = DAY(now())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
