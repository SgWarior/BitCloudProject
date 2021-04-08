import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class CheckBlockTree extends SimpleFileVisitor {
    static ArrayDeque<String> usersList = new ArrayDeque<>(1000);

    private BufferedWriter usersOutput;
    private BufferedWriter followerOutput;
    private BufferedWriter whalesOutput;
    private BufferedWriter maxVolume;

    public CheckBlockTree(BufferedWriter usersOutput, BufferedWriter followerOutput, BufferedWriter whalesOutput, BufferedWriter maxVolum) {
        this.usersOutput = usersOutput;
        this.followerOutput= followerOutput;
        this.whalesOutput = whalesOutput;
        this.maxVolume = maxVolum;
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
                        case "FOLLOW":            MostFollowed.addInflu(reader);break;
                        case "CREATOR_COIN":        WhalesDeals.addDeal(reader);break;
                        case "SUBMIT_POST":                                    ;break;
                        case "LIKE":                  MostLike.addInflu(reader);break;
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
        WhalesDeals.writeResultInfile(whalesOutput);

        WhalesDeals.mostVolumeDealer();
        WhalesDeals.writeMaxVolumeDealers(maxVolume);

        return FileVisitResult.CONTINUE;
    }

}
