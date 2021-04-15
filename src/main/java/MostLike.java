import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MostLike {

    private static Properties pr= Main.pr;
    static public HashMap<String, Integer> likeTree= new HashMap<>();

    public static int getLikeInBlock() {
        return likeInBlock;
    }

    private static int likeInBlock = 0;

    public static void addInflu(BufferedReader reader) throws IOException {
        for (int i = 0; i <7 ; i++)         reader.readLine();
        String influincer=
                reader.readLine().replace("      \"PublicKeyBase58Check\": \"", "").replace("\",","");
        for (int i = 0; i < 15; i++) reader.readLine();
        boolean IsUnlike = reader.readLine().equals("    \"IsUnlike\": false,");
        if (IsUnlike)
        {likeTree.put(influincer, likeTree.get(influincer)==null? -1 : likeTree.get(influincer)-1);
            likeInBlock--;}
        else{ likeTree.put(influincer, likeTree.get(influincer)==null? 1 : likeTree.get(influincer)+1);
            likeInBlock++; }

    }

    public static String getThreePlaces() {
        //TopLikedUsers
        StringBuilder sb= new StringBuilder();

        if(likeTree.size()>4) {
            String first = Collections.max(likeTree.entrySet(), Map.Entry.comparingByValue()).getKey();
            String firstName = userNames.changeHashToName(first);
            int firstLikeTotal = likeTree.get(first);
            likeTree.remove(first);

            String second = Collections.max(likeTree.entrySet(), Map.Entry.comparingByValue()).getKey();
            String secondName = userNames.changeHashToName(second);
            int secondTotalNewLike = likeTree.get(second);
            likeTree.remove(second);

            String third = Collections.max(likeTree.entrySet(), Map.Entry.comparingByValue()).getKey();
            String thirdName = userNames.changeHashToName(third);
            int thirdTotalLike = likeTree.get(third);
            likeTree.remove(third);

            String fourth = Collections.max(likeTree.entrySet(), Map.Entry.comparingByValue()).getKey();
            String fourthName = userNames.changeHashToName(fourth);
            int fourthTotalRaiseLikes = likeTree.get(fourth);
            likeTree.remove(fourth);

            String fifth = Collections.max(likeTree.entrySet(), Map.Entry.comparingByValue()).getKey();
            String fifthhName = userNames.changeHashToName(fifth);
            int fifthTotalRaseLikes = likeTree.get(fifth);


            sb.append(pr.getProperty("MaxLikeBefore")).append("1-st place: ").append(firstName).append(" with ").append(firstLikeTotal).append(" likes!\n")
                    .append("2-nd place: ").append(secondName).append(" with ").append(secondTotalNewLike).append(" likes!\n")
                    .append("3-rd place: ").append(thirdName).append(" with ").append(thirdTotalLike).append(" likes!\n")
                    .append("4-rd place: ").append(fourthName).append(" with ").append(fourthTotalRaiseLikes).append(" likes!\n")
                    .append("5-rd place: ").append(fifthhName).append(" with ").append(fifthTotalRaseLikes).append(" likes!\n")
                    .append(pr.getProperty("MaxLikeAfter"));

        }
        return sb.toString();

    }


}
