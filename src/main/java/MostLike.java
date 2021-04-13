import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MostLike {

    private static Properties pr= Main.pr;
    static public HashMap<String, Integer> likeTree= new HashMap<>();

    public static void addInflu(BufferedReader reader) throws IOException {
        for (int i = 0; i <7 ; i++)         reader.readLine();
        String influincer=
                reader.readLine().replace("      \"PublicKeyBase58Check\": \"", "").replace("\",","");
        for (int i = 0; i < 15; i++) reader.readLine();
        boolean IsUnlike = reader.readLine().equals("    \"IsUnlike\": false,");
        if (IsUnlike)
            likeTree.put(influincer, likeTree.get(influincer)==null? -1 : likeTree.get(influincer)-1);
        else likeTree.put(influincer, likeTree.get(influincer)==null? 1 : likeTree.get(influincer)+1);

    }

    public static String getThreePlaces() {
        //TopLikedUsers
        StringBuilder sb= new StringBuilder();

        if(likeTree.size()>4) {
            String goldHash = Collections.max(likeTree.entrySet(), Map.Entry.comparingByValue()).getKey();
            String goldName = userNames.changeHashToName(goldHash);
            int goldTotalNewFollowers = likeTree.get(goldHash);
            likeTree.remove(goldHash);

            String silverHash = Collections.max(likeTree.entrySet(), Map.Entry.comparingByValue()).getKey();
            String silverName = userNames.changeHashToName(silverHash);
            int silverTotalNewFollowers = likeTree.get(silverHash);
            likeTree.remove(silverHash);

            String bronzeHash = Collections.max(likeTree.entrySet(), Map.Entry.comparingByValue()).getKey();
            String bronzeName = userNames.changeHashToName(bronzeHash);
            int bronzeTotalNewFollowers = likeTree.get(bronzeHash);

            sb.append(pr.getProperty("MaxLikeBefore")).append("Gold price: ").append(goldName).append(" with :").append(goldTotalNewFollowers).append(" likes!\n")
                    .append("Silver price: ").append(silverName).append(" with :").append(silverTotalNewFollowers).append(" likes!\n")
                    .append("Bronze price: ").append(bronzeName).append(" with :").append(bronzeTotalNewFollowers).append(" likes")
                    .append(pr.getProperty("MaxLikeAfter"));

        }
        return sb.toString();

    }


}
