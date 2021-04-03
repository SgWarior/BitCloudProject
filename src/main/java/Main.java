import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.*;
import java.util.Date;


public class Main {
    public static void main(String[] args) throws IOException {
        Date date = new Date();
        Path start = Paths.get("src/main/resources");

        File newUsers = new File("newUser" + date.getTime() + ".txt");
        File fHash    = new File("Hash"   + date.getTime() + ".txt");
        File maxDeals = new File("MaxBuy" + date.getTime() + ".txt");
        File maxSell = new File("MaxSell" + date.getTime() + ".txt");

        File parsik = new File("Parsik"  +  date.getTime() + ".txt");


        try (final BufferedWriter usersOutStream = new BufferedWriter(new FileWriter(newUsers))) {
            Files.walkFileTree(start, new FindNewUsers(usersOutStream));
        }

        try (final BufferedWriter hashFinder = new BufferedWriter(new FileWriter(fHash))) {
            Files.walkFileTree(start, new FindHash(hashFinder));
        }

        try (final BufferedWriter maxBuyOutStream = new BufferedWriter(new FileWriter(maxDeals))) {
            Files.walkFileTree(start, new FindMaxBuy(maxBuyOutStream));
        }

        try (final BufferedWriter maxSellOutStream = new BufferedWriter(new FileWriter(maxSell))) {
            Files.walkFileTree(start, new FindMaxSell(maxSellOutStream));
        }

        try (final BufferedWriter output3 = new BufferedWriter(new FileWriter(parsik))) {
            Document doc = new Pars().doc;
            Elements element = doc.body().getAllElements();
            Document doc2 = Jsoup.parse(new Pars().url, 10000);
        for (Element elemen : element) {                System.out.println(elemen.toString());            }
        }

    }
}
// https://api.bitclout.com/api/v1/block с содержимым: {"Height":11427,"FullBlock":true}