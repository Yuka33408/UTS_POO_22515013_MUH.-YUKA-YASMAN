public class Pembayaran {
    private Sewa sewa;
    private double jumlahBayar;
    private double kembalian;
    private boolean lunas;

    // Konstruktor default
    public Pembayaran() {
        this.sewa = null;
        this.jumlahBayar = 0;
        this.kembalian = 0;
        this.lunas = false;
    }

    // Konstruktor berparameter
    public Pembayaran(Sewa sewa) {
        this.setSewa(sewa);
        this.jumlahBayar = 0;
        this.kembalian = 0;
        this.lunas = false;
    }

    // ===== Getter =====
    public Sewa getSewa() {
        return sewa;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public double getKembalian() {
        return kembalian;
    }

    public boolean isLunas() {
        return lunas;
    }

    // ===== Setter (dengan validasi) =====
    public void setSewa(Sewa sewa) {
        if (sewa == null) {
            throw new IllegalArgumentException("Data sewa tidak boleh kosong!");
        }
        this.sewa = sewa;
    }

    // ===== Method perilaku =====
    public void bayar(double jumlah) {
        if (sewa == null) {
            System.out.println("Data sewa belum diset.");
            return;
        }
        if (jumlah < sewa.getTotalBiaya()) {
            lunas = false;
            System.out.println("Uang kurang! Masukkan jumlah yang cukup.");
            return;
        }
        this.jumlahBayar = jumlah;
        this.kembalian = jumlah - sewa.getTotalBiaya();
        this.lunas = true;
    }

    public void cetakStruk() {
        if (sewa == null || !lunas) {
            System.out.println("Struk belum bisa dicetak (pembayaran belum lunas).");
            return;
        }
        System.out.println();
        System.out.println("========== STRUK PEMBAYARAN ==========");
        System.out.println("ID Sewa      : " + sewa.getIdSewa());
        System.out.println("Penyewa      : " + sewa.getPenyewa().getNama());
        System.out.println("Kendaraan    : " + sewa.getKendaraan().getMerk()
                + " (" + sewa.getKendaraan().getPlat() + ")");
        System.out.println("Tarif/Hari   : " + rupiah(sewa.getKendaraan().getTarifPerHari()));
        System.out.println("Lama Sewa    : " + sewa.getLamaHari() + " hari");
        if (sewa.isDiskon()) {
            System.out.println("Diskon       : Member 10%");
        }
        System.out.println("Total Biaya  : " + rupiah(sewa.getTotalBiaya()));
        System.out.println("Jumlah Bayar : " + rupiah(jumlahBayar));
        System.out.println("Kembalian    : " + rupiah(kembalian));
        System.out.println("======================================");
    }

    private String rupiah(double nilai) {
        return String.format("Rp%,.0f", nilai);
    }
}
