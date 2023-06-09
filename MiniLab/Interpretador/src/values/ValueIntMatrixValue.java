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
public class ValueIntMatrixValue extends IntMatrixValue{
    private Value<?> val1;
    private Value<?> val2;

    public ValueIntMatrixValue(Value<?> m, Value<?> val1, Value<?> val2, int line) {
        super(m, line);
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public Integer value() {
        Value<?> v1 = (this.val1 instanceof Variable ? ((Variable)this.val1).value() : this.val1);                   
        Value<?> v2 = (this.val2 instanceof Variable ? ((Variable)this.val2).value() : this.val2);
        Value<?> v3 = (this.m instanceof Variable ? ((Variable)this.m).value() : this.m);
        
        if (!(v1 instanceof IntValue && v2 instanceof IntValue)) {
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        if(!(v3 instanceof MatrixValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        
        int i1 = ((IntValue) v1).value();
        int i2 = ((IntValue) v2).value();
        Matrix mat = ((MatrixValue)v3).value();
        if(i1 > mat.rows() | i1 < 0 | i2 > mat.cols() | i2 < 0){
            System.out.println(this.line() +": Operação inválida");
            System.exit(1);
            return null;
        }
        return mat.value(i1, i2);
    }
}
