import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class Main {
    public static HashMap<String , String > usersMap = new HashMap<>();
    public static Properties pr = new Properties();

    public static void main(String[] args) throws IOException {

        pr.load(new FileInputStream(new File("src/main/resources/text.properties")));
        Date date = new Date();
        long time  = date.getTime() / 1000000;

        userNames.initialization();
        TrueNewUser.initialization();
        Path start = Paths.get("src/main/resources/Bloks");

        File newUsers          = new File("newUser" + time + ".txt");
        File whalesOutput       = new File("MwhalesOutput" + time + ".txt");
        File likesAndFollowers = new File("likesAndFollowers"+time+ ".txt");
        File maxVolume          = new File("MaxVolume"+time+ ".txt");

        try (final BufferedWriter usersOutStream = new BufferedWriter(new FileWriter(newUsers));
             final BufferedWriter moustFollowedOutStr = new BufferedWriter(new FileWriter(likesAndFollowers));
             final BufferedWriter whalesOutputStr = new BufferedWriter(new FileWriter(whalesOutput));
             final BufferedWriter maxVolum = new BufferedWriter(new FileWriter(maxVolume))
) {
            Files.walkFileTree(start, new CheckBlockTree(usersOutStream, moustFollowedOutStr, whalesOutputStr, maxVolum));
        }
        TrueNewUser.destroy();
        userNames.destroy();
    }
}