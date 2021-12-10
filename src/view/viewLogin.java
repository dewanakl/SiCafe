package view;

import model.*;
import config.*;
import controller.*;

import java.util.Scanner;

public class viewLogin extends Login implements View {

    Setup st;

    public viewLogin() {
        super.db = new DBPgsql();
        super.sc = new Scanner(System.in);
        this.st = new Setup();
    }

    @Override
    public void showMenu() {
        Fungsi.clearScreen();
        System.out.println("Selamat datang di SiCafe\nSilahkan login terlebih dahulu");
        System.out.println("1. Login\n2. Exit");
        System.out.print(">> ");
    }

    @Override
    public int getInput() {
        String s = super.sc.next();
        int input;
        try {
            input = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            input = 0;
        }
        return input;
    }

    public void vMain() {
        this.st.check();
        while (true) {
            Fungsi.clearScreen();
            this.showMenu();
            switch (this.getInput()) {
                case 1:
                    if (super.loginForm()) {
                        switch (super.idTingkat) {
                            case 1:
                                viewAdmin va = new viewAdmin();
                                va.mainView(super.nama, super.peran);
                                break;
                            case 2:
                                viewKaryawan vk = new viewKaryawan();
                                vk.mainView(super.nama, super.peran, super.idTingkat, super.idPengguna);
                                break;
                            default:
                                Fungsi.backToMenu("something wrong !");
                        }
                    }
                    break;
                case 2:
                    Fungsi.Ekspektasi("exit");
                default:
                    Fungsi.backToMenu("salah input !", 2);
            }
        }
    }
}