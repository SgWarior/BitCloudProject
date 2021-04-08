import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MostFollowed {

    static private HashMap<String, Integer> followedTree = new HashMap<>();
    private static Properties pr= Main.pr;

    public static void addInflu(BufferedReader reader) throws IOException {
        for (int i = 0; i <7 ; i++)         reader.readLine();
      String influincer=
              reader.readLine().replace("      \"PublicKeyBase58Check\": \"", "").replace("\",","");
        for (int i = 0; i < 15; i++) reader.readLine();
        boolean IsUnfollow = reader.readLine().equals("    \"IsUnfollow\": true\n");
        if (IsUnfollow)
            followedTree.put(influincer, followedTree.get(influincer)==null? -1 : followedTree.get(influincer)-1);
      else followedTree.put(influincer, followedTree.get(influincer)==null? 1 : followedTree.get(influincer)+1);
    }


    public static String getThreePlaces() {
        //TopFollowedUsers
        StringBuilder sb= new StringBuilder();

        String goldHash = Collections.max(followedTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        String goldName = WhaleNamesByHash.changeHashToName(goldHash);
        int goldTotalNewFollowers = followedTree.get(goldHash);
        followedTree.remove(goldHash);

        String silverHash = Collections.max(followedTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        String silverName = WhaleNamesByHash.changeHashToName(silverHash);
        int silverTotalNewFollowers = followedTree.get(silverHash);
        followedTree.remove(silverHash);

        String bronzeHash = Collections.max(followedTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        String bronzeName = WhaleNamesByHash.changeHashToName(bronzeHash);
        int bronzeTotalNewFollowers =  followedTree.get(bronzeHash);

        sb.append(pr.getProperty("MaxFollBefore")).append("Gold price: ").append(goldName).append(" with :").append(goldTotalNewFollowers).append(" followers!\n")
        .append("Silver price: ").append(silverName).append(" with :").append(silverTotalNewFollowers).append(" followers!\n")
        .append("Bronze price: ").append(bronzeName).append(" with :").append(bronzeTotalNewFollowers).append(" followers")
        .append(pr.getProperty("MaxFolAfter"));

        return sb.toString();

    }
}

