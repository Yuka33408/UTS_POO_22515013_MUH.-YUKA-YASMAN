import java.util.Locale;

public class Main {
    private static Rental rental = new Rental("Rental Kendaraan Surabaya", "Surabaya");

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("id-ID")); // format rupiah: 300.000
        int pilihan;
        do {
            tampilkanMenu();
            pilihan = InputHelper.bacaInt("Pilih menu: ");
            System.out.println();
            switch (pilihan) {
                case 1: {
                    Kendaraan k = new Kendaraan();
                    while (true) {
                        try {
                            String plat = InputHelper.bacaString("Plat           : ");
                            if (rental.isPlatTerdaftar(plat)) {
                                System.out.println("Plat sudah terdaftar! Masukkan plat lain.");
                                continue;
                            }
                            k.setPlat(plat);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    while (true) {
                        try {
                            k.setMerk(InputHelper.bacaString("Merk           : "));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    while (true) {
                        try {
                            k.setJenis(InputHelper.bacaString("Jenis          : "));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    while (true) {
                        try {
                            k.setTarifPerHari(InputHelper.bacaDouble("Tarif per hari : "));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    rental.tambahKendaraan(k);
                    break;
                }
                case 2: {
                    Penyewa p = new Penyewa();
                    while (true) {
                        try {
                            String id = InputHelper.bacaString("ID Penyewa : ");
                            if (rental.isIdPenyewaTerdaftar(id)) {
                                System.out.println("ID penyewa sudah terdaftar! Masukkan ID lain.");
                                continue;
                            }
                            p.setIdPenyewa(id);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    while (true) {
                        try {
                            p.setNama(InputHelper.bacaString("Nama        : "));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    while (true) {
                        try {
                            p.setNoTelp(InputHelper.bacaString("No. Telp    : "));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    while (true) {
                        try {
                            p.setAlamat(InputHelper.bacaString("Alamat      : "));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    rental.daftarkanPenyewa(p);
                    break;
                }
                case 3:
                    rental.ajukanSewa();
                    break;
                case 4:
                    rental.prosesSewa();
                    break;
                case 5:
                    rental.prosesKembali();
                    break;
                case 6:
                    rental.batalkanSewaTerdepan();
                    break;
                case 7:
                    rental.tampilkanData();
                    break;
                case 0:
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Menu tidak valid!");
            }
        } while (pilihan != 0);
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== RENTAL KENDARAAN =====");
        System.out.println("1. Tambah Kendaraan");
        System.out.println("2. Daftarkan Penyewa");
        System.out.println("3. Ajukan Sewa (Masuk Antrean)");
        System.out.println("4. Proses Sewa Terdepan");
        System.out.println("5. Proses Kembali");
        System.out.println("6. Batalkan Sewa Terdepan");
        System.out.println("7. Tampilkan Data");
        System.out.println("0. Keluar");
        System.out.println("============================");
    }
}