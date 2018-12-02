package me.xethh.utils.JDBCProvider;

public interface PersistedJDBCProvider extends JDBCProvider{
    boolean clearConnection();
}
