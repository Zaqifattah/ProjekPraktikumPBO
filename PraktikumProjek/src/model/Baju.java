package model;

public class Baju {
    private int id;
    private String nama;
    private String ukuran;
    private double harga;

    public Baju(int id, String nama, String ukuran, double harga) {
        this.id = id;
        this.nama = nama;
        this.ukuran = ukuran;
        this.harga = harga;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getUkuran() { return ukuran; }
    public double getHarga() { return harga; }

    public void setId(int id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }
    public void setUkuran(String ukuran) { this.ukuran = ukuran; }
    public void setHarga(double harga) { this.harga = harga; }
}