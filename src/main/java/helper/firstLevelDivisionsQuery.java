package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * contains all queries to the first level division table
 */
public class firstLevelDivisionsQuery {

    /**
     * selects all first level divisions
     * @return
     * @throws SQLException
     */
    public static ResultSet select() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * selects specific division given the ID
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static ResultSet select(int divisionID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects specific division given the name
     * @param divisionName
     * @return
     * @throws SQLException
     */
    public static ResultSet select(String divisionName) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects the specific divisions associated to a given country
     * @param countryID
     * @return
     * @throws SQLException
     */
    public static ResultSet selectStates(int countryID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

}
