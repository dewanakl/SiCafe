package view;

import java.util.Scanner;

import config.Fungsi;
import controller.*;
import model.*;

public class viewKaryawan implements View {

    private String nama;
    private String peran;

    private int idTingkat;
    private int idPengguna;
    private boolean isLogout = false;

    public viewKaryawan(String nama, String peran, int idTingkat, int idPengguna) {
        // super.sc = new Scanner(System.in);
        // super.db = new DBPgsql();
        this.nama = nama;
        this.peran = peran;
        this.idTingkat = idTingkat;
        this.idPengguna = idPengguna;
        this.mainView();
    }

    @Override
    public void showMenu() {
        Fungsi.clearScreen();
        System.out.println("hi, " + this.nama);
        System.out.println("Pilihan sebagai " + this.peran + "\n");
        System.out.println("1. Karyawan ");
        System.out.println("2. Lihat Transaksi ");
        System.out.println("3. Lihat Daftar Menu ");
        System.out.println("0. Logout ");
        System.out.print("Masukkan Pilihan (0,1,2,3):");
    }

    @Override
    public int getInput() {
        // String s = super.sc.next();
        // int input;
        // try {
        // input = Integer.parseInt(s);
        // } catch (NumberFormatException e) {
        // input = 0;
        // }
        // return input;
        return 0;
    }

    public void mainView() {
        while (!isLogout) {
            showMenu();
            switch (getInput()) {
            case 1:
                System.out.println("karyawan");
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