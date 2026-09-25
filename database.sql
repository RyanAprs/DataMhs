-- =====================================================================
-- database.sql - Skema & data awal Aplikasi Pengelolaan Data Mahasiswa
-- Unit 6: Akses Basis Data (BNSP J.620100.021.02)
-- DBMS: SQLite (kompatibel; sintaks standar SQL)
-- =====================================================================

-- Struktur tabel utama
CREATE TABLE IF NOT EXISTS mahasiswa (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,  -- primary key: identitas unik tiap baris
    nim      TEXT    NOT NULL UNIQUE,             -- NIM wajib unik
    nama     TEXT    NOT NULL,
    jurusan  TEXT    NOT NULL,
    email    TEXT    NOT NULL,
    ipk      REAL    NOT NULL
);

-- Contoh relasi (foreign key) - untuk menjelaskan konsep ke asesor.
-- Satu mahasiswa bisa punya banyak nilai mata kuliah.
CREATE TABLE IF NOT EXISTS nilai (
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    mahasiswa_id INTEGER NOT NULL,
    mata_kuliah  TEXT    NOT NULL,
    nilai        REAL    NOT NULL,
    FOREIGN KEY (mahasiswa_id) REFERENCES mahasiswa(id) ON DELETE CASCADE
);

-- Indeks (KUK 1.3): mempercepat pencarian mahasiswa berdasarkan nama.
CREATE INDEX IF NOT EXISTS idx_mahasiswa_nama ON mahasiswa(nama);

-- Data awal (seed) untuk demo
INSERT INTO mahasiswa (nim, nama, jurusan, email, ipk) VALUES
    ('2021001', 'Budi Santoso',  'Informatika',      'budi@kampus.ac.id',  3.55),
    ('2021002', 'Siti Aminah',   'Sistem Informasi', 'siti@kampus.ac.id',  3.80),
    ('2021003', 'Andi Wijaya',   'Informatika',      'andi@kampus.ac.id',  3.10);

-- Contoh query CRUD (bahan tanya jawab asesor) --------------------------
-- CREATE : INSERT INTO mahasiswa(nim,nama,jurusan,email,ipk) VALUES ('2021004','Rina','TI','rina@kampus.ac.id',3.9);
-- READ   : SELECT * FROM mahasiswa;
-- UPDATE : UPDATE mahasiswa SET ipk = 3.75 WHERE nim = '2021001';
-- DELETE : DELETE FROM mahasiswa WHERE nim = '2021003';
-- SEARCH : SELECT * FROM mahasiswa WHERE nama LIKE '%Budi%';
-- JOIN   : SELECT m.nama, n.mata_kuliah, n.nilai
--          FROM mahasiswa m JOIN nilai n ON m.id = n.mahasiswa_id;
