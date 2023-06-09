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
public class RandMatrixValue extends MatrixValue{
    private Value<?> r;
    private Value<?> c;

    public RandMatrixValue(Value<?> r, Value<?> c, int line) {
        super(line);

        this.r = r;
        this.c = c;
    }

  
    @Override
    public Matrix value() {
        Value<?> v1 = (this.r instanceof Variable ? ((Variable)this.r).value() : this.r);
        Value<?> v2 = (this.c instanceof Variable ? ((Variable)this.c).value() : this.c);
        
        if(!(v1 instanceof IntValue && v2 instanceof IntValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        
        IntValue ivr = (IntValue) v1;
        IntValue ivc = (IntValue) v2;

        return Matrix.rand(ivr.value(), ivc.value());
    }
    
}
