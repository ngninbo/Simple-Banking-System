package banking.generator;

public class NumberGeneratorContext {

    private NumberGenerator numberGenerator;

    public NumberGeneratorContext setNumberGenerator(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
        return this;
    }

    public String next() {
        return numberGenerator.next();
    }
}
