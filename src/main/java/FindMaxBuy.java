import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FindMaxBuy extends SimpleFileVisitor {
    BufferedWriter output;
    public FindMaxBuy (BufferedWriter output) {
        this.output = output;
    }
    static List<Long> listWithTopBuy;

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile()){
            File current = new File(file.toString());
            ArrayList<Long> resultList= new ArrayList<>();

            try(BufferedReader reader = new BufferedReader(new FileReader(current))){
                while (reader.ready()){
                    String tmp = reader.readLine();
                    if (tmp.contains("\"buy\"")) {
                        String thisLong = reader.readLine().replaceAll("\\D+","").trim();
                         long lo= Long.parseLong(thisLong);
                         if (lo >1000000000)
                        resultList.add(lo);
                    }
                }
                Collections.sort(resultList);
                listWithTopBuy = resultList.stream().limit(10).collect(Collectors.toList());
            }

        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        List<Long> superFinalResult = listWithTopBuy.stream().limit(10).collect(Collectors.toList());


        for (Long aLong : superFinalResult) {
            output.write(String.valueOf(aLong));
            output.newLine();
        }

        return FileVisitResult.CONTINUE;
    }
}
