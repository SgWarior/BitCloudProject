import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WhaleNamesByHash {
    private static Path wileFile = Paths.get("src/main/resources/whales/whaleNames.txt");
     static HashMap<String , String > whaleMap = new HashMap<>();

    public static void initialization ()throws IOException  {
        try(BufferedReader reader = new BufferedReader(new FileReader(wileFile.toFile()))) {
            while (reader.ready()){
             String[] lines = reader.readLine().split(" ");
                whaleMap.putIfAbsent(lines[0], lines[1]);
            }
        }
    }

    public static void addWhales(String whale){
                whaleMap.putIfAbsent(whale, "FindME");
    }

    public static void writeWhales() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(wileFile.toFile()))) {
            for (Map.Entry<String, String> entry : whaleMap.entrySet()) {
                writer.write(entry.getKey()+" "+entry.getValue());
                writer.newLine();
            }
        }

    }

}
