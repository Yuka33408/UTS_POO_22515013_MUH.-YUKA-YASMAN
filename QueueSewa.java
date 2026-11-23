public class QueueSewa {
    private Sewa[] data;
    private int depan;
    private int belakang;
    private int jumlah;

    // Konstruktor default (kapasitas 100)
    public QueueSewa() {
        this(100);
    }

    // Konstruktor berparameter
    public QueueSewa(int kapasitas) {
        if (kapasitas <= 0) {
            kapasitas = 100;
        }
        this.data = new Sewa[kapasitas];
        this.depan = 0;
        this.belakang = -1;
        this.jumlah = 0;
    }

    // Menambah elemen di belakang antrean
    public void enqueue(Sewa s) {
        if (s == null) {
            System.out.println("Data sewa tidak valid.");
            return;
        }
        if (isFull()) {
            System.out.println("Antrean penuh, sewa tidak dapat ditambahkan.");
            return;
        }
        belakang = (belakang + 1) % data.length;
        data[belakang] = s;
        jumlah++;
    }

    // Mengambil dan menghapus elemen di depan antrean
    public Sewa dequeue() {
        if (isEmpty()) {
            return null;
        }
        Sewa s = data[depan];
        data[depan] = null;
        depan = (depan + 1) % data.length;
        jumlah--;
        return s;
    }

    // Melihat elemen terdepan tanpa menghapus
    public Sewa front() {
        if (isEmpty()) {
            return null;
        }
        return data[depan];
    }

    public boolean isEmpty() {
        return jumlah == 0;
    }

    public boolean isFull() {
        return jumlah == data.length;
    }

    public int size() {
        return jumlah;
    }
}