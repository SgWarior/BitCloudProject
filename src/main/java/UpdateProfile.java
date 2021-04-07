import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;

public class UpdateProfile {

    static HashMap<String , String > map = WhaleNamesByHash.whaleMap;
    private static ArrayDeque<String> usersList = FindNewUsers.usersList;

    public static void updateUser(BufferedReader reader) throws IOException {
        String userHash = reader.readLine().replace("  \"TransactorPublicKeyBase58Check\": \"", "").replace("\",","");
        for (int i = 0; i < 20; i++) reader.readLine();  //skip 20 lines
        String userNewName= reader.readLine();
        if(!userNewName.equals("    \"NewUsername\": \"\",")) {
            String nameChanged = userNewName.replace("    \"NewUsername\": \"","@").replace("\",","");
            map.put(userHash,nameChanged);
            usersList.add(nameChanged);
        }
    }
}
