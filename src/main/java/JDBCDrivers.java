public enum  JDBCDrivers {
    Mysql("com.mysql.jdbc.Driver"),Mysql_New("com.mysql.cj.jdbc.Driver");
    JDBCDrivers(String driverName){
        this._driver=driverName;
    }
    private String _driver;

    public String driver(){return _driver;}

}
