package com.latihan.datamahasiswa;

import com.latihan.datamahasiswa.dao.NilaiDAO;
import com.latihan.datamahasiswa.db.DatabaseConnection;
import com.latihan.datamahasiswa.model.Mahasiswa;
import com.latihan.datamahasiswa.model.Nilai;
import com.latihan.datamahasiswa.service.MahasiswaService;
import com.latihan.datamahasiswa.util.CsvFileManager;
import com.latihan.datamahasiswa.util.MahasiswaSorter;

import java.util.List;
import java.util.Scanner;

/**
 * Titik masuk aplikasi (menu console).
 *
 * Model dasar mengikuti Unit 3: INPUT -> PROCESS -> OUTPUT.
 * Menu ini mendemonstrasikan alur CRUD end-to-end untuk asesmen.
 */
public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final MahasiswaService service = new MahasiswaService();
    private static final NilaiDAO nilaiDao = new NilaiDAO();

    public static void main(String[] args) {
        DatabaseConnection.initSchema();
        System.out.println("=== APLIKASI PENGELOLAAN DATA MAHASISWA ===");

        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            String pilihan = scanner.nextLine().trim();
            switch (pilihan) {
                case "1" -> tampilkanSemua();
                case "2" -> tambahData();
                case "3" -> ubahData();
                case "4" -> hapusData();
                case "5" -> cariData();
                case "6" -> tambahNilai();
                case "7" -> lihatNilai();
                case "8" -> urutkanByIpk();
                case "9" -> exportCsv();
                case "10" -> importCsv();
                case "0" -> jalan = false;
                default -> System.out.println("Pilihan tidak dikenal. Coba lagi.");
            }
        }
        System.out.println("Terima kasih. Aplikasi ditutup.");
    }

    private static void tampilkanMenu() {
        System.out.println("""
                \n---------------- MENU ----------------
                1. Tampilkan semua data
                2. Tambah data
                3. Ubah data
                4. Hapus data
                5. Cari data (nama / NIM)
                6. Tambah nilai mahasiswa
                7. Lihat nilai mahasiswa (JOIN)
                8. Urutkan mahasiswa by IPK (array + sorting)
                9. Export data ke file CSV
                10. Import data dari file CSV
                0. Keluar
                --------------------------------------""");
        System.out.print("Pilih menu: ");
    }

    private static void tampilkanSemua() {
        try {
            cetakTabel(service.semua());
        } catch (Exception e) {
            System.out.println("Gagal memuat data: " + e.getMessage());
        }
    }

    private static void tambahData() {
        try {
            String nim = minta("NIM");
            String nama = minta("Nama");
            String jurusan = minta("Jurusan");
            String email = minta("Email");
            double ipk = mintaAngka("IPK (0.0 - 4.0)");

            Mahasiswa m = new Mahasiswa(nim, nama, jurusan, email, ipk);
            service.tambah(m); // validasi terjadi di dalam service
            System.out.println("Berhasil ditambahkan dengan id = " + m.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Validasi gagal: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Gagal menambah data: " + e.getMessage());
        }
    }

    private static void ubahData() {
        try {
            int id = (int) mintaAngka("ID mahasiswa yang diubah");
            Mahasiswa m = service.cariById(id);
            if (m == null) {
                System.out.println("Data dengan id " + id + " tidak ditemukan.");
                return;
            }
            System.out.println("Data saat ini: " + m.toRow());
            System.out.println("(kosongkan input jika tidak ingin mengubah field tersebut)");

            m.setNim(mintaOpsional("NIM", m.getNim()));
            m.setNama(mintaOpsional("Nama", m.getNama()));
            m.setJurusan(mintaOpsional("Jurusan", m.getJurusan()));
            m.setEmail(mintaOpsional("Email", m.getEmail()));
            String ipkStr = minta("IPK (kosongkan untuk tetap " + m.getIpk() + ")");
            if (!ipkStr.isBlank()) {
                m.setIpk(Double.parseDouble(ipkStr));
            }

            if (service.ubah(m)) {
                System.out.println("Data berhasil diubah.");
            } else {
                System.out.println("Tidak ada perubahan tersimpan.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Validasi gagal: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Gagal mengubah data: " + e.getMessage());
        }
    }

    private static void hapusData() {
        try {
            int id = (int) mintaAngka("ID mahasiswa yang dihapus");
            if (service.hapus(id)) {
                System.out.println("Data berhasil dihapus.");
            } else {
                System.out.println("Data dengan id " + id + " tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Gagal menghapus data: " + e.getMessage());
        }
    }

    private static void cariData() {
        try {
            String keyword = minta("Kata kunci (nama / NIM)");
            cetakTabel(service.cari(keyword));
        } catch (Exception e) {
            System.out.println("Gagal mencari data: " + e.getMessage());
        }
    }

    private static void tambahNilai() {
        try {
            int id = (int) mintaAngka("ID mahasiswa");
            Mahasiswa m = service.cariById(id);
            if (m == null) {
                System.out.println("Mahasiswa dengan id " + id + " tidak ditemukan.");
                return;
            }
            System.out.println("Menambah nilai untuk: " + m.getNama());
            String mk = minta("Mata kuliah");
            double nilai = mintaAngka("Nilai (0 - 100)");
            if (nilai < 0 || nilai > 100) {
                System.out.println("Nilai harus 0 - 100.");
                return;
            }
            nilaiDao.insert(new Nilai(id, mk, nilai));
            System.out.println("Nilai berhasil ditambahkan.");
        } catch (Exception e) {
            System.out.println("Gagal menambah nilai: " + e.getMessage());
        }
    }

    private static void lihatNilai() {
        try {
            int id = (int) mintaAngka("ID mahasiswa");
            List<Nilai> daftar = nilaiDao.findByMahasiswa(id);
            if (daftar.isEmpty()) {
                System.out.println("(belum ada nilai untuk mahasiswa ini)");
                return;
            }
            System.out.printf("%-4s %-22s %-25s %s%n", "ID", "MAHASISWA", "MATA KULIAH", "NILAI");
            System.out.println("-".repeat(60));
            for (Nilai n : daftar) {
                System.out.println(n.toRow());
            }
            System.out.println("Total: " + daftar.size() + " nilai");
        } catch (Exception e) {
            System.out.println("Gagal memuat nilai: " + e.getMessage());
        }
    }

    /** Menu 8 — Unit 3 (array + sorting): urutkan mahasiswa by IPK tertinggi. */
    private static void urutkanByIpk() {
        try {
            List<Mahasiswa> daftar = service.semua();
            if (daftar.isEmpty()) {
                System.out.println("(tidak ada data untuk diurutkan)");
                return;
            }
            // Konversi List -> array, lalu urutkan dengan algoritma selection sort.
            Mahasiswa[] terurut = MahasiswaSorter.urutkanDariList(daftar);
            System.out.println("Data terurut berdasarkan IPK (tertinggi -> terendah):");
            System.out.printf("%-4s %-12s %-22s %-18s %-24s %s%n",
                    "ID", "NIM", "NAMA", "JURUSAN", "EMAIL", "IPK");
            System.out.println("-".repeat(90));
            for (Mahasiswa m : terurut) {              // KUK 4.3: iterasi sepanjang array
                System.out.println(m.toRow());
            }
            System.out.println("Total: " + terurut.length + " data (panjang array)");
        } catch (Exception e) {
            System.out.println("Gagal mengurutkan data: " + e.getMessage());
        }
    }

    /** Menu 9 — Unit 3 (KUK 5.1): tulis seluruh data ke file CSV. */
    private static void exportCsv() {
        try {
            String namaFile = minta("Nama file tujuan [data_mahasiswa.csv]");
            if (namaFile.isBlank()) {
                namaFile = "data_mahasiswa.csv";
            }
            int jumlah = CsvFileManager.eksporKeFile(service.semua(), namaFile);
            System.out.println("Berhasil menulis " + jumlah + " data ke file: " + namaFile);
        } catch (Exception e) {
            System.out.println("Gagal export ke file: " + e.getMessage());
        }
    }

    /** Menu 10 — Unit 3 (KUK 5.2): baca data dari file CSV lalu simpan ke database. */
    private static void importCsv() {
        try {
            String namaFile = minta("Nama file sumber [data_mahasiswa.csv]");
            if (namaFile.isBlank()) {
                namaFile = "data_mahasiswa.csv";
            }
            List<Mahasiswa> dariFile = CsvFileManager.imporDariFile(namaFile);
            int berhasil = 0;
            int gagal = 0;
            for (Mahasiswa m : dariFile) {
                try {
                    service.tambah(m);   // tetap lewat validasi + cek NIM unik
                    berhasil++;
                } catch (Exception e) {
                    gagal++;             // mis. NIM duplikat / data tidak valid
                }
            }
            System.out.printf("Import selesai: %d berhasil, %d dilewati (duplikat/invalid).%n",
                    berhasil, gagal);
        } catch (Exception e) {
            System.out.println("Gagal import dari file: " + e.getMessage());
        }
    }

    // ---------- Helper input/output (dipisah agar tidak berulang - prinsip DRY) ----------

    private static void cetakTabel(List<Mahasiswa> daftar) {
        if (daftar.isEmpty()) {
            System.out.println("(tidak ada data)");
            return;
        }
        System.out.printf("%-4s %-12s %-22s %-18s %-24s %s%n",
                "ID", "NIM", "NAMA", "JURUSAN", "EMAIL", "IPK");
        System.out.println("-".repeat(90));
        for (Mahasiswa m : daftar) {
            System.out.println(m.toRow());
        }
        System.out.println("Total: " + daftar.size() + " data");
    }

    private static String minta(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    private static String mintaOpsional(String label, String nilaiLama) {
        String input = minta(label + " [" + nilaiLama + "]");
        return input.isBlank() ? nilaiLama : input;
    }

    private static double mintaAngka(String label) {
        while (true) {
            try {
                return Double.parseDouble(minta(label));
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Ulangi.");
            }
        }
    }
}
