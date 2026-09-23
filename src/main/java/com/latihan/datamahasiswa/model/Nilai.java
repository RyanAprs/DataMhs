package com.latihan.datamahasiswa.model;

/**
 * Entitas Nilai — mata kuliah & nilai milik seorang mahasiswa.
 *
 * Berelasi ke {@link Mahasiswa} lewat {@code mahasiswaId} (foreign key).
 * Field {@code namaMahasiswa} diisi saat hasil JOIN (tidak disimpan di tabel nilai).
 */
public class Nilai {

    private int id;
    private int mahasiswaId;
    private String mataKuliah;
    private double nilai;
    private String namaMahasiswa; // hanya terisi pada hasil JOIN

    public Nilai(int mahasiswaId, String mataKuliah, double nilai) {
        this.mahasiswaId = mahasiswaId;
        this.mataKuliah = mataKuliah;
        this.nilai = nilai;
    }

    public Nilai(int id, int mahasiswaId, String mataKuliah, double nilai, String namaMahasiswa) {
        this.id = id;
        this.mahasiswaId = mahasiswaId;
        this.mataKuliah = mataKuliah;
        this.nilai = nilai;
        this.namaMahasiswa = namaMahasiswa;
    }

    public int getId() {
        return id;
    }

    public int getMahasiswaId() {
        return mahasiswaId;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public double getNilai() {
        return nilai;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public String toRow() {
        return String.format("%-4d %-22s %-25s %.2f", id, namaMahasiswa, mataKuliah, nilai);
    }
}
