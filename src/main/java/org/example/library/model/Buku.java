package org.example.library.model;

import java.time.LocalDate;

public class Buku {
    public String id;
    public String judul;
    public String penulis;
    public int tahun;

    public boolean dipinjam = false;
    public String peminjam = "-";
    public LocalDate kembali;

    public Buku(String id, String judul, String penulis, int tahun) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.tahun = tahun;
    }
}