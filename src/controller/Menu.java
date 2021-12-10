package controller;

import java.util.Scanner;

import config.Fungsi;
import model.DBPgsql;

public class Menu {

    private Scanner sc;
    private Barang ba;
    private Makanan ma;
    private Minuman mi;
    private DBPgsql db;

    Menu() {
        this.sc = new Scanner(System.in);
        this.ba = new Barang();
        this.ma = new Makanan();
        this.mi = new Minuman();
        this.db = new DBPgsql();
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

    private void tambahMenu() {
        Fungsi.clearScreen();
        System.out.println("Tambah Menu Baru");
        this.sc.nextLine();
        System.out.print("Masukkan nama : ");
        String nama = this.sc.nextLine();
        System.out.print("Masukkan harga: Rp ");
        String rawPrice = this.sc.nextLine();
        int price = Integer.parseInt(rawPrice.replace(".", ""));
        System.out.println("1. makanan\n2. minuman");
        System.out.print("Masukkan kategori: ");
        boolean result;
        switch (this.getInput()) {
            case 1:
                result = this.ma.inputBarang(nama, price);
                break;
            case 2:
                result = this.mi.inputBarang(nama, price);
                break;
            default:
                result = false;
                break;
        }
        if (result) {
            Fungsi.backToMenu("behasil menambahkan !");
        } else {
            Fungsi.backToMenu("input kategori dengan benar / salah input");
        }
    }

    private void editMenu() {
        System.out.print("Masukan id_daftar_menu : ");
        int idEdit = this.getInput();
        Fungsi.clearScreen();
        if (this.ba.cekBarang(idEdit)) {
            Fungsi.backToMenu("data tidak ada / salah input");
        } else {
            this.ba.lihatBarang(idEdit);
            System.out.println("1. edit\n2. batal");
            System.out.print(">> ");
            int sure = this.getInput();
            if (sure == 1) {
                System.out.println("Edit Menu");
                this.sc.nextLine();
                System.out.print("Masukkan nama : ");
                String nama = this.sc.nextLine();
                System.out.print("Masukkan harga: Rp ");
                String rawPrice = this.sc.nextLine();
                int price = Integer.parseInt(rawPrice.replace(".", ""));
                System.out.println("1. makanan\n2. minuman");
                System.out.print("Masukkan kategori: ");
                boolean result;
                switch (this.getInput()) {
                    case 1:
                        result = this.ma.editBarang(nama, price, idEdit);
                        break;
                    case 2:
                        result = this.mi.editBarang(nama, price, idEdit);
                        break;
                    default:
                        result = false;
                        break;
                }
                if (result) {
                    Fungsi.backToMenu("berhasil mengedit");
                } else {
                    Fungsi.backToMenu("input kategori dengan benar / salah input");
                }
            } else {
                Fungsi.backToMenu("batal mengedit");
            }
        }
    }

    private void hapusMenu() {
        System.out.print("Masukan id_daftar_menu : ");
        int idHapus = this.getInput();
        Fungsi.clearScreen();
        if (this.ba.cekBarang(idHapus)) {
            Fungsi.backToMenu("data tidak ada / salah input");
        } else {
            this.ba.lihatBarang(idHapus);
            System.out.println("1. hapus\n2. batal");
            System.out.print(">> ");
            int sure = this.getInput();
            if (sure == 1) {
                if (this.db.CUD("delete from daftar_menu where id_daftar_menu = " + idHapus)) {
                    Fungsi.backToMenu("berhasil menghapus");
                } else {
                    Fungsi.backToMenu("gagal menghapus");
                }
            } else {
                Fungsi.backToMenu("batal menghapus");
            }
        }
    }

    public void lihatMenu() {
        this.ba.lihatBarang();
    }

    public void daftarMenu() {
        boolean kembali = false;
        while (!kembali) {
            Fungsi.clearScreen();
            System.out.println("Data Daftar Menu");
            this.ba.lihatBarang();
            System.out.println("1. tambah\n2. edit\n3. hapus\n4. kembali");
            System.out.print(">> ");
            switch (getInput()) {
                case 1:
                    this.tambahMenu();
                    break;
                case 2:
                    this.editMenu();
                    break;
                case 3:
                    this.hapusMenu();
                    break;
                case 4:
                    kembali = true;
                    break;
                default:
                    Fungsi.backToMenu("salah input !");
            }
        }
    }
}
