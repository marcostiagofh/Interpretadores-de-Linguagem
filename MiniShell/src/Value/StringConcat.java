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
public class StringConcat extends StringValue{
    private StringValue left, right;

    public StringConcat(StringValue left, StringValue right, int line) {
        super(line);
        this.left = left;
        this.right = right;
    }
    
    public String getValue(){
        return null;
    }
}
