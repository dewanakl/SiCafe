package view;

import java.util.Scanner;

import config.Fungsi;
import controller.*;
import model.*;

public class viewKaryawan extends Karyawan implements View {

    private boolean isLogout = false;

    public viewKaryawan() {
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
        System.out.println("1. Transaksi Baru\n2. Riwayat Transaksi\n3. Daftar Menu\n4. Profil \n5. Logout");
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

    public void mainView(String nama, String peran, int idTingkat, int idPengguna) {
        super.setNama(nama);
        super.setPeran(peran);
        super.idTingkat = idTingkat;
        super.idPengguna = idPengguna;

        while (!this.isLogout) {
            showMenu();
            switch (getInput()) {
                case 1:
                    super.transaksiBaru();
                    break;
                case 2:
                    super.riwayatTransaksi();
                    break;
                case 3:
                    super.mainViewDaftarMenu();
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