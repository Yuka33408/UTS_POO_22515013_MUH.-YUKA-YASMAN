public class Sewa {
    private String idSewa;
    private Kendaraan kendaraan;
    private Penyewa penyewa;
    private int lamaHari;
    private double totalBiaya;
    private boolean diskon;

    // Konstruktor default
    public Sewa() {
        this.idSewa = "-";
        this.kendaraan = null;
        this.penyewa = null;
        this.lamaHari = 0;
        this.totalBiaya = 0;
        this.diskon = false;
    }

    // Konstruktor berparameter
    public Sewa(String idSewa, Kendaraan kendaraan, Penyewa penyewa, int lamaHari) {
        if (idSewa == null || idSewa.trim().isEmpty()) {
            throw new IllegalArgumentException("ID sewa tidak boleh kosong!");
        }
        if (kendaraan == null || penyewa == null) {
            throw new IllegalArgumentException("Kendaraan dan penyewa tidak boleh kosong!");
        }
        this.idSewa = idSewa;
        this.kendaraan = kendaraan;
        this.penyewa = penyewa;
        this.diskon = penyewa.isMember();
        this.setLamaHari(lamaHari);
    }

    // ===== Getter =====
    public String getIdSewa() {
        return idSewa;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public Penyewa getPenyewa() {
        return penyewa;
    }

    public int getLamaHari() {
        return lamaHari;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public boolean isDiskon() {
        return diskon;
    }

    // ===== Setter (dengan validasi) =====
    public void setLamaHari(int lamaHari) {
        if (lamaHari <= 0) {
            throw new IllegalArgumentException("Lama sewa harus lebih dari 0 hari!");
        }
        this.lamaHari = lamaHari;
        hitungTotal();
    }

    // ===== Method perilaku =====
    public double hitungTotal() {
        if (kendaraan == null) {
            totalBiaya = 0;
            return totalBiaya;
        }
        double biaya = kendaraan.hitungBiaya(lamaHari);
        if (diskon) {
            biaya = biaya * 0.9;
        }
        totalBiaya = biaya;
        return totalBiaya;
    }

    // Memulai sewa: kendaraan ditandai disewa (false bila tidak tersedia)
    public boolean mulai() {
        return kendaraan != null && kendaraan.sewakan();
    }

    // Menyelesaikan sewa: kendaraan tersedia kembali
    public void selesai() {
        if (kendaraan != null) {
            kendaraan.kembalikan();
        }
    }

    public void tampilkanInfo() {
        if (kendaraan == null || penyewa == null) {
            System.out.println("Data sewa belum lengkap.");
            return;
        }
        System.out.printf("%-6s %-15s %-10s %2d hari  Rp%,.0f%s%n",
                idSewa, penyewa.getNama(), kendaraan.getPlat(),
                lamaHari, totalBiaya, diskon ? " (diskon member 10%)" : "");
    }
}