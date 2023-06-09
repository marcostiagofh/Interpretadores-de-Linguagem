/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Value.StringValue;

/**
 *
 * @author marcos
 */
public class PrintCommand extends Command{
    private StringValue value;
    private boolean println;
    
    public PrintCommand(StringValue value, boolean println, int line){
        super(line);
        this.value = value;
        this.println = println;
    }
    
    @Override
    public void execute(){
     
    }
    
}

