import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCProvider {

    String jdbcDriver;
    String connectionString;
    String username;
    String password;
    protected JDBCProvider(String jdbcDriver, String  connectionString, String username, String password){
        this.jdbcDriver = jdbcDriver;
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection(){
        Logger logger = LoggerFactory.getLogger(JDBCProvider.class);
        logger.info("Start obtaining jdbc connection");
        try{
            logger.info("Driver name: "+jdbcDriver);
            logger.debug("Connection string: "+connectionString);
            logger.debug("Username: "+connectionString);
            Class.forName(jdbcDriver).newInstance();
            return DriverManager.getConnection(connectionString,username,password);
        }
        catch (ClassNotFoundException ex){
            throw new JDBCProviderException("Driver class not found, maybe the JDBC jar not included",ex);
        }
        catch (Exception ex){
            throw new JDBCProviderException("Fail to create connection",ex);
        }
    }

    public static class JDBCProviderException extends RuntimeException{
        public JDBCProviderException(String message, Throwable originException){
            super(message,originException);
        }
    }
}
