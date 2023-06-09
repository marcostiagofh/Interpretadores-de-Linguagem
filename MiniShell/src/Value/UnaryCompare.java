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
public class UnaryCompare extends BoolValue{
    private UnaryOp op;
    private StringValue value;

    public UnaryCompare(UnaryOp op, StringValue value, int line) {
        super(line);
        this.op = op;
        this.value = value;
    }
    
    public Boolean getValue(){
        return null;
    }
}
