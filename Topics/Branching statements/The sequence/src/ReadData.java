import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadData {

    public static void main(String[] args) {

        String pathToFile = "/Users/bdongmo-ngnintedem/IdeaProjects/Simple Banking System/dataset_91033.txt";

        File file = new File(pathToFile);
        int sum = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                int number = scanner.nextInt();
                sum += number;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file found: " + pathToFile);
        }
        System.out.println("Sum: " + sum);
    }
}
