import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class FindLast {

    public static String getTransID(int index, String SQL) throws SQLException {
        String s = "nil";
        Connection c;
        c = DataBaseConnection.connect();

        // ResultSet
        ResultSet rs = c.createStatement().executeQuery(SQL);

        while (rs.next()) {
            s = rs.getString(index);
        }

        System.out.println("Last Entry For Transaction ID: "+ s);
        c.close();

        return s;

    }

}