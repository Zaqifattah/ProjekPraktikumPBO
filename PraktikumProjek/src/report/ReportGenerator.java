package report;

import model.Baju;
import java.io.FileWriter;
import java.util.List;

public class ReportGenerator {
    public static void exportToText(List<Baju> list) {
        try (FileWriter writer = new FileWriter("laporan/laporan_baju.txt")) {
            for (Baju b : list) {
                writer.write(b.getNama() + " | " + b.getUkuran() + " | " + b.getHarga() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}