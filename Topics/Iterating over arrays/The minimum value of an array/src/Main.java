import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int limit = scanner.nextInt();
        int min = scanner.tokens()
                .map(Integer::parseInt)
                .limit(limit)
                .min(Integer::compare)
                .orElse(0);

        System.out.println(min);
    }
}