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
public class RefMatrixValue extends MatrixValue{
    
    private Matrix m;

    public RefMatrixValue(Matrix m, int line) {
        super(line);

        this.m = m;
    }
    
    @Override
    public Matrix value(){
        return this.m;
    }    
    
}
