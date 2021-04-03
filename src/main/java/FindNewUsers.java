import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FindNewUsers extends SimpleFileVisitor {
    BufferedWriter output;
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
                        output.write("@"+tmp);
                        output.newLine();
                    }
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }
}
