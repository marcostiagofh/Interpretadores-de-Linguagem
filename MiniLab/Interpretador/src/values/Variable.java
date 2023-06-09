/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

/**
 *
 * @author Marcos
 */
public class Variable extends Value<Value<?>>{
    private Value<?> value;
    private String name;

    public Variable(String name, int line) {
        super(line);
        this.name = name;        
    }

    public String getName(){
        return name;
    }
    
    public void setValue(Value<?> value) {
        this.value = value;
    }
    
    @Override
    public Value<?> value(){        
        return value;
    }
}
