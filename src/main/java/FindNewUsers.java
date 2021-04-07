import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;

public class FindNewUsers extends SimpleFileVisitor {

    private BufferedWriter output;
    static ArrayDeque<String> usersList = new ArrayDeque<>(1000);
    public FindNewUsers(BufferedWriter output) {
        this.output = output;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile()){
            File current = new File(file.toString());
            try(BufferedReader reader = new BufferedReader(new FileReader(current))){
                while (reader.ready()){
                    String tmp = reader.readLine();
                    if (tmp.equals("  \"TxnType\": \"UPDATE_PROFILE\","))
                        UpdateProfile.updateUser(reader);
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
               String intro = "Welcome New users\n";
        String outro= "\nFollow @welcomenewusers to get announcement!\nLet's become mutual followers";
        StringBuilder sb = new StringBuilder();
        if (usersList.contains("@demibagby")||usersList.contains("@demibagb")) {
            sb.append("________________ALERT_______________ @demibagby ____detected!__anouncment @ashtanmoore");
        } //remove when is done

        while (!usersList.isEmpty()){
            if(sb.length()==0)sb.append("\n").append(intro);
            if (sb.length()>192) {sb.append(outro); output.write(sb.append("\n").toString());sb.setLength(0);}
            else {
                sb.append(usersList.pop()).append(" ");
            }
        }
        return FileVisitResult.CONTINUE;
    }
}
