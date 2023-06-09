/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Value.StringValue;
import Value.Variable;

/**
 *
 * @author marcos
 */
public class ForCommand extends Command{
    private Variable var;
    private StringValue value;  
    private Command cmd;
    
    public ForCommand(Variable var, StringValue value, Command cmd, int line) {
        super(line);
        this.var = var;
        this.value = value;
        this.cmd = cmd;
    }
    
    public void execute(){
    
    }
    
}
