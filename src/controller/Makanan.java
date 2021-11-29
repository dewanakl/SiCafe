package controller;

public class Makanan extends Barang {

    Makanan() {
        super();
    }

    public boolean inputBarang(String nama, int price) {
        String query = "insert into daftar_menu(nama, harga, kategori) values ('%s', '%s', 'makanan')";
        query = String.format(query, nama, price);
        if (super.db.CUD(query)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editBarang(String nama, int price, int idMakanan) {
        String query = "update daftar_menu set nama = '%s', harga = '%s', kategori = 'makanan' where id_daftar_menu = "
                + idMakanan;
        query = String.format(query, nama, price);
        if (super.db.CUD(query)) {
            return true;
        } else {
            return false;
        }
    }
}
