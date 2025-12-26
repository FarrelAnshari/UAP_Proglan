package org.example.library.config;

import org.example.library.model.Buku;
import java.util.ArrayList;

public class AppConfig {
    public static final String FILE_BUKU = "buku.txt";
    public static final String PASS_ADMIN = "admin123";
    public static final String PASS_SISWA = "siswa123";

    public static boolean isAdmin = false;
    public static ArrayList<Buku> daftarBuku = new ArrayList<>();
}
