import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

 public class Main {
    public static void main(String[] args) throws IOException {
        Date date = new Date();
        long time  = date.getTime() % 10000000;
        WhaleNamesByHash.initialization();
        Path start = Paths.get("src/main/resources/Bloks");

        File newUsers = new File("newUser" + time + ".txt");
        File fHash    = new File("Hash"   + time + ".txt");
        File maxBuy = new File("MaxBuy" + time+ ".txt");
        File maxSell = new File("MaxSell" + time + ".txt");

        File parsik = new File("Parsik"  +  time + ".txt");

        try (final BufferedWriter usersOutStream = new BufferedWriter(new FileWriter(newUsers))) {
            Files.walkFileTree(start, new FindNewUsers(usersOutStream));
        }


/*
        try (final BufferedWriter hashFinder = new BufferedWriter(new FileWriter(fHash))) {
            Files.walkFileTree(start, new FindHash(hashFinder));
        }

 */
        try (final BufferedWriter maxBuyStream = new BufferedWriter(new FileWriter(maxBuy));
             final BufferedWriter maxSellStream = new BufferedWriter(new FileWriter(maxSell))) {
            Files.walkFileTree(start, new MaxBuy(maxBuyStream,maxSellStream));
        }


     /*   try (final BufferedWriter maxSellOutStream = new BufferedWriter(new FileWriter(maxSell))) {
            Files.walkFileTree(start, new FindMaxSell(maxSellOutStream));
        }
      */

     /*   try (final BufferedWriter output3 = new BufferedWriter(new FileWriter(parsik))) {
            Document doc = new Pars().doc;
            Elements element = doc.body().getAllElements();
            Document doc2 = Jsoup.parse(new Pars().url, 10000);
        for (Element elemen : element) {                System.out.println(elemen.toString());            }
        }
     */


    }
}
// https://api.bitclout.com/api/v1/block с содержимым: {"Height":11427,"FullBlock":true}
