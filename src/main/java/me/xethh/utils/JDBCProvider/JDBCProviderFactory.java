package me.xethh.utils.JDBCProvider;

public class JDBCProviderFactory {
    public static JDBCProvider createProvider(String jdbcDriver, String connectionString, String username, String password){
        return new ImmutableJDBCProvider(jdbcDriver,connectionString,username,password);
    }
    public static JDBCProvider mysqlProvider(String connectionString, String username, String password){
        return createProvider(JDBCDrivers.Mysql.driver(),connectionString,username,password);
    }
    public static PersistedJDBCProvider createPersistedProvider(String jdbcDriver, String connectionString, String username, String password){
        return new PersistedDBCProviderImpl(jdbcDriver,connectionString,username,password);
    }
    public static PersistedJDBCProvider mysqlPersistedProvider(String connectionString, String username, String password){
        return createPersistedProvider(JDBCDrivers.Mysql.driver(),connectionString,username,password);
    }

}
