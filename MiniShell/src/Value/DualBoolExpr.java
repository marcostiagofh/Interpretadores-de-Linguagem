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
public class DualBoolExpr extends BoolValue{
    private BoolOp op;
    private BoolValue left, right;

    public DualBoolExpr(BoolOp op, BoolValue left, BoolValue right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }
    
    public Boolean getValue(){
        return null;
    }
}
