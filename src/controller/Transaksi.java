package controller;

import java.util.Scanner;

import config.Fungsi;
import model.DBPgsql;

public class Transaksi {

    private DBPgsql db;
    private Scanner sc;
    private Menu mn;

    private String namaPelanggan;

    Transaksi() {
        this.db = new DBPgsql();
        this.sc = new Scanner(System.in);
        this.mn = new Menu();
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

    public void showTransaksi() {
        this.db.getData("select * from transaksi");
        Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
    }

    public void showTransaksi(boolean detail) {
        if (detail) {
            this.db.getData("select pengguna.nama as nama_karyawan, transaksi.nama_pelanggan,\n" +
                    "TO_CHAR(transaksi.tanggal_pembayaran, 'yyyy/mm/dd HH12:MI') as tanggal_transaksi,\n" +
                    "'Rp. '|| sum(daftar_menu.harga) as total_pembayaran,\n" +
                    "count(daftar_menu.harga) as jumlah_item\n" +
                    "from transaksi join pesanan on(transaksi.id_transaksi = pesanan.transaksi_id_transaksi)\n" +
                    "join daftar_menu on(pesanan.daftar_menu_id_daftar_menu = daftar_menu.id_daftar_menu)\n" +
                    "join pengguna on(pengguna.id_pengguna = transaksi.pengguna_id_pengguna)\n" +
                    "GROUP BY transaksi.id_transaksi, pengguna.nama order by pengguna.nama");
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
        } else {
            Fungsi.backToMenu("require param boolean true");
        }
    }

    private boolean showTransaksi(int id_transaksi) {
        this.db.getData(
                "SELECT daftar_menu.nama, daftar_menu.harga, daftar_menu.kategori FROM pesanan join daftar_menu\n" +
                        "on(pesanan.daftar_menu_id_daftar_menu = daftar_menu.id_daftar_menu)\n" +
                        "WHERE pesanan.transaksi_id_transaksi = " + id_transaksi);
        if (this.db.getListData().isEmpty()) {
            System.out.println();
            System.out.println("data belum ada");
            return false;
        } else {
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
            return true;
        }
    }

    public void showTransaksiByPengguna(int id_pengguna) {
        this.db.getData(
                "select transaksi.nama_pelanggan, TO_CHAR(transaksi.tanggal_pembayaran, 'yyyy/mm/dd HH12:MI') as tanggal_transaksi,\n"
                        +
                        "count(daftar_menu.harga) as jumlah_item,\n" +
                        "'Rp. '||sum(daftar_menu.harga) as total_pembayaran,\n" +
                        "'Rp. '||transaksi.dibayarkan as total_dibayarkan\n" +
                        "from transaksi join pesanan on(transaksi.id_transaksi = pesanan.transaksi_id_transaksi)\n" +
                        "join daftar_menu on(pesanan.daftar_menu_id_daftar_menu = daftar_menu.id_daftar_menu)\n" +
                        "join pengguna on(pengguna.id_pengguna = transaksi.pengguna_id_pengguna)\n" +
                        "where pengguna.id_pengguna = " + id_pengguna
                        + " GROUP BY transaksi.id_transaksi, pengguna.nama order by transaksi.id_transaksi desc");
        if (this.db.getListData().isEmpty()) {
            System.out.println();
            System.out.println("data belum ada");
        } else {
            Fungsi.displayTabel(this.db.getListKolom(), this.db.getListData());
        }
    }

    private boolean cekDaftarmenu() {
        String query = "SELECT * FROM daftar_menu";
        this.db.getData(query);
        if (this.db.getListData().size() > 0) {
            return true;
        }
        return false;
    }

    private void formPesan(int id_transaksi) {
        Fungsi.clearScreen();
        mn.lihatMenu();
        System.out.print("Masukkan id menu : ");
        int id = this.getInput();
        this.db.getData("SELECT * FROM daftar_menu WHERE id_daftar_menu = " + id);
        if (this.db.getListData().isEmpty()) {
            Fungsi.backToMenu("data tidak ada / salah input");
        } else {
            String query = "insert into pesanan(transaksi_id_transaksi, daftar_menu_id_daftar_menu)\n"
                    + "values(%s, %s)";
            query = String.format(query, id_transaksi, id);
            this.db.CUD(query);
        }
    }

    public void tambahTransaksi(int idKaryawan) {
        if (this.cekDaftarmenu()) {
            Fungsi.clearScreen();
            System.out.println("Transaksi Baru");
            System.out.println();
            System.out.print("Masukkan nama pembeli : ");
            this.namaPelanggan = this.sc.nextLine();
            String query = "insert into transaksi(nama_pelanggan, dibayarkan, pengguna_id_pengguna)\n"
                    + "values('%s', 0, %s) RETURNING id_transaksi";
            query = String.format(query, this.namaPelanggan, idKaryawan);
            if (this.db.getData(query)) {
                String id = this.db.getListData().get(0);
                int id_transaksi = Integer.parseInt(id);
                Boolean kembali = false;
                while (!kembali) {
                    Fungsi.clearScreen();
                    System.out.println("Daftar Pesanan Sementara");
                    boolean cekTransaksi = this.showTransaksi(id_transaksi);
                    System.out.println();
                    System.out.println("1. Tambah Menu\n2. sudah cukup");
                    System.out.print(">> ");
                    switch (getInput()) {
                        case 1:
                            this.formPesan(id_transaksi);
                            break;
                        case 2:
                            if (!cekTransaksi) {
                                Fungsi.backToMenu("harus tambahkan daftar pesanan", 2);
                            } else {
                                kembali = true;
                            }
                            break;
                        default:
                            Fungsi.backToMenu("salah input", 1);
                            break;
                    }
                }
                String total = "SELECT sum(daftar_menu.harga) FROM pesanan join daftar_menu\n" +
                        "on(pesanan.daftar_menu_id_daftar_menu = daftar_menu.id_daftar_menu)\n" +
                        "WHERE pesanan.transaksi_id_transaksi = " + id_transaksi;
                this.db.getData(total);
                total = this.db.getListData().get(0);
                Boolean isLoop = true;
                while (isLoop) {
                    Fungsi.clearScreen();
                    System.out.println("Total harga pembayaran Rp. " + total);
                    System.out.print("Masukkan harga dibayarkan Rp. ");
                    String harga = this.sc.next();
                    int dibayarkan = Integer.parseInt(harga.replace(".", ""));
                    if (dibayarkan >= Integer.parseInt(total)) {
                        isLoop = false;
                        String update = "update transaksi set dibayarkan = %s where id_transaksi = %s";
                        update = String.format(update, dibayarkan, id_transaksi);
                        Fungsi.clearScreen();
                        System.out.println("Atas Nama: " + this.namaPelanggan);
                        System.out.println("Keranjang Belanja:");
                        this.showTransaksi(id_transaksi);
                        System.out.println("Subtotal: Rp." + total);
                        if (dibayarkan != Integer.parseInt(total)) {
                            System.out.println("Kebalian : Rp." + (dibayarkan - Integer.parseInt(total)));
                        }
                        System.out.println("inputkan apa saja untuk melanjutkan");
                        this.getInput();
                        if (this.db.CUD(update)) {
                            Fungsi.backToMenu("berhasil menambahkan");
                        } else {
                            Fungsi.backToMenu("server error 2");
                        }
                    } else {
                        Fungsi.backToMenu("Harga dibayarkan kurang Rp. " + (Integer.parseInt(total) - dibayarkan), 2);
                        isLoop = true;
                    }
                }
            } else {
                Fungsi.backToMenu("server error");
            }
        } else {
            Fungsi.backToMenu("silahkan isikan daftar menu terlebih dahulu", 4);
        }
    }
}