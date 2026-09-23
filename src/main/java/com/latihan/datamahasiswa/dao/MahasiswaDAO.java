package com.latihan.datamahasiswa.dao;

import com.latihan.datamahasiswa.db.DatabaseConnection;
import com.latihan.datamahasiswa.model.Mahasiswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) untuk entitas Mahasiswa.
 *
 * Unit 6 (Akses Basis Data): CRUD lengkap -> Create, Read, Update, Delete + pencarian.
 * Unit 2 (Best Practices)  : memakai PreparedStatement (mencegah SQL injection),
 *                            try-with-resources (mencegah kebocoran koneksi).
 *
 * Pola DAO memisahkan logika akses database dari logika bisnis (service) dan tampilan (App).
 */
public class MahasiswaDAO {

    /** CREATE */
    public void insert(Mahasiswa m) throws SQLException {
        String sql = "INSERT INTO mahasiswa(nim, nama, jurusan, email, ipk) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNim());
            ps.setString(2, m.getNama());
            ps.setString(3, m.getJurusan());
            ps.setString(4, m.getEmail());
            ps.setDouble(5, m.getIpk());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    m.setId(keys.getInt(1));
                }
            }
        }
    }

    /** READ - semua data */
    public List<Mahasiswa> findAll() throws SQLException {
        String sql = "SELECT * FROM mahasiswa ORDER BY id";
        List<Mahasiswa> hasil = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                hasil.add(mapRow(rs));
            }
        }
        return hasil;
    }

    /** READ - satu data berdasarkan id */
    public Mahasiswa findById(int id) throws SQLException {
        String sql = "SELECT * FROM mahasiswa WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    /** SEARCH - pencarian berdasarkan nama atau NIM (LIKE) */
    public List<Mahasiswa> search(String keyword) throws SQLException {
        String sql = "SELECT * FROM mahasiswa WHERE nama LIKE ? OR nim LIKE ? ORDER BY id";
        List<Mahasiswa> hasil = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pola = "%" + keyword + "%";
            ps.setString(1, pola);
            ps.setString(2, pola);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hasil.add(mapRow(rs));
                }
            }
        }
        return hasil;
    }

    /** UPDATE */
    public boolean update(Mahasiswa m) throws SQLException {
        String sql = "UPDATE mahasiswa SET nim=?, nama=?, jurusan=?, email=?, ipk=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNim());
            ps.setString(2, m.getNama());
            ps.setString(3, m.getJurusan());
            ps.setString(4, m.getEmail());
            ps.setDouble(5, m.getIpk());
            ps.setInt(6, m.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /** DELETE */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM mahasiswa WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /** Helper: mengubah satu baris ResultSet menjadi objek Mahasiswa (menghindari duplikasi kode). */
    private Mahasiswa mapRow(ResultSet rs) throws SQLException {
        return new Mahasiswa(
                rs.getInt("id"),
                rs.getString("nim"),
                rs.getString("nama"),
                rs.getString("jurusan"),
                rs.getString("email"),
                rs.getDouble("ipk")
        );
    }
}
