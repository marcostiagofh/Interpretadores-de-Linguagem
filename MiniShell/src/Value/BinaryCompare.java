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
public class BinaryCompare extends BoolValue{
    private BinaryOp op;
    private StringValue left, right;

    public BinaryCompare(BinaryOp op, StringValue left, StringValue right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public Boolean getValue(){
        return null;
    }
}
