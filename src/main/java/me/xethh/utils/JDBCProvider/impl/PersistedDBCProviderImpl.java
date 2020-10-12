package me.xethh.utils.JDBCProvider.impl;

import me.xethh.utils.JDBCProvider.PersistedJDBCProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class PersistedDBCProviderImpl extends ImmutableJDBCProvider implements PersistedJDBCProvider {
    protected Connection _conn;

    public PersistedDBCProviderImpl(String jdbcDriver, String  connectionString, String username, String password){
        super(jdbcDriver,connectionString,username,password);
    }

    @Override
    public Connection getConnection(){
        logger.info("Start obtaining persisted jdbc connection");
        try {
            if(_conn!=null && !_conn.isClosed() && _conn.isValid(3)){
                return _conn;
            }
            else{
                _conn = super.getConnection();
                return _conn;
            }
        } catch (SQLException e) {
            throw new PersistedJDBCProviderException("Error while obtaining existing connection info",e);
        }
        catch (JDBCProviderException ex){
            throw ex;
        }
    }

    @Override
    public boolean clearConnection() {
        logger.info("Start reset persisted jdbc connection");
        if(_conn==null){
            return true;
        }
        else {
            try {
                _conn.close();
            } catch (SQLException e) {
                throw new PersistedJDBCProviderException("Fail to close connection",e);
            }
            finally {
                _conn=null;
            }
        }
        return false;
    }

    public static class PersistedJDBCProviderException extends RuntimeException{
        public PersistedJDBCProviderException(String message, Throwable originException){
            super(message,originException);
        }
    }
}
