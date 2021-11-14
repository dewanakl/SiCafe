package view;

import java.util.Scanner;

import config.Fungsi;
import controller.*;
import model.*;

public class viewAdmin extends Admin implements View {

    // private int idTingkat;
    // private int idPengguna;
    private boolean isLogout = false;

    public viewAdmin(String nama, String peran) {
        super.sc = new Scanner(System.in);
        super.db = new DBPgsql();
        super.setNama(nama);
        super.setPeran(peran);
        // this.idTingkat = idTingkat;
        // this.idPengguna = idPengguna;
        this.mainView();
    }

    @Override
    public void showMenu() {
        Fungsi.clearScreen();
        System.out.println("hi, " + super.nama);
        System.out.println("Pilihan sebagai " + super.peran + "\n");
        System.out.println("1. Karyawan ");
        System.out.println("2. Lihat Transaksi ");
        System.out.println("3. Lihat Daftar Menu ");
        System.out.println("0. Logout ");
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

    public void mainView() {
        while (!isLogout) {
            showMenu();
            switch (getInput()) {
            case 1:
                super.mainViewKaryawan();
                break;
            case 2:
                System.out.println("transaksi");
                break;
            case 5:
                isLogout = true;
                break;
            default:
                Fungsi.backToMenu("salah input !");
            }
        }
    }
}