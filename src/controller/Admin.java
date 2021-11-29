package controller;

import model.*;
import config.*;

import java.util.Scanner;

public class Admin extends Person {

    protected Scanner sc;
    protected DBPgsql db;
    protected String nama;
    protected String peran;

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
        System.out.println("ingin logout ?\n1. Logout\n2. Batal");
        System.out.print(">> ");
        if (this.getInput() == 1) {
            this.nama = null;
            this.peran = null;
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

    protected void showDataKaryawan() {
        this.db.getData(
                "SELECT id_pengguna, nama, username, password FROM pengguna WHERE tingkat_id_tingkat = 2 order by id_pengguna asc");
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
    }

    private void registerKaryawan() {
        Fungsi.clearScreen();
        System.out.println("Register Karyawan SiCafe");
        this.sc.nextLine();
        System.out.print("Masukkan nama : ");
        String nama = this.sc.nextLine();
        System.out.print("Masukkan username: ");
        String namaUser = this.sc.nextLine();
        System.out.print("Masukkan password: ");
        String pswd = this.sc.nextLine();
        String query = "insert into pengguna(nama, username, password, tingkat_id_tingkat)\n"
                + "values ('%s', '%s', '%s', 2)";
        query = String.format(query, nama, namaUser, pswd);
        if (this.db.CUD(query)) {
            Fungsi.backToMenu("behasil register !");
        } else {
            Fungsi.backToMenu("username harus berbeda");
        }
    }

    private void editKaryawan() {
        System.out.print("Masukan id_pengguna : ");
        int idEdit = this.getInput();
        Fungsi.clearScreen();
        this.db.getData("SELECT id_pengguna, nama, username, password FROM pengguna WHERE id_pengguna = " + idEdit);
        if (this.db.getListData().isEmpty()) {
            Fungsi.backToMenu("data tidak ada / salah input");
        } else {
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
            System.out.println("1. edit\n2. batal");
            System.out.print(">> ");
            int sure = this.getInput();
            if (sure == 1) {
                System.out.println("Edit karyawan");
                this.sc.nextLine();
                System.out.print("Masukkan nama : ");
                String nama = this.sc.nextLine();
                System.out.print("Masukkan username: ");
                String namaUser = this.sc.nextLine();
                System.out.print("Masukkan password: ");
                String pswd = this.sc.nextLine();
                String query = "update pengguna set nama = '%s', username = '%s', password = '%s' where id_pengguna = "
                        + idEdit;
                query = String.format(query, nama, namaUser, pswd);
                if (this.db.CUD(query)) {
                    Fungsi.backToMenu("berhasil mengedit");
                } else {
                    Fungsi.backToMenu("username harus berbeda");
                }
            } else {
                Fungsi.backToMenu("batal mengedit");
            }
        }
    }

    private void hapusKaryawan() {
        System.out.print("Masukan id_pengguna : ");
        int idHapus = this.getInput();
        Fungsi.clearScreen();
        this.db.getData("SELECT id_pengguna, nama, username, password FROM pengguna WHERE id_pengguna = " + idHapus);
        if (this.db.getListData().isEmpty()) {
            Fungsi.backToMenu("data tidak ada / salah input");
        } else {
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
            System.out.println("1. hapus\n2. batal");
            System.out.print(">> ");
            int sure = this.getInput();
            if (sure == 1) {
                if (this.db.CUD("delete from pengguna where id_pengguna = " + idHapus)) {
                    Fungsi.backToMenu("berhasil menghapus");
                } else {
                    Fungsi.backToMenu("gagal menghapus");
                }
            } else {
                Fungsi.backToMenu("batal menghapus");
            }
        }
    }

    protected void mainViewKaryawan() {
        boolean kembali = false;
        while (!kembali) {
            Fungsi.clearScreen();
            System.out.println("Data Karyawan");
            this.showDataKaryawan();
            System.out.println("1. register\n2. edit\n3. hapus\n4. kembali");
            System.out.print(">> ");
            switch (getInput()) {
            case 1:
                this.registerKaryawan();
                break;
            case 2:
                this.editKaryawan();
                break;
            case 3:
                this.hapusKaryawan();
                break;
            case 4:
                kembali = true;
                break;
            default:
                Fungsi.backToMenu("salah input !");
            }
        }
    }

    protected void lihatTransaksi() {
        boolean kembali = false;
        while (!kembali) {
            Fungsi.clearScreen();
            this.db.getData("select * from transaksi");
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
            System.out.println("1. kembali");
            System.out.print(">> ");
            if (getInput() == 1) {
                kembali = true;
                break;
            }
        }
    }

    protected void lihatDaftarMenu() {
        boolean kembali = false;
        while (!kembali) {
            Fungsi.clearScreen();
            Barang barang = new Barang();
            barang.lihatBarang();
            System.out.println("1. kembali");
            System.out.print(">> ");
            if (this.getInput() == 1) {
                kembali = true;
                break;
            }
        }
    }

    protected void mainProfil() {
        Fungsi.clearScreen();
        this.db.getData("SELECT nama, username, password FROM pengguna WHERE id_pengguna = 1");
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
        System.out.println("1. ubah\n2. kembali");
        System.out.print(">> ");
        if (this.getInput() == 1) {
            System.out.println("Edit profil");
            this.sc.nextLine();
            System.out.print("Masukkan nama : ");
            String nama = this.sc.nextLine();
            System.out.print("Masukkan password: ");
            String pswd = this.sc.nextLine();
            String query = "update pengguna set nama = '%s', password = '%s' where id_pengguna = 1";
            query = String.format(query, nama, pswd);
            if (this.db.CUD(query)) {
                Fungsi.backToMenu("berhasil mengedit");
            } else {
                Fungsi.backToMenu("error !");
            }
        }
    }
}