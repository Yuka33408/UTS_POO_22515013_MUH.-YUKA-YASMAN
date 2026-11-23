public class Penyewa {
    private String idPenyewa;
    private String nama;
    private String noTelp;
    private String alamat;
    private int jumlahSewa;

    // Konstruktor default
    public Penyewa() {
        this.idPenyewa = "-";
        this.nama = "-";
        this.noTelp = "-";
        this.alamat = "-";
        this.jumlahSewa = 0;
    }

    // Konstruktor berparameter
    public Penyewa(String idPenyewa, String nama, String noTelp, String alamat) {
        this.setIdPenyewa(idPenyewa);
        this.setNama(nama);
        this.setNoTelp(noTelp);
        this.setAlamat(alamat);
        this.jumlahSewa = 0;
    }

    // ===== Getter =====
    public String getIdPenyewa() {
        return idPenyewa;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getJumlahSewa() {
        return jumlahSewa;
    }

    // ===== Setter (dengan validasi) =====
    public void setIdPenyewa(String idPenyewa) {
        if (idPenyewa == null || idPenyewa.trim().isEmpty()) {
            throw new IllegalArgumentException("ID penyewa tidak boleh kosong!");
        }
        this.idPenyewa = idPenyewa.trim();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong!");
        }
        this.nama = nama.trim();
    }

    public void setNoTelp(String noTelp) {
        if (noTelp == null || !noTelp.trim().matches("\\d{8,15}")) {
            throw new IllegalArgumentException("No. telp harus berupa 8-15 digit angka!");
        }
        this.noTelp = noTelp.trim();
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat tidak boleh kosong!");
        }
        this.alamat = alamat.trim();
    }

    // ===== Method perilaku =====
    public void catatSewa() {
        jumlahSewa++;
    }

    // Penyewa dengan minimal 3 kali sewa dianggap member (diskon 10%)
    public boolean isMember() {
        return jumlahSewa >= 3;
    }

    public void tampilkanInfo() {
        System.out.printf("%-8s %-15s %-14s %-20s %d kali sewa%s%n",
                idPenyewa, nama, noTelp, alamat, jumlahSewa, isMember() ? " (Member)" : "");
    }
}