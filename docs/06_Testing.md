# Dokumen Pengujian Unit
> Unit 9 (J.620100.033.02) — Melaksanakan Pengujian Unit Program

**Cara menjalankan:** `mvn test`
**File uji:** `src/test/java/com/latihan/datamahasiswa/MahasiswaServiceTest.java`

## Tabel Test Case
Format bukti: **Input → Expected Result → Actual Result → Status**

| No | Test Case | Input | Expected | Actual | Status |
|----|-----------|-------|----------|--------|--------|
| TC-1 | Data normal valid | NIM 2021001, IPK 3.5, email valid | valid (null) | null | ✅ PASS |
| TC-2 | NIM berisi huruf | NIM "ABC123" | ditolak | ditolak | ✅ PASS |
| TC-3 | Email tanpa @ | "budikampus.ac.id" | ditolak | ditolak | ✅ PASS |
| TC-4 | IPK di atas batas | IPK 4.5 | ditolak | ditolak | ✅ PASS |
| TC-5 | IPK batas atas (boundary) | IPK 4.0 | diterima | diterima | ✅ PASS |
| TC-6 | Nama kosong | "" | pesan error spesifik | sesuai | ✅ PASS |
| TC-7 | Polymorphism getRole | objek Mahasiswa | "Mahasiswa" | "Mahasiswa" | ✅ PASS |

## Jenis data uji yang dicakup
- **Data normal:** TC-1 (semua field wajar).
- **Data batas (boundary):** TC-4 & TC-5 (nilai tepat/di luar batas IPK).
- **Data tidak valid:** TC-2, TC-3, TC-6.

## Konsep yang ditanyakan asesor
- **Unit:** bagian terkecil yang diuji (di sini: method `validate`).
- **Test case:** skenario uji dengan input & hasil yang diharapkan.
- **Expected vs Actual:** hasil seharusnya vs hasil sebenarnya.
- **Pass/Fail:** status kesesuaian keduanya.
