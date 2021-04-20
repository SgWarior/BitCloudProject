import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class Main {
    public static HashMap<String , String > usersMap = new HashMap<>();
    public static Properties pr = new Properties();
    public static long time  = new Date().getTime();

    public static void main(String[] args) throws IOException {

        pr.load(new FileInputStream(new File("src/main/resources/text.properties")));


        userNames.initialization();
        TrueNewUser.initialization();
        File start = new File("src/main/resources/Bloks");

        File newUsers          = new File("NewUsers" + time + ".txt");
        File whalesOutput       = new File("WhalesDeals" + time + ".txt");
        File likesAndFollowers = new File("Likes_Follow"+time+ ".txt");
        File maxVolume          = new File("MaxVolume"+time+ ".txt");

        File sellYourself      = new File("sellYourself"+time+".txt");

        try (final BufferedWriter usersOutStream = new BufferedWriter(new FileWriter(newUsers));
             final BufferedWriter moustFollowedOutF = new BufferedWriter(new FileWriter(likesAndFollowers));
             final BufferedWriter whalesOutputF = new BufferedWriter(new FileWriter(whalesOutput));
             final BufferedWriter maxVolumF = new BufferedWriter(new FileWriter(maxVolume));
             final BufferedWriter sellYourselfF = new BufferedWriter(new FileWriter(sellYourself))
) {
            Files.walkFileTree(start.toPath(), new CheckBlockTree(usersOutStream, moustFollowedOutF, whalesOutputF, maxVolumF,sellYourselfF));
        }
        TrueNewUser.destroy();
        userNames.destroy();
    }
}