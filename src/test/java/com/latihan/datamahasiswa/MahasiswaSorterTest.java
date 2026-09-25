package com.latihan.datamahasiswa;

import com.latihan.datamahasiswa.model.Mahasiswa;
import com.latihan.datamahasiswa.util.MahasiswaSorter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test untuk pengurutan array (Unit 3 — KUK 4.4) sekaligus bukti Unit 9.
 */
class MahasiswaSorterTest {

    private List<Mahasiswa> contohData() {
        return List.of(
                new Mahasiswa("2021001", "Budi", "TI", "budi@kampus.ac.id", 3.10),
                new Mahasiswa("2021002", "Siti", "SI", "siti@kampus.ac.id", 3.80),
                new Mahasiswa("2021003", "Andi", "TI", "andi@kampus.ac.id", 3.55));
    }

    @Test
    @DisplayName("Array terurut IPK menurun: elemen pertama IPK tertinggi")
    void urutMenurun() {
        Mahasiswa[] hasil = MahasiswaSorter.urutkanDariList(contohData());

        assertEquals(3, hasil.length);                 // KUK 4.3: panjang array
        assertEquals(3.80, hasil[0].getIpk());         // tertinggi di depan
        assertEquals(3.55, hasil[1].getIpk());
        assertEquals(3.10, hasil[2].getIpk());         // terendah di akhir
    }

    @Test
    @DisplayName("Urutan tidak naik di sepanjang array (monoton menurun)")
    void monotonMenurun() {
        Mahasiswa[] hasil = MahasiswaSorter.urutkanDariList(contohData());
        for (int i = 0; i < hasil.length - 1; i++) {
            assertTrue(hasil[i].getIpk() >= hasil[i + 1].getIpk());
        }
    }
}
