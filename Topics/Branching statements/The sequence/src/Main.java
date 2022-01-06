import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int sequenceLength = 0;

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (sequenceLength > n - 1) {
                    break;
                }
                System.out.print(i + " ");
                sequenceLength += 1;
            }
        }
    }
}