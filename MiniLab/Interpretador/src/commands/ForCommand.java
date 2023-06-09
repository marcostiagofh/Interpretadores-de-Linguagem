/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import matrix.Matrix;
import values.ConstIntValue;
import values.MatrixValue;
import values.Value;
import values.Variable;
/**
 *
 * @author Marcos
 */
public class ForCommand extends Command{
    private CommandBlock cmdblock;
    private Value<?> value;
    private Variable var;

    public ForCommand(Variable var, Value<?> value, CommandBlock cmdblock, int line) {
        super(line);
        
        this.cmdblock = cmdblock;
        this.value = value;
        this.var = var;
    }
    
    @Override
    public void execute(){
        Value<?> v = (this.value instanceof Variable? ((Variable)this.value).value() : this.value);

        if (v instanceof MatrixValue) {
            Matrix m = ((MatrixValue) v).value();
            
            for(int i = 0; i < m.rows(); i++){
                for(int j = 0; j < m.cols(); j++){
                    int x = m.value(i, j);

                    ConstIntValue civ = new ConstIntValue(x,-1);
                    this.var.setValue(civ);
                    
                    this.cmdblock.execute();
                }
            }
        } else {
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
        }
    }
}