import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CreateEmptyTxt {

    public static Properties pr = new Properties();

    public static void main(String[] args) throws IOException {
        pr.load(new FileInputStream(new File("src/main/resources/text.properties")));

        int begin = Integer.parseInt(pr.getProperty("newFStart"));
        int finish = Integer.parseInt(pr.getProperty("newFFinish"));
        if (begin-finish > 0) System.out.println(" Паша диапазон задан НЕВЕРНО.НАЧАЛО БОЛЬШЕ КОНЦА");
        else
        for (int i =begin ; i <finish ; i++) {
            new File("src/main/resources/Bloks/" + i + ".txt").createNewFile();
        }

    }
}
