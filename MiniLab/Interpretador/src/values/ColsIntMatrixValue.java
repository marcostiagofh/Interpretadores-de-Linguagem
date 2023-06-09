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
public class ColsIntMatrixValue extends IntMatrixValue {

    public ColsIntMatrixValue(Value<?> m, int line) {
        super(m,line);
    }

    @Override
    public Integer value() {
        Value<?> v = (this.m instanceof Variable ? ((Variable)this.m).value() : this.m);
        
        if(!(v instanceof MatrixValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        Matrix mat = ((MatrixValue)v).value();
        return mat.cols();
    }
    
    
}