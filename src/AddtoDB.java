
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddtoDB {

    public static void insertThis(String SQL) throws SQLException {

        Connection c;
        c = DataBaseConnection.connect();

        // ResultSet
        try {
            c.createStatement().executeUpdate(SQL);

            System.out.println("Insertion success!");
        } catch (Exception e) {
            System.out.println("Insertion failed");
            System.err.println(e.getMessage());
        }

        c.close();

    }
}
