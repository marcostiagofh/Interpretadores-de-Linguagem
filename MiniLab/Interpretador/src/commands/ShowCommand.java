/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
import matrix.Matrix;
import values.IntValue;
import values.RefMatrixValue;
import values.StringValue;
import values.Value;
import values.Variable;
/**
 *
 * @author Marcos
 */
public class ShowCommand extends Command{
    
    private Value<?> value;

    public ShowCommand(Value<?> value, int line) {
        super(line);
        
        this.value = value;
    }
    
    /**
     * 
     */
    @Override
    public void execute(){
        if (this.value == null)
            System.out.println();
        else {
            Value<?> v = (this.value instanceof Variable ? ((Variable)this.value).value() : this.value);
            
            if(v instanceof IntValue){
                int n = ((IntValue) v).value();
                System.out.println(n);
            } else if(v instanceof StringValue){
                String s = ((StringValue) v).value();
                System.out.println(s);
            } else if(v instanceof RefMatrixValue){
                Matrix m = ((RefMatrixValue) v).value();
                m.show();
            } else {
                System.out.println(this.line()+": Tipos inválidos");
                System.exit(1);
            }
        }
    }
}
