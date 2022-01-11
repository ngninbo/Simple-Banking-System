import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadData {

    public static final String PATH_TO_FILE = "/Users/bdongmo-ngnintedem/IdeaProjects/Simple Banking System/dataset_91033.txt";

    public static void main(String[] args) {

        File file = new File(PATH_TO_FILE);
        int sum = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                int number = scanner.nextInt();
                sum += number;
            }
        } catch (FileNotFoundException e) {
            System.out.printf("No such file found: %s", PATH_TO_FILE);
        }
        System.out.printf("Sum: %d", sum);
    }
}
