package com.latihan.datamahasiswa.util;

import com.latihan.datamahasiswa.model.Mahasiswa;

import java.util.List;

/**
 * Pengurutan data mahasiswa menggunakan <b>array</b>.
 *
 * Unit 3 (Pemrograman Terstruktur) — Membuat program menggunakan array:
 *  - KUK 4.1 Dimensi array   : array 1 dimensi ({@code Mahasiswa[]}).
 *  - KUK 4.2 Tipe data array : bertipe objek {@link Mahasiswa}.
 *  - KUK 4.3 Panjang array   : diketahui lewat properti {@code array.length}.
 *  - KUK 4.4 Pengurutan array: algoritma Selection Sort (ditulis manual agar
 *            alur pengurutannya terlihat jelas untuk asesor).
 */
public final class MahasiswaSorter {

    private MahasiswaSorter() {
        // utility class -> tidak untuk di-instansiasi.
    }

    /**
     * Mengurutkan array mahasiswa berdasarkan IPK secara menurun (tertinggi dulu)
     * dengan algoritma <b>Selection Sort</b>.
     *
     * Cara kerja: untuk setiap posisi i, cari IPK terbesar di sisa array (i..n-1),
     * lalu tukar (swap) ke posisi i.
     *
     * @param data array mahasiswa yang akan diurutkan (diubah langsung / in-place).
     */
    public static void urutkanByIpkDesc(Mahasiswa[] data) {
        int n = data.length; // KUK 4.3: panjang array
        for (int i = 0; i < n - 1; i++) {
            int indeksTerbesar = i;
            for (int j = i + 1; j < n; j++) {
                if (data[j].getIpk() > data[indeksTerbesar].getIpk()) {
                    indeksTerbesar = j;
                }
            }
            // tukar elemen i dengan elemen ber-IPK terbesar yang ditemukan
            if (indeksTerbesar != i) {
                Mahasiswa temp = data[i];
                data[i] = data[indeksTerbesar];
                data[indeksTerbesar] = temp;
            }
        }
    }

    /**
     * Helper: mengubah List menjadi array {@code Mahasiswa[]} lalu mengurutkannya.
     * Memudahkan pemanggilan dari lapisan yang memakai List (mis. hasil query DAO).
     *
     * @return array baru yang sudah terurut menurun berdasarkan IPK.
     */
    public static Mahasiswa[] urutkanDariList(List<Mahasiswa> daftar) {
        // KUK 4.1 & 4.2: mendeklarasikan array 1 dimensi bertipe Mahasiswa.
        Mahasiswa[] array = daftar.toArray(new Mahasiswa[0]);
        urutkanByIpkDesc(array);
        return array;
    }
}
