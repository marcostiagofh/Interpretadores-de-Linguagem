/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

/**
 *
 * @author marcos
 */
public class ExitCommand extends Command{
    
    public ExitCommand(int line){
        super(line);
    }
    
    @Override
    public void execute(){
        //stop execution of commands
        System.exit(1);
    }
    
}
