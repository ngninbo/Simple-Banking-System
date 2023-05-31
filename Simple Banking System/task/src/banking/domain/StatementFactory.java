package banking.domain;

public class StatementFactory extends Formatter {

    public StatementFactory() {
        super(ResourceProperties.from("statements"));
    }
}
