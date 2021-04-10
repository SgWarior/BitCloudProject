import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrueNewUser {
    private  static ArrayList<String> hashOfNewUsers= new ArrayList<>();

    public static void chek(BufferedReader reader) throws IOException {
        String merlin="  \"TransactorPublicKeyBase58Check\": \"BC1YLhSkfH28QrMAVkbejMUZELwkAEMwr2FFwhEtofHvzHRtP6rd7s6\",";
        if(reader.readLine().equals(merlin)){
            reader.readLine();reader.readLine();
            String userHash = reader.readLine().replace("  \"TransactorPublicKeyBase58Check\": \"", "").replace("\",","");
            hashOfNewUsers.add(userHash);
        };

    }

    public static ArrayList<String> getHashOfNewUsers() {
        return hashOfNewUsers;
    }
}
