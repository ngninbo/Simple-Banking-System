package banking.menu;

import banking.domain.MessageFactory;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public abstract class Menu {

    protected abstract boolean process(MenuItem menuItem) throws IOException;

    protected MessageFactory messageFactory = new MessageFactory();

    protected abstract MenuItem getMenuItem(int choice);

    /**
     * Display menu and read user input from standard input
     * @return {@link Integer} number corresponding to chosen menu item
     */
    protected abstract int display();

    protected MenuItem item = MenuItem.UNKNOWN;

    /**
     * Show menu and process user input.
     * @throws IOException e.g. when reading/loading data failed
     */
    public void show() throws IOException {
        while (process(item)) {
            int choice = display();
            item = getMenuItem(choice);
        }
    }

    protected void exit() {
        printByeMessage();
    }

    /**
     * Display menu options and read user input using {@link Scanner}
     * @param items List of {@link MenuItem}
     * @return {@link Integer} number of selected menu item
     */
    protected int display(List<MenuItem> items) {
        // Print menu
        IntStream.range(0, items.size())
                .mapToObj(i -> format(items.get(i), i))
                .forEach(System.out::println);

        // Prompt user to select an option
        return new Scanner(System.in).nextInt();
    }

    /**
     * Say 'Bye' and end the program
     */
    protected void printByeMessage() {
        System.out.println(messageFactory.from("BYE_MSG"));
    }

    /**
     * Get menu item by given index in the given list of items
     * @param index {@link Integer}
     * @param items List of {@link MenuItem}
     * @return {@link MenuItem}
     */
    protected MenuItem getMenuItem(int index, List<MenuItem> items) {

        if (isLower(index, items.size())) {
            System.out.println(messageFactory.from("UNKNOWN_COMMAND_TXT"));
            return MenuItem.UNKNOWN;
        } else if (index == 0) {
            return MenuItem.EXIT;
        } else {
            return items.get(index - 1);
        }
    }

    private String format(MenuItem menuItem, int index) {
        return messageFactory.get(
                "item.display",
                String.valueOf(MenuItem.EXIT.equals(menuItem) ? 0 : index + 1),
                messageFactory.from(menuItem.getText()));
    }

    private boolean isLower(int value, int upper) {
        return value >= upper || value < 0;
    }

}
