package com.latihan.datamahasiswa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Mengelola koneksi ke database SQLite.
 *
 * Unit 6 (Akses Basis Data): connection, penanganan connection error.
 * Unit 5 (Library)         : memanfaatkan driver JDBC (sqlite-jdbc) sebagai komponen pre-existing.
 *
 * SQLite dipilih agar portable -> database berupa satu file, tanpa perlu server.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:datamahasiswa.db";

    /**
     * Membuka koneksi baru ke database.
     * Foreign key di SQLite harus diaktifkan per-koneksi (default: off).
     * @throws SQLException jika koneksi gagal (mis. driver tidak ada / file terkunci).
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    /**
     * Membuat tabel bila belum ada (auto-migrasi sederhana).
     * Dipanggil sekali saat aplikasi start.
     */
    public static void initSchema() {
        String sqlMahasiswa = """
                CREATE TABLE IF NOT EXISTS mahasiswa (
                    id       INTEGER PRIMARY KEY AUTOINCREMENT,
                    nim      TEXT    NOT NULL UNIQUE,
                    nama     TEXT    NOT NULL,
                    jurusan  TEXT    NOT NULL,
                    email    TEXT    NOT NULL,
                    ipk      REAL    NOT NULL
                );
                """;
        // Tabel nilai berelasi ke mahasiswa (one-to-many) lewat foreign key.
        String sqlNilai = """
                CREATE TABLE IF NOT EXISTS nilai (
                    id           INTEGER PRIMARY KEY AUTOINCREMENT,
                    mahasiswa_id INTEGER NOT NULL,
                    mata_kuliah  TEXT    NOT NULL,
                    nilai        REAL    NOT NULL,
                    FOREIGN KEY (mahasiswa_id) REFERENCES mahasiswa(id) ON DELETE CASCADE
                );
                """;
        // Unit 6 (KUK 1.3): indeks eksplisit untuk mempercepat pencarian by nama.
        String sqlIndex = "CREATE INDEX IF NOT EXISTS idx_mahasiswa_nama ON mahasiswa(nama)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlMahasiswa);
            stmt.execute(sqlNilai);
            stmt.execute(sqlIndex);
        } catch (SQLException e) {
            // Unit 8 (Debugging): pesan error dibuat informatif agar mudah ditelusuri.
            throw new RuntimeException("Gagal inisialisasi database: " + e.getMessage(), e);
        }
    }
}
