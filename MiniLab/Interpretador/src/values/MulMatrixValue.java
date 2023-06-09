/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

import matrix.Matrix;

/**
 *
 * @author Marcos
 */
public class MulMatrixValue extends MatrixValue{
    
    private Value<?> m;
    private Value<?> val;

    public MulMatrixValue(Value<?> m, Value<?> val, int line) {
        super(line);
        this.m = m;
        this.val = val;
    }
    
    @Override
    public Matrix value() {
        Value<?> v1 = (this.m instanceof Variable ? ((Variable)this.m).value() : this.m);
        Value<?> v2 = (this.val instanceof Variable? ((Variable)this.val).value() : this.val);
        
        if(!(v1 instanceof MatrixValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        
        MatrixValue mv = (MatrixValue) v1;
      
        if(v2 instanceof IntValue) {
            Matrix tmp = mv.value();
            Matrix res = tmp.mul(((IntValue) v2).value());
            return res;
        } else if (v2 instanceof MatrixValue) {
            Matrix tmp = mv.value();
            if(tmp.cols() != (((MatrixValue) v2).value()).rows()){
                System.out.println(this.line() +": Operação inválida");
                System.exit(1);
                return null;
            }
            Matrix res = tmp.mul(((MatrixValue) v2).value());
            return res;
        } else {
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
    }
}
