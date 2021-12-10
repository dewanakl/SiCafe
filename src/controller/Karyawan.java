package controller;

import model.*;
import config.*;

import java.util.Scanner;

public class Karyawan extends Person {

    protected Scanner sc;
    protected DBPgsql db;

    protected String nama;
    protected String peran;
    protected int idTingkat;
    protected int idPengguna;

    private Menu mn = new Menu();

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
        System.out.println("Ingin logout ?\n1. Logout\n2. Batal");
        System.out.print(">> ");
        if (this.getInput() == 1) {
            this.nama = null;
            this.peran = null;
            this.idTingkat = 0;
            this.idPengguna = 0;
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

    protected void transaksiBaru() {
        Transaksi tr = new Transaksi();
        tr.tambahTransaksi(this.idPengguna);
    }

    protected void mainViewDaftarMenu() {
        this.mn.daftarMenu();
    }

    protected void showProfil() {
        super.profil(this.idPengguna);
    }

    protected void riwayatTransaksi() {
        Transaksi tr = new Transaksi();
        while (true) {
            Fungsi.clearScreen();
            System.out.println("Riwayat Transaksi");
            tr.showTransaksiByPengguna(this.idPengguna);
            System.out.println("1. kembali");
            System.out.print(">> ");
            if (getInput() == 1) {
                break;
            }
        }
    }
}
