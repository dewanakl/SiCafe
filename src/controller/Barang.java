package controller;

import config.Fungsi;
import model.DBPgsql;

public class Barang {
    protected String nama;
    protected String kategori;
    protected int harga;

    protected DBPgsql db;

    Barang() {
        this.db = new DBPgsql();
    }

    public void lihatBarang() {
        this.db.getData("SELECT * FROM daftar_menu order by id_daftar_menu asc", null);
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData(), 2);
    }

    public void lihatBarang(int id) {
        Object[] x = new Object[] { id };
        this.db.getData("SELECT * FROM daftar_menu WHERE id_daftar_menu = ?", x);
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData(), 2);
    }

    public boolean cekBarang(int id) {
        Object[] x = new Object[] { id };
        this.db.getData("SELECT * FROM daftar_menu WHERE id_daftar_menu = ?", x);
        if (this.db.getListData().isEmpty()) {
            return true;
        }
        return false;
    }
}
