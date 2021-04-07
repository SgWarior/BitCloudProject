import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MostFollowed {

    static private HashMap<String, Integer> followedTree = new HashMap<>();

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
        String intro = "Most Followed users by 24 ours\n";
        String outro = "\nFollow @WhaleRadar and get fresh data!";

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

        sb.append(intro).append("Gold price: ").append(goldName).append(" with :").append(goldTotalNewFollowers).append(" followers!\n")
        .append("Silver price: ").append(silverName).append(" with :").append(silverTotalNewFollowers).append(" followers!\n")
        .append("Bronze price: ").append(bronzeName).append(" with :").append(bronzeTotalNewFollowers).append(" followers")
        .append(outro);

        return sb.toString();

    }
}

