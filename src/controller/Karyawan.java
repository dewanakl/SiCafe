package controller;

import model.*;
import config.*;

import java.util.Scanner;

public class Karyawan extends Person {

    protected Scanner sc;
    protected DBPgsql db;

    protected String nama;
    protected String peran;
    protected int idTingkat;
    protected int idPengguna;

    private Barang ba = new Barang();
    private Makanan ma = new Makanan();
    private Minuman mi = new Minuman();

    @Override
    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public void setPeran(String peran) {
        this.peran = peran;
    }

    @Override
    public boolean isLogout() {
        Fungsi.clearScreen();
        System.out.println("Ingin logout ?\n1. Logout\n2. Batal");
        System.out.print(">> ");
        if (this.getInput() == 1) {
            this.nama = null;
            this.peran = null;
            this.idTingkat = 0;
            this.idPengguna = 0;
            return true;
        }
        return false;
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
            Fungsi.backToMenu("error tak terduga");
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

    protected void mainViewDaftarMenu() {
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
