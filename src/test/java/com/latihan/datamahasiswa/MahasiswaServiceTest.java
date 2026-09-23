package com.latihan.datamahasiswa;

import com.latihan.datamahasiswa.model.Mahasiswa;
import com.latihan.datamahasiswa.service.MahasiswaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test untuk logika validasi.
 *
 * Unit 9 (Pengujian Unit Program):
 *  - Menguji method secara terpisah (tanpa database).
 *  - Data uji mencakup: data NORMAL, data BATAS, dan data TIDAK VALID.
 *  - Format bukti: Input -> Expected Result -> Actual Result -> Status (pass/fail).
 */
class MahasiswaServiceTest {

    private final MahasiswaService service = new MahasiswaService();

    private Mahasiswa contohValid() {
        return new Mahasiswa("2021001", "Budi Santoso", "Informatika", "budi@kampus.ac.id", 3.5);
    }

    @Test
    @DisplayName("Data normal yang lengkap harus valid")
    void dataNormalValid() {
        assertNull(service.validate(contohValid()));
        assertTrue(service.isValid(contohValid()));
    }

    @Test
    @DisplayName("NIM berisi huruf harus ditolak")
    void nimTidakValid() {
        Mahasiswa m = contohValid();
        m.setNim("ABC123");
        assertFalse(service.isValid(m));
    }

    @Test
    @DisplayName("Email tanpa @ harus ditolak")
    void emailTidakValid() {
        Mahasiswa m = contohValid();
        m.setEmail("budikampus.ac.id");
        assertFalse(service.isValid(m));
    }

    @Test
    @DisplayName("IPK di atas batas (4.0) harus ditolak - pengujian nilai batas")
    void ipkDiLuarBatas() {
        Mahasiswa m = contohValid();
        m.setIpk(4.5);
        assertFalse(service.isValid(m));
    }

    @Test
    @DisplayName("IPK tepat pada batas 4.0 harus diterima - boundary value")
    void ipkTepatBatasAtas() {
        Mahasiswa m = contohValid();
        m.setIpk(4.0);
        assertTrue(service.isValid(m));
    }

    @Test
    @DisplayName("Nama kosong harus ditolak dengan pesan yang tepat")
    void namaKosong() {
        Mahasiswa m = contohValid();
        m.setNama("");
        assertEquals("Nama tidak valid (minimal 2 karakter).", service.validate(m));
    }

    @Test
    @DisplayName("Polymorphism: getRole() mengembalikan 'Mahasiswa'")
    void polymorphismGetRole() {
        assertEquals("Mahasiswa", contohValid().getRole());
    }
}
