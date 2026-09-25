# Spesifikasi Program — Aplikasi Pengelolaan Data Mahasiswa
> Unit 1 (J.620100.009.01) — Menggunakan Spesifikasi Program

## 1. Tujuan & Pengguna
- **Fungsi aplikasi:** mengelola data mahasiswa (menambah, menampilkan, mengubah, menghapus, mencari).
- **Pengguna:** staf akademik / admin program studi.

## 2. Kebutuhan (Requirement)

### Functional Requirement
| Kode | Kebutuhan |
|------|-----------|
| FR-1 | Sistem dapat **menambah** data mahasiswa. |
| FR-2 | Sistem dapat **menampilkan** seluruh data mahasiswa. |
| FR-3 | Sistem dapat **mengubah** data mahasiswa berdasarkan ID. |
| FR-4 | Sistem dapat **menghapus** data mahasiswa berdasarkan ID. |
| FR-5 | Sistem dapat **mencari** data berdasarkan nama atau NIM. |
| FR-6 | Sistem **memvalidasi** input (NIM angka, email valid, IPK 0–4). |

### Non-Functional Requirement
| Kode | Kebutuhan |
|------|-----------|
| NFR-1 | Data tersimpan permanen di database (SQLite). |
| NFR-2 | Kode terstruktur, mudah dibaca & dipelihara (berlapis: model/dao/service/app). |
| NFR-3 | Portable — cukup Java + Maven, tanpa server database terpisah. |
| NFR-4 | Aman dari SQL injection (memakai PreparedStatement). |

## 3. Model Input – Proses – Output
```
INPUT            PROCESS                         OUTPUT
------------     ---------------------------     ----------------------
Data mahasiswa → Validasi (Service) → Simpan   → Konfirmasi + ID baru
Kata kunci     → Query LIKE (DAO)              → Daftar hasil pencarian
ID + data baru → Validasi → UPDATE             → Status berhasil/gagal
```

## 4. Struktur Data (entitas Mahasiswa)
| Field | Tipe | Keterangan |
|-------|------|-----------|
| id | int | primary key, auto increment |
| nim | String | unik, 5–15 digit |
| nama | String | minimal 2 karakter |
| jurusan | String | wajib |
| email | String | format email valid |
| ipk | double | rentang 0.0 – 4.0 |

## 5. Alur Aplikasi (Flowchart teks)
```
[Mulai] → [Inisialisasi database] → [Tampil menu]
   → pilih 1 → tampilkan semua data ─┐
   → pilih 2 → input → validasi → simpan ┤
   → pilih 3 → cari id → edit → validasi → update ┤→ [kembali ke menu]
   → pilih 4 → cari id → hapus ┤
   → pilih 5 → input keyword → cari ─┘
   → pilih 0 → [Selesai]
```

## 6. Use Case (ringkas)
- **Aktor:** Admin.
- **Use case:** Tambah Data, Lihat Data, Ubah Data, Hapus Data, Cari Data, Urutkan, Export/Import File.

## 7. Class Diagram (Unit 1 — KUK 2.2: diagram objek)
Menggambarkan kelas, atribut, method, dan relasinya (inheritance, implements, dependency).
```
        <<abstract>>
          Person
   ─────────────────────
   - nama : String
   - email : String
   ─────────────────────
   + getRole() : String   <<abstract>>
   + getNama() / setNama()
   + getEmail() / setEmail()
          ▲  (extends / inheritance)
          │
       Mahasiswa
   ─────────────────────
   - id : int
   - nim : String
   - jurusan : String
   - ipk : double
   ─────────────────────
   + getRole() : String   (override → polymorphism)
   + toRow() : String

   <<interface>>
   CrudRepository<T>
   ─────────────────────
   + insert(T)          + findById(int) : T
   + findAll() : List   + update(T) : boolean
   + delete(int) : boolean
          ▲  (implements)
          │
     MahasiswaDAO ───uses──▶ DatabaseConnection
          ▲
          │ (dipakai)
   MahasiswaService ───validasi──▶ InputValidator

   App ──▶ MahasiswaService, NilaiDAO, CsvFileManager, MahasiswaSorter
```

## 8. Component Diagram (Unit 1 — KUK 2.2: diagram komponen)
Menggambarkan komponen/lapisan sistem dan ketergantungan antar-komponennya.
```
┌─────────────────────────────────────────────────────────┐
│                    App (UI / Menu Console)               │
└───────────────┬───────────────────────┬─────────────────┘
                │                        │
        ┌───────▼────────┐      ┌────────▼─────────┐
        │  Util          │      │  Service         │
        │  - Validator   │◀─────│  (business logic)│
        │  - CsvFile     │      │  - validasi      │
        │  - Sorter      │      └────────┬─────────┘
        └───────┬────────┘               │
             (file .csv)         ┌────────▼─────────┐
                                 │  DAO (CrudRepo)  │
                                 │  Mahasiswa/Nilai │
                                 └────────┬─────────┘
                                          │ JDBC
                                 ┌────────▼─────────┐
                                 │  Database (SQLite)│
                                 │  sqlite-jdbc lib  │
                                 └──────────────────┘
```
