# Aplikasi Pengelolaan Data Mahasiswa
Project latihan **Asesmen Kompetensi BNSP — Bidang Pengembangan Software**.
Satu project ini dirancang untuk menjadi bukti **9 unit kompetensi** sekaligus.

---

## 1. Teknologi
| Komponen | Pilihan |
|----------|---------|
| Bahasa | Java 17 |
| Build & dependency | Maven |
| Database | SQLite (file `datamahasiswa.db`, otomatis dibuat) |
| Driver DB | `sqlite-jdbc` (Unit 5 — library pre-existing) |
| Unit testing | JUnit 5 |

## 2. Struktur Project
```
DataMahasiswa/
├── pom.xml                     # konfigurasi Maven & dependency
├── database.sql                # skema + contoh query (Unit 6)
├── README.md                   # dokumentasi ini (Unit 7)
├── docs/
│   ├── 01_Spesifikasi.md       # requirement, IPO, flowchart (Unit 1)
│   ├── 05_Debugging.md         # catatan error & perbaikan (Unit 8)
│   └── 06_Testing.md           # test case & hasil (Unit 9)
└── src/
    ├── main/java/com/latihan/datamahasiswa/
    │   ├── App.java            # menu console (INPUT→PROCESS→OUTPUT)
    │   ├── model/Person.java   # abstract (abstraction, inheritance)
    │   ├── model/Mahasiswa.java# entitas (encapsulation, polymorphism)
    │   ├── db/DatabaseConnection.java  # koneksi & skema (Unit 6)
    │   ├── dao/MahasiswaDAO.java       # CRUD (Unit 6)
    │   ├── service/MahasiswaService.java # validasi/business logic
    │   └── util/InputValidator.java    # validasi reusable (Unit 2)
    └── test/java/com/latihan/datamahasiswa/
        └── MahasiswaServiceTest.java   # unit test (Unit 9)
```

## 3. Cara Menjalankan
Prasyarat: **JDK 17+** dan **Maven** terpasang (`java -version`, `mvn -version`).

```bash
# 1. masuk ke folder project
cd DataMahasiswa

# 2. jalankan unit test
mvn test

# 3. build jar lengkap (termasuk dependency)
mvn clean package

# 4. jalankan aplikasi
java -jar target/data-mahasiswa-jar-with-dependencies.jar
```
Alternatif menjalankan tanpa build jar:
```bash
mvn compile exec:java -Dexec.mainClass=com.latihan.datamahasiswa.App
```

## 4. Cara Menggunakan
Setelah aplikasi jalan, akan muncul menu:
```
1. Tampilkan semua data
2. Tambah data
3. Ubah data
4. Hapus data
5. Cari data (nama / NIM)
6. Tambah nilai mahasiswa
7. Lihat nilai mahasiswa (JOIN)
0. Keluar
```
> Menu 6 & 7 mendemonstrasikan **relasi antar tabel** (foreign key), **JOIN**, dan **cascade delete** — saat mahasiswa dihapus, seluruh nilainya ikut terhapus otomatis.
Ketik angka menu lalu ikuti perintah input. Database dibuat otomatis saat pertama dijalankan.

## 5. Peta 9 Unit Kompetensi → Bukti di Project
| Unit | Kompetensi | Bukti di project |
|------|-----------|------------------|
| 1 | Spesifikasi Program | `docs/01_Spesifikasi.md` |
| 2 | Guidelines & Best Practices | penamaan bermakna, berlapis, `InputValidator`, PreparedStatement, DRY |
| 3 | Pemrograman Terstruktur | `App.java` (menu, loop, switch), validasi kondisional |
| 4 | Pemrograman Berorientasi Objek | `Person` (abstract) → `Mahasiswa` (inheritance, encapsulation, polymorphism) |
| 5 | Library / Pre-Existing | dependency `sqlite-jdbc` & JUnit di `pom.xml` |
| 6 | Akses Basis Data | `DatabaseConnection`, `MahasiswaDAO` (CRUD), `database.sql` |
| 7 | Dokumen Kode Program | `README.md` + komentar Javadoc di tiap kelas |
| 8 | Debugging | `docs/05_Debugging.md` |
| 9 | Pengujian Unit Program | `MahasiswaServiceTest.java` + `docs/06_Testing.md` |

## 6. Catatan untuk Asesmen
Saat demo, siapkan jawaban untuk: *apa yang dibuat, bagaimana caranya, dan mengapa memilih cara tersebut.*
Contoh: "Saya pakai pola DAO agar logika database terpisah dari tampilan, sehingga lebih mudah diuji dan dipelihara."
