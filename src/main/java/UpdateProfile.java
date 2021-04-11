import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class UpdateProfile {

    private static Properties pr= Main.pr;

    private static ArrayList<String> userListInvitationMessage = new ArrayList<>();

    public static void updateUser(BufferedReader reader) throws IOException {
        String userHash = reader.readLine().replace("  \"TransactorPublicKeyBase58Check\": \"", "").replace("\",","");
        for (int i = 0; i < 20; i++) reader.readLine();  //skip 20 lines
        String userNewName= reader.readLine();
        if (!Main.usersMap.containsKey(userHash))
            if (!userNewName.equals("    \"NewUsername\": \"\",")) {
                String nameChanged = userNewName.replace("    \"NewUsername\": \"", "@").replace("\",", "");
                Main.usersMap.put(userHash, nameChanged);
            }
    }

    public static ArrayList<String> inviteUsersList(ArrayList hashList) {
        StringBuilder sb = new StringBuilder();
        System.out.println("hash list size "+hashList.size());
        for (Object o : hashList) {
            if (sb.length() == 0) sb.append("\n").append(pr.getProperty("UsersBefore"));

            if (sb.length() > 190) {
                sb.append(pr.getProperty("UsersAfter")).append("\n");
                userListInvitationMessage.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(o).append(" ");
            }
        }
        userListInvitationMessage.add(sb.toString());
        return userListInvitationMessage;
    }

}
