package model;

import java.util.Scanner;

import config.Fungsi;

public class Setup {
    Scanner sc;
    DBPgsql db;

    public Setup() {
        this.sc = new Scanner(System.in);
        this.db = new DBPgsql();
    }

    private boolean dbSetup() {
        boolean exists = this.db.CUD("drop table if exists daftar_menu, pengguna, pesanan, tingkat, transaksi;");

        boolean tmenu = this.db.CUD("CREATE TABLE daftar_menu (" +
                "id_daftar_menu  SERIAL NOT NULL," +
                "nama            VARCHAR(50) NOT NULL," +
                "harga           INTEGER NOT NULL," +
                "kategori        VARCHAR(10) NOT NULL);" +
                "ALTER TABLE daftar_menu ADD CONSTRAINT daftar_menu_pk PRIMARY KEY ( id_daftar_menu );");

        boolean tpegguna = this.db.CUD("CREATE TABLE pengguna (" +
                "id_pengguna         SERIAL NOT NULL," +
                "nama                VARCHAR(50) NOT NULL," +
                "username            VARCHAR(50) NOT NULL unique," +
                "password            VARCHAR(50) NOT NULL," +
                "tingkat_id_tingkat  INTEGER NOT NULL);" +
                "ALTER TABLE pengguna ADD CONSTRAINT pengguna_pk PRIMARY KEY ( id_pengguna );");

        boolean tpesanan = this.db.CUD("CREATE TABLE pesanan (" +
                "id_pesanan                  SERIAL NOT NULL," +
                "transaksi_id_transaksi      INTEGER NOT NULL," +
                "daftar_menu_id_daftar_menu  INTEGER NOT NULL);" +
                "ALTER TABLE pesanan ADD CONSTRAINT pesanan_pk PRIMARY KEY ( id_pesanan );");

        boolean ttingkat = this.db.CUD("CREATE TABLE tingkat (" +
                "id_tingkat  SERIAL NOT NULL," +
                "hak_akses   VARCHAR(20) NOT NULL);" +
                "ALTER TABLE tingkat ADD CONSTRAINT tingkat_pk PRIMARY KEY ( id_tingkat );");

        boolean ttransaksi = this.db.CUD("CREATE TABLE transaksi (" +
                "id_transaksi          SERIAL NOT NULL," +
                "nama_pelanggan        VARCHAR(50) NOT NULL," +
                "tanggal_pembayaran    TIMESTAMP NOT NULL DEFAULT now()," +
                "dibayarkan            INTEGER NOT NULL," +
                "pengguna_id_pengguna  INTEGER NOT NULL);" +
                "ALTER TABLE transaksi ADD CONSTRAINT transaksi_pk PRIMARY KEY ( id_transaksi );");

        boolean apengguna = this.db.CUD("ALTER TABLE pengguna\n" +
                "ADD CONSTRAINT pengguna_tingkat_fk FOREIGN KEY ( tingkat_id_tingkat )\n" +
                "REFERENCES tingkat ( id_tingkat );");

        boolean apesananMenu = this.db.CUD("ALTER TABLE pesanan\n" +
                "ADD CONSTRAINT pesanan_daftar_menu_fk FOREIGN KEY ( daftar_menu_id_daftar_menu )\n" +
                "REFERENCES daftar_menu ( id_daftar_menu );");

        boolean apesananTransaksi = this.db.CUD("ALTER TABLE pesanan\n" +
                "ADD CONSTRAINT pesanan_transaksi_fk FOREIGN KEY ( transaksi_id_transaksi )\n" +
                "REFERENCES transaksi ( id_transaksi );");

        boolean atransaksi = this.db.CUD("ALTER TABLE transaksi\n" +
                "ADD CONSTRAINT transaksi_pengguna_fk FOREIGN KEY ( pengguna_id_pengguna )\n" +
                "REFERENCES pengguna ( id_pengguna );");

        boolean itingkat = this.db.CUD("insert into tingkat(hak_akses) values ('admin'), ('karyawan');");

        return exists && tmenu && tpegguna && tpesanan && ttingkat && ttransaksi && apengguna && apesananMenu
                && apesananTransaksi && atransaksi && itingkat;
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
        if (this.db.CUD(query)) {
            Fungsi.backToMenu("behasil setup !");
        } else {
            Fungsi.backToMenu("gagal setup !");
        }
    }

    private boolean checkAdmin() {
        String query = "SELECT * FROM pengguna WHERE tingkat_id_tingkat = 1";
        this.db.getData(query);
        if (this.db.getListData().size() > 0) {
            return false;
        }
        return true;
    }

    public void check() {
        if (this.checkAdmin()) {
            if (this.dbSetup()) {
                this.setup();
            } else {
                Fungsi.backToMenu("database error", 4);
            }
        }
    }
}
