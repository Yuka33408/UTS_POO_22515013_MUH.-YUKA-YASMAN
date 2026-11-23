import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(bersihkanAngka(scanner.nextLine()));
            } catch (NumberFormatException e) {
                System.out.println("Input Tidak Boleh Kosong!");
            }
        }
    }

    public static String bacaString(String pesan) {
        System.out.print(pesan);
        return scanner.nextLine().trim();
    }

    public static double bacaDouble(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Double.parseDouble(bersihkanAngka(scanner.nextLine()));
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }


    // Menentukan pemisah, titik dianggap pemisah ribuan, koma dianggap desimal.
    private static String bersihkanAngka(String teks) {
        return teks.trim()
                .replace("Rp", "")
                .replace("rp", "")
                .replace(" ", "")
                .replace(".", "")
                .replace(",", ".");
    }
}
