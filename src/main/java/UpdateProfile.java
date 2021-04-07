import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class UpdateProfile {

    private static ArrayDeque<String> usersList = FindNewUsers.usersList;

    public static void updateUser(BufferedReader reader) throws IOException {
        String userHash = reader.readLine().replace("  \"TransactorPublicKeyBase58Check\": \"", "").replace("\",","");
        for (int i = 0; i < 20; i++) reader.readLine();  //skip 20 lines
        String userNewName= reader.readLine();
        if(!userNewName.equals("    \"NewUsername\": \"\",")) {
            String nameChanged = userNewName.replace("    \"NewUsername\": \"","@").replace("\",","");
            Main.usersMap.put(userHash,nameChanged);
            usersList.add(nameChanged);
        }
    }


    public static ArrayList<String> inviteUsersList() {
        ArrayList usersInvList = new ArrayList();
           StringBuilder sb = new StringBuilder();
           String intro = "Welcome New users\n";
           String outro = "\nFollow @welcomenewusers to get announcement!\nLet's become mutual followers";

           while (!usersList.isEmpty()) {
               if (sb.length() == 0) sb.append("\n").append(intro);
               if (sb.length() > 192) {
                   sb.append(outro);
                   usersInvList.add(sb.append("\n").toString());
                   sb.setLength(0);
               } else {
                   sb.append(usersList.pop()).append(" ");
               }
            }
           return usersInvList;
        }
}
