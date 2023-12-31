package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains all queries to the contacts table
 */
public abstract class contactsQuery {

    /**
     * Selects all contacts
     * @return
     * @throws SQLException
     */
    public static ResultSet select() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects a given contact with their name
     * @param contactName
     * @return
     * @throws SQLException
     */
    public static ResultSet select(String contactName) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects a given contact with their ID
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static ResultSet select(int contactID) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
