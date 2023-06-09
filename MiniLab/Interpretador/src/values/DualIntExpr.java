/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;
import op.IntOp;
/**
 *
 * @author Marcos
 */
public class DualIntExpr extends IntValue{
    private Value<?> left;    
    private Value<?> right;
    
    private IntOp op;

    public DualIntExpr(Value<?> left, Value<?> right, IntOp op, int line) {
        super(line);
        this.left = left;        
        this.right = right;
        this.op = op;
    }
    
    @Override
    public Integer value(){
        Value<?> v1 = (this.left instanceof Variable ? ((Variable)this.left).value() : this.left);
        Value<?> v2 = (this.right instanceof Variable ? ((Variable)this.right).value() : this.right);
        if(!(v1 instanceof IntValue && v2 instanceof IntValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        int i1 = ((IntValue) v1).value();
        int i2 = ((IntValue) v2).value();
        
        
        switch (this.op) {
            case Add:
                return i1 + i2;
            case Sub:
                return i1 - i2;
            case Mul:
                return i1 * i2;
            case Div:
                return i1 / i2;
            case Mod:
                return i1 % i2;
            default:
                // unreacheable
                throw new RuntimeException("Unrecheable");
        }
    }
}

