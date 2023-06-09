package Value;

public abstract class StringValue extends Value<String> {

    public StringValue(int line) {
        super(line);
    }

  public abstract String getValue();

}
