package view;

import model.*;
import config.*;
import controller.*;

import java.util.Scanner;

public class viewLogin extends Login implements View {

    public viewLogin() {
        super.db = new DBPgsql();
        super.sc = new Scanner(System.in);
        checkAdmin();
        if (super.checkAdmin) {
            this.setup();
        }
    }

    @Override
    public void showMenu() {
        Fungsi.clearScreen();
        System.out.println("Selamat datang di SiCafe\nSilahkan login terlebih dahulu");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print(">> ");
    }

    @Override
    public int getInput() {
        String s = this.sc.next();
        int input;
        try {
            input = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            input = 0;
        }
        return input;
    }

    private void setup() {
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
        this.db.CUD(query);
        Fungsi.backToMenu("behasil setup !");
    }

    public void mainView() {
        boolean isRunning = true;
        while (isRunning) {
            Fungsi.clearScreen();
            this.showMenu();
            int input = this.getInput();
            if (input == 1) {
                if (super.loginForm()) {
                    switch (super.idTingkat) {
                    case 1:
                        new viewAdmin(super.nama, super.peran);
                        break;
                    case 2:
                        new viewKaryawan(super.nama, super.peran, super.idTingkat, super.idPengguna);
                        break;
                    default:
                        Fungsi.backToMenu("something wrong !");
                    }
                }
            } else if (input == 2) {
                Fungsi.Exspetasi("exit");
            } else {
                Fungsi.backToMenu("salah input !");
            }
        }
    }
}
