package controller;

public class Makanan extends Barang {

    Makanan() {
        super();
    }

    public boolean inputBarang(String nama, int price) {
        String query = "insert into daftar_menu(nama, harga, kategori) values (?, ?, 'makanan')";
        Object[] x = new Object[] { nama, price };
        if (super.db.CUD(query, x)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editBarang(String nama, int price, int idMakanan) {
        String query = "update daftar_menu set nama = ?, harga = ?, kategori = 'makanan' where id_daftar_menu = ?";
        Object[] x = new Object[] { nama, price, idMakanan };
        if (super.db.CUD(query, x)) {
            return true;
        } else {
            return false;
        }
    }
}
