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
public class ConstStringValue extends StringValue{
    private String value;

    public ConstStringValue(String value, int line) {
        super(line);
        this.value = value;
    }
    
    @Override
    public String value() {
        return value;
    }
    
}
