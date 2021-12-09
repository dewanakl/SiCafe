package config;

import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Fungsi {

    // print and exit, void
    public static void Exspetasi(String prm) {
        System.err.println("Upss... " + prm);
        System.exit(0);
    }

    // clear screen, void
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // repeat string, String
    public static String repeatStr(String msg, int n) {
        return msg.repeat(n);
    }

    // display as table, void
    public static void displayTabel(ArrayList<String> lstField, ArrayList<String> lstBrg) {
        if (lstBrg.isEmpty()) {
            System.out.println("Tidak ada data");
        } else {
            System.out.println(repeatStr("-", lstField.size() * 30));
            for (int a = 0; a < lstBrg.size(); a++) {
                if (a == 0) {
                    for (int b = 0; b < lstField.size(); b++) {
                        System.out.printf("%-30s", lstField.get(b).toString());
                    }
                    System.out.print("\n");
                    System.out.println(repeatStr("-", lstField.size() * 30));
                }
                System.out.printf("%-30s", lstBrg.get(a).toString());
                if ((a + 1) % lstField.size() == 0) {
                    System.out.println();
                }
            }
            System.out.println(repeatStr("-", lstField.size() * 30));
        }
    }

    // overloading display as table format rupiah, void
    public static void displayTabel(ArrayList<String> lstField, ArrayList<String> lstBrg, int num) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        if (lstBrg.isEmpty()) {
            System.out.println("Tidak ada data");
        } else {
            System.out.println(repeatStr("-", lstField.size() * 30));
            for (int a = 0; a < lstBrg.size(); a++) {
                if (a == 0) {
                    for (int b = 0; b < lstField.size(); b++) {
                        System.out.printf("%-30s", lstField.get(b).toString());
                    }
                    System.out.print("\n");
                    System.out.println(repeatStr("-", lstField.size() * 30));
                }

                if (a % num == 0) {
                    Double price = Double.valueOf(lstBrg.get(a));
                    if (price >= 500.d) {
                        System.out.printf("%-30s", kursIndonesia.format(price));
                    } else {
                        System.out.printf("%-30s", lstBrg.get(a).toString());
                    }
                } else {
                    System.out.printf("%-30s", lstBrg.get(a).toString());
                }
                if ((a + 1) % lstField.size() == 0) {
                    System.out.println();
                }
            }
            System.out.println(repeatStr("-", lstField.size() * 30));
        }
    }

    // beauty back to menu, void
    public static void backToMenu(String msg) {
        int n = 3;
        while (n >= 1) {
            clearScreen();
            System.out.println(msg);
            System.out.println("kembali main menu dalam " + n);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            n--;
        }
    }

    // overloading beauty back to menu, void
    public static void backToMenu(String msg, int time) {
        clearScreen();
        System.out.println(msg);
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}