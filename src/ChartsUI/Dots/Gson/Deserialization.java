package ChartsUI.Dots.Gson;

import ChartsUI.Dots.SeriesOfChartDistance;
import ChartsUI.Dots.SeriesOfChartQuality;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Deserialization {

    public static SeriesOfChartDistance deserializeDistance(String path) {
        return new Gson().fromJson(readFromFile(path),SeriesOfChartDistance.class);
    }

    public static SeriesOfChartQuality deserializeQuality(String path) {
        return new Gson().fromJson(readFromFile(path),SeriesOfChartQuality.class);
    }

    private static String readFromFile(String path) {
        StringBuilder sb = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return sb.toString();
    }
}
