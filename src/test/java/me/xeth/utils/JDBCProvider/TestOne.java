package me.xeth.utils.JDBCProvider;

import me.xethh.utils.JDBCProvider.ConnectionString;
import me.xethh.utils.JDBCProvider.JDBCProvider;
import me.xethh.utils.JDBCProvider.JDBCProviderFactory;
import me.xethh.utils.JDBCProvider.PersistedJDBCProvider;
import me.xethh.utils.JDBCProvider.impl.ImmutableJDBCProvider;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.Assert.*;

public class TestOne {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(ImmutableJDBCProvider.class);
    static Logger log = Logger.getLogger("my.logger");
    static {
        log.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);
    }

    @Test
    public void testConnectionStringBuilder(){
        String str = ConnectionString.Builder()
                .host("localhost:3306")
                .name("testing")
                .useUnicode()
                .utf8Encoding()
                .build();

        assertEquals(str, "jdbc:mysql://localhost:3306/testing?useUnicode=yes&characterEncoding=UTF8");

    }

    @Test
    public void testConnection(){
        JDBCProvider conn = JDBCProviderFactory.mysqlProvider(
                ConnectionString.Builder().host("localhost:3306").name("testing").useUnicode().utf8Encoding().serverTimezoneP8().build()
                , "testing", "testing");
        try {
            logger.info("Compare one time connection");
            logger.info(conn.getConnection().toString());
            logger.info(conn.getConnection().toString());
            Statement stmt = conn.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select 1");
            while(rs.next()){
                assertTrue(rs.getInt(1)==1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testConnectionPersisted(){
        JDBCProvider conn = JDBCProviderFactory.mysqlProvider(
                ConnectionString.Builder().host("localhost:3306").name("testing").useUnicode().utf8Encoding().serverTimezoneP8().build()
                , "testing", "testing"
        );
        String conn1Str = conn.getConnection().toString();
        String conn2Str = conn.getConnection().toString();
        logger.info("Compare non persisted connection");
        assertNotEquals(conn1Str,conn2Str);
        logger.info(conn.getConnection().toString());
        logger.info(conn.getConnection().toString());

        PersistedJDBCProvider conn2 = JDBCProviderFactory.mysqlPersistedProvider(
                ConnectionString.Builder().host("localhost:3306").name("testing").useUnicode().utf8Encoding().serverTimezoneP8().build()
                , "testing", "testing");
        try {
            logger.info("Compare persisted connection");
            String conn2_1String = conn2.getConnection().toString();
            String conn2_2String = conn2.getConnection().toString();
            assertEquals(conn2_1String,conn2_2String);
            conn2.getConnection().close();
            String conn2_3String = conn2.getConnection().toString();
            assertNotEquals(conn2_1String,conn2_3String);
            conn2.clearConnection();
            String conn2_4String = conn2.getConnection().toString();
            assertNotEquals(conn2_1String,conn2_4String);
            assertNotEquals(conn2_3String,conn2_4String);
            logger.info(conn2.getConnection().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
