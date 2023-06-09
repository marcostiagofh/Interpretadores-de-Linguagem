/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Value.StringValue;
import Value.Value;
import Value.Variable;

/**
 *
 * @author marcos
 */
public class SetCommand extends Command{
    private Variable var;
    private Value<?> value;
    
    public SetCommand(Variable var, Value<?> value, int line){
        super(line);
        this.var = var;
        this.value = value;
    }
    
    public void execute(){
    
    }
}
