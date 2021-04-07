import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;

public class Main {
    public static HashMap<String , String > usersMap = new HashMap<>();
    public static void main(String[] args) throws IOException {
        Date date = new Date();
        long time  = date.getTime() / 10000000;

        WhaleNamesByHash.initialization();
        Path start = Paths.get("src/main/resources/Bloks");

        File newUsers          = new File("newUser" + time + ".txt");
        File maxBuyFile        = new File("MaxBuy" + time+ ".txt");
        File maxSellFile       = new File("MaxSell" + time + ".txt");
        File likesAndFollowers = new File("likesAndFollowers"+time+ ".txt");

        try (final BufferedWriter usersOutStream = new BufferedWriter(new FileWriter(newUsers));
             final BufferedWriter moustFollowedOutStr = new BufferedWriter(new FileWriter(likesAndFollowers))) {
            Files.walkFileTree(start, new FindNewUsers(usersOutStream, moustFollowedOutStr));
        }

        try (final BufferedWriter maxBuyStream = new BufferedWriter(new FileWriter(maxBuyFile));
             final BufferedWriter maxSellStream = new BufferedWriter(new FileWriter(maxSellFile))) {
            Files.walkFileTree(start, new MaxBuy(maxBuyStream,maxSellStream));
        }

    }
}
