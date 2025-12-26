package org.example.library.util;

import org.example.library.config.AppConfig;
import org.example.library.model.Buku;

import java.io.*;
import java.time.LocalDate;

public class DataManager {

    public static void loadData() {
        try (BufferedReader br = new BufferedReader(
                new FileReader(AppConfig.FILE_BUKU))) {

            String s;
            while ((s = br.readLine()) != null) {
                String[] d = s.split(",");

                Buku b = new Buku(
                        d[0],
                        d[1],
                        d[2],
                        Integer.parseInt(d[3])
                );

                if (d.length > 4 && d[4].equals("1")) {
                    b.dipinjam = true;
                    b.peminjam = d[5];
                    b.kembali = LocalDate.parse(d[6]);
                }

                AppConfig.daftarBuku.add(b);
            }

        } catch (Exception e) {
            // file belum ada → aman diabaikan
        }
    }

    public static void saveData() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(AppConfig.FILE_BUKU))) {

            for (Buku b : AppConfig.daftarBuku) {
                bw.write(
                        b.id + "," +
                                b.judul + "," +
                                b.penulis + "," +
                                b.tahun + "," +
                                (b.dipinjam
                                        ? "1," + b.peminjam + "," + b.kembali
                                        : "0")
                );
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
