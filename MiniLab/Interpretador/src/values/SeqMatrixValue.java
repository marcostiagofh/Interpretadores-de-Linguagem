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
public class SeqMatrixValue extends MatrixValue{
    private final boolean inverted;
    private Value<?> from;
    private Value<?> to;


    public SeqMatrixValue(boolean inverted, Value<?> from, Value<?> to, int line) {
        super(line);
        this.inverted = inverted;
        this.from = from;
        this.to = to;
    }
   

    @Override
    public Matrix value() {
        Value<?> v1 = (this.from instanceof Variable ? ((Variable)this.from).value() : this.from);
        Value<?> v2 = (this.to instanceof Variable ? ((Variable)this.to).value() : this.to);
                
        if(!(v1 instanceof IntValue && v2 instanceof IntValue)){
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }
        IntValue ivf = (IntValue) v1;
        IntValue ivt = (IntValue) v2;        
        
        if(inverted){
            if(ivf.value()<ivt.value()){
                System.out.println(this.line()+": Operação inválida");
                System.exit(1);
                return null;
            }
            return Matrix.iseq(ivf.value(),ivt.value());
        }else{
            if(ivf.value()>ivt.value()){
                System.out.println(this.line()+": Operação inválida");
                System.exit(1);
                return null;
            }
            return Matrix.seq(ivf.value(),ivt.value());
        }
    }
    
}
