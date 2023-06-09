package Value;

public abstract class Value<T> {
    private int line;
    
    public Value(int line){
        this.line = line;
    }
    
    private int line(){
        return this.line;
    }
    
    public abstract T getValue();

}
