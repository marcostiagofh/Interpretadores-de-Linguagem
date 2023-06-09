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
public abstract class StringValue extends Value<String>{

    public StringValue(int line) {
        super(line);
    }
    
    @Override
    public abstract String value();
}
