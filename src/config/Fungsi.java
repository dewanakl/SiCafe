package config;

import java.util.ArrayList;

public class Fungsi {

    // print and exit, void
    public static void Exspetasi(String prm) {
        System.out.println("Upss... " + prm);
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
}