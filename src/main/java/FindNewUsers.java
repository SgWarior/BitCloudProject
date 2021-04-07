import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FindNewUsers extends SimpleFileVisitor {
    static ArrayDeque<String> usersList = new ArrayDeque<>(1000);

    private BufferedWriter usersOutput;
    private BufferedWriter followerOutput;
    public FindNewUsers(BufferedWriter usersOutput, BufferedWriter followerOutput) {
        this.usersOutput = usersOutput;
        this.followerOutput= followerOutput;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile()){
            File current = new File(file.toString());
            try(BufferedReader reader = new BufferedReader(new FileReader(current))){
                while (reader.ready()){
                    String tnxType = reader.readLine().replace("  \"TxnType\": \"","").replace("\",","");
                    switch (tnxType){
                        case "BASIC_TRANSFER":               break;
                        case "UPDATE_PROFILE": UpdateProfile.updateUser(reader);break;
                        case "FOLLOW":         MostFollowed.addInflu(reader);   break;
                        case "CREATOR_COIN":                                   ;break;
                        case "SUBMIT_POST":                                    ;break;
                        case "LIKE":               MostLike.addInflu(reader);  break;
                        case "BLOCK_REWARD":   ;                                break;
                        case "BITCOIN_EXCHANGE":    ;                           break;
                        case  "PRIVATE_MESSAGE":     ;                          break;
                    }
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {

        for (String st: UpdateProfile.inviteUsersList()) usersOutput.write(st);
        followerOutput.write(MostFollowed.getThreePlaces());
        followerOutput.newLine();
        followerOutput.write(MostLike.getThreePlaces());

        return FileVisitResult.CONTINUE;
    }

}
