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
public class FillMatrixValue extends MatrixValue{
    private Value<?> r;
    private Value<?> c;
    private Value<?> v;

    public FillMatrixValue(Value<?> r, Value<?> c, Value<?> v, int line) {
        super(line);
        this.r = r;
        this.c = c;
        this.v = v;
    } 

    @Override
    public Matrix value() {
        Value<?> v1 = (this.r instanceof Variable? ((Variable)this.r).value() : this.r);
        Value<?> v2 = (this.c instanceof Variable? ((Variable)this.c).value() : this.c);
        Value<?> v3 = (this.v instanceof Variable? ((Variable)this.v).value() : this.v);
        
        if(!(v1 instanceof IntValue && v2 instanceof IntValue && v3 instanceof IntValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        IntValue i3 = (IntValue) v3;        
        
        return Matrix.fill(i1.value(), i2.value(), i3.value());
    }
    
    
}
