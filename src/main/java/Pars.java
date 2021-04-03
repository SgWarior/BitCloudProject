import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;

public class Pars {
    URL url = new URL("https://explorer.bitclout.com/?query-node=https:%2F%2Fapi.bitclout.com&block-height=11427");
    Document doc = Jsoup.parse(url, 3000);


    public Pars() throws IOException {
    }
}
