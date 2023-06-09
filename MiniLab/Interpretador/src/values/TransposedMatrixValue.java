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
public class TransposedMatrixValue extends MatrixValue{
    private Value<?> m;

    public TransposedMatrixValue(Value<?> m, int line) {
        super(line);
        this.m = m;
    }
    
    @Override
    public Matrix value(){
        Value<?> v = (this.m instanceof Variable ? ((Variable)this.m).value() : this.m);
        
        if(!(v instanceof MatrixValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        MatrixValue mv = (MatrixValue) v;
        return mv.value().transposed();
    }
}