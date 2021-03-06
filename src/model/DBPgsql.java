package model;

import config.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class DBPgsql {
    private String host = Config.DB_HOST;
    private String user = Config.DB_USER;
    private String pass = Config.DB_PASS;
    private String port = Config.DB_PORT;
    private String db_name = Config.DB_NAME;
    private Connection dbh;
    private ArrayList<String> listField;
    private ArrayList<String> listData;

    public DBPgsql() {
        String dsn = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.db_name;
        try {
            this.dbh = DriverManager.getConnection(dsn, this.user, this.pass);
        } catch (SQLException ex) {
            Fungsi.Ekspektasi(ex.toString());
        }
        this.newArray();
    }

    // new array, void
    private void newArray() {
        this.listField = new ArrayList<String>();
        this.listData = new ArrayList<String>();
    }

    // get data array, boolean
    public boolean getData(String query, Object[] x) {
        this.listData.clear();
        this.listField.clear();
        try {
            PreparedStatement pst = this.dbh.prepareStatement(
                    query,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            if (x != null) {
                int num = 1;
                for (Object o : x) {
                    if (o instanceof Integer) {
                        pst.setInt(num, Integer.valueOf(o.toString()));
                    } else if (o instanceof String) {
                        pst.setString(num, String.valueOf(o));
                    }
                    num++;
                }
            }
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int kol = rsmd.getColumnCount();
            int idx = 0;
            while (rs.next()) {
                for (int i = 0; i < kol; i++) {
                    String namaKol = rsmd.getColumnLabel(i + 1);
                    if (idx == 0) {
                        this.listField.add(namaKol);
                    }
                    this.listData.add((rs.getString(namaKol)));
                }
                idx++;
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    // create or update or delete, boolean
    public boolean CUD(String query, Object[] x) {
        this.listData.clear();
        this.listField.clear();
        try {
            PreparedStatement pst = this.dbh.prepareStatement(
                    query,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            if (x != null) {
                int num = 1;
                for (Object o : x) {
                    if (o instanceof Integer) {
                        pst.setInt(num, Integer.valueOf(o.toString()));
                    } else if (o instanceof String) {
                        pst.setString(num, String.valueOf(o));
                    }
                    num++;
                }
            }
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public ArrayList<String> getListKolom() {
        return listField;
    }

    public ArrayList<String> getListData() {
        return listData;
    }
}