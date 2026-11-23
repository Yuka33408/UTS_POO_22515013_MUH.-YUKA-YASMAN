public class Kendaraan {
    private String plat;
    private String merk;
    private String jenis;
    private double tarifPerHari;
    private boolean tersedia;

    // Konstruktor default
    public Kendaraan() {
        this.plat = "-";
        this.merk = "-";
        this.jenis = "-";
        this.tarifPerHari = 0;
        this.tersedia = true;
    }

    // Konstruktor berparameter
    public Kendaraan(String plat, String merk, String jenis, double tarifPerHari) {
        this.setPlat(plat);
        this.setMerk(merk);
        this.setJenis(jenis);
        this.setTarifPerHari(tarifPerHari);
        this.tersedia = true;
    }

    // ===== Getter =====
    public String getPlat() {
        return plat;
    }

    public String getMerk() {
        return merk;
    }

    public String getJenis() {
        return jenis;
    }

    public double getTarifPerHari() {
        return tarifPerHari;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    // ===== Setter (dengan validasi) =====
    public void setPlat(String plat) {
        if (plat == null || plat.trim().isEmpty()) {
            throw new IllegalArgumentException("Plat tidak boleh kosong!");
        }
        this.plat = plat.trim().toUpperCase();
    }

    public void setMerk(String merk) {
        if (merk == null || merk.trim().isEmpty()) {
            throw new IllegalArgumentException("Merk tidak boleh kosong!");
        }
        this.merk = merk.trim();
    }

    public void setJenis(String jenis) {
        if (jenis == null || jenis.trim().isEmpty()) {
            throw new IllegalArgumentException("Jenis tidak boleh kosong!");
        }
        this.jenis = jenis.trim();
    }

    public void setTarifPerHari(double tarifPerHari) {
        if (tarifPerHari <= 0) {
            throw new IllegalArgumentException("Tarif per hari harus lebih dari 0!");
        }
        this.tarifPerHari = tarifPerHari;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    // ===== Method perilaku =====
    public double hitungBiaya(int hari) {
        return tarifPerHari * hari;
    }

    public boolean sewakan() {
        if (!tersedia) {
            return false;
        }
        tersedia = false;
        return true;
    }

    public void kembalikan() {
        tersedia = true;
    }

    public void tampilkanInfo() {
        System.out.printf("%-10s %-12s %-10s Rp%,.0f/hari  [%s]%n",
                plat, merk, jenis, tarifPerHari, tersedia ? "Tersedia" : "Disewa");
    }
}
