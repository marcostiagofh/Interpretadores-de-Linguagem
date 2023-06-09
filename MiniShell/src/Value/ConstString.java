/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Value;

/**
 *
 * @author marcos
 */
public class ConstString extends StringValue{
    private String value;

    public ConstString(String value, int line) {
        super(line);
        this.value = value;
    }

    public String getValue() {
        return null;
    }
    
}
