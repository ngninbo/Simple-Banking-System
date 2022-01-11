import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CountGreaterNumber {

    public static final String PATH_TO_FILE = "/Users/bdongmo-ngnintedem/IdeaProjects/Simple Banking System/Topics/Branching statements/";
    public static final String FILE_NAME = "dataset_91022.txt";
    public static final String PATHNAME = PATH_TO_FILE + FILE_NAME;
    public static final int LIMIT_VALUE = 9999;

    public static void main(String[] args) {

        File file = new File(PATHNAME);
        int counter = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                int value = Integer.parseInt(scanner.next());
                if (value >= LIMIT_VALUE) {
                    counter += 1;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Not such file found: %s", PATHNAME);
        }

        System.out.printf("Number of value greater than or equal to %d: %d", LIMIT_VALUE, counter);
    }
}
