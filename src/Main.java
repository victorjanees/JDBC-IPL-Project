import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

//    }

//    public static List<Match> getMatchesData() throws Exception {
//        List<Match> matches = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/IPL";
        String username = "root";
        String password = "Dhoni@007";
        String query = "select * from Matches";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while(rs.next()){
            String matchData = rs.getString(2);
            System.out.println(matchData);
        }
        st.close();
        con.close();
    }
}
