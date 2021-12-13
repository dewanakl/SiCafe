package controller;

public class Minuman extends Barang {

    Minuman() {
        super();
    }

    public boolean inputBarang(String nama, int price) {
        String query = "insert into daftar_menu(nama, harga, kategori) values (?, ?, 'minuman')";
        Object[] x = new Object[] { nama, price };
        if (super.db.CUD(query, x)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editBarang(String nama, int price, int idMakanan) {
        String query = "update daftar_menu set nama = ?, harga = ?, kategori = 'minuman' where id_daftar_menu = ?";
        Object[] x = new Object[] { nama, price, idMakanan };
        if (super.db.CUD(query, x)) {
            return true;
        } else {
            return false;
        }
    }
}
