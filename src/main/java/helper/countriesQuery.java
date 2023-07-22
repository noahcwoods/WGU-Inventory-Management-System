package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains all queries for the countries table
 */
public class countriesQuery {

    /**
     * Selects all countries
     * @return
     * @throws SQLException
     */
    public static ResultSet select() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Selects specific country given the ID
     * @param countryID
     * @return
     * @throws SQLException
     */
    public static ResultSet select(int countryID) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * selects given country given the country's name
     * @param countryName
     * @return
     * @throws SQLException
     */
    public static ResultSet select(String countryName) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, countryName);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

}
