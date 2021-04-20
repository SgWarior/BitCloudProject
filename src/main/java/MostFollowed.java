import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MostFollowed {

    static private HashMap<String, Integer> followedTree = new HashMap<>();
    private static Properties pr= Main.pr;

    public static int getFollowerInBlock() {
        return followerInBlock;
    }

    private static int followerInBlock = 0;

    public static void addInflu(BufferedReader reader) throws IOException {
        for (int i = 0; i <7 ; i++)         reader.readLine();
      String influincer=
              reader.readLine().replace("      \"PublicKeyBase58Check\": \"", "").replace("\",","");
        for (int i = 0; i < 15; i++) reader.readLine();
        boolean IsUnfollow = reader.readLine().equals("    \"IsUnfollow\": true\n");
        if (IsUnfollow)
        { followedTree.put(influincer, followedTree.get(influincer)==null? -1 : followedTree.get(influincer)-1); followerInBlock--;}
      else {followedTree.put(influincer, followedTree.get(influincer)==null? 1 : followedTree.get(influincer)+1);followerInBlock++;}
    }

    public static String getThreePlaces() {
        //TopFollowedUsers
        StringBuilder sb= new StringBuilder().append(pr.getProperty("MaxFollBefore"));

        followedTree.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> sb
                        .append(" : ")
                        .append(userNames.changeHashToName(entry.getKey()))
                        .append(" with  ")
                        .append(entry.getValue())
                        .append(" followers!")
                        .append("\n"));

        sb.append(pr.getProperty("MaxFolAfter"));

        /*if(followedTree.size()>6) {
        String first = Collections.max(followedTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        String firstName = userNames.changeHashToName(first);
        int firstTotalFoll = followedTree.get(first);
        followedTree.remove(first);

        String second = Collections.max(followedTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        String secondName = userNames.changeHashToName(second);
        int silverTotalNewFollowers = followedTree.get(second);
        followedTree.remove(second);

        String third = Collections.max(followedTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        String thirdName = userNames.changeHashToName(third);
        int bronzeTotalNewFollowers =  followedTree.get(third);
        followedTree.remove(third);

        String fourth = Collections.max(followedTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        String fourthName = userNames.changeHashToName(fourth);
        int fourthTotalNewFollow = followedTree.get(fourth);
        followedTree.remove(fourth);

        String fifth = Collections.max(followedTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        String fifthhName = userNames.changeHashToName(fifth);
        int fifthTotalNewFoll = followedTree.get(fifth);


        sb.append(pr.getProperty("MaxFollBefore")).append("1-st place: ").append(firstName).append(" with ").append(firstTotalFoll).append(" followers!\n")
        .append("2-nd place: ").append(secondName).append(" with ").append(silverTotalNewFollowers).append(" followers!\n   ")
        .append("3-rd place: ").append(thirdName).append(" with ").append(bronzeTotalNewFollowers).append(" followers!\n")
        .append("4-rd place: ").append(fourthName).append(" with ").append(fourthTotalNewFollow).append(" followers!\n")
        .append("5-rd place: ").append(fifthhName).append(" with ").append(fifthTotalNewFoll).append(" followers!\n")
        .append(pr.getProperty("MaxFolAfter"));
        }

         */

        return sb.toString();

    }
}

