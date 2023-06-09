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
public class DualIntExpr extends IntValue{
    private IntOp op;
    private IntValue left, right;

    public DualIntExpr(IntOp op, IntValue left, IntValue right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }
    
    public Integer getValue(){
        return null;
    }
}
