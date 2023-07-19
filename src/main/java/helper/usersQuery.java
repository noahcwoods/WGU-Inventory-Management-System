package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class usersQuery {
    public static int insert(String userName, String password) throws SQLException{
        String sql = "INSERT INTO USERS (User_Name, Password) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int update(String userName, String password, int user_ID) throws SQLException {
        String sql = "UPDATE USERS  SET User_name = ?, Password = ? WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.setInt(3, user_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int delete(int user_ID) throws SQLException{
        String sql = "DELETE FROM USERS WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, user_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
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
}
