# Catatan Debugging
> Unit 8 (J.620100.025.02) — Melakukan Debugging

Format: setiap kasus mengikuti alur **reproduksi → identifikasi → analisis → sumber → perbaikan → verifikasi**.

## Kasus 1 — Logic Error: IPK 4.5 lolos tersimpan
- **Jenis error:** Logic error (program jalan, hasil salah).
- **Reproduksi:** tambah mahasiswa dengan IPK = 4.5, ternyata tersimpan.
- **Analisis:** validasi hanya cek `ipk >= 0`, batas atas tidak dicek.
- **Sumber:** `InputValidator.isIpkValid()`.
- **Perbaikan:** tambah kondisi `ipk <= 4.0`.
- **Verifikasi:** jalankan `MahasiswaServiceTest.ipkDiLuarBatas()` → PASS.

## Kasus 2 — Runtime Error: NumberFormatException saat input IPK huruf
- **Jenis error:** Runtime error.
- **Reproduksi:** pada menu tambah, isi IPK dengan "abc" → aplikasi crash.
- **Analisis:** `Double.parseDouble("abc")` melempar exception.
- **Sumber:** helper input di `App.mintaAngka()`.
- **Perbaikan:** bungkus dengan try-catch `NumberFormatException` dan minta ulang.
- **Verifikasi:** input huruf kini menampilkan "Input harus berupa angka. Ulangi."

## Kasus 3 — Syntax Error (contoh saat pengembangan)
- **Jenis error:** Syntax error (gagal compile).
- **Contoh:** lupa titik koma / kurung tidak seimbang.
- **Deteksi:** kompiler `javac` menunjuk baris & kolom error.
- **Perbaikan:** lengkapi sintaks sesuai pesan kompiler.

## Teknik debugging yang dipakai
- **Breakpoint** di IDE (IntelliJ/VS Code) pada method `validate()` dan `insert()`.
- **Step over / step into** untuk menelusuri alur pemanggilan.
- **Inspeksi variable** melihat isi objek `Mahasiswa` sebelum disimpan.
- **Logging/console** (`System.out.println`) untuk melacak nilai runtime.

## Beda Debugging vs Testing (bahan tanya jawab asesor)
- **Debugging:** *mencari & memperbaiki* penyebab masalah yang sudah terjadi.
- **Testing:** *memverifikasi* apakah program memenuhi hasil yang diharapkan (mencegah/menemukan masalah).
