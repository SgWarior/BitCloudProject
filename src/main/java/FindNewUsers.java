import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FindNewUsers extends SimpleFileVisitor {

    private BufferedWriter usersOutput;
    private BufferedWriter followerOutput;
    static ArrayDeque<String> usersList = new ArrayDeque<>(1000);
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
        // newUsersOutputInFile
        {
        StringBuilder sb = new StringBuilder();
        String intro = "Welcome New users\n";
        String outro= "\nFollow @welcomenewusers to get announcement!\nLet's become mutual followers";

        if (usersList.contains("@demibagby")||usersList.contains("@demibagb")) {
            sb.append("________________ALERT_______________ @demibagby ____detected!__anouncment @ashtanmoore"); } //remove when is done

        while (!usersList.isEmpty()){
            if(sb.length()==0)sb.append("\n").append(intro);
            if (sb.length()>192) {sb.append(outro); usersOutput.write(sb.append("\n").toString());sb.setLength(0);}
            else {
                sb.append(usersList.pop()).append(" ");
            }
        }}
        System.out.println(MostFollowed.getThreePlaces());
        followerOutput.write(MostFollowed.getThreePlaces());

        System.out.println(MostLike.getThreePlaces());
        followerOutput.write(MostLike.getThreePlaces());

        return FileVisitResult.CONTINUE;
    }

}
