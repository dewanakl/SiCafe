package controller;

import java.util.Scanner;

import config.Fungsi;
import model.DBPgsql;

public class Transaksi {

    private DBPgsql db;
    private Scanner sc;
    private Menu mn;

    private String namaPelanggan;

    Transaksi() {
        this.db = new DBPgsql();
        this.sc = new Scanner(System.in);
        this.mn = new Menu();
    }

    protected int getInput() {
        String s = this.sc.next();
        int input;
        try {
            input = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            input = 0;
        }
        return input;
    }

    public void showTransaksi() {
        this.db.getData("select * from transaksi");
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
    }

    public void showTransaksi(boolean detail) {
        if (detail) {
            this.db.getData("select * from transaksi");
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
        } else {
            Fungsi.backToMenu("require param boolean true");
        }
    }

    private void showTransaksi(int id_transaksi) {
        this.db.getData(
                "SELECT daftar_menu.nama, daftar_menu.harga, daftar_menu.kategori FROM pesanan join daftar_menu\n" +
                        "on(pesanan.daftar_menu_id_daftar_menu = daftar_menu.id_daftar_menu)\n" +
                        "WHERE pesanan.transaksi_id_transaksi = " + id_transaksi);
        if (this.db.getListData().isEmpty()) {
            System.out.println();
            System.out.println("data belum ada");
        } else {
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
        }
    }

    private void formTransaksi() {
        Fungsi.clearScreen();
        System.out.println("Transaksi Baru");
        System.out.println();
        System.out.print("Masukkan nama pembeli : ");
        this.namaPelanggan = this.sc.nextLine();
    }

    private void formPesan(int id_transaksi) {
        Fungsi.clearScreen();
        mn.lihatMenu();
        System.out.print("Masukkan id menu : ");
        int id = this.getInput();
        this.db.getData("SELECT * FROM daftar_menu WHERE id_daftar_menu = " + id);
        if (this.db.getListData().isEmpty()) {
            Fungsi.backToMenu("data tidak ada / salah input");
        } else {
            String query = "insert into pesanan(transaksi_id_transaksi, daftar_menu_id_daftar_menu)\n"
                    + "values(%s, %s)";
            query = String.format(query, id_transaksi, id);
            this.db.CUD(query);
        }
    }

    public void tambahTransaksi(int idKaryawan) {
        this.formTransaksi();
        String query = "insert into transaksi(nama_pelanggan, dibayarkan, pengguna_id_pengguna)\n"
                + "values('%s', 0, %s) RETURNING id_transaksi";
        query = String.format(query, this.namaPelanggan, idKaryawan);
        if (this.db.getData(query)) {
            String id = this.db.getListData().get(0);
            int id_transaksi = Integer.parseInt(id);
            Boolean kembali = false;
            while (!kembali) {
                Fungsi.clearScreen();
                System.out.println("Daftar Menu Sementara");
                this.showTransaksi(id_transaksi);
                System.out.println();
                System.out.println("1. Tambah Menu\n2. sudah cukup");
                System.out.print(">> ");
                switch (getInput()) {
                    case 1:
                        this.formPesan(id_transaksi);
                        break;
                    case 2:
                        kembali = true;
                        break;
                    default:
                        Fungsi.backToMenu("salah input", 1);
                        break;
                }
            }
            System.out.print("Masukkan harga dibayarkan Rp. ");
            String harga = this.sc.next();
            int dibayarkan = Integer.parseInt(harga.replace(".", ""));
            String update = "update transaksi set dibayarkan = %s where id_transaksi = %s";
            update = String.format(update, dibayarkan, id_transaksi);
            if (this.db.CUD(update)) {
                Fungsi.backToMenu("berhasil menambahkan");
            } else {
                Fungsi.backToMenu("server error 2");
            }
        } else {
            Fungsi.backToMenu("server error");
        }
    }

    // private void nota(){
    // System.out.println("Atas Nama: " );
    // System.out.println("Alamat: " + "\n");
    // System.out.println("Keranjang Belanja:");
    // System.out.println("====================================");
    // int totalharga = 0;
    // double totalberat = 0;
    // int n = 0;
    // while (n < orang.getitemBarang().size()) {
    // System.out.println(orang.getnamaBarang().get(n) + " (" +
    // orang.gethargaBarang().get(n) + " x "
    // + orang.getitemBarang().get(n) + ")");
    // System.out.println("Rp" + orang.gethargaBarang().get(n) *
    // orang.getitemBarang().get(n));
    // System.out.println();
    // totalharga = totalharga + (orang.gethargaBarang().get(n) *
    // orang.getitemBarang().get(n));
    // totalberat = totalberat + orang.getberatBarang().get(n);
    // n++;
    // }
    // System.out.println("====================================");
    // System.out.println("Subtotal: " + totalharga + "\n");
    // }
}
