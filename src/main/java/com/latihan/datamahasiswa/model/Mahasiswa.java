package com.latihan.datamahasiswa.model;

/**
 * Entitas Mahasiswa.
 *
 * Unit 4 (OOP):
 *  - extends Person        -> inheritance.
 *  - atribut private       -> encapsulation (hanya bisa diubah lewat setter yang terkontrol).
 *  - override getRole()    -> polymorphism.
 *  - beberapa constructor  -> constructor overloading.
 */
public class Mahasiswa extends Person {

    private int id;         // primary key di database (0 = belum tersimpan)
    private String nim;
    private String jurusan;
    private double ipk;

    /** Constructor untuk data baru (belum punya id dari database). */
    public Mahasiswa(String nim, String nama, String jurusan, String email, double ipk) {
        this(0, nim, nama, jurusan, email, ipk);
    }

    /** Constructor lengkap, dipakai saat memuat data dari database. */
    public Mahasiswa(int id, String nim, String nama, String jurusan, String email, double ipk) {
        super(nama, email);
        this.id = id;
        this.nim = nim;
        this.jurusan = jurusan;
        this.ipk = ipk;
    }

    @Override
    public String getRole() {
        return "Mahasiswa";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }

    /** Baris ringkas untuk ditampilkan pada tabel di layar. */
    public String toRow() {
        return String.format("%-4d %-12s %-22s %-18s %-24s %.2f",
                id, nim, getNama(), jurusan, getEmail(), ipk);
    }
}
