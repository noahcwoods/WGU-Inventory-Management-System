package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Contains all queries associated with the users table
 */
public abstract class usersQuery {

    /**
     * inserts users into table
     * @param userName
     * @param password
     * @return
     * @throws SQLException
     */
    public static int insert(String userName, String password) throws SQLException{
        String sql = "INSERT INTO USERS (User_Name, Password) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * updates specified user
     * @param userName
     * @param password
     * @param user_ID
     * @return
     * @throws SQLException
     */
    public static int update(String userName, String password, int user_ID) throws SQLException {
        String sql = "UPDATE USERS  SET User_name = ?, Password = ? WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.setInt(3, user_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * deletes specified user
     * @param user_ID
     * @return
     * @throws SQLException
     */
    public static int delete(int user_ID) throws SQLException{
        String sql = "DELETE FROM USERS WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, user_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Selects all users
     * @return
     * @throws SQLException
     */
    public static ResultSet select() throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        /*while(rs.next()){
            int user_ID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            System.out.print(user_ID + " | " + userName + " | " + password);
            System.out.println();
        }*/
        return rs;
    }

    /**
     * checks user input against database, returns true if login creds are valid, false else
     * @param userName
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean select(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ?";
        String db_password = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            db_password = rs.getString("Password");
        }
        return Objects.equals(db_password, password);
    }

    /**
     * selects specific user given the users name
     * @param userName
     * @return
     * @throws SQLException
     */
    public static ResultSet selectUser(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();

        return rs;
    }
}
