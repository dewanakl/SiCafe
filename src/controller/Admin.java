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
        this.db.getData("SELECT * FROM pengguna WHERE tingkat_id_tingkat = 2");
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
    }

    private void registerKaryawan() {
        Fungsi.clearScreen();
        System.out.println("Register Karyawan SiCafe");
        System.out.print("Masukkan nama : ");
        String nama = this.sc.nextLine();
        System.out.print("Masukkan username: ");
        String namaUser = this.sc.nextLine();
        System.out.print("Masukkan password: ");
        String pswd = this.sc.nextLine();

        String query = "insert into pengguna(nama, username, password, tingkat_id_tingkat)\n"
                + "values ('%s', '%s', '%s', 2)";
        query = String.format(query, nama, namaUser, pswd);
        this.db.CUD(query);
        Fungsi.backToMenu("behasil register !");
    }

    private void editKaryawan() {
        System.out.print("masukan id_pengguna : ");
        int idEdit = this.getInput();
        Fungsi.clearScreen();
        this.db.getData("SELECT * FROM pengguna WHERE id_pengguna = " + idEdit);
        if (this.db.getListData().isEmpty()) {
            Fungsi.backToMenu("data tidak ada / salah input");
        } else {
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
            System.out.println("1. edit\n2. batal");
            System.out.print(">> ");
            int sure = this.getInput();
            if (sure == 1) {
                Fungsi.clearScreen();
                System.out.println("edit karyawan");
                System.out.print("Masukkan nama : ");
                String nama = this.sc.nextLine();
                System.out.print("Masukkan username: ");
                String namaUser = this.sc.nextLine();
                System.out.print("Masukkan password: ");
                String pswd = this.sc.nextLine();
                String query = "update pengguna set nama = '%s', username = '%s', password = '%s' where id_pengguna = "
                        + idEdit;
                query = String.format(query, nama, namaUser, pswd);
                this.db.CUD(query);
                Fungsi.backToMenu("berhasil mengedit");
            } else {
                Fungsi.backToMenu("batal mengedit");
            }
        }
    }

    private void hapusKaryawan() {
        System.out.print("masukan id_pengguna : ");
        int idHapus = this.getInput();
        Fungsi.clearScreen();
        this.db.getData("SELECT * FROM pengguna WHERE id_pengguna = " + idHapus);
        if (this.db.getListData().isEmpty()) {
            Fungsi.backToMenu("data tidak ada / salah input");
        } else {
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
            System.out.println("1. hapus\n2. batal");
            System.out.print(">> ");
            int sure = this.getInput();
            if (sure == 1) {
                this.db.CUD("delete from pengguna where id_pengguna = " + idHapus);
                Fungsi.backToMenu("berhasil menghapus");
            } else {
                Fungsi.backToMenu("batal menghapus");
            }
        }
    }

    protected void mainViewKaryawan() {
        boolean kembali = false;
        while (!kembali) {
            Fungsi.clearScreen();
            System.out.println("Data Karyawan\n");
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
}
