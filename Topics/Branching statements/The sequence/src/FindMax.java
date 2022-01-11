import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FindMax {

    public static final String PATH_TO_FILE = "/Users/bdongmo-ngnintedem/IdeaProjects/Simple Banking System/Topics/Branching statements/";
    public static final String FILENAME = "dataset_91007.txt";
    public static final String FULL_FILE_PATH = PATH_TO_FILE + FILENAME;

    public static void main(String[] args) {

        File file = new File(FULL_FILE_PATH);
        int maxValue = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                int value = Integer.parseInt(scanner.next());
                if (value > maxValue) {
                    maxValue = value;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.printf("No such file: %s", FULL_FILE_PATH);
        }

        System.out.printf("Max value: %d", maxValue);
    }
}
