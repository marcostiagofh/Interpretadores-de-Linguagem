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
public class SumMatrixValue extends MatrixValue{
    private Value m1;
    private Value m2;

    public SumMatrixValue(Value<?> m1, Value<?> m2, int line) {
        super(line);
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public Matrix value() {
        Value<?> v1 = (this.m1 instanceof Variable ? ((Variable)this.m1).value() : this.m1);
        Value<?> v2 = (this.m2 instanceof Variable ? ((Variable)this.m2).value() : this.m2);
        
        if(!(v1 instanceof MatrixValue && v2 instanceof MatrixValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        
        Matrix mv1 = ((MatrixValue) v1).value();
        Matrix mv2 = ((MatrixValue) v2).value();
        if(mv1.rows() != mv2.rows() | mv1.cols() != mv2.cols()){
            System.out.println(this.line() +": Operação inválida");
            System.exit(1);
            return null;
        }
        return mv1.sum(mv2);
    }
}
