package com.latihan.datamahasiswa.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Kontrak operasi dasar akses data (CRUD) untuk sebuah entitas.
 *
 * Unit 4 (OOP) — KUK 4.1: mendemonstrasikan <b>interface</b>.
 *  - Interface hanya mendefinisikan "APA" yang harus bisa dilakukan (kontrak),
 *    tanpa "BAGAIMANA" caranya. Implementasinya ada di kelas seperti {@link MahasiswaDAO}.
 *  - Memudahkan penggantian implementasi (mis. ganti dari SQLite ke MySQL)
 *    tanpa mengubah kode yang memakai kontrak ini.
 *
 * @param <T> tipe entitas yang dikelola (mis. Mahasiswa).
 */
public interface CrudRepository<T> {

    /** Menyimpan entitas baru ke media penyimpanan. */
    void insert(T entity) throws SQLException;

    /** Mengambil seluruh entitas. */
    List<T> findAll() throws SQLException;

    /** Mengambil satu entitas berdasarkan id, atau {@code null} bila tidak ada. */
    T findById(int id) throws SQLException;

    /** Memperbarui entitas; {@code true} bila ada baris yang berubah. */
    boolean update(T entity) throws SQLException;

    /** Menghapus entitas berdasarkan id; {@code true} bila ada baris terhapus. */
    boolean delete(int id) throws SQLException;
}
