package com.app.APResturant.conn;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import es.dmoral.toasty.Toasty;

public class  DB_CON {
    private static final String LOG = "DEBUG";
    private static String ip = "";
    private static String port = "";
    private static String classs = "net.sourceforge.jtds.jdbc.Driver";
    private static String db = "";
    private static String un = "";
    private static String password = "";


    private static Context context;


    public static Connection CONN(){
        Connection conn = null;
        String ConnURL = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip +":"+port+";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
          //  Toasty.success(context, "Succss" ,Toasty.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Log.d(LOG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.d(LOG, e.getMessage());
        }
        return conn;
    }
}
