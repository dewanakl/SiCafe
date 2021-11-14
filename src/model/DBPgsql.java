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
    private String db_name = Config.DB_NAME;

    private Connection dbh;

    private ArrayList<String> listField;
    private ArrayList<String> listData;

    public DBPgsql() {
        listField = new ArrayList<String>();
        listData = new ArrayList<String>();

        String dsn = "jdbc:postgresql://" + this.host + ":5432/" + this.db_name;

        try {
            this.dbh = DriverManager.getConnection(dsn, this.user, this.pass);
        } catch (SQLException ex) {
            Fungsi.Exspetasi(ex.toString());
        }
    }

    // get data array, void
    public void getData(String query) {
        this.listData.clear();
        this.listField.clear();
        try {
            PreparedStatement pst = this.dbh.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int kol = rsmd.getColumnCount();
            int idx = 0;
            while (rs.next()) {
                for (int i = 0; i < kol; i++) {
                    String namaKol = rsmd.getColumnLabel(i + 1);
                    if (idx == 0) {
                        listField.add(namaKol);
                    }
                    listData.add((rs.getString(namaKol)));
                }
                idx++;
            }
        } catch (SQLException ex) {
            Fungsi.Exspetasi(ex.toString());
        }
    }

    // create or update or delete, void
    public void CUD(String query) {
        this.listData.clear();
        this.listField.clear();
        try {
            PreparedStatement pst = this.dbh.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Fungsi.Exspetasi(ex.toString());
        }
    }

    public ArrayList<String> getListKolom() {
        return listField;
    }

    public ArrayList<String> getListData() {
        return listData;
    }
}
