import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class TrueNewUser {

    private  static ArrayList<String> listOfNameNewUsers = new ArrayList();
    private  static HashSet<String> hashOfNewUsers= new HashSet<>();
    private static Path newUsersHashFile = Paths.get("src/main/resources/whales/newUsersHash.txt");

    public static void initialization ()throws IOException  {
        try(BufferedReader reader = new BufferedReader(new FileReader(newUsersHashFile.toFile()))) {
            while (reader.ready()){
                hashOfNewUsers.add(reader.readLine());
            }
        }
    }

    public static void check(BufferedReader reader) throws IOException {
        String merlin="  \"TransactorPublicKeyBase58Check\": \"BC1YLhSkfH28QrMAVkbejMUZELwkAEMwr2FFwhEtofHvzHRtP6rd7s6\",";
        if(reader.readLine().equals(merlin)){
            reader.readLine();reader.readLine();
            String userHash = reader.readLine().replace("      \"PublicKeyBase58Check\": \"", "").replace("\",","");
            hashOfNewUsers.add(userHash);
        }
    }

    public static ArrayList<String> getNameOfNewUsers() {
        ArrayList<String> hashForRemove = new ArrayList<>();
        for (String userHash : hashOfNewUsers) {
            if(Main.usersMap.containsKey(userHash)) {
                listOfNameNewUsers.add(Main.usersMap.get(userHash));
                hashForRemove.add(userHash);
            }
        }

        hashOfNewUsers.removeAll(hashForRemove);
        return listOfNameNewUsers;
    }

    public static void destroy () throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(newUsersHashFile.toFile()))) {
            for (String hashOfNewUser : hashOfNewUsers) {
                writer.write(hashOfNewUser);
                writer.newLine();
            }
        }
    }


}
