package ChartsUI.Dots.Gson;

import ChartsUI.Dots.SeriesOfChartDistance;
import ChartsUI.Dots.DotsOfChartQuality;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        return creatingPathForFile(result, "DotsOfChartDistanceGraph");
    }

    /**
     * @param dofcq    DotsOfChartQuality class which we will write to gson file
     * @param filePath name of file to which we want to save
     * @return string param,Which store result of creating gson file
     */
    public static String serialize(DotsOfChartQuality dofcq, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(dofcq);
        return writeToFile(result, filePath);
    }

    /**
     * @param dofcq DotsOfChartQuality class which we will write to gson file
     * @return string param,Which store result of creating gson file
     */
    public static String serialize(DotsOfChartQuality dofcq) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(dofcq);
        return creatingPathForFile(result, "DotsOfChartQualityGraph");
    }

    private static String creatingPathForFile(String gson, String whichClass) {
        String path = "";

        File[] filesList = new File(defaultPath).listFiles();
        Set<String> strings = new HashSet<>();

        for (File f : filesList) {
            strings.add(f.getName());
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        path = whichClass +"("+ dateFormat.format(date)+")";
        String path1 = path;
        for (int i = 0; i < 100; i++) {
            if (!strings.contains(path1)) {
                break;
            } else {
                path1 = path + "(" + i + ")";
            }
        }

        return writeToFile(gson, defaultPath + "\\" + path1+".json");
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
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(gson);
            writer.close();

            return "Gson file successfully created in " + path;
        } catch (IOException e) {
            return "IOException Error writing to file " + e.getMessage();
        }
    }

}
