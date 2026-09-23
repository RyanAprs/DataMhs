package com.latihan.datamahasiswa.model;

/**
 * Kelas dasar (abstract) untuk semua individu di sistem.
 *
 * Unit 4 (OOP) - mendemonstrasikan:
 *  - Abstraction  : kelas abstrak yang tidak bisa di-instansiasi langsung.
 *  - Encapsulation: atribut bersifat private + diakses lewat getter/setter.
 *  - Inheritance  : diwariskan oleh {@link Mahasiswa}.
 *  - Polymorphism : method abstrak {@link #getRole()} di-override subclass.
 */
public abstract class Person {

    private String nama;
    private String email;

    protected Person(String nama, String email) {
        this.nama = nama;
        this.email = email;
    }

    /**
     * Method abstrak -> setiap subclass WAJIB menjelaskan perannya sendiri.
     * Inilah titik polymorphism: pemanggil cukup tahu "Person", hasil berbeda per subclass.
     */
    public abstract String getRole();

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getRole() + " - " + nama + " (" + email + ")";
    }
}
