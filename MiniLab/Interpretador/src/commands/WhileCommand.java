/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
import values.BoolValue;
/**
 *
 * @author Marcos
 */
public class WhileCommand extends Command{
    private Command cmd;
    private BoolValue expr;

    public WhileCommand(BoolValue expr, Command cmd, int line) {
        super(line);
        this.cmd = cmd;
        this.expr = expr;
    }
    
    @Override
    public void execute(){
        while(expr.value()){
            this.cmd.execute();
        }
    }
}
