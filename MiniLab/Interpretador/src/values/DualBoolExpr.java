/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;
import op.BoolOp;
/**
 *
 * @author Marcos
 */
public class DualBoolExpr extends BoolValue{

    private BoolValue left;    
    private BoolValue right;
    private BoolOp op;

    public DualBoolExpr(BoolValue left, BoolValue right, BoolOp op, int line) {
        super(line);
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Boolean value(){
        switch (this.op) {
            case And:
                return this.right.value() & this.left.value();
            case Or:
                return this.right.value() | this.left.value();
            default:
                // unreacheable
                throw new RuntimeException("Unrecheable");
        }
    }
    
}
