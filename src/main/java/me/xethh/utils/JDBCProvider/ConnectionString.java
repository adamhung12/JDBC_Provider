package me.xethh.utils.JDBCProvider;

import java.util.HashMap;
import java.util.Map;

public class ConnectionString {
    public static class DBPropertyChooser {
        private DBNameChooser dBnameChooser;
        private Map<String,String> properties = new HashMap<>();
        protected DBPropertyChooser(DBNameChooser dBnameChooser){
            this.dBnameChooser = dBnameChooser;
        }

        public DBPropertyChooser prop(String name, String value){
            this.properties.put(name, value);
            return this;
        }

        public DBPropertyChooser useUnicode(){
            return prop("useUnicode","yes");
        }
        public DBPropertyChooser utf8Encoding(){
            return prop("characterEncoding","UTF8");
        }
        public DBPropertyChooser serverTimezoneUTC(){
            return serverTimezone("UTC");
        }
        public DBPropertyChooser serverTimezoneP8(){
            return serverTimezone("Hongkong");
        }
        public DBPropertyChooser serverTimezone(String timezoneStr){
            return prop("serverTimezone",timezoneStr);
        }

        public String build(){
            String host = dBnameChooser.dbHostChooser.host;
            String name = dBnameChooser.name;

            StringBuilder connStr = new StringBuilder("jdbc:mysql://" + host + "/" + name + (properties.size() == 0 ? "" : "?"));

            for(Map.Entry<String, String> entry : properties.entrySet()){
                connStr.append(entry.getKey()).append("=").append(entry.getValue()+"&");
            }

            return connStr.subSequence(0,connStr.length()-1).toString();
        }

    }
    public static class DBNameChooser {
        private DBHostChooser dbHostChooser;
        private String name;
        protected DBNameChooser(DBHostChooser dbHostChooser){
            this.dbHostChooser = dbHostChooser;
        }
        public DBPropertyChooser name(String name){
            this.name = name;
            return new DBPropertyChooser(this);
        }
    }
    public static class DBHostChooser {
        private String host;
        protected DBHostChooser(){
        }
        public DBNameChooser host(String host){
            this.host = host;
            return new DBNameChooser(this);
        }

    }

    public static DBHostChooser Builder(){
        return new DBHostChooser();
    }
}
