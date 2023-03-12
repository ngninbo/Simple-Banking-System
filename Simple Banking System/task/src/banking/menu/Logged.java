package banking.menu;

public enum Logged {

    IN,
    OUT;

    public String toLowerCase() {
        return name().toLowerCase();
    }
}
