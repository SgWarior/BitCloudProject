import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;


public class FindNewUsers extends SimpleFileVisitor {
    BufferedWriter output;

    private static ArrayDeque<String> usersList = new ArrayDeque<>(1000);
    private static HashSet<String> testSet = new HashSet<>();
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
                    if (tmp.contains("NewUsername")&& !tmp.contains("NewUsername\": \"\"")) {
                        String tmp2 = tmp.trim().replace("\"NewUsername\": \"", "@").replace("\",","");
                        usersList.add(tmp2);
                        testSet.add(tmp2);
                    }
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
