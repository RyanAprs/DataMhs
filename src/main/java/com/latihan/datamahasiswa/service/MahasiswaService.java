package com.latihan.datamahasiswa.service;

import com.latihan.datamahasiswa.dao.MahasiswaDAO;
import com.latihan.datamahasiswa.model.Mahasiswa;
import com.latihan.datamahasiswa.util.InputValidator;

import java.sql.SQLException;
import java.util.List;

/**
 * Lapisan business logic: menjembatani App (tampilan) dengan DAO (database).
 *
 * Tanggung jawab utama: MEMVALIDASI data sebelum disimpan.
 * Unit 3 (Terstruktur)  : kumpulan aturan/percabangan validasi.
 * Unit 9 (Unit Testing) : method validate() murni (tanpa database) sehingga mudah diuji dengan JUnit.
 */
public class MahasiswaService {

    private final MahasiswaDAO dao;

    public MahasiswaService() {
        this(new MahasiswaDAO());
    }

    /** Constructor injection -> memudahkan pengujian (bisa disuntik DAO tiruan). */
    public MahasiswaService(MahasiswaDAO dao) {
        this.dao = dao;
    }

    /**
     * Memvalidasi data mahasiswa.
     * @return pesan error, atau null jika semua valid.
     *         (return null = valid, mempermudah pengujian expected vs actual)
     */
    public String validate(Mahasiswa m) {
        if (!InputValidator.isNimValid(m.getNim())) {
            return "NIM tidak valid (harus 5-15 digit angka).";
        }
        if (!InputValidator.isNamaValid(m.getNama())) {
            return "Nama tidak valid (minimal 2 karakter).";
        }
        if (m.getJurusan() == null || m.getJurusan().isBlank()) {
            return "Jurusan tidak boleh kosong.";
        }
        if (!InputValidator.isEmailValid(m.getEmail())) {
            return "Email tidak valid.";
        }
        if (!InputValidator.isIpkValid(m.getIpk())) {
            return "IPK tidak valid (rentang 0.0 - 4.0).";
        }
        return null;
    }

    public boolean isValid(Mahasiswa m) {
        return validate(m) == null;
    }

    public void tambah(Mahasiswa m) throws SQLException {
        String error = validate(m);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        dao.insert(m);
    }

    public boolean ubah(Mahasiswa m) throws SQLException {
        String error = validate(m);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return dao.update(m);
    }

    public boolean hapus(int id) throws SQLException {
        return dao.delete(id);
    }

    public List<Mahasiswa> semua() throws SQLException {
        return dao.findAll();
    }

    public List<Mahasiswa> cari(String keyword) throws SQLException {
        return dao.search(keyword);
    }

    public Mahasiswa cariById(int id) throws SQLException {
        return dao.findById(id);
    }
}
