import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class WhaleNamesByHash {
    private static Path wileFile = Paths.get("src/main/resources/whaleNames.txt");
    private  static HashMap<String , String > whaleMap = new HashMap<>();

    public static void initialization ()throws IOException  {
        try(BufferedReader reader = new BufferedReader(new FileReader(wileFile.toFile()))) {
            while (reader.ready()){
             String[] lines = reader.readLine().split(" ");
                whaleMap.put(lines[0], lines[1]);
            }
        }
    }

    public static void addWhales(){}

    public WhaleNamesByHash() throws IOException {
        initialization();

    }
}
