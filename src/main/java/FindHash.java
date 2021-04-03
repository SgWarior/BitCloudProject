import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FindHash extends SimpleFileVisitor {
    BufferedWriter output;
    public FindHash(BufferedWriter output) {
        this.output = output;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile()){
            File current = new File(file.toString());
            try(BufferedReader reader = new BufferedReader(new FileReader(current))){
                while (reader.ready()){
                    String fLine = reader.readLine();
                    String sLine = reader.readLine();
                    if (fLine.contains("\"TxnType\": \"CREATOR_COIN\"") &&
                            sLine.contains("BC1YLgoGKCGAWNUfx9JSRoYUk1CWBsP3cB9FfAWxe8AQbnxhVDP8jSD")
                    ) {
                        output.write(current.toString()+sLine);
                        output.newLine();
                    }
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }
}
