package me.xethh.utils.JDBCProvider;

import java.sql.Connection;

public interface JDBCProvider {
    Connection getConnection();
}
