package controller;

import model.*;
import config.*;

import java.util.Scanner;

public class Login {
    protected boolean isLogin;
    protected String nama, peran;
    protected int idTingkat, idPengguna;

    protected boolean checkAdmin;

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
        this.isLogin = false;
        String query = "select pengguna.id_pengguna, pengguna.nama, tingkat.id_tingkat, tingkat.hak_akses\n"
                + "from pengguna inner join tingkat on(pengguna.tingkat_id_tingkat = tingkat.id_tingkat)\n"
                + "where pengguna.username = '%s' and pengguna.password = '%s'";
        query = String.format(query, username, password);
        this.db.getData(query);
        if (this.db.getListData().isEmpty()) {
            this.isLogin = false;
        } else {
            this.idPengguna = Integer.valueOf(this.db.getListData().get(0).toString());
            this.nama = this.db.getListData().get(1).toString();
            this.idTingkat = Integer.valueOf(this.db.getListData().get(2).toString());
            this.peran = this.db.getListData().get(3).toString();
            this.isLogin = true;
        }
        return this.isLogin;
    }

    protected void checkAdmin() {
        String query = "SELECT * FROM pengguna WHERE tingkat_id_tingkat = 1";
        this.db.getData(query);
        if (this.db.getListData().size() > 0) {
            this.checkAdmin = false;
        } else {
            this.checkAdmin = true;
        }
    }
}
