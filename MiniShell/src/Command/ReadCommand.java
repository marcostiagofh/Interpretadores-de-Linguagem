/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Value.Variable;

/**
 *
 * @author marcos
 */
public class ReadCommand extends Command{
    private Variable var;
    
    public ReadCommand(Variable var, int line){
        super(line);
        this.var = var;
    }
    
    @Override
    public void execute(){
    
    }
    
}

