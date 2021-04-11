import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CreateEmptyTxt {

    public static Properties prCreateNewFile = new Properties();

    public static void main(String[] args) throws IOException {
        prCreateNewFile.load(new FileInputStream(new File("src/main/resources/text.properties")));

        int begin = Integer.parseInt(prCreateNewFile.getProperty("newFStart"));
        int finish = Integer.parseInt(prCreateNewFile.getProperty("newFFinish"));
        if (begin-finish > 0) System.out.println(" Паша диапазон задан НЕВЕРНО.НАЧАЛО БОЛЬШЕ КОНЦА");
        else
        for (int i =begin ; i <finish ; i++) {
            new File("src/main/resources/Bloks/" + i + ".txt").createNewFile();
        }

    }
}
