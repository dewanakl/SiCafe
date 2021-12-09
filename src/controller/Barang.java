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
        this.db.getData("SELECT * FROM daftar_menu order by id_daftar_menu asc");
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData(), 2);
    }

    public void lihatBarang(int id) {
        this.db.getData("SELECT * FROM daftar_menu WHERE id_daftar_menu = " + id);
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData(), 2);
    }

    public boolean cekBarang(int id) {
        this.db.getData("SELECT * FROM daftar_menu WHERE id_daftar_menu = " + id);
        if (this.db.getListData().isEmpty()) {
            return true;
        }
        return false;
    }
}
