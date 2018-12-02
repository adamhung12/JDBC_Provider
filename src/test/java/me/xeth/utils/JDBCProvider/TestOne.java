package me.xeth.utils.JDBCProvider;

import me.xethh.utils.JDBCProvider.ImmutableJDBCProvider;
import me.xethh.utils.JDBCProvider.JDBCProvider;
import me.xethh.utils.JDBCProvider.JDBCProviderFactory;
import me.xethh.utils.JDBCProvider.PersistedJDBCProvider;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TestOne {
    public static void main(String[] args){
        org.slf4j.Logger logger = LoggerFactory.getLogger(ImmutableJDBCProvider.class);
        Logger log = Logger.getLogger("my.logger");
        log.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);

        JDBCProvider conn = JDBCProviderFactory.mysqlProvider("jdbc:mysql://localhost:3306/explorer_playgroup?useUnicode=yes&amp&characterEncoding=UTF-8", "explorer_playgroup", "explorer_playgroup");
        try {
            Statement stmt = conn.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("show tables");
            while(rs.next()){
//                logger.info(rs.getString(1));
            }
            logger.info("Compare one time connection");
            logger.info(conn.getConnection().toString());
            logger.info(conn.getConnection().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PersistedJDBCProvider conn2 = JDBCProviderFactory.mysqlPersistedProvider("jdbc:mysql://localhost:3306/explorer_playgroup?useUnicode=yes&amp&characterEncoding=UTF-8", "explorer_playgroup", "explorer_playgroup");
        try {
            logger.info("Compare persisted connection");
            logger.info(conn2.getConnection().toString());
            logger.info(conn2.getConnection().toString());
            conn2.getConnection().close();
            logger.info(conn2.getConnection().toString());
            logger.info(conn2.getConnection().toString());
            conn2.clearConnection();
            logger.info(conn2.getConnection().toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
