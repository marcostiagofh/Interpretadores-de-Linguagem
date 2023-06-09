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
public abstract class MatrixValue extends Value<Matrix>{

    public MatrixValue(int line) {
        super(line);
    }
    @Override
    public abstract Matrix value();
}
