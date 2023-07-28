package main.java.jp.posl.itemTracker;

import java.awt.List;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbControler {
    private static String address = ""; // "jdbc:postgresql://<接続先DBサーバのIP or ホスト名>:<DBのポート番号>/<DB名>";
    private static String user = "";
    private static String password = "";

    public static boolean setupConnection(String address, String user, String password) {
        return true; // for debug
        // try{
        //     DriverManager.getConnection(address, user, password);
        // } catch (SQLException e) {
        //     return false;
        // }
        // setVariables(address, user, password);
        // return true;
    }

    public static void setVariables(String address, String user, String password) {
        DbControler.address = address;
        DbControler.user = user;
        DbControler.password = password;
    }

    public static String[][] getItems(String itemType) {
        // TODO: Implement
        return new String[][]{
            {"pc1", "pc", "macbook pro 13inch", "蔵元", "科研費B", "鵜林", "MM00HB000000296.000", "廃棄予定", "2023.09.09", "蔵元"}, 
            {"pc2", "pc", "macbook pro 15inch", "森田", "稲盛", "亀井", "MM00HB000000296.000", "資産シール無", "2023.09.09", "森田"}
        };
    }

    public static String[] getItem(String itemType, String itemID) {
        // TODO: Implement
        return null;
    }
}
