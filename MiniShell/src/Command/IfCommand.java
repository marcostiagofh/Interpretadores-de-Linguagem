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
public class IfCommand extends Command{
    private BoolValue expr;
    private Command then;
    private Command else_cmd;
    
    public IfCommand(BoolValue expr, Command then, int line){
        super(line);
        this.expr = expr;
        this.then = then;
    }
    
    public IfCommand(BoolValue expr, Command then, Command else_cmd, int line){
        super(line);
        this.expr = expr;
        this.then = then;
        this.else_cmd = else_cmd;
    }
    
    @Override
    public void execute(){
        
    }
}
