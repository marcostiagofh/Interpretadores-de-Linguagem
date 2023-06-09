/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import java.util.List;

/**
 *
 * @author marcos
 */
public class CommandBlock extends Command{
    private List<Command> commands;
    
    public CommandBlock(int line){
        super(line);
        List<Command> commands = null;        
        this.commands = commands;
    }
    public void addCommand(Command c){
        this.commands.add(c);
    }

    @Override
    public void execute(){
        for (Command c : commands)
            c.execute();
    }
 
}
