package model;

import java.util.Scanner;

import config.Fungsi;

public abstract class Person {
    private Scanner sc;

    public abstract void setNama(String nama);

    public abstract void setPeran(String peran);

    public abstract boolean isLogout();

    protected void profil(int idPengguna) {
        DBPgsql db = new DBPgsql();
        this.sc = new Scanner(System.in);

        Fungsi.clearScreen();
        Object[] x = new Object[] { idPengguna };
        db.getData("SELECT nama, username, password FROM pengguna WHERE id_pengguna = ?", x);
        Fungsi.displayTabel(db.getListKolom(), db.getListData());
        System.out.println("1. ubah nama\n2. ubah password\n3. kembali");
        System.out.print(">> ");
        String s = this.sc.next();
        int input;
        try {
            input = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            input = 0;
        }
        switch (input) {
            case 1:
                System.out.println("Edit nama");
                this.sc.nextLine();
                System.out.print("Masukkan nama : ");
                String nama = this.sc.nextLine();
                String updatename = "update pengguna set nama = ? where id_pengguna = ?";
                Object[] xx = new Object[] { nama, idPengguna };
                if (db.CUD(updatename, xx)) {
                    Fungsi.backToMenu("berhasil mengedit");
                } else {
                    Fungsi.backToMenu("error !");
                }
                break;
            case 2:
                System.out.println("Edit password");
                this.sc.nextLine();
                System.out.print("Masukkan password: ");
                String pswd1 = this.sc.nextLine();
                System.out.print("Ulangi password: ");
                String pswd2 = this.sc.nextLine();
                if (pswd1.equals(pswd2)) {
                    String updatepass = "update pengguna set password = ? where id_pengguna = ?";
                    Object[] xxx = new Object[] { pswd2, idPengguna };
                    if (db.CUD(updatepass, xxx)) {
                        Fungsi.backToMenu("berhasil mengedit");
                    } else {
                        Fungsi.backToMenu("error !");
                    }
                } else {
                    Fungsi.backToMenu("password tidak sama", 2);
                }
                break;
            default:
                break;
        }
    }
}
