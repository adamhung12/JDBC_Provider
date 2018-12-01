import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCProviderFactory {
    public static JDBCProvider createProvider(String jdbcDriver, String connectionString, String username, String password){
        return new JDBCProvider(jdbcDriver,connectionString,username,password);
    }
    public static JDBCProvider mysqlProvider(String connectionString, String username, String password){
        return createProvider(JDBCDrivers.Mysql.driver(),connectionString,username,password);
    }

    public static void main(String[] args){
        JDBCProvider conn = mysqlProvider("jdbc:mysql://localhost:3306/explorer_playgroup?useUnicode=yes&amp&characterEncoding=UTF-8", "explorer_playgroup", "explorer_playgroup");
        try {
            Statement stmt = conn.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("show tables");
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
