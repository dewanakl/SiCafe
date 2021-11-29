package controller;

import model.*;
import config.*;

import java.util.Scanner;

public class Login {
    protected String nama;
    protected String peran;
    protected int idTingkat;
    protected int idPengguna;
    protected DBPgsql db;
    protected Scanner sc;

    protected boolean loginForm() {
        System.out.print("Masukkan username: ");
        String namaUser = this.sc.next();
        System.out.print("Masukkan password: ");
        String pswd = this.sc.next();
        boolean isLogin = this.loginUser(namaUser, pswd);
        if (!isLogin) {
            Fungsi.backToMenu("username / password salah !");
        }
        return isLogin;
    }

    protected boolean loginUser(String username, String password) {
        boolean isLogin = false;
        this.idPengguna = 0;
        this.nama = null;
        this.idTingkat = 0;
        this.peran = null;
        String query = "select pengguna.id_pengguna, pengguna.nama, tingkat.id_tingkat, tingkat.hak_akses\n"
                + "from pengguna inner join tingkat on(pengguna.tingkat_id_tingkat = tingkat.id_tingkat)\n"
                + "where pengguna.username = '%s' and pengguna.password = '%s'";
        query = String.format(query, username, password);
        this.db.getData(query);
        if (this.db.getListData().isEmpty()) {
            isLogin = false;
        } else {
            this.idPengguna = Integer.valueOf(this.db.getListData().get(0).toString());
            this.nama = this.db.getListData().get(1).toString();
            this.idTingkat = Integer.valueOf(this.db.getListData().get(2).toString());
            this.peran = this.db.getListData().get(3).toString();
            isLogin = true;
        }
        return isLogin;
    }

    protected void setup() {
        Fungsi.clearScreen();
        System.out.println("Setup Aplikasi SiCafe\n");
        System.out.println("Silahkan input nama, username, dan password\n");
        System.out.print("Masukkan nama : ");
        String nama = this.sc.nextLine();
        System.out.print("Masukkan username: ");
        String namaUser = this.sc.nextLine();
        System.out.print("Masukkan password: ");
        String pswd = this.sc.nextLine();
        String query = "insert into pengguna(nama, username, password, tingkat_id_tingkat)\n"
                + "values ('%s', '%s', '%s', 1)";
        query = String.format(query, nama, namaUser, pswd);
        if (this.db.CUD(query)) {
            Fungsi.backToMenu("behasil setup !");
        } else {
            Fungsi.backToMenu("gagal setup !");
        }
    }

    protected boolean checkAdmin() {
        String query = "SELECT * FROM pengguna WHERE tingkat_id_tingkat = 1";
        this.db.getData(query);
        if (this.db.getListData().size() > 0) {
            return false;
        }
        return true;
    }
}
