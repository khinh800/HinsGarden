import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Narayan
 */

public class DataBaseConnection {

    private static Connection conn;
//    private static String url = "jdbc:sqlserver://localhost\\CoT-CIS3365-14:1433;databaseName=HinsGarden_Database;user=admin;password=admin;";
    private static String url = "jdbc:postgresql://localhost:5432/HinsGarden_Database";

    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver()").newInstance();
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error: " + cnfe.getMessage());
        } catch (InstantiationException ie) {
            System.err.println("Error: " + ie.getMessage());
        } catch (IllegalAccessException iae) {
            System.err.println("Error: " + iae.getMessage());
        }

        conn = DriverManager.getConnection(url,"admin","admin");
        return conn;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn != null && !conn.isClosed())
            return conn;
        connect();
        return conn;

    }
}

//import com.microsoft.sqlserver.jdbc.*;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//
///**
// *
// * @author Narayan
// */
//
//public class DataBaseConnection {
//    Connection con = null;
//    CallableStatement cstmt = null;
//    ResultSet rs = null;
//
//        try {
//        // Establish the connection.
//        SQLServerDataSource ds = new SQLServerDataSource();
//        ds.setUser("COUGARNET\khinh");
//        ds.setPassword("Idontlikeit010891!");
//        ds.setServerName("localhost");
//        ds.setPortNumber(1433);
//        ds.setDatabaseName("");
//        con = ds.getConnection();
//
//        // Execute a stored procedure that returns some data.
//        cstmt = con.prepareCall("{call dbo.uspGetEmployeeManagers(?)}");
//        cstmt.setInt(1, 50);
//        rs = cstmt.executeQuery();
//
//        // Iterate through the data in the result set and display it.
//        while (rs.next()) {
//            System.out.println("EMPLOYEE: " + rs.getString("LastName") +
//                    ", " + rs.getString("FirstName"));
//            System.out.println("MANAGER: " + rs.getString("ManagerLastName") +
//                    ", " + rs.getString("ManagerFirstName"));
//            System.out.println();
//        }
//    }
//
//    // Handle any errors that may have occurred.
//        catch (Exception e) {
//        e.printStackTrace();
//    }
//        finally {
//        if (rs != null) try { rs.close(); } catch(Exception e) {}
//        if (cstmt != null) try { cstmt.close(); } catch(Exception e) {}
//        if (con != null) try { con.close(); } catch(Exception e) {}
//        System.exit(1);
//    }


//    private static Connection conn;
//    private static String url = "jdbc:sqlserver://localhost\\;databaseName=HinsGarden;";

   /* public static Connection connect() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver()").newInstance();
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error: " + cnfe.getMessage());
        } catch (InstantiationException ie) {
            System.err.println("Error: " + ie.getMessage());
        } catch (IllegalAccessException iae) {
            System.err.println("Error: " + iae.getMessage());
        }

        conn = DriverManager.getConnection(url);
        return conn;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn != null && !conn.isClosed())
            return conn;
        connect();
        return conn;

    }}*/
