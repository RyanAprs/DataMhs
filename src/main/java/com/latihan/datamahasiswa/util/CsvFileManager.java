package com.latihan.datamahasiswa.util;

import com.latihan.datamahasiswa.model.Mahasiswa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Membaca & menulis data mahasiswa ke file CSV (media penyimpan berupa berkas teks).
 *
 * Unit 3 (Pemrograman Terstruktur) — Membuat program untuk akses file:
 *  - KUK 5.1 Menulis data ke media penyimpan  : {@link #eksporKeFile(List, String)}.
 *  - KUK 5.2 Membaca data dari media penyimpan : {@link #imporDariFile(String)}.
 *
 * Catatan: ini melengkapi akses database (Unit 6) dengan akses file mentah,
 * sehingga bukti "baca/tulis file" pada Unit 3 terpenuhi secara eksplisit.
 */
public final class CsvFileManager {

    private static final String HEADER = "nim,nama,jurusan,email,ipk";

    private CsvFileManager() {
        // utility class -> tidak untuk di-instansiasi.
    }

    /**
     * MENULIS file: menyimpan daftar mahasiswa ke file CSV.
     * Memakai try-with-resources agar file selalu tertutup meski terjadi error.
     *
     * @param daftar   data yang akan ditulis.
     * @param namaFile lokasi file tujuan (mis. "data_mahasiswa.csv").
     * @return jumlah baris data yang berhasil ditulis.
     */
    public static int eksporKeFile(List<Mahasiswa> daftar, String namaFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            writer.write(HEADER);
            writer.newLine();
            for (Mahasiswa m : daftar) {
                // Susun satu baris CSV: nim,nama,jurusan,email,ipk
                String baris = String.join(",",
                        m.getNim(), m.getNama(), m.getJurusan(), m.getEmail(),
                        String.valueOf(m.getIpk()));
                writer.write(baris);
                writer.newLine();
            }
        }
        return daftar.size();
    }

    /**
     * MEMBACA file: memuat daftar mahasiswa dari file CSV.
     * Baris pertama (header) dilewati. Baris rusak/tidak lengkap diabaikan.
     *
     * @param namaFile lokasi file sumber.
     * @return daftar mahasiswa hasil pembacaan file.
     */
    public static List<Mahasiswa> imporDariFile(String namaFile) throws IOException {
        List<Mahasiswa> hasil = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String baris;
            boolean barisPertama = true;
            while ((baris = reader.readLine()) != null) {
                if (barisPertama) {          // lewati header
                    barisPertama = false;
                    continue;
                }
                if (baris.isBlank()) {
                    continue;
                }
                String[] kolom = baris.split(",");
                if (kolom.length < 5) {      // baris tidak lengkap -> lewati
                    continue;
                }
                Mahasiswa m = new Mahasiswa(
                        kolom[0].trim(),                 // nim
                        kolom[1].trim(),                 // nama
                        kolom[2].trim(),                 // jurusan
                        kolom[3].trim(),                 // email
                        Double.parseDouble(kolom[4].trim())); // ipk
                hasil.add(m);
            }
        }
        return hasil;
    }
}
