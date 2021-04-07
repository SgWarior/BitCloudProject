import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class WhaleNamesByHash {
    private static Path wileFile = Paths.get("src/main/resources/whales/whaleNames.txt");

    public static void initialization ()throws IOException  {
        try(BufferedReader reader = new BufferedReader(new FileReader(wileFile.toFile()))) {
            while (reader.ready()){
             String[] lines = reader.readLine().split(" ");
                Main.usersMap.putIfAbsent(lines[0], lines[1]);
            }
        }
    }

    public static void addWhales(String whale, String influincer){
        Main.usersMap.putIfAbsent(whale, whale);
        Main.usersMap.putIfAbsent(influincer, whale);
    }

    public static void writeWhales() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(wileFile.toFile()))) {
            for (Map.Entry<String, String> entry : Main.usersMap.entrySet()) {
                writer.write(entry.getKey()+" "+entry.getValue());
                writer.newLine();
            }
        }
    }

    public static String changeHashToName(String Hash){
        if(Main.usersMap.get(Hash).equals("@"))return Hash;
        return Main.usersMap.getOrDefault(Hash, Hash);
    }



}
