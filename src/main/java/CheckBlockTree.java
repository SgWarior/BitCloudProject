import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CheckBlockTree extends SimpleFileVisitor {

    private BufferedWriter usersOutput;
    private BufferedWriter followerOutput;
    private BufferedWriter whalesOutput;
    private BufferedWriter maxVolume;
    private BufferedWriter sellYourselfF;

    public CheckBlockTree(BufferedWriter usersOutput, BufferedWriter followerOutput, BufferedWriter whalesOutput, BufferedWriter maxVolume, BufferedWriter sellYourselfF) {
        this.usersOutput = usersOutput;
        this.followerOutput= followerOutput;
        this.whalesOutput = whalesOutput;
        this.maxVolume = maxVolume;
        this.sellYourselfF = sellYourselfF;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file.toString());
        if (attrs.isRegularFile()){
            File current = new File(file.toString());
            try(BufferedReader reader = new BufferedReader(new FileReader(current))){
                while (reader.ready()){
                    String tnxType = reader.readLine().replace("  \"TxnType\": \"","").replace("\",","");
                    switch (tnxType){
                        case "BASIC_TRANSFER":        TrueNewUser.check(reader);break;
                        case "UPDATE_PROFILE": UpdateProfile.updateUser(reader);break;
                        case "FOLLOW":            MostFollowed.addInflu(reader);break;
                        case "CREATOR_COIN":        WhalesDeals.addDeal(reader);break;
                        case "SUBMIT_POST":                                     break;
                        case "LIKE":                  MostLike.addInflu(reader);break;
                        case "BLOCK_REWARD":                                    break;
                        case "BITCOIN_EXCHANGE":                                break;
                        case "PRIVATE_MESSAGE":                                 break;
                    }
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        usersOutput.write("In this block detected "+ TrueNewUser.getNewUsersCounter() +" new creators.\n\n");
        usersOutput.write("There are totally "+ MostFollowed.getFollowerInBlock() +" new followings in the last 24 hours.\n\n");
        usersOutput.write("There are totally "+ MostLike.getLikeInBlock() +" likes in the last 24 hours.\n\n");


        for (String s : UpdateProfile.inviteUsersList(TrueNewUser.getNameOfNewUsers())) {
            usersOutput.write(s);
        }

        followerOutput.write(MostFollowed.getThreePlaces()+"\n");
        followerOutput.write(MostLike.getThreePlaces());

        WhalesDeals.writeResultInfile(whalesOutput,sellYourselfF);
        WhalesDeals.mostVolumeDealer();
        WhalesDeals.writeMaxVolumeDealers(maxVolume);

        return FileVisitResult.CONTINUE;
    }

}
