/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

import java.util.Scanner;
import matrix.Matrix;
/**
 *
 * @author Marcos
 */
public class InputIntValue extends IntValue{
    
    private Value<?> string;
    private static Scanner kb;

    static {
        kb = new Scanner(System.in);
    }
    
    public InputIntValue(Value<?> string, int line){
        super(line);

        this.string = string;
    }

    @Override
    public Integer value() {
        if (this.string != null) {
            Value<?> v = (this.string instanceof Variable ? ((Variable)this.string).value() : this.string);
                    
            if(v instanceof IntValue){
                int n = ((IntValue) v).value();
                System.out.print(n);
            } else if(v instanceof StringValue){
                String s = ((StringValue) v).value();
                System.out.print(s);
            } else if(v instanceof MatrixValue){
                Matrix m = ((MatrixValue) v).value();
                m.show();
            } else {
                System.out.println(this.line()+": Tipos inválidos");
                System.exit(1);
                return null;
            }
        }

        return kb.nextInt();
    }    
}
