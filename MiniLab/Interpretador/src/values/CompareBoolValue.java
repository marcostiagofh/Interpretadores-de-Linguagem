/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

import op.RelOp;

/**
 *
 * @author Marcos
 */
public class CompareBoolValue extends BoolValue{
    
    private Value<?> left;
    private Value<?> right;
    private RelOp op;

    public CompareBoolValue(Value<?> left, Value<?> right, RelOp op, int line) {
        super(line);
        
        this.left = left;
        this.right = right;
        this.op = op;
    }

    /**
     *
     * @return
     */
    @Override
    public Boolean value() {
        Value<?> v1 = (this.left instanceof Variable ? ((Variable)this.left).value() : this.left);
        Value<?> v2 = (this.right instanceof Variable ? ((Variable)this.right).value() : this.right);
                
        if (!(v1 instanceof IntValue && v2 instanceof IntValue)) {
            System.out.println(this.line()+" Tipos inválidos");
            System.exit(1);
            return null;
        }
        
        int i1 = ((IntValue) v1).value();
        int i2 = ((IntValue) v2).value();
        
        switch (op) {
            case EQUAL:
                return (i1 == i2);
            case DIFF:
                return (i1 != i2);
            case GREATER:
                return (i1 > i2);
            case GREATER_EQUAL:
                return (i1 >= i2);
            case LOWER:
                return (i1 < i2);
            case LOWER_EQUAL:
                return (i1 <= i2);
            default:
                // unreacheable
                throw new RuntimeException("Unrecheable");
        }

    }
}
