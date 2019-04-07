public class Trait {
    String symbol;
    String dominant;
    String recessive;

    Trait(String symbol, String dominant, String recessive) {
        this.dominant = dominant;
        this.recessive = recessive;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Trait{" +
                "symbol='" + symbol + '\'' +
                ", dominant='" + dominant + '\'' +
                ", recessive='" + recessive + '\'' +
                '}';
    }
}
