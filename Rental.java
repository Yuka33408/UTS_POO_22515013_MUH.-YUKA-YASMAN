import java.util.ArrayList;

public class Rental {
    private String namaRental;
    private String alamatRental;
    private int jumlahTransaksi;
    private double totalPendapatan;
    private ArrayList<Kendaraan> daftarKendaraan;
    private ArrayList<Penyewa> daftarPenyewa;
    private ArrayList<Sewa> daftarSewa;
    private QueueSewa antrean;

    // Konstruktor default
    public Rental() {
        this("Rental Kendaraan", "-");
    }

    // Konstruktor berparameter
    public Rental(String namaRental, String alamatRental) {
        this.setNamaRental(namaRental);
        this.setAlamatRental(alamatRental);
        this.jumlahTransaksi = 0;
        this.totalPendapatan = 0;
        this.daftarKendaraan = new ArrayList<>();
        this.daftarPenyewa = new ArrayList<>();
        this.daftarSewa = new ArrayList<>();
        this.antrean = new QueueSewa(100);
    }

    // ===== Getter =====
    public String getNamaRental() {
        return namaRental;
    }

    public String getAlamatRental() {
        return alamatRental;
    }

    public int getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public double getTotalPendapatan() {
        return totalPendapatan;
    }

    // ===== Setter (dengan validasi) =====
    public void setNamaRental(String namaRental) {
        if (namaRental == null || namaRental.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama rental tidak boleh kosong!");
        }
        this.namaRental = namaRental.trim();
    }

    public void setAlamatRental(String alamatRental) {
        if (alamatRental == null || alamatRental.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat rental tidak boleh kosong!");
        }
        this.alamatRental = alamatRental.trim();
    }

    // ===== Method perilaku =====
    public boolean isPlatTerdaftar(String plat) {
        return cariKendaraan(plat) != null;
    }

    public boolean isIdPenyewaTerdaftar(String id) {
        return cariPenyewa(id) != null;
    }

    public void tambahKendaraan(Kendaraan k) {
        if (cariKendaraan(k.getPlat()) != null) {
            System.out.println("Kendaraan dengan plat tersebut sudah terdaftar!");
            return;
        }
        daftarKendaraan.add(k);
        System.out.println("Kendaraan berhasil ditambahkan.");
    }

    public void daftarkanPenyewa(Penyewa p) {
        if (cariPenyewa(p.getIdPenyewa()) != null) {
            System.out.println("ID penyewa sudah terdaftar!");
            return;
        }
        daftarPenyewa.add(p);
        System.out.println("Penyewa berhasil didaftarkan.");
    }

    // Mengajukan sewa: sewa masuk ke antrean (enqueue)
    public void ajukanSewa() {
        if (daftarKendaraan.isEmpty() || daftarPenyewa.isEmpty()) {
            System.out.println("Data kendaraan atau penyewa masih kosong!");
            return;
        }
        if (antrean.isFull()) {
            System.out.println("Antrean penuh, tidak dapat mengajukan sewa baru.");
            return;
        }

        Penyewa penyewa = cariPenyewa(InputHelper.bacaString("ID Penyewa      : "));
        if (penyewa == null) {
            System.out.println("Penyewa tidak ditemukan!");
            return;
        }

        Kendaraan kendaraan = cariKendaraan(InputHelper.bacaString("Plat Kendaraan  : "));
        if (kendaraan == null) {
            System.out.println("Kendaraan tidak ditemukan!");
            return;
        }
        if (!kendaraan.isTersedia()) {
            System.out.println("Kendaraan sedang disewa!");
            return;
        }

        int lama = InputHelper.bacaInt("Lama Sewa (hari): ");
        Sewa sewa;
        try {
            sewa = new Sewa(String.format("S%03d", jumlahTransaksi + 1), kendaraan, penyewa, lama);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        antrean.enqueue(sewa);
        jumlahTransaksi++;
        if (sewa.isDiskon()) {
            System.out.println("Penyewa adalah member, diskon 10% diterapkan.");
        }
        System.out.printf("Total biaya      : Rp%,.0f%n", sewa.getTotalBiaya());
        System.out.println("Sewa " + sewa.getIdSewa() + " masuk antrean. Jumlah antrean: " + antrean.size());
    }

    // Memproses sewa terdepan pada antrean (dequeue)
    public void prosesSewa() {
        if (antrean.isEmpty()) {
            System.out.println("Antrean kosong, tidak ada sewa yang diproses.");
            return;
        }

        Sewa sewa = antrean.front();
        System.out.println("Memproses sewa terdepan:");
        sewa.tampilkanInfo();

        if (!sewa.mulai()) {
            antrean.dequeue();
            System.out.println("Kendaraan " + sewa.getKendaraan().getPlat()
                    + " sudah tidak tersedia. Sewa " + sewa.getIdSewa() + " dikeluarkan dari antrean.");
            return;
        }

        Pembayaran pembayaran = new Pembayaran(sewa);
        while (!pembayaran.isLunas()) {
            pembayaran.bayar(InputHelper.bacaDouble("Jumlah bayar    : "));
        }

        antrean.dequeue();
        sewa.getPenyewa().catatSewa();
        daftarSewa.add(sewa);
        totalPendapatan += sewa.getTotalBiaya();
        pembayaran.cetakStruk();
    }

    public void prosesKembali() {
        Kendaraan kendaraan = cariKendaraan(InputHelper.bacaString("Plat kendaraan yang dikembalikan: "));
        if (kendaraan == null) {
            System.out.println("Kendaraan tidak ditemukan!");
            return;
        }
        if (kendaraan.isTersedia()) {
            System.out.println("Kendaraan ini tidak sedang disewa.");
            return;
        }
        for (int i = daftarSewa.size() - 1; i >= 0; i--) {
            Sewa s = daftarSewa.get(i);
            if (s.getKendaraan() == kendaraan) {
                s.selesai();
                System.out.println("Kendaraan berhasil dikembalikan.");
                return;
            }
        }
        kendaraan.kembalikan();
        System.out.println("Kendaraan berhasil dikembalikan.");
    }

    // Membatalkan sewa terdepan pada antrean (dequeue tanpa pembayaran)
    public void batalkanSewaTerdepan() {
        if (antrean.isEmpty()) {
            System.out.println("Antrean kosong, tidak ada sewa yang dibatalkan.");
            return;
        }
        Sewa terdepan = antrean.dequeue();
        System.out.println("Sewa " + terdepan.getIdSewa() + " ("
                + terdepan.getPenyewa().getNama() + " - "
                + terdepan.getKendaraan().getPlat() + ") dibatalkan dan dikeluarkan dari antrean.");
    }

    public void tampilkanData() {
        System.out.println("\n===== " + namaRental + " (" + alamatRental + ") =====");

        System.out.println("\n--- DAFTAR KENDARAAN ---");
        if (daftarKendaraan.isEmpty()) {
            System.out.println("(kosong)");
        }
        for (Kendaraan k : daftarKendaraan) {
            k.tampilkanInfo();
        }

        System.out.println("\n--- DAFTAR PENYEWA ---");
        if (daftarPenyewa.isEmpty()) {
            System.out.println("(kosong)");
        }
        for (Penyewa p : daftarPenyewa) {
            p.tampilkanInfo();
        }

        System.out.println("\n--- ANTREAN SEWA ---");
        System.out.println("Jumlah antrean : " + antrean.size());
        if (antrean.isEmpty()) {
            System.out.println("(kosong)");
        } else {
            System.out.println("Terdepan       :");
            antrean.front().tampilkanInfo();
        }

        System.out.println("\n--- DAFTAR SEWA (SUDAH DIPROSES) ---");
        if (daftarSewa.isEmpty()) {
            System.out.println("(kosong)");
        }
        for (Sewa s : daftarSewa) {
            s.tampilkanInfo();
        }

        System.out.printf("%nJumlah pengajuan sewa : %d%n", jumlahTransaksi);
        System.out.printf("Total pendapatan       : Rp%,.0f%n", totalPendapatan);
    }

    private Kendaraan cariKendaraan(String plat) {
        for (Kendaraan k : daftarKendaraan) {
            if (k.getPlat().equalsIgnoreCase(plat.trim())) {
                return k;
            }
        }
        return null;
    }

    private Penyewa cariPenyewa(String id) {
        for (Penyewa p : daftarPenyewa) {
            if (p.getIdPenyewa().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }
}