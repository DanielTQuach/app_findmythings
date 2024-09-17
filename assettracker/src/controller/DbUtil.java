package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {
    private static final String URL = "jdbc:sqlite:appdatabase.db"; 

    static {
        initializeDatabase();
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private static void initializeDatabase() {
        String sql_cat = "CREATE TABLE IF NOT EXISTS categories ("
                + "	catID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "	cat_name TEXT NOT NULL"
                + ");";
        String sql_loc = "CREATE TABLE IF NOT EXISTS locations ("
                + "	locID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "	loc_name TEXT NOT NULL,"
                + "	loc_desc TEXT"
                + ");";
        String sql_asset = "CREATE TABLE IF NOT EXISTS assets ("
        		+ "	assetID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "	asset_name TEXT NOT NULL,"
                + "	asset_loc TEXT NOT NULL,"
                + "	asset_cat TEXT NOT NULL,"
                + "	date_purchased TEXT,"
                + "	warranty_exp TEXT,"
                + "	asset_desc TEXT,"
                + "	purchase_value REAL,"
                + "	FOREIGN KEY(asset_loc) REFERENCES locations(locID),"
                + " FOREIGN KEY(asset_cat) REFERENCES categories(catID)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql_cat);
            stmt.execute(sql_loc);
//            stmt.execute(sql_asset);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
