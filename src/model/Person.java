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
        db.getData("SELECT nama, username, password FROM pengguna WHERE id_pengguna = " + idPengguna);
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
                String updatename = "update pengguna set nama = '%s' where id_pengguna = %s";
                updatename = String.format(updatename, nama, idPengguna);
                if (db.CUD(updatename)) {
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
                    String updatepass = "update pengguna set password = '%s' where id_pengguna = %s";
                    updatepass = String.format(updatepass, pswd2, idPengguna);
                    if (db.CUD(updatepass)) {
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
