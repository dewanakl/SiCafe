package view;

import java.util.Scanner;

import config.Fungsi;
import controller.*;
import model.*;

public class viewAdmin extends Admin implements View {

    private boolean isLogout = false;

    public viewAdmin() {
        super.sc = new Scanner(System.in);
        super.db = new DBPgsql();
        super.nama = null;
        super.peran = null;
    }

    @Override
    public void showMenu() {
        Fungsi.clearScreen();
        System.out.println("hi, " + super.nama);
        System.out.println("Pilihan sebagai " + super.peran);
        System.out.println(Fungsi.repeatStr("-", 15));
        System.out.println("1. Karyawan\n2. Lihat Transaksi\n3. Lihat Daftar Menu\n4. Profil \n5. Logout");
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

    public void mainView(String nama, String peran) {
        super.setNama(nama);
        super.setPeran(peran);
        while (!this.isLogout) {
            showMenu();
            switch (getInput()) {
                case 1:
                    super.mainViewKaryawan();
                    break;
                case 2:
                    super.lihatTransaksi();
                    break;
                case 3:
                    super.lihatDaftarMenu();
                    break;
                case 4:
                    super.showProfil();
                    break;
                case 5:
                    this.isLogout = super.isLogout();
                    break;
                default:
                    Fungsi.backToMenu("salah input !", 2);
            }
        }
    }
}