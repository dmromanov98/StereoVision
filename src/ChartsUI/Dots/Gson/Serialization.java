package ChartsUI.Dots.Gson;

import ChartsUI.Dots.SeriesOfChartDistance;
import ChartsUI.Dots.SeriesOfChartQuality;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Serialization {

    private static String defaultPath = "GraphsSaves";

    /**
     * @param sofcd    DotsOfChartDistance class which we will write to gson file
     * @param filePath name of file to which we want to save
     * @return string param,Which store result of creating gson file
     */
    public static String serialize(SeriesOfChartDistance sofcd, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(sofcd);
        return writeToFile(result, filePath);
    }

    /**
     * @param sofcd DotsOfChartDistance class which we will write to gson file
     * @return string param,Which store result of creating gson file
     */
    public static String serialize(SeriesOfChartDistance sofcd) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(sofcd);
        return creatingPathForFile(result, "D");
    }

    /**
     * @param cofcq    DotsOfChartQuality class which we will write to gson file
     * @param filePath name of file to which we want to save
     * @return string param,Which store result of creating gson file
     */
    public static String serialize(SeriesOfChartQuality cofcq, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(cofcq);
        return writeToFile(result, filePath);
    }

    /**
     * @param cofcq DotsOfChartQuality class which we will write to gson file
     * @return string param,Which store result of creating gson file
     */
    public static String serialize(SeriesOfChartQuality cofcq) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(cofcq);
        return creatingPathForFile(result, "Q");
    }

    private static String creatingPathForFile(String gson, String whichClass) {
        String path = "";

        File[] filesList = new File(defaultPath).listFiles();
        Set<String> strings = new HashSet<>();

        for (File f : filesList) {
            strings.add(f.getName());
        }

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        //System.out.println(dateFormat.format(date));

        path = whichClass + "(" + dateFormat.format(date) + ")";
        String path1 = path;

        for (int i = 0; i < 100; i++) {
            if (!strings.contains(path1+".json")) {
                break;
            } else {
                path1 = path + "(" + i + ")";
            }
        }

        return writeToFile(gson, defaultPath+"\\"+path1 + ".json");
    }


    /**
     * Writing gson String to file with path
     *
     * @param gson
     * @param path
     * @return
     */
    private static String writeToFile(String gson, String path) {
        BufferedWriter writer = null;
        try {

            System.out.println(path);
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(gson);


            return "Gson file successfully created in " + path;
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException Error writing to file " + e.getMessage();
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }
    }

}
