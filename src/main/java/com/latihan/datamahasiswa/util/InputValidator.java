package com.latihan.datamahasiswa.util;

/**
 * Kumpulan validasi input.
 *
 * Unit 2 (Best Practices) : validasi & error handling dipisah agar reusable (prinsip DRY).
 * Unit 3 (Terstruktur)    : berisi logika kondisi/percabangan yang jelas.
 */
public final class InputValidator {

    private InputValidator() {
        // utility class -> tidak untuk di-instansiasi.
    }

    public static boolean isNimValid(String nim) {
        // NIM: tidak kosong, hanya angka, panjang 5-15 digit.
        return nim != null && nim.matches("\\d{5,15}");
    }

    public static boolean isNamaValid(String nama) {
        return nama != null && nama.trim().length() >= 2;
    }

    public static boolean isEmailValid(String email) {
        return email != null && email.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$");
    }

    public static boolean isIpkValid(double ipk) {
        return ipk >= 0.0 && ipk <= 4.0;
    }
}
