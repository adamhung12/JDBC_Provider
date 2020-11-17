package me.xethh.utils.JDBCProvider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.xethh.utils.JDBCProvider.impl.ImmutableJDBCProvider;
import me.xethh.utils.JDBCProvider.impl.PersistedDBCProviderImpl;

public class JDBCProviderFactory {
    public static JDBCProvider createProvider(String jdbcDriver, String connectionString, String username, String password){
        return new ImmutableJDBCProvider(jdbcDriver,connectionString,username,password);
    }
    public static JDBCProvider mysqlProvider(String connectionString, String username, String password){
        return createProvider(JDBCDrivers.Mysql.driver(),connectionString,username,password);
    }
    public static JDBCProvider mysqlNewProvider(String connectionString, String username, String password){
        return createProvider(JDBCDrivers.Mysql_New.driver(),connectionString,username,password);
    }
    public static HikariDataSource mysqlDataSource(String connectionString, String username, String password){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(connectionString);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts",true);
        config.addDataSourceProperty("prepStmtCacheSize",512);
        config.addDataSourceProperty("prepStmtCacheSqlLimit",true);
        config.addDataSourceProperty("useServerPrepStmts",true);
        config.addDataSourceProperty("useLocalSessionState",true);
        config.addDataSourceProperty("rewriteBatchedStatements",true);
        config.addDataSourceProperty("cacheResultSetMetadata",true);
        config.addDataSourceProperty("cacheServerConfiguration",true);
        config.addDataSourceProperty("elideSetAutoCommits",true);
        config.addDataSourceProperty("maintainTimeStats",false);
        return new HikariDataSource(config);
    }
    public static PersistedJDBCProvider createPersistedProvider(String jdbcDriver, String connectionString, String username, String password){
        return new PersistedDBCProviderImpl(jdbcDriver,connectionString,username,password);
    }
    public static PersistedJDBCProvider mysqlPersistedProvider(String connectionString, String username, String password){
        return createPersistedProvider(JDBCDrivers.Mysql.driver(),connectionString,username,password);
    }
    public static PersistedJDBCProvider mysqlNewPersistedProvider(String connectionString, String username, String password){
        return createPersistedProvider(JDBCDrivers.Mysql_New.driver(),connectionString,username,password);
    }

}
