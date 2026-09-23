package com.latihan.datamahasiswa.dao;

import com.latihan.datamahasiswa.db.DatabaseConnection;
import com.latihan.datamahasiswa.model.Nilai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk entitas Nilai.
 *
 * Unit 6 (Akses Basis Data): mendemonstrasikan relasi antar tabel (foreign key)
 * dan pengambilan data gabungan lewat JOIN.
 */
public class NilaiDAO {

    /** CREATE — menambah nilai untuk seorang mahasiswa. */
    public void insert(Nilai n) throws SQLException {
        String sql = "INSERT INTO nilai(mahasiswa_id, mata_kuliah, nilai) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, n.getMahasiswaId());
            ps.setString(2, n.getMataKuliah());
            ps.setDouble(3, n.getNilai());
            ps.executeUpdate();
        }
    }

    /**
     * READ dengan JOIN — daftar nilai satu mahasiswa lengkap dengan namanya.
     * Menggabungkan tabel nilai dan mahasiswa berdasarkan relasi foreign key.
     */
    public List<Nilai> findByMahasiswa(int mahasiswaId) throws SQLException {
        String sql = """
                SELECT n.id, n.mahasiswa_id, n.mata_kuliah, n.nilai, m.nama AS nama_mahasiswa
                FROM nilai n
                JOIN mahasiswa m ON n.mahasiswa_id = m.id
                WHERE n.mahasiswa_id = ?
                ORDER BY n.id
                """;
        List<Nilai> hasil = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mahasiswaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hasil.add(new Nilai(
                            rs.getInt("id"),
                            rs.getInt("mahasiswa_id"),
                            rs.getString("mata_kuliah"),
                            rs.getDouble("nilai"),
                            rs.getString("nama_mahasiswa")
                    ));
                }
            }
        }
        return hasil;
    }
}
